package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.world.block.pasterdelightskillet.PasterDelightSkilletBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.item.SkilletItem;

/**
 * 农夫乐事的煎锅攻击音效（{@code SkilletItem.SkilletEvents#playSkilletAttackSound}）只对原版
 * {@code ModItems.SKILLET} 生效。这里把判据放宽为「原版煎锅 或 帕斯特自定义煎锅」。
 */
@Mixin(SkilletItem.SkilletEvents.class)
public abstract class SkilletEventsMixin
{
    @WrapOperation(method = "playSkilletAttackSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean pasterdelight$isSkillet(ItemStack stack, Item item, Operation<Boolean> original)
    {
        if (original.call(stack, item))
        {
            return true; // 原版煎锅
        }
        return stack.getItem() instanceof SkilletItem skilletItem
                && skilletItem.getBlock() instanceof PasterDelightSkilletBlock;
    }
}
