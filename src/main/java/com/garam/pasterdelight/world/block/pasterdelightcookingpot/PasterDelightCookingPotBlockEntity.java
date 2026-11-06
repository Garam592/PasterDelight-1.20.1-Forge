package com.garam.pasterdelight.world.block.pasterdelightcookingpot;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

public class PasterDelightCookingPotBlockEntity extends CookingPotBlockEntity
{
    public boolean isInputSlotLimitStackSize = false;

    public PasterDelightCookingPotBlockEntity(BlockEntityType blockEntityType, BlockPos blockPosition, BlockState blockState)
    {
        super(prepare(blockPosition, blockEntityType), blockState);
    }

    private static BlockPos prepare(BlockPos blockPosition, BlockEntityType<?> blockEntityType)
    {
        CookingPotTypeHelper.setPendingType(blockEntityType);
        return blockPosition;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction)
    {
        if (capability == ForgeCapabilities.ITEM_HANDLER && (direction == null || direction.equals(Direction.UP)))
        {
            LazyOptional<IItemHandler> original = super.getCapability(capability, direction).cast();
            LazyOptional<IItemHandler> wrapped = original.lazyMap(handler -> new DynamicStackSizeLimitWrapper(handler, this));

            return wrapped.cast();
        }
        return super.getCapability(capability, direction);
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        this.isInputSlotLimitStackSize = compoundTag.getBoolean("isInputSlotLimitStackSize");
    }

    @Override
    public void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("isInputSlotLimitStackSize", this.isInputSlotLimitStackSize);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = super.getUpdateTag();
        tag.putBoolean("isInputSlotLimitStackSize", this.isInputSlotLimitStackSize);
        return tag;
    }

    public boolean getIsInputSlotLimitStackSize()
    {
        return this.isInputSlotLimitStackSize;
    }

    public void setIsInputSlotLimitStackSize(boolean isLimit)
    {
        this.isInputSlotLimitStackSize = isLimit;
    }

    public ContainerData getCookingPotData()
    {
        return this.cookingPotData;
    }
}
