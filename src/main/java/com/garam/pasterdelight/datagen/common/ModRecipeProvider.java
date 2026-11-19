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
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

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

    private void saveNbtPreservingShaped(ShapedRecipeBuilder builder, Consumer<FinishedRecipe> writer, String name) {
        builder.save(wrapped -> writer.accept(new FinishedRecipe() {
            @Override public void serializeRecipeData(JsonObject json) { wrapped.serializeRecipeData(json); }
            @Override public ResourceLocation getId() { return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name); }
            @Override public RecipeSerializer<?> getType() { return ModRecipes.NBT_PRESERVING_SHAPED_SERIALIZER.get(); }
            @Override public JsonObject serializeAdvancement() { return wrapped.serializeAdvancement(); }
            @Override public ResourceLocation getAdvancementId() { return wrapped.getAdvancementId(); }
        }), ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    private void saveAxolotlDrink(ShapelessRecipeBuilder builder, Consumer<FinishedRecipe> writer, String name) {
        builder.save(wrapped -> writer.accept(new FinishedRecipe() {
            @Override public void serializeRecipeData(JsonObject json) { wrapped.serializeRecipeData(json); }
            @Override public ResourceLocation getId() { return ResourceLocation.fromNamespaceAndPath(PasterDelight.MOD_ID, name); }
            @Override public RecipeSerializer<?> getType() { return com.garam.pasterdelight.init.ModRecipes.AXOLOTL_SPECIAL_DRINK.get(); }
            @Override public JsonObject serializeAdvancement() { return wrapped.serializeAdvancement(); }
            @Override public ResourceLocation getAdvancementId() { return wrapped.getAdvancementId(); }
        }), ResourceLocation.fromNamespaceAndPath(PasterDelight.MOD_ID, name));
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        KnifeRecipes(consumer);
        CookingRecipes(consumer);
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

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CONGEAL_WIND_IRON_KNIFE.get())
                .pattern("a")
                .pattern("b")
                .define('a', com.pasterdream.pasterdreammod.init.ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .define('b', com.pasterdream.pasterdreammod.init.ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(com.pasterdream.pasterdreammod.init.ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(com.pasterdream.pasterdreammod.init.ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(consumer);

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(com.pasterdream.pasterdreammod.init.ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()),
                        Ingredient.of(ModItems.CONGEAL_WIND_IRON_KNIFE.get()),
                        Ingredient.of(com.pasterdream.pasterdreammod.init.ModItems.CONGEAL_WIND.get()),
                        RecipeCategory.TOOLS, ModItems.FLUFFY_WIND_ALLOY_KNIFE.get())
                .unlocks("has_fluffy_wind_alloy_upgrade", has(com.pasterdream.pasterdreammod.init.ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()))
                .save(consumer, PasterDelight.MOD_ID + ":fluffy_wind_alloy_knife_smithing");


        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOW_ALLOY_KNIFE.get())
                .pattern("cbc")
                .pattern("bab")
                .pattern("cbc")
                .define('a', ModItems.SHADOW_EROSION_KNIFE.get())
                .define('b', com.pasterdream.pasterdreammod.init.ModItems.SHADOW_ALLOY_INGOT.get())
                .define('c', com.pasterdream.pasterdreammod.init.ModItems.NIGHTMARE_FUEL.get())
                .unlockedBy(getHasName(com.pasterdream.pasterdreammod.init.ModItems.SHADOW_ALLOY_INGOT.get()),
                        has(com.pasterdream.pasterdreammod.init.ModItems.SHADOW_ALLOY_INGOT.get()))
                ,consumer,"shadow_alloy_knife_crafting");

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

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModBlocks.MOLTEN_GOLD_SKILLET.get())
                .pattern(" aa")
                .pattern(" aa")
                .pattern("b  ")
                .define('a', com.pasterdream.pasterdreammod.init.ModItems.MOLTEN_GOLD_INGOT.get())
                .define('b', Items.BLAZE_ROD)
                .unlockedBy(getHasName(com.pasterdream.pasterdreammod.init.ModItems.MOLTEN_GOLD_INGOT.get()),
                        has(com.pasterdream.pasterdreammod.init.ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(consumer);
    }
    // 烹饪配方
    private void CookingRecipes(Consumer<FinishedRecipe> consumer){
        // ====== 锅烹饪配方 ======
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
                .save(consumer,PasterDelight.MOD_ID+":glass_cup_of_cooked_dyedream_flower_tea_from_cooking_pot");

        CookingPotRecipeBuilder.cookingPotRecipe(com.pasterdream.pasterdreammod.init.ModItems.QUEER_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP,
                        Items.BOWL)
                .addIngredient(Items.MUSHROOM_STEW)
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.SINGULARITY_FERN.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.MISTY_DREAMING_LOTUS.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_COROLLA.get())
                .addIngredient(Items.GHAST_TEAR)
                .unlockedByItems("has_misty_dreaming_lotus", com.pasterdream.pasterdreammod.init.ModItems.MISTY_DREAMING_LOTUS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer,PasterDelight.MOD_ID+":queer_soup_from_cooking_pot");

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.GLASS_CUP_OF_FIG_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP,
                        com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.FIG.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.FIG.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_fig", com.pasterdream.pasterdreammod.init.ModItems.FIG.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.SHROOMCLAW_ENERGY.get(), 1, NORMAL_COOKING, MEDIUM_EXP,
                        com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.HOT_COCOA.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.SHADOW_FUNGUS.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.SHADOW_ROOTS.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.NIGHTMARE_FUEL.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_nightmare_fuel", com.pasterdream.pasterdreammod.init.ModItems.NIGHTMARE_FUEL.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.LIGHT_BALL_FLUORESCENT_SAUCE.get(), 1, NORMAL_COOKING, MEDIUM_EXP,
                        Items.BOWL)
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.LIGHT_BALL.get())
                .addIngredient(com.pasterdream.pasterdreammod.init.ModItems.LIGHT_BALL.get())
                .unlockedByItems("has_light_ball", com.pasterdream.pasterdreammod.init.ModItems.LIGHT_BALL.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer);

        // ====== 派 ======
        // 染梦果派
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.DYEDREAM_FRUIT_PIE.get())
                .pattern("aaa")
                .pattern("bbb")
                .pattern("cdc")
                .define('a', com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_DUST_PIECE.get())
                .define('b', com.pasterdream.pasterdreammod.init.ModItems.DYEDREAM_FRUIT.get())
                .define('c', com.pasterdream.pasterdreammod.init.ModItems.AMBER_CANDY.get())
                .define('d', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy(getHasName(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()),
                        has(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.DYEDREAM_FRUIT_PIE.get())
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_FRUIT_PIE_SLICE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_FRUIT_PIE_SLICE.get()),
                        has(ModItems.DYEDREAM_FRUIT_PIE_SLICE.get()))
                .save(consumer,PasterDelight.MOD_ID+":dyedream_fruit_pie_from_slices");

        CuttingBoardRecipeBuilder.cuttingRecipe(
                        Ingredient.of(ModItems.DYEDREAM_FRUIT_PIE.get()),
                        Ingredient.of(ItemTags.create(
                                ResourceLocation.fromNamespaceAndPath("farmersdelight", "tools/knives"))),
                        ModItems.DYEDREAM_FRUIT_PIE_SLICE.get(),
                        4)
                .unlockedBy("has_dyedream_fruit_pie", has(ModItems.DYEDREAM_FRUIT_PIE.get()))
                .save(consumer, PasterDelight.MOD_ID + ":cutting/dyedream_fruit_pie");

        // 无花果派
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.FIG_PIE.get())
                .pattern("bab")
                .pattern("bcb")
                .pattern(" d ")
                .define('a', com.pasterdream.pasterdreammod.init.ModItems.JELLYFISH_MUD.get())
                .define('b', com.pasterdream.pasterdreammod.init.ModItems.FIG.get())
                .define('c', Items.SUGAR)
                .define('d', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy(getHasName(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()),
                        has(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.FIG_PIE.get())
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.FIG_PIE_SLICE.get())
                .unlockedBy(getHasName(ModItems.FIG_PIE_SLICE.get()),
                        has(ModItems.FIG_PIE_SLICE.get()))
                .save(consumer,PasterDelight.MOD_ID+":fig_pie_from_slices");

        CuttingBoardRecipeBuilder.cuttingRecipe(
                        Ingredient.of(ModItems.FIG_PIE.get()),
                        Ingredient.of(ItemTags.create(
                                ResourceLocation.fromNamespaceAndPath("farmersdelight", "tools/knives"))),
                        ModItems.FIG_PIE_SLICE.get(),
                        4)
                .unlockedBy("has_fig_pie", has(ModItems.FIG_PIE.get()))
                .save(consumer, PasterDelight.MOD_ID + ":cutting/fig_pie");

        //巧克力派新配方
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, vectorwing.farmersdelight.common.registry.ModItems.CHOCOLATE_PIE.get(),2)
                .pattern("aaa")
                .pattern("bbb")
                .pattern("c c")
                .define('a', com.pasterdream.pasterdreammod.init.ModItems.CHOCOLATE.get())
                .define('b', Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "milk"))))
                .define('c', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy(getHasName(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()),
                        has(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()))
                .save(consumer,PasterDelight.MOD_ID+":chocolate_pie_from_chocolate");

        // 覆盖原版帕斯特的三明治配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, com.pasterdream.pasterdreammod.init.ModItems.SANDWICH.get())
                .requires(Items.BREAD)
                .requires(Items.COOKED_CHICKEN)
                .requires(Ingredient.of(ItemTags.create(
                        ResourceLocation.fromNamespaceAndPath("forge", "cooked_eggs"))))
                .requires(vectorwing.farmersdelight.common.registry.ModItems.CABBAGE_LEAF.get())
                .unlockedBy(getHasName(Items.BREAD), has(Items.BREAD))
                .save(consumer);

        // 美西螈特饮（保留美西螈桶）
        saveAxolotlDrink(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.MARTINI_GLASS_OF_AXOLOTL_SPECIAL_DRINK.get(),2)
                .requires(com.pasterdream.pasterdreammod.init.ModItems.MARTINI_GLASS.get(),2)
                .requires(Items.AXOLOTL_BUCKET)
                .requires(com.pasterdream.pasterdreammod.init.ModItems.SALT.get())
                .requires(com.pasterdream.pasterdreammod.init.ModItems.BLUE_DEW.get(),2)
                .unlockedBy(getHasName(Items.AXOLOTL_BUCKET), has(Items.AXOLOTL_BUCKET)),
                consumer, "martini_glass_of_axolotl_special_drink");
    }
}
