package com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot;

import com.garam.pasterdelight.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
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
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPosition);
            if (blockEntity instanceof MoltenGoldCookingPotBlockEntity moltenGoldCookingPot)
            {
                NetworkHooks.openScreen((ServerPlayer) player, moltenGoldCookingPot, buffer -> buffer.writeBlockPos(blockPosition));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity)
    {
        BlockEntityTicker<T> ticker = level.isClientSide ? createTickerHelper(blockEntity, ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get(), MoltenGoldCookingPotBlockEntity::animationTick) : createTickerHelper(blockEntity, ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get(), MoltenGoldCookingPotBlockEntity::cookingTick);
        return ticker;
    }
}
