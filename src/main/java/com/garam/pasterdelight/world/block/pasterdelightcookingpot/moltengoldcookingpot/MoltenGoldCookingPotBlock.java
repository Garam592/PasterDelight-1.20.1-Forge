package com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot;

import com.garam.pasterdelight.init.ModBlockEntities;
import com.garam.pasterdelight.world.block.pasterdelightcookingpot.PasterDelightCookingPotBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MoltenGoldCookingPotBlock extends PasterDelightCookingPotBlock
{
    public MoltenGoldCookingPotBlock()
    {
        super(BlockBehaviour.Properties.copy(vectorwing.farmersdelight.common.registry.ModBlocks.COOKING_POT.get()).lightLevel(blockState -> 12));
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get().create(blockPosition, blockState);
    }

    public BlockEntityType<MoltenGoldCookingPotBlockEntity> getBlockEntity()
    {
        return ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get();
    }
}
