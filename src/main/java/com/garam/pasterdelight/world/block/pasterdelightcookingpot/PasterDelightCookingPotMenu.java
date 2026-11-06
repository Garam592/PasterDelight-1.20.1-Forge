package com.garam.pasterdelight.world.block.pasterdelightcookingpot;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMenu;

public class PasterDelightCookingPotMenu extends CookingPotMenu
{
    public PasterDelightCookingPotMenu(int windowId, Inventory playerInventory, CookingPotBlockEntity blockEntity, ContainerData cookingPotData)
    {
        super(windowId, playerInventory, blockEntity, cookingPotData);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return player.level().getBlockEntity(blockEntity.getBlockPos()) == blockEntity && player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.5, blockEntity.getBlockPos().getZ() + 0.5) <= 64;
    }
}
