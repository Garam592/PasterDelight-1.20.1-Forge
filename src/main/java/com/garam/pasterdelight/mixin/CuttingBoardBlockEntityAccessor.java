package com.garam.pasterdelight.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.util.Optional;

@Mixin(value = CuttingBoardBlockEntity.class, remap = false)
public interface CuttingBoardBlockEntityAccessor
{
    @Accessor("inventory")
    ItemStackHandler inventoryAccessor();

    @Accessor("isItemCarvingBoard")
    boolean isItemCarvingBoardAccessor();

    @Invoker("getMatchingRecipe")
    Optional<CuttingBoardRecipe> getMatchingRecipeInvoker(RecipeWrapper recipeWrapper, ItemStack toolStack, Player player);
}
