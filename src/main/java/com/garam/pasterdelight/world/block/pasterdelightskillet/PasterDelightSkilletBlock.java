package com.garam.pasterdelight.world.block.pasterdelightskillet;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        BlockEntity var8 = level.getBlockEntity(pos);
        if (var8 instanceof PasterDelightSkilletBlockEntity pasterDelightSkillet)
        {
            if (level.isClientSide)
            {
                return InteractionResult.CONSUME;
            }

            ItemStack heldStack = player.getItemInHand(hand);
            EquipmentSlot heldSlot = hand.equals(InteractionHand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            if (heldStack.isEmpty())
            {
                ItemStack extractedStack = pasterDelightSkillet.removeItem();
                pasterDelightSkillet.setOutput(ItemStack.EMPTY);
                if (!player.isCreative())
                {
                    player.setItemSlot(heldSlot, extractedStack);
                }

                return InteractionResult.SUCCESS;
            }

            ItemStack remainderStack = pasterDelightSkillet.addItemToCook(heldStack, player);
            if (remainderStack.getCount() != heldStack.getCount())
            {
                if (!player.isCreative())
                {
                    player.setItemSlot(heldSlot, remainderStack);
                }

                level.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
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
        if (!state.is(newState.getBlock()) && level.getBlockEntity(blockPosition) instanceof PasterDelightSkilletBlockEntity pasterDelightSkillet)
        {
            ItemStack output = pasterDelightSkillet.getOutput();
            if (!output.isEmpty())
            {
                Containers.dropItemStack(level, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), output);
                pasterDelightSkillet.setOutput(ItemStack.EMPTY);
            }
        }
        super.onRemove(state, level, blockPosition, newState, isMoving);
    }

    public abstract <T extends PasterDelightSkilletBlockEntity> BlockEntityType<T> getBlockEntity();
}
