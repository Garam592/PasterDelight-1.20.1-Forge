package com.garam.pasterdelight.init;

import com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot.MoltenGoldCookingPotScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModScreens
{
    public static void register(FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            MenuScreens.register(ModMenus.MOLTEN_GOLD_COOKING_POT.get(), MoltenGoldCookingPotScreen::new);
        });
    }
}
