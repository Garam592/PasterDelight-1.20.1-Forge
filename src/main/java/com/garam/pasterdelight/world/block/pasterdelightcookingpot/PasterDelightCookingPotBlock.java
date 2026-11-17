package com.garam.pasterdelight.world.block.pasterdelightcookingpot;

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

public abstract class PasterDelightCookingPotBlock extends CookingPotBlock
{
    public PasterDelightCookingPotBlock(Properties properties)
    {
        super(properties);
    }

    public PasterDelightCookingPotBlock()
    {
        super(BlockBehaviour.Properties.copy(vectorwing.farmersdelight.common.registry.ModBlocks.COOKING_POT.get()));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPosition);
            if (blockEntity instanceof PasterDelightCookingPotBlockEntity pasterDelightCookingPotBlockEntity)
            {
                NetworkHooks.openScreen((ServerPlayer) player, pasterDelightCookingPotBlockEntity, buffer -> buffer.writeBlockPos(blockPosition));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity)
    {
        if (level.isClientSide)
        {
            return createTickerHelper(blockEntity, getBlockEntity(), PasterDelightCookingPotBlockEntity::animationTick);
        }
        else
        {
            return createTickerHelper(blockEntity, getBlockEntity(), (tickLevel, blockPosition, blockState, pasterDelightCookingPot) ->
            {
                PasterDelightCookingPotBlockEntity.cookingTick(tickLevel, blockPosition, blockState, pasterDelightCookingPot);
                pasterDelightCookingPot.tick();
            });
        }
    }

    public abstract <T extends PasterDelightCookingPotBlockEntity> BlockEntityType<T> getBlockEntity();
}
