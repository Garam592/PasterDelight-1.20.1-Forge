package com.garam.pasterdelight.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vectorwing.farmersdelight.client.renderer.SkilletRenderer;

import java.util.Random;

@Mixin(value = SkilletRenderer.class, remap = false)
public interface SkilletRendererAccessor
{
    @Accessor("random")
    Random randomAccessor();
}
