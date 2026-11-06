package com.garam.pasterdelight.world.block.pasterdreamcookingpot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class DynamicStackSizeLimitWrapper implements IItemHandlerModifiable
{
    private final IItemHandlerModifiable delegate;
    private final PasterDreamCookingPotBlockEntity pasterDreamCookingPotBlockEntity;

    public DynamicStackSizeLimitWrapper(IItemHandlerModifiable delegate, PasterDreamCookingPotBlockEntity pasterDreamCookingPotBlockEntity)
    {
        this.delegate = delegate;
        this.pasterDreamCookingPotBlockEntity = pasterDreamCookingPotBlockEntity;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        if (slot >= 0 && slot < 6 && pasterDreamCookingPotBlockEntity.isInputSlotLimitStackSize)
        {
            return 1;
        }
        return delegate.getSlotLimit(slot);
    }

    @Override
    public int getSlots()
    {
        return delegate.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return delegate.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        return delegate.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return delegate.extractItem(slot, amount, simulate);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        delegate.setStackInSlot(slot, stack);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        return delegate.isItemValid(slot, stack);
    }
}
