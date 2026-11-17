package com.garam.pasterdelight.world.block.pasterdelightskillet.moltengoldskillet;

import com.garam.pasterdelight.init.ModBlockEntities;
import com.garam.pasterdelight.world.block.pasterdelightskillet.PasterDelightSkilletBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MoltenGoldSkilletBlock extends PasterDelightSkilletBlock
{
    public MoltenGoldSkilletBlock()
    {
        super(BlockBehaviour.Properties.copy(vectorwing.farmersdelight.common.registry.ModBlocks.SKILLET.get()).lightLevel(blockState -> 12));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return ModBlockEntities.MOLTEN_GOLD_SKILLET.get().create(blockPosition, blockState);
    }

    @Override
    public BlockEntityType<MoltenGoldSkilletBlockEntity> getBlockEntity()
    {
        return ModBlockEntities.MOLTEN_GOLD_SKILLET.get();
    }
}
