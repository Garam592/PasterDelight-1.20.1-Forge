package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.world.item.knife.FluffyWindAlloyKnifeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

@Mixin(CuttingBoardBlock.class)
public abstract class CuttingBoardBlockMixin {

    // 在切割发生前（use 里调用 processStoredItemUsingTool 之前），先额外处理 (n-1) 次，凑够 n = min(4, 砧板数量) 次
    @Inject(method = "use",
            at = @At(value = "INVOKE",
                    target = "Lvectorwing/farmersdelight/common/block/entity/CuttingBoardBlockEntity;processStoredItemUsingTool(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Z",
                    remap = false))
    private void pasterdelight$multiCut(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack held = player.getItemInHand(hand);
        if (!(held.getItem() instanceof FluffyWindAlloyKnifeItem)) return;
        if (!FluffyWindAlloyKnifeItem.isMultiCutEnabled(held)) return;

        CuttingBoardBlockEntity board = (CuttingBoardBlockEntity) level.getBlockEntity(pos);
        int n = Math.min(4, board.getStoredItem().getCount());
        for (int i = 1; i < n; i++) {
            board.processStoredItemUsingTool(held, player);
        }
    }
}
