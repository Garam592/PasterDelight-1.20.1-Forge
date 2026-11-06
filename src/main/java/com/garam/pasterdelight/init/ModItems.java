package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.world.item.knife.MeltDreamKnifeItem;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamDrinkItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PasterDelight.MOD_ID);

    // 刀子（物理层面）
    public static final RegistryObject<Item> TITANIUM_KNIFE =
            ITEMS.register("titanium_knife",()-> new KnifeItem(ModToolTiers.TITANIUM, 0.5F, -2.0F,new Item.Properties()));
    public static final RegistryObject<Item> COPPER_KNIFE =
            ITEMS.register("copper_knife",()-> new KnifeItem(ModToolTiers.COPPER, 0.5F, -2.0F,new Item.Properties()));
    public static final RegistryObject<Item> DYEDREAM_KNIFE =
            ITEMS.register("dyedream_knife",()-> new KnifeItem(ModToolTiers.DYEDREAM, 0.5F, -2.0F,new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MELT_DREAM_KNIFE =
            ITEMS.register("melt_dream_knife",()-> new MeltDreamKnifeItem(ModToolTiers.MELT_DREAM, 0.5F, -2.0F,new Item.Properties().fireResistant().rarity(Rarity.RARE)));

    // 吃的
    public static final RegistryObject<Item> GLASS_CUP_OF_BARLEY_TEA = ITEMS.register("glass_cup_of_barley_tea", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(com.pasterdream.pasterdreammod.init.ModEffects.REST.get(), 1800, 0), 1.0f)
            .effect(() -> new MobEffectInstance(com.pasterdream.pasterdreammod.init.ModEffects.COOK.get(), 1800, 0), 1.0f) .nutrition(4).saturationMod(0.5f).alwaysEat().build())));

    public static final RegistryObject<Item> MOLTEN_GOLD_COOKING_POT = ITEMS.register("molten_gold_cooking_pot", () -> new BlockItem(ModBlocks.MOLTEN_GOLD_COOKING_POT.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
