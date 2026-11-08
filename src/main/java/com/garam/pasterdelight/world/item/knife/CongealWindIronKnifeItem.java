package com.garam.pasterdelight.world.item.knife;

import net.minecraft.world.item.Tier;

public class CongealWindIronKnifeItem extends RecipeMultiplyingKnifeItem implements IRecipeMultiplyingKnife
{
    public CongealWindIronKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties)
    {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public int getMultiplyRounds()
    {
        return 8;
    }
}
