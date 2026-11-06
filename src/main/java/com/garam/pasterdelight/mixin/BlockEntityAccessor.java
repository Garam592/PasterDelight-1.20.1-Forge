package com.garam.pasterdelight.mixin;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockEntity.class)
public interface BlockEntityAccessor
{
    @Accessor("type")
    BlockEntityType<?> pasterdelight$getType();

    @Mutable
    @Accessor("type")
    void pasterdelight$setType(BlockEntityType<?> type);
}
