package com.garam.pasterdelight.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class GetBottomBlockCapability
{
    public static IItemHandler getTargetInventory(Level level, BlockPos blockPosition)
    {
        BlockPos targetPosition = blockPosition.relative(Direction.DOWN);
        BlockEntity targetBlockEntity = level.getBlockEntity(targetPosition);
        if (targetBlockEntity != null)
        {
            return targetBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).orElse(null);
        }
            else
            {
                return null;
            }
    }
}
