package com.garam.pasterdelight.world.item.pasterdelightknife.knive;

import com.garam.pasterdelight.world.item.pasterdelightknife.RecipeMultiplyingKnifeItem;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.world.item.fluffywindalloy.FluffyWindAlloyTool;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.List;

public class FluffyWindAlloyKnifeItem extends RecipeMultiplyingKnifeItem implements FluffyWindAlloyTool{

    private static final int TRIPLE_HIT_COOLDOWN_TICKS = 60;

    public FluffyWindAlloyKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (attacker instanceof Player player
                && !player.level().isClientSide
                && !player.getCooldowns().isOnCooldown(this)) {
            SkillCooldownHelper.applySharedCooldown(player, TRIPLE_HIT_COOLDOWN_TICKS);

            float skillDamageMultiplier = SkillCooldownHelper.getSkillDamageMultiplier(player);
            float attackDamage = (float) ((player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                    + EnchantmentHelper.getDamageBonus(stack, target.getMobType()))
                    * skillDamageMultiplier);
            for (int i = 0; i < 2; i++) {
                target.invulnerableTime = 0;
                target.hurt(target.damageSources().playerAttack(player), attackDamage);
            }
        }
        return result;
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.quick_cutting"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive1"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive2"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive3"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive4"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.fluffy_wind_alloy_knife.custom_tip"));

    }

    @Override
    public int getMultiplyRounds()
    {
        return 16;
    }
}
