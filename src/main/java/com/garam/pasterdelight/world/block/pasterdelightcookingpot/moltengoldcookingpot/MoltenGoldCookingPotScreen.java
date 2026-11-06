package com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot;

import com.garam.pasterdelight.component.IsInputSlotLimitStackSizeChangeButton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import vectorwing.farmersdelight.client.gui.CookingPotScreen;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMenu;

public class MoltenGoldCookingPotScreen extends CookingPotScreen
{
    public MoltenGoldCookingPotScreen(CookingPotMenu screenContainer, Inventory inv, Component titleIn)
    {
        super(screenContainer, inv, titleIn);
    }

    public void init()
    {
        super.init();

        IsInputSlotLimitStackSizeChangeButton isInputSlotLimitStackSizeChangeButton = new IsInputSlotLimitStackSizeChangeButton(leftPos + 5, topPos + 5, menu.blockEntity);
        addRenderableWidget(isInputSlotLimitStackSizeChangeButton);
    }
}
