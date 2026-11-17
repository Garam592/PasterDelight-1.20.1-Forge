package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.world.item.pasterdelightknife.knive.FluffyWindAlloyKnifeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * 萦风合金刀自带一套自定义被动描述（tooltip.pasterdelight.fluffy_wind_alloy_knife.passive_*），
 * 该mixin把帕斯特注入的那几行按翻译 key 精确删除。只影响萦风合金刀，不影响帕斯特自家的萦风合金工具。
 */
@Mixin(ItemStack.class)
public abstract class ItemStackFluffyWindAlloyTooltipMixin {

    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    private void pasterdelight$removeStandardFluffyWindAlloyPassive(Player player, TooltipFlag flag,
                                                                    CallbackInfoReturnable<List<Component>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (!(stack.getItem() instanceof FluffyWindAlloyKnifeItem)) {
            return;
        }
        cir.getReturnValue().removeIf(c ->
                c.getContents() instanceof TranslatableContents tc
                        && tc.getKey().startsWith("tooltip.pasterdream.fluffy_wind_alloy_tool.passive"));
    }
}
