package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.recipe.AxolotlSpecialDrinkRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PasterDelight.MOD_ID);

    public static final RegistryObject<RecipeSerializer<AxolotlSpecialDrinkRecipe>> AXOLOTL_SPECIAL_DRINK =
            RECIPE_SERIALIZERS.register("axolotl_special_drink", AxolotlSpecialDrinkRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
