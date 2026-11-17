package com.garam.pasterdelight.world.item.pasterdelightknife.knive;

import com.garam.pasterdelight.world.item.pasterdelightknife.RecipeMultiplyingKnifeItem;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.MeltDreamTool;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MeltDreamKnifeItem extends RecipeMultiplyingKnifeItem implements MeltDreamTool {

    private static final int MAGIC_HIT_COOLDOWN_TICKS = 40;

    public MeltDreamKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (attacker instanceof Player player
                && !player.level().isClientSide
                && !player.getCooldowns().isOnCooldown(this)) {
            player.getCooldowns().addCooldown(this, MAGIC_HIT_COOLDOWN_TICKS);

            float magicDamage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5);

            target.invulnerableTime = 0;
            target.hurt(player.damageSources().indirectMagic(player, player), magicDamage);
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdelight.melt_dream_knife.quick_cutting"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.melt_dream_knife.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.melt_dream_knife.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.melt_dream_knife.desc2"));
    }

    @Override
    public int getMultiplyRounds() {
        return 4;
    }
}
