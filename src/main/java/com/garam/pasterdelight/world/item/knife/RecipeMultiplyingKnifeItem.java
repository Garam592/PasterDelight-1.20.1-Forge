package com.garam.pasterdelight.world.item.knife;

import com.garam.pasterdelight.helper.RecipeMultiplyingSwitch;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class RecipeMultiplyingKnifeItem extends KnifeItem implements IRecipeMultiplyingKnife
{
    public RecipeMultiplyingKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties)
    {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);

        if(player.isShiftKeyDown())
        {
            RecipeMultiplyingSwitch.setStatus(player, !RecipeMultiplyingSwitch.getStatus(player));
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

}
