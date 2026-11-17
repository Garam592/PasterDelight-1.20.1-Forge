package com.garam.pasterdelight.init;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModItemRenderer
{
    public static void FMLClientSetupEventRegister(FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            ItemProperties.register(ModItems.MOLTEN_GOLD_SKILLET.get(), ResourceLocation.parse("cooking"), (stack, level, entity, seed) -> stack.getTagElement("Cooking") != null ? 1.0F : 0.0F);
        });
    }
}
