package com.garam.pasterdelight.world.block.pasterdelightskillet;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class PasterDelightSkilletItemHandler implements IItemHandler
{
    private final PasterDelightSkilletBlockEntity blockEntity;

    public PasterDelightSkilletItemHandler(PasterDelightSkilletBlockEntity blockEntity)
    {
        this.blockEntity = blockEntity;
    }

    @Override
    public int getSlots()
    {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        if (slotIndex == 0)
        {
            return blockEntity.getStoredStack();
        }
        else
            if (slotIndex == 1)
            {
                return blockEntity.getOutputStack();
            }
                else
                {
                    return ItemStack.EMPTY;
                }
    }

    @Override
    public ItemStack insertItem(int slotIndex, @NotNull ItemStack itemStack, boolean simulate)
    {
        if(slotIndex == 0)
        {
            return blockEntity.insertCookingStack(itemStack, simulate);
        }
            else
            {
                return itemStack;
            }
    }

    @Override
    public ItemStack extractItem(int slotIndex, int count, boolean simulate)
    {
        if (slotIndex == 1)
        {
            return blockEntity.extractOutput(count, simulate);
        }
            else
            {
                return ItemStack.EMPTY;
            }
    }

    @Override
    public int getSlotLimit(int slotIndex)
    {
        return slotIndex == 0 || slotIndex == 1 ? 64 : 0;
    }

    @Override
    public boolean isItemValid(int slotIndex, @NotNull ItemStack itemStack)
    {
        return slotIndex == 0 && blockEntity.hasCookingRecipe(itemStack);
    }
}
