package com.garam.pasterdelight.world.item.pasterdelightknife.knive;

import com.garam.pasterdelight.world.item.pasterdelightknife.RecipeMultiplyingKnifeItem;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.world.item.shadowalloytool.ShadowAlloyTool;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.enchantment.BackstabbingEnchantment;
import vectorwing.farmersdelight.common.registry.ModEnchantments;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ShadowAlloyKnifeItem extends RecipeMultiplyingKnifeItem implements ShadowAlloyTool
{
    //战技冷却：2秒
    private static final int SKILL_COOLDOWN_TICKS = 40;

    //暗影合金被动：理智浮动（withSanVariability 是包级私有方法，只能反射调用）
    private static final Method WITH_SAN_VARIABILITY;

    static {
        try {
            Class<?> helper = Class.forName(
                    "com.pasterdream.pasterdreammod.world.item.shadowalloytool.ShadowAlloyToolHelper");
            WITH_SAN_VARIABILITY = helper.getDeclaredMethod(
                    "withSanVariability", EquipmentSlot.class, Multimap.class);
            WITH_SAN_VARIABILITY.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to resolve ShadowAlloyToolHelper", e);
        }
    }

    public ShadowAlloyKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    //暗影合金被动：+1方块交互范围、理智浮动
    @Override
    @SuppressWarnings("unchecked")
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        try {
            return (Multimap<Attribute, AttributeModifier>) WITH_SAN_VARIABILITY.invoke(
                    null, slot, super.getAttributeModifiers(slot, stack));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke ShadowAlloyToolHelper.withSanVariability", e);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (attacker instanceof Player player
                && !player.level().isClientSide
                && !player.getCooldowns().isOnCooldown(this)) {
            player.getCooldowns().addCooldown(this, SKILL_COOLDOWN_TICKS);

            float attackDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);

            int enchantLevel = EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.BACKSTABBING.get(), stack);
            int effectiveLevel = 1 + enchantLevel;
            boolean behind = BackstabbingEnchantment.isLookingBehindTarget(target, player.position());

            target.invulnerableTime = 0;

            if (enchantLevel > 0 && behind) {
                target.hurt(player.damageSources().playerAttack(player), attackDamage);
            } else {
                float backstabDamage = BackstabbingEnchantment.getBackstabbingDamagePerLevel(
                        attackDamage, effectiveLevel);
                target.hurt(player.damageSources().playerAttack(player), backstabDamage);
                target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                        SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdelight.shadow_alloy_knife.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.shadow_alloy_knife.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.shadow_alloy_knife.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.shadow_alloy_tool.backstabbing"));
    }

    @Override
    public int getMultiplyRounds()
    {
        return 32;
    }
}
