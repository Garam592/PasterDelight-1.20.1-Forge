package com.garam.pasterdelight.world.block.pasterdelightcookingpot;

import com.garam.pasterdelight.helper.BlockEntityTypeHelper;
import com.garam.pasterdelight.helper.GetBottomBlockCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
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
        BlockEntityTypeHelper.setPendingType(blockEntityType);
        return blockPosition;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction)
    {
        if (capability == ForgeCapabilities.ITEM_HANDLER && (direction == null || direction.equals(Direction.UP) || direction.equals(Direction.DOWN)))
        {
            LazyOptional<IItemHandler> original = super.getCapability(capability, direction).cast();
            LazyOptional<IItemHandler> wrapped = original.lazyMap(handler -> new DynamicStackSizeLimitWrapper(handler, this));

            return wrapped.cast();
        }
        return super.getCapability(capability, direction);
    }

    public void tick()
    {
        if (level != null && !level.isClientSide)
        {
            ItemStackHandler inventory = getInventory();
            ItemStack output = inventory.getStackInSlot(OUTPUT_SLOT);
            if (!output.isEmpty())
            {
                IItemHandler targetInventory = GetBottomBlockCapability.getTargetInventory(level, getBlockPos());
                if (targetInventory != null)
                {
                    ItemStack simulatedRemainder = ItemHandlerHelper.insertItemStacked(targetInventory, output.copy(), true);
                    if(simulatedRemainder.getCount() < output.getCount())
                    {
                        ItemStack extracted = inventory.extractItem(OUTPUT_SLOT, output.getCount() - simulatedRemainder.getCount(), false);
                        if (!extracted.isEmpty())
                        {
                            ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, extracted, false);
                            if (!remainder.isEmpty())
                            {
                                Block.popResource(level, getBlockPos().above(), remainder);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        this.isInputSlotLimitStackSize = compoundTag.getBoolean("isInputSlotLimitStackSize");
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.putBoolean("isInputSlotLimitStackSize", this.isInputSlotLimitStackSize);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag compoundTag = super.getUpdateTag();
        compoundTag.putBoolean("isInputSlotLimitStackSize", this.isInputSlotLimitStackSize);
        return compoundTag;
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
