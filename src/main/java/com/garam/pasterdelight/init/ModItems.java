package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.world.MeltDreamKnifeItem;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
