package com.garam.pasterdelight.world.block.pasterdreamcookingpot;

import com.garam.pasterdelight.init.ModBlockEntities;
import com.garam.pasterdelight.mixin.CookingPotBlockEntityMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

public class PasterDreamCookingPotBlockEntity extends CookingPotBlockEntity
{
    public boolean isInputSlotLimitStackSize = false;

    public PasterDreamCookingPotBlockEntity(BlockEntityType blockEntityType, BlockPos blockPosition, BlockState blockState)
    {
        super(prepare(blockPosition, blockEntityType), blockState);
    }

    private static BlockPos prepare(BlockPos blockPosition, BlockEntityType<?> blockEntityType)
    {
        CookingPotTypeHelper.setPendingType(blockEntityType);
        return blockPosition;
    }

    public boolean isInputSlotLimitSetAmount()
    {
        return isInputSlotLimitStackSize;
    }

    public void setInputSlotLimitSetAmount(boolean isLimit)
    {
        this.isInputSlotLimitStackSize = isLimit;
        this.setChanged();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction)
    {
        if (capability == ForgeCapabilities.ITEM_HANDLER && direction != null && !direction.equals(Direction.UP))
        {
            LazyOptional<IItemHandler> original = super.getCapability(capability, direction).cast();
            LazyOptional<IItemHandler> wrapped = original.lazyMap(handler ->
            {
                if (handler instanceof IItemHandlerModifiable modifiable)
                {
                    return new DynamicStackSizeLimitWrapper(modifiable, this);
                }
                return handler;
            });

            return wrapped.cast();
        }
        return super.getCapability(capability, direction);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity)
    {
        return new PasterDreamCookingPotMenu(id, player, this, this.cookingPotData);
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
}
