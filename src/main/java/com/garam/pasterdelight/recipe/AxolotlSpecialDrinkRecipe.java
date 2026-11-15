package com.garam.pasterdelight.recipe;

import com.garam.pasterdelight.init.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

/**
 * 美西螈特饮无序配方：美西螈桶不会被消耗，而是原样（含实体 NBT）留在合成栏。
 */
public class AxolotlSpecialDrinkRecipe extends ShapelessRecipe {

    public AxolotlSpecialDrinkRecipe(ResourceLocation id, String group, CraftingBookCategory category,
                                     ItemStack result, NonNullList<Ingredient> ingredients) {
        super(id, group, category, result, ingredients);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < remaining.size(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.is(Items.AXOLOTL_BUCKET)) {
                ItemStack keep = stack.copy();   // 保留桶里的实体 NBT（变种/年龄/血量）
                keep.setCount(1);                // 返还数量=消耗数量，防止堆叠翻倍
                remaining.set(i, keep);          // 美西螈桶原样留在合成栏
            } else if (!stack.isEmpty()) {
                remaining.set(i, stack.getItem().getCraftingRemainingItem(stack));
            }
        }
        return remaining;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.AXOLOTL_SPECIAL_DRINK.get();
    }

    public static class Serializer implements RecipeSerializer<AxolotlSpecialDrinkRecipe> {
        private static final ShapelessRecipe.Serializer FALLBACK = new ShapelessRecipe.Serializer();

        @Override
        public AxolotlSpecialDrinkRecipe fromJson(ResourceLocation id, com.google.gson.JsonObject json) {
            ShapelessRecipe vanilla = FALLBACK.fromJson(id, json);
            return new AxolotlSpecialDrinkRecipe(vanilla.getId(), vanilla.getGroup(),
                    vanilla.category(), vanilla.getResultItem(RegistryAccess.EMPTY),
                    vanilla.getIngredients());
        }

        @Override
        public AxolotlSpecialDrinkRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ShapelessRecipe vanilla = FALLBACK.fromNetwork(id, buf);
            return new AxolotlSpecialDrinkRecipe(vanilla.getId(), vanilla.getGroup(),
                    vanilla.category(), vanilla.getResultItem(RegistryAccess.EMPTY),
                    vanilla.getIngredients());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AxolotlSpecialDrinkRecipe recipe) {
            FALLBACK.toNetwork(buf, recipe);
        }
    }
}
