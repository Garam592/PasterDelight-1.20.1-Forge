package com.garam.pasterdelight.world.item.knife;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

//TODO: 改用帕斯特重生新版本依赖后，该处使用帕斯特重生的新接口，而不是反射
public class MeltDreamKnifeItem extends RecipeMultiplyingKnifeItem{

    private static final Method ON_INVENTORY_TICK;
    private static final Method APPEND_HOVER_TEXT;

    private static final int MAGIC_HIT_COOLDOWN_TICKS = 40;

    static {
        try {
            Class<?> helper = Class.forName(
                    "com.pasterdream.pasterdreammod.world.item.meltdreamtool.MeltDreamToolHelper");
            ON_INVENTORY_TICK = helper.getDeclaredMethod(
                    "onInventoryTick", ItemStack.class, Level.class, Entity.class, int.class, boolean.class);
            ON_INVENTORY_TICK.setAccessible(true);
            APPEND_HOVER_TEXT = helper.getDeclaredMethod(
                    "appendHoverText", ItemStack.class, Level.class, List.class, TooltipFlag.class);
            APPEND_HOVER_TEXT.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to resolve MeltDreamToolHelper", e);
        }
    }

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
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        try {
            ON_INVENTORY_TICK.invoke(null, stack, level, entity, slot, selected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke MeltDreamToolHelper.onInventoryTick", e);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        try {
            APPEND_HOVER_TEXT.invoke(null, stack, level, tooltip, flag);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke MeltDreamToolHelper.appendHoverText", e);
        }
        tooltip.add(Component.translatable("tooltip.pasterdelight.melt_dream_knife.quick_cutting"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.melt_dream_knife.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.melt_dream_knife.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.melt_dream_knife.desc2"));

    }

    @Override
    public int getMultiplyRounds()
    {
        return 4;
    }
}
