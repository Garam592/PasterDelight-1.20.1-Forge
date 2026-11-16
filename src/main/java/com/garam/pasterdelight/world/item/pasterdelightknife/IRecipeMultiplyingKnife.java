package com.garam.pasterdelight.world.item.pasterdelightknife;

public interface IRecipeMultiplyingKnife
{
    //每次并行配方数
    default int getMultiplyRounds()
    {
        return 1;
    }
}
