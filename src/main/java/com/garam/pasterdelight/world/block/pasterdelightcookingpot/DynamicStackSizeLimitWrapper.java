package com.garam.pasterdelight.world.block.pasterdelightcookingpot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class DynamicStackSizeLimitWrapper implements IItemHandler
{
    private final IItemHandler delegate;
    private final PasterDelightCookingPotBlockEntity pasterDreamCookingPot;

    public DynamicStackSizeLimitWrapper(IItemHandler delegate, PasterDelightCookingPotBlockEntity pasterDreamCookingPot)
    {
        this.delegate = delegate;
        this.pasterDreamCookingPot = pasterDreamCookingPot;
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
        int limit = getSlotLimit(slot);
        if (stack.isEmpty() || limit <= 0)
        {
            return stack;
        }

        ItemStack existing = delegate.getStackInSlot(slot);
        int currentCount;
        if (existing.isEmpty())
        {
            currentCount = 0;
        }
        else
            if (ItemStack.isSameItemSameTags(existing, stack))
            {
                currentCount = existing.getCount();
            }
                else
                {
                    return stack;
                }

        int space = limit - currentCount;
        if (space <= 0)
        {
            return stack;
        }

        if (stack.getCount() <= space)
        {
            return delegate.insertItem(slot, stack, simulate);
        }

        ItemStack toInsert = stack.copy();
        toInsert.setCount(space);
        ItemStack remainder = delegate.insertItem(slot, toInsert, simulate);
        int inserted = space - remainder.getCount();

        ItemStack result = stack.copy();
        result.setCount(stack.getCount() - inserted);
        return result;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return delegate.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot)
    {
        if (slot >= 0 && slot < 6 && pasterDreamCookingPot.isInputSlotLimitStackSize)
        {
            return 1;
        }
        return delegate.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        return delegate.isItemValid(slot, stack);
    }
}
