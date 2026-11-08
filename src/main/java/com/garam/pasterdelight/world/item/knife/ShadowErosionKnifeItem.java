package com.garam.pasterdelight.world.item.knife;

import com.pasterdream.pasterdreammod.world.item.shadowerosiontool.ShadowErosionTool;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import java.util.List;

public class ShadowErosionKnifeItem extends RecipeMultiplyingKnifeItem implements ShadowErosionTool, IRecipeMultiplyingKnife {

    public ShadowErosionKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_erosion_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_erosion_tool.2"));
        tooltip.add(Component.translatable("tooltip.pasterdelight.shadow_erosion_knife.quick_cutting"));

    }
    @Override
    public int getMultiplyRounds()
    {
        return 8;
    }
}
