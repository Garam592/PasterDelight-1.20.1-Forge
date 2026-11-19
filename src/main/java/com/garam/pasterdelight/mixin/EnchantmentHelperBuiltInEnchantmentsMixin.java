package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.world.item.pasterdelightknife.knive.FluffyWindAlloyKnifeItem;
import com.garam.pasterdelight.world.item.pasterdelightknife.knive.ShadowAlloyKnifeBackstabbing;
import com.garam.pasterdelight.world.item.pasterdelightknife.knive.ShadowAlloyKnifeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.enchantment.BackstabbingEnchantment;

/**
 * 刀类「内置附魔等级」被动。
 * <ul>
 *   <li>暗影合金刀：背刺等级 +2（战技期间通过 {@link ShadowAlloyKnifeBackstabbing} 屏蔽，避免 FD 背刺事件二次触发）。</li>
 *   <li>萦风合金刀：抢夺等级 +2。</li>
 * </ul>
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperBuiltInEnchantmentsMixin {

    /** 萦风合金刀内置抢夺等级（抢夺II） */
    @Unique
    private static final int FLUFFY_WIND_LOOTING_BUILT_IN_LEVEL = 2;

    @Inject(method = "getItemEnchantmentLevel", at = @At("RETURN"), cancellable = true)
    private static void pasterdelight$addBuiltInEnchantmentLevel(Enchantment enchantment, ItemStack stack,
                                                                 CallbackInfoReturnable<Integer> cir) {
        Item item = stack.getItem();
        // 暗影合金刀自带背刺II
        if (enchantment instanceof BackstabbingEnchantment && item instanceof ShadowAlloyKnifeItem) {
            if (ShadowAlloyKnifeBackstabbing.isSuppressed()) {
                cir.setReturnValue(0);
            } else {
                cir.setReturnValue(cir.getReturnValue() + ShadowAlloyKnifeBackstabbing.BUILT_IN_LEVEL);
            }
        } else if (enchantment == Enchantments.MOB_LOOTING && item instanceof FluffyWindAlloyKnifeItem) {
            cir.setReturnValue(cir.getReturnValue() + FLUFFY_WIND_LOOTING_BUILT_IN_LEVEL);
        }
    }
}
