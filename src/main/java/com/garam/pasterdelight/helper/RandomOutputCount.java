package com.garam.pasterdelight.helper;

import net.minecraft.util.RandomSource;

public class RandomOutputCount
{
    public static int Calculate(int baseRound, double multiply, RandomSource random)
    {
        int total = 0;

        if(multiply >= 0)
        {
            int multiplyIntPart = (int)multiply;
            total += (baseRound * (1 + multiplyIntPart));
            for(int i = 0; i < baseRound; i++)
            {
                if(random.nextDouble() < multiply - multiplyIntPart)
                {
                    total++;
                }
            }
        }
        else
            if(multiply > -1)
            {
                for(int i = 0; i < baseRound; i++)
                {
                    if(random.nextDouble() < -multiply)
                    {
                        total--;
                    }
                }
            }

        return total;
    }
}
