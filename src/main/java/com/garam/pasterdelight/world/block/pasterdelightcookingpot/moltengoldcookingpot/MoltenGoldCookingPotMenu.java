package com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot;

import com.garam.pasterdelight.init.ModMenus;
import com.garam.pasterdelight.world.block.pasterdelightcookingpot.PasterDelightCookingPotMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

public class MoltenGoldCookingPotMenu extends PasterDelightCookingPotMenu
{
    public MoltenGoldCookingPotMenu(int windowId, Inventory playerInventory, CookingPotBlockEntity blockEntity, ContainerData cookingPotData)
    {
        super(windowId, playerInventory, blockEntity, cookingPotData);
    }

    @Override
    public MenuType<?> getType()
    {
        return ModMenus.MOLTEN_GOLD_COOKING_POT.get();
    }
}
