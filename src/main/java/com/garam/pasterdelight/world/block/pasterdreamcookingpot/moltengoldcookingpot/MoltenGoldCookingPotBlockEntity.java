package com.garam.pasterdelight.world.block.pasterdreamcookingpot.moltengoldcookingpot;

import com.garam.pasterdelight.init.ModBlockEntities;
import com.garam.pasterdelight.world.block.pasterdreamcookingpot.PasterDreamCookingPotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MoltenGoldCookingPotBlockEntity extends PasterDreamCookingPotBlockEntity
{
    public MoltenGoldCookingPotBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get(), blockPos, blockState);
    }

    @Override
    public boolean isHeated(Level level, BlockPos blockPosition)
    {
        return true;
    }

    @Override
    public boolean isHeated()
    {
        return true;
    }
}
