package com.garam.pasterdelight.mixin;

import com.garam.pasterdelight.helper.RecipeMultiplyingSwitch;
import com.garam.pasterdelight.world.item.knife.IRecipeMultiplyingKnife;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModAdvancements;
import vectorwing.farmersdelight.common.utility.ItemUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(value = CuttingBoardBlockEntity.class, remap = false)
public class CuttingBoardBlockEntityMixin
{
    @Inject(method = "processStoredItemUsingTool", at = @At("HEAD"), cancellable = true, remap = false)
    public void processStoredItemUsingToolMixin(ItemStack toolStack, @Nullable Player player, CallbackInfoReturnable<Boolean> cir)
    {
        if(RecipeMultiplyingSwitch.getStatus(player) && toolStack.getItem() instanceof IRecipeMultiplyingKnife recipeMultiplyingKnife)
        {
            CuttingBoardBlockEntity mixinThis = (CuttingBoardBlockEntity)(Object)this;
            CuttingBoardBlockEntityAccessor accessorThis = (CuttingBoardBlockEntityAccessor)this;

            if(mixinThis.getLevel() != null && !accessorThis.isItemCarvingBoardAccessor())
            {
                Optional<CuttingBoardRecipe> matchingRecipe = accessorThis.getMatchingRecipeInvoker(new RecipeWrapper(accessorThis.inventoryAccessor()), toolStack, player);
                if(matchingRecipe.isPresent())
                {
                    CuttingBoardRecipe recipe = matchingRecipe.get();
                    int rounds = Math.min(recipeMultiplyingKnife.getMultiplyRounds(), mixinThis.getStoredItem().getCount());

                    for(int i = 0; i < rounds; i++)
                    {
                        //生成产物
                        for (ItemStack resultStack : recipe.rollResults(mixinThis.getLevel().random, EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, toolStack), new RecipeWrapper(accessorThis.inventoryAccessor())))
                        {
                            Direction direction = mixinThis.getBlockState().getValue(CuttingBoardBlock.FACING).getCounterClockWise();
                            ItemUtils.spawnItemEntity(mixinThis.getLevel(), resultStack.copy(), mixinThis.getBlockPos().getX() + 0.5 + direction.getStepX() * 0.2, mixinThis.getBlockPos().getY() + 0.2, mixinThis.getBlockPos().getZ() + 0.5 + direction.getStepZ() * 0.2, direction.getStepX() * 0.2F, 0.0F, direction.getStepZ() * 0.2F);
                        }

                        //消耗刀耐久
                        if (player != null)
                        {
                            toolStack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                            player.awardStat(Stats.ITEM_USED.get(toolStack.getItem()));
                        }
                        else
                            if (toolStack.hurt(1, mixinThis.getLevel().random, null))
                            {
                                toolStack.setCount(0);
                            }

                        //播放音效
                        if (mixinThis.getLevel() instanceof ServerLevel serverLevel)
                        {
                            mixinThis.spawnCuttingParticles(serverLevel, mixinThis.getBlockPos(), mixinThis.getStoredItem());
                        }
                    }
                    //播放音效
                    mixinThis.playProcessingSound(recipe.getSoundEventID(), toolStack, mixinThis.getStoredItem());
                    //消耗原材料
                    accessorThis.inventoryAccessor().extractItem(0, rounds, false);

                    //触发成就/显示物品数量
                    if (player instanceof ServerPlayer)
                    {
                        ModAdvancements.CUTTING_BOARD.trigger((ServerPlayer)player);
                        if (!mixinThis.getStoredItem().isEmpty())
                        {
                            player.displayClientMessage(TextUtils.block("cutting_board.remaining_items", mixinThis.getStoredItem().getCount()), true);
                        }
                            else
                            {
                                player.displayClientMessage(Component.empty(), true);
                            }
                    }
                    cir.setReturnValue(true);
                }
            }
            cir.setReturnValue(false);
        }
    }
}
