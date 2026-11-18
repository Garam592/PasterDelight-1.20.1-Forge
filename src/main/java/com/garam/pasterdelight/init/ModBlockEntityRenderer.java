package com.garam.pasterdelight.init;

import com.garam.pasterdelight.world.block.pasterdelightskillet.PasterDelightSkilletBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModBlockEntityRenderer
{
    public static void FMLClientSetupEventRegister(FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            BlockEntityRenderers.register(ModBlockEntities.MOLTEN_GOLD_SKILLET.get(), PasterDelightSkilletBlockEntityRenderer::new);
        });
    }
}
