package com.garam.pasterdelight.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.Set;

public class PasterKnifeItem extends DiggerItem {

    // 小刀专属的方块标签
    public static final TagKey<Block> MINEABLE_WITH_KNIFE =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("farmersdelight", "mineable/knife"));

    public static final ToolAction KNIFE_DIG = ToolAction.get("knife_dig");
    public static final ToolAction KNIFE_HARVEST = ToolAction.get("knife_harvest");
    public static final Set<ToolAction> KNIFE_ACTIONS =
            Set.of(ToolActions.SHEARS_CARVE, ToolActions.SWORD_DIG, KNIFE_DIG, KNIFE_HARVEST);

    public PasterKnifeItem(Tier tier, float attackDamageBonus, float attackSpeed, Item.Properties properties) {
        super(attackDamageBonus, attackSpeed, tier, MINEABLE_WITH_KNIFE, properties);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return KNIFE_ACTIONS.contains(toolAction);
    }
}
