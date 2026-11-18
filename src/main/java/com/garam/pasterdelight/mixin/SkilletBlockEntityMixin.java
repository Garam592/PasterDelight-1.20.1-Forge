package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.helper.BlockEntityTypeHelper;
import com.garam.pasterdelight.world.block.pasterdelightskillet.PasterDelightSkilletBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

@Mixin(value = SkilletBlockEntity.class, remap = false)
public class SkilletBlockEntityMixin
{
    @Inject(method = "<init>", at = @At("RETURN"))
    private void pasterdelight$replaceType(BlockPos pos, BlockState state, CallbackInfo ci)
    {
        BlockEntityType<?> pending = BlockEntityTypeHelper.getAndClearPendingType();
        if (pending != null && ((Object) this) instanceof PasterDelightSkilletBlockEntity)
        {
            ((BlockEntityAccessor) this).pasterdelight$setType(pending);
        }
    }

    @Inject(method = "cookAndOutputItems", at = @At("HEAD"), cancellable = true, remap = false)
    public void cookAndOutputItemsMixin(ItemStack cookingStack, Level level, CallbackInfo cir)
    {
        if(((Object) this) instanceof PasterDelightSkilletBlockEntity pasterDelightSkilletBlockEntity)
        {
            pasterDelightSkilletBlockEntity.pasterDelightCookAndOutputItems(cookingStack, level);
            cir.cancel();
        }
    }
}
