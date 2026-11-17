package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.init.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.SkilletItem;

@Mixin(value = SkilletItem.class, remap = false)
public class SkilletItemMixin
{
    @Inject(method = "isPlayerNearHeatSource", at = @At("HEAD"), cancellable = true, remap = false)
    private static void pasterdelight$moltenGoldSkilletAlwaysHeated(Player player, LevelReader level, CallbackInfoReturnable<Boolean> cir)
    {
        if (player.getMainHandItem().is(ModItems.MOLTEN_GOLD_SKILLET.get()) || player.getOffhandItem().is(ModItems.MOLTEN_GOLD_SKILLET.get()))
        {
            cir.setReturnValue(true);
        }
    }
}
