package com.garam.pasterdelight.world.block.pasterdelightskillet.moltengoldskillet;

import com.garam.pasterdelight.init.ModBlockEntities;
import com.garam.pasterdelight.world.block.pasterdelightskillet.PasterDelightSkilletBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MoltenGoldSkilletBlockEntity extends PasterDelightSkilletBlockEntity
{
    public MoltenGoldSkilletBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        super(ModBlockEntities.MOLTEN_GOLD_SKILLET.get(), blockPosition, blockState);
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
