package com.garam.pasterdelight.world.item.knife;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CongealWindIronKnifeItem extends RecipeMultiplyingKnifeItem
{
    public CongealWindIronKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties)
    {
        super(tier, attackDamage, attackSpeed, properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdelight.congeal_wind_iron_knife.quick_cutting"));

    }
    @Override
    public int getMultiplyRounds()
    {
        return 8;
    }
}
