package com.garam.pasterdelight.world.item.knife;

import com.pasterdream.pasterdreammod.world.item.fluffywindalloy.FluffyWindAlloyTool;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;

public class FluffyWindAlloyKnifeItem extends KnifeItem implements FluffyWindAlloyTool, RecipeMultiplyingKnife {

    private static final int TRIPLE_HIT_COOLDOWN_TICKS = 60;
    private static final String MULTI_CUT_TAG = "pasterdelight_fluffy_wind_multi_cut";

    public FluffyWindAlloyKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    public static boolean isMultiCutEnabled(ItemStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().getBoolean(MULTI_CUT_TAG);
    }

    public static void toggleMultiCut(ItemStack stack) {
        stack.getOrCreateTag().putBoolean(MULTI_CUT_TAG, !isMultiCutEnabled(stack));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (attacker instanceof Player player
                && !player.level().isClientSide
                && !player.getCooldowns().isOnCooldown(this)) {
            player.getCooldowns().addCooldown(this, TRIPLE_HIT_COOLDOWN_TICKS);

            for (int i = 0; i < 2; i++) {
                target.invulnerableTime = 0;
                target.hurt(target.damageSources().playerAttack(player),
                        (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
            }
        }
        return result;
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.passive1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.passive2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.passive3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.passive4"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_knife.custom_tip"));

    }

    public static class LootingHandler {
        @SubscribeEvent
        public static void onLootingLevel(LootingLevelEvent event) {
            DamageSource source = event.getDamageSource();
            if (source != null
                    && source.getEntity() instanceof Player player
                    && player.getMainHandItem().getItem() instanceof FluffyWindAlloyKnifeItem) {
                event.setLootingLevel(event.getLootingLevel() + 2);
            }
        }
    }

    public static class CuttingBoardToggleHandler {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
            if (!event.getEntity().isShiftKeyDown()) return;
            ItemStack held = event.getEntity().getMainHandItem();
            if (!(held.getItem() instanceof FluffyWindAlloyKnifeItem)) return;
            if ((event.getLevel().getBlockEntity(event.getPos()) instanceof CuttingBoardBlockEntity)) return;

            if (!event.getLevel().isClientSide) {
                toggleMultiCut(held);
                boolean enabled = isMultiCutEnabled(held);
                event.getEntity().displayClientMessage(Component.translatable(
                        enabled ? "tooltip.pasterdelight.fluffy_wind_alloy_knife.quick_cutting_enabled":
                                "tooltip.pasterdelight.fluffy_wind_alloy_knife.quick_cutting_disabled"), true);
            }
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    @Override
    public int getMultiplyRounds()
    {
        return 4;
    }
}
