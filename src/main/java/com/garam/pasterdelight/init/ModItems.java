package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PasterDelight.MOD_ID);

    // 钛金小刀
    public static final RegistryObject<Item> TITANIUM_KNIFE = ITEMS.register("titanium_knife", () -> new KnifeItem(ModToolTiers.TITANIUM, 0.5F, -2.0F, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
