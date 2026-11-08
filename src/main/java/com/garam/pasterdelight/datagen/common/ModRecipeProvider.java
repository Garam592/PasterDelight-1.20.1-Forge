package com.garam.pasterdelight.datagen.common;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModBlocks;
import com.garam.pasterdelight.init.ModItems;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.function.Consumer;

import static vectorwing.farmersdelight.data.recipe.CookingRecipes.MEDIUM_EXP;
import static vectorwing.farmersdelight.data.recipe.CookingRecipes.NORMAL_COOKING;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    // 感谢拭柳喑雨前面铺的路
    private void saveNbtPreserving(ShapelessRecipeBuilder builder, Consumer<FinishedRecipe> writer, String name) {
        builder.save(wrapped -> writer.accept(new FinishedRecipe() {
            @Override public void serializeRecipeData(JsonObject json) { wrapped.serializeRecipeData(json); }
            @Override public ResourceLocation getId() { return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name); }
            @Override public RecipeSerializer<?> getType() { return ModRecipes.NBT_PRESERVING_SHAPELESS_SERIALIZER.get(); }
            @Override public JsonObject serializeAdvancement() { return wrapped.serializeAdvancement(); }
            @Override public ResourceLocation getAdvancementId() { return wrapped.getAdvancementId(); }
        }), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        KnifeRecipes(consumer);
    }

    // 刀合成配方
    private void KnifeRecipes(Consumer<FinishedRecipe> consumer) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_KNIFE.get()),
                        Ingredient.of(com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_KNIFE.get())
                .unlocks("has_dyedream_upgrade", has(com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_UPGRADE.get()))
                .save(consumer, PasterDelight.MOD_ID + ":dyedream_knife_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(vectorwing.farmersdelight.common.registry.ModItems.DIAMOND_KNIFE.get()),
                        Ingredient.of(com.pasterdream.pasterdreammod.init.ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_KNIFE.get())
                .unlocks("has_titanium_upgrade", has(com.pasterdream.pasterdreammod.init.ModItems.TITANIUM_UPGRADE.get()))
                .save(consumer, PasterDelight.MOD_ID + ":titanium_knife_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_UPGRADE.get()),
                        Ingredient.of(ModItems.DYEDREAM_KNIFE.get()),
                        Ingredient.of(com.pasterdream.pasterdreammod.init.ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        RecipeCategory.TOOLS, ModItems.MELT_DREAM_KNIFE.get())
                .unlocks("has_melt_dream_fragment", has(com.pasterdream.pasterdreammod.init.ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(consumer, PasterDelight.MOD_ID + ":melt_dream_knife_smithing");

        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_KNIFE.get())
                        .requires(vectorwing.farmersdelight.common.registry.ModItems.DIAMOND_KNIFE.get())
                        .requires(com.pasterdream.pasterdreammod.init.ModItems.TITANIUM_INGOT.get())
                        .requires(com.pasterdream.pasterdreammod.init.ModItems.BLACK_STICK.get())
                        .unlockedBy(getHasName(com.pasterdream.pasterdreammod.init.ModItems.TITANIUM_INGOT.get()), has(com.pasterdream.pasterdreammod.init.ModItems.TITANIUM_INGOT.get())),
                consumer, "titanium_knife_crafting");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TITANIUM_KNIFE.get())
                .pattern("a")
                .pattern("b")
                .define('a', com.pasterdream.pasterdreammod.init.ModItems.TITANIUM_INGOT.get())
                .define('b', com.pasterdream.pasterdreammod.init.ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(com.pasterdream.pasterdreammod.init.ModItems.TITANIUM_INGOT.get()), has(com.pasterdream.pasterdreammod.init.ModItems.TITANIUM_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_KNIFE.get())
                .pattern("a")
                .pattern("b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(consumer);

        // 覆盖原版帕斯特的三明治配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, com.pasterdream.pasterdreammod.init.ModItems.SANDWICH.get())
                .requires(Items.BREAD)
                .requires(Items.COOKED_CHICKEN)
                .requires(Ingredient.of(ItemTags.create(
                                ResourceLocation.fromNamespaceAndPath("forge", "cooked_eggs"))))
                .requires(vectorwing.farmersdelight.common.registry.ModItems.CABBAGE_LEAF.get())
                .unlockedBy(getHasName(Items.BREAD), has(Items.BREAD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MOLTEN_GOLD_COOKING_POT.get())
                .pattern("c c")
                .pattern("aba")
                .pattern("aaa")
                .define('a', com.pasterdream.pasterdreammod.init.ModItems.MOLTEN_GOLD_INGOT.get())
                .define('b', vectorwing.farmersdelight.common.registry.ModItems.COOKING_POT.get())
                .define('c', Items.NETHER_BRICK)
                .unlockedBy(getHasName(com.pasterdream.pasterdreammod.init.ModItems.MOLTEN_GOLD_INGOT.get()),
                        has(com.pasterdream.pasterdreammod.init.ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(consumer);

        //锅烹饪配方
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.GLASS_CUP_OF_BARLEY_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP,
                        com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.RYE_SEED.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.RYE_SEED.get())
                .unlockedByItems("has_rye_seed", com.pasterdream.pasterdreammod.init.ModItems.RYE_SEED.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP,
                        com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.AMBER_CANDY.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_LEAVES.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_COROLLA.get())
                .unlockedByItems("has_glass_cup", com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer,PasterDelight.MOD_ID+"glass_cup_of_cooked_dyedream_flower_tea_from_cooking_pot");
    }
}
