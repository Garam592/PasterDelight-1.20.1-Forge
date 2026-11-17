package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.helper.BlockEntityTypeHelper;
import com.garam.pasterdelight.world.block.pasterdelightcookingpot.PasterDelightCookingPotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

@Mixin(CookingPotBlockEntity.class)
public class CookingPotBlockEntityMixin
{
    @Inject(method = "<init>", at = @At("RETURN"))
    private void pasterdream$replaceType(BlockPos pos, BlockState state, CallbackInfo ci)
    {
        BlockEntityType<?> pending = BlockEntityTypeHelper.getAndClearPendingType();
        if (pending != null && (Object) this instanceof PasterDelightCookingPotBlockEntity)
        {
            ((BlockEntityAccessor) this).pasterdelight$setType(pending);
        }
    }

    @Redirect(method = "getMealFromItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean redirectIsCheck(ItemStack stack, Item item)
    {
        return true;
    }
}
