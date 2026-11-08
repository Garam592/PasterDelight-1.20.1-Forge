package com.garam.pasterdelight.compat.jei;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.integration.jei.FDRecipeTypes;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin
{
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModItems.MOLTEN_GOLD_COOKING_POT.get()), FDRecipeTypes.COOKING);
    }

    @Override
    public ResourceLocation getPluginUid()
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDelight.MOD_ID, "jei_plugin");
    }
}
