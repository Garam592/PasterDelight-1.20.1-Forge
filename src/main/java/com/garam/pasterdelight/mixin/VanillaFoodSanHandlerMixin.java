package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.tags.ModItemTags;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.event.VanillaFoodSanHandler;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FoodValueTooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * 若食物带有 pasterdelight_recover_sanity 标签，则改走帕斯特的新 san 恢复系统。
 * 这里抹去帕斯特重生(PasterDream)旧版 VanillaFoodSanHandler 对该食物原本的直接回 san 与其 san tooltip，
 * 保留其附加的 COOK 效果及 COOK tooltip。
 */
@Mixin(value = VanillaFoodSanHandler.class, remap = false)
public class VanillaFoodSanHandlerMixin
{
    @Unique
    private static boolean pasterdelight$stripEatenSan = false;

    @Unique
    private static boolean pasterdelight$stripTooltipSan = false;

    // ---- 进食时：只抹去直接回 san，保留 COOK 效果 ----

    @Inject(method = "onFoodEaten", at = @At("HEAD"), remap = false)
    private static void pasterdelight$captureEatenTagged(LivingEntityUseItemEvent.Finish event, CallbackInfo ci)
    {
        pasterdelight$stripEatenSan = event.getItem().is(ModItemTags.RECOVER_SANITY);
    }

    @Redirect(
            method = "onFoodEaten",
            at = @At(value = "INVOKE", target = "Lcom/pasterdream/pasterdreammod/capability/san/SanHelper;addPlayerSanAndSync(Lnet/minecraft/server/level/ServerPlayer;D)V"),
            remap = false)
    private static void pasterdelight$skipOriginalSan(ServerPlayer player, double san)
    {
        if (!pasterdelight$stripEatenSan)
        {
            SanHelper.addPlayerSanAndSync(player, san);
        }
    }

    // ---- 提示信息：只抹去 san tooltip，保留 COOK tooltip ----

    @Inject(method = "onItemTooltip", at = @At("HEAD"), remap = false)
    private static void pasterdelight$captureTooltipTagged(ItemTooltipEvent event, CallbackInfo ci)
    {
        pasterdelight$stripTooltipSan = event.getItemStack().is(ModItemTags.RECOVER_SANITY);
    }

    @Redirect(
            method = "onItemTooltip",
            at = @At(value = "INVOKE", target = "Lcom/pasterdream/pasterdreammod/helper/drinkandfoodproperties/FoodValueTooltips;appendSanTooltip(Ljava/util/List;D)V"),
            remap = false)
    private static void pasterdelight$skipOriginalSanTooltip(List<Component> tooltip, double san)
    {
        if (!pasterdelight$stripTooltipSan)
        {
            FoodValueTooltips.appendSanTooltip(tooltip, san);
        }
    }
}
