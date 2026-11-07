package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PasterDelight.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PASTERDREAM_FOOD_TAB = CREATIVE_MODE_TABS.register("pasterdream_food_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdelight.creative_mode_tab"))
                    .icon(() -> new ItemStack(ModItems.TITANIUM_KNIFE.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.COPPER_KNIFE.get());
                        output.accept(ModItems.TITANIUM_KNIFE.get());
                        output.accept(ModItems.DYEDREAM_KNIFE.get());
                        output.accept(ModItems.MELT_DREAM_KNIFE.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_KNIFE.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get());

                        output.accept(ModItems.GLASS_CUP_OF_BARLEY_TEA.get());

                        output.accept(ModBlocks.MOLTEN_GOLD_COOKING_POT.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
