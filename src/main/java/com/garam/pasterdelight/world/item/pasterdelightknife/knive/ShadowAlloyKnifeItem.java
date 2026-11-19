package com.garam.pasterdelight.world.item.pasterdelightknife.knive;

import com.garam.pasterdelight.world.item.pasterdelightknife.RecipeMultiplyingKnifeItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
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
import net.minecraftforge.common.ForgeMod;
import vectorwing.farmersdelight.common.item.enchantment.BackstabbingEnchantment;
import vectorwing.farmersdelight.common.registry.ModEnchantments;

import java.util.List;
import java.util.UUID;

public class ShadowAlloyKnifeItem extends RecipeMultiplyingKnifeItem implements ShadowAlloyTool
{
    //战技冷却：2秒
    private static final int SKILL_COOLDOWN_TICKS = 40;

    private static final UUID SAN_VARIABILITY_UUID = UUID.fromString("daf6c47c-454a-48e4-a05d-4d7fb0deb673");
    private static final UUID BLOCK_REACH_UUID = UUID.fromString("4828780d-fdf1-4ed4-b6ce-71925026f828");

    public ShadowAlloyKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot, stack);
        }
        return ImmutableMultimap.<Attribute, AttributeModifier>builder()
                .putAll(super.getAttributeModifiers(slot, stack))
                .put(
                        ModAttributes.SAN_VARIABILITY.get(),
                        new AttributeModifier(
                                SAN_VARIABILITY_UUID,
                                "pasterdream.shadow_alloy.san_variability",
                                -1.2d,
                                AttributeModifier.Operation.ADDITION))
                .put(
                        ForgeMod.BLOCK_REACH.get(),
                        new AttributeModifier(
                                BLOCK_REACH_UUID,
                                "pasterdream.shadow_alloy.block_reach",
                                1.0d,
                                AttributeModifier.Operation.ADDITION))
                .build();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (attacker instanceof Player player
                && !player.level().isClientSide
                && !player.getCooldowns().isOnCooldown(this)) {
            SkillCooldownHelper.applySharedCooldown(player, SKILL_COOLDOWN_TICKS);

            float attackDamage = (float) ((player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                    + EnchantmentHelper.getDamageBonus(stack, target.getMobType()))
                    * SkillCooldownHelper.getSkillDamageMultiplier(player));

            // 背刺等级已含内置背刺II（mixin 注入 +2）
            int backstabbingLevel = EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.BACKSTABBING.get(), stack);

            float backstabDamage = BackstabbingEnchantment.getBackstabbingDamagePerLevel(
                    attackDamage, backstabbingLevel);

            target.invulnerableTime = 0;
            // 战技期间屏蔽内置背刺，避免 FD 背刺事件对技能伤害二次触发
            ShadowAlloyKnifeBackstabbing.suppress(true);
            try {
                target.hurt(player.damageSources().playerAttack(player), backstabDamage);
            } finally {
                ShadowAlloyKnifeBackstabbing.suppress(false);
            }
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdelight.shadow_alloy_knife.quick_cutting"));
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
