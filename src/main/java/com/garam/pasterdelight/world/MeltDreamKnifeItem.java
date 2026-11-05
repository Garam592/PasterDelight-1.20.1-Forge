package com.garam.pasterdelight.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

//TODO: 待解决访问问题后，修改模组版本依赖，不再使用反射方法
public class MeltDreamKnifeItem extends KnifeItem {

    private static final Method ON_INVENTORY_TICK;
    private static final Method APPEND_HOVER_TEXT;

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
    }

}
