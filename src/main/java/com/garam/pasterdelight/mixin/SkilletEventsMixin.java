package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.world.block.pasterdelightskillet.ISkilletAttackSoundProvider;
import com.garam.pasterdelight.world.block.pasterdelightskillet.PasterDelightSkilletBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.item.SkilletItem;

/**
 * 农夫乐事的煎锅攻击音效（{@code SkilletItem.SkilletEvents#playSkilletAttackSound}）只对原版
 * {@code ModItems.SKILLET} 生效。这里分两条路放宽：
 * <ul>
 *     <li>{@code pasterdelight$isSkillet} 把判据放宽为「原版煎锅 / 帕斯特自定义煎锅」，仍走默认音效；</li>
 *     <li>{@code pasterdelight$playCustomSkilletAttackSound} 识别实现了
 *     {@link ISkilletAttackSoundProvider} 的第三方煎锅，按接口返回的音效播放。</li>
 * </ul>
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
                && skilletItem.getBlock() instanceof PasterDelightSkilletBlock; // 帕斯特自定义煎锅
    }

    @Inject(method = "playSkilletAttackSound", at = @At("HEAD"), cancellable = true, remap = false)
    private static void pasterdelight$playCustomSkilletAttackSound(LivingDamageEvent event, CallbackInfo ci)
    {
        DamageSource damageSource = event.getSource();
        Entity attacker = damageSource.getDirectEntity();
        if (!(attacker instanceof LivingEntity livingEntity)) return;

        ItemStack stack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if (!(stack.getItem() instanceof ISkilletAttackSoundProvider provider)) return; // 非第三方煎锅，交给原逻辑

        boolean strong;
        if (livingEntity instanceof Player player)
        {
            strong = player.getAttackStrengthScale(0.0F) > 0.8F;
        }
        else
        {
            strong = true;
        }

        SoundEvent sound = provider.getSkilletAttackSound(stack, livingEntity, strong);

        float pitch = 0.9F + (livingEntity.getRandom().nextFloat() * 0.2F);
        if (livingEntity instanceof Player player)
        {
            if (strong)
            {
                player.getCommandSenderWorld().playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS, 1.0F, pitch);
            }
            else
            {
                player.getCommandSenderWorld().playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS, 0.8F, 0.9F);
            }
        }
        else
        {
            livingEntity.getCommandSenderWorld().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), sound, SoundSource.PLAYERS, 1.0F, pitch);
        }

        ci.cancel();
    }
}
