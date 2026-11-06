package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.world.block.pasterdreamcookingpot.CookingPotTypeHelper;
import com.garam.pasterdelight.world.block.pasterdreamcookingpot.PasterDreamCookingPotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

@Mixin(CookingPotBlockEntity.class)
public class CookingPotBlockEntityMixin
{
    @Inject(method = "<init>", at = @At("RETURN"))
    private void pasterdream$replaceType(BlockPos pos, BlockState state, CallbackInfo ci)
    {
        BlockEntityType<?> pending = CookingPotTypeHelper.getAndClearPendingType();
        if (pending != null && (Object) this instanceof PasterDreamCookingPotBlockEntity)
        {
            ((BlockEntityAccessor) this).pasterdelight$setType(pending);
        }
    }
}
