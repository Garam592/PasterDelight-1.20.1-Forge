package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.world.item.PasterKnifeItem;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PasterDelight.MOD_ID);

    // 钛金小刀
    public static final RegistryObject<Item> TITANIUM_KNIFE =
            registerKnife("titanium_knife", ModToolTiers.TITANIUM);

    private static RegistryObject<Item> registerKnife(String name, Tier tier) {
        return ITEMS.register(name,
                () -> new PasterKnifeItem(tier, 0.5F, -2.0F, new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
