package com.garam.pasterdelight.world.block.pasterdelightskillet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.SkilletBlock;

public abstract class PasterDelightSkilletBlock extends SkilletBlock
{
    public PasterDelightSkilletBlock(Properties properties)
    {
        super(properties);
    }

    public PasterDelightSkilletBlock()
    {
        super(BlockBehaviour.Properties.copy(vectorwing.farmersdelight.common.registry.ModBlocks.SKILLET.get()));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity)
    {
        if (level.isClientSide)
        {
            return createTickerHelper(blockEntity, getBlockEntity(), PasterDelightSkilletBlockEntity::animationTick);
        }
        else
        {
            return createTickerHelper(blockEntity, getBlockEntity(), (tickLevel, blockPosition, blockState, pasterDelightSkillet) ->
            {
                PasterDelightSkilletBlockEntity.cookingTick(tickLevel, blockPosition, blockState, pasterDelightSkillet);
                pasterDelightSkillet.tick();
            });
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos blockPosition, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, level, blockPosition, newState, isMoving);

        if (!state.is(newState.getBlock()) && level.getBlockEntity(blockPosition) instanceof PasterDelightSkilletBlockEntity pasterDelightSkillet)
        {
            ItemStack output = pasterDelightSkillet.takeOutput();

            if (!output.isEmpty())
            {
                Containers.dropItemStack(level, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), output);
            }
        }
    }

    public abstract <T extends PasterDelightSkilletBlockEntity> BlockEntityType<T> getBlockEntity();
}
