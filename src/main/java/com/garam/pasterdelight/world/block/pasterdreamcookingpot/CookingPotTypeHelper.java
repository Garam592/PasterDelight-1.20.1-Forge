package com.garam.pasterdelight.world.block.pasterdreamcookingpot;

import net.minecraft.world.level.block.entity.BlockEntityType;

public class CookingPotTypeHelper
{
    private static final ThreadLocal<BlockEntityType<?>> PENDING_TYPE = new ThreadLocal<>();

    public static void setPendingType(BlockEntityType<?> type)
    {
        PENDING_TYPE.set(type);
    }

    public static BlockEntityType<?> getAndClearPendingType()
    {
        BlockEntityType<?> type = PENDING_TYPE.get();
        PENDING_TYPE.remove();
        return type;
    }
}
