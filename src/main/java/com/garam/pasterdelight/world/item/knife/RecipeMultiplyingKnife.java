package com.garam.pasterdelight.world.item.knife;

public interface RecipeMultiplyingKnife
{
    //每次并行配方数
    int getMultiplyRounds();

    //增加产出的概率
    default double getMoreSpawnResultProbability()
    {
        return 0;
    }

    //不消耗原材料的概率
    default double getNotUseMaterialProbability()
    {
        return 0;
    }
}
