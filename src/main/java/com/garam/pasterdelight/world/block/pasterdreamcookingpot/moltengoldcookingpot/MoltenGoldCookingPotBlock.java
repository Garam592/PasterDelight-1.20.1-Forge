package com.garam.pasterdelight.world.block.pasterdreamcookingpot.moltengoldcookingpot;

import com.garam.pasterdelight.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.CookingPotBlock;

import javax.annotation.Nullable;

public class MoltenGoldCookingPotBlock extends CookingPotBlock
{
    public MoltenGoldCookingPotBlock()
    {
        super(BlockBehaviour.Properties.copy(vectorwing.farmersdelight.common.registry.ModBlocks.COOKING_POT.get()));
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get().create(blockPosition, blockState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity)
    {
        BlockEntityType<?> expected = ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get();
        System.out.println("[MoltenGoldPot] actual=" + blockEntity + ", expected=" + expected + ", same=" + (blockEntity == expected));

        BlockEntityTicker<T> ticker = level.isClientSide ? createTickerHelper(blockEntity, ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get(), MoltenGoldCookingPotBlockEntity::animationTick) : createTickerHelper(blockEntity, ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get(), MoltenGoldCookingPotBlockEntity::cookingTick);
        return ticker;
    }
}
