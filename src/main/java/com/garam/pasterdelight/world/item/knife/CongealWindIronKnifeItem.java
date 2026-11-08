package com.garam.pasterdelight.world.item.knife;

import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class CongealWindIronKnifeItem extends KnifeItem implements RecipeMultiplyingKnife
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
