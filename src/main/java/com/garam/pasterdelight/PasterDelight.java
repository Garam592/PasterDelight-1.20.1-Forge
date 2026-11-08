package com.garam.pasterdelight;

import com.garam.pasterdelight.init.*;
import com.garam.pasterdelight.world.item.knife.FluffyWindAlloyKnifeItem;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.UUID;

@Mod(PasterDelight.MOD_ID)
public class PasterDelight
{
    public static final String MOD_ID = "pasterdelight";
    private static final Logger LOGGER = LogUtils.getLogger();

    // 与帕斯特重生(PasterDream)共用的凝风铁刀实体范围修饰符 UUID
    private static final UUID CONGEAL_WIND_IRON_REACH_UUID =
            UUID.fromString("7c2a9f4e-5d81-4b3a-9e6c-1f8d0a2b4c6e");

    public PasterDelight(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModFluids.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModMenus.register(modEventBus);
        ModNetWork.register();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        MinecraftForge.EVENT_BUS.addListener(PasterDelight::onItemAttributeModifier);
        MinecraftForge.EVENT_BUS.register(FluffyWindAlloyKnifeItem.LootingHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ModFluidContainerRelation::ResisterModFluidContainerRelation);
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        ModScreens.register(event);
    }

    public static void onItemAttributeModifier(ItemAttributeModifierEvent event)
    {
        if (event.getSlotType() != EquipmentSlot.MAINHAND) return;

        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        if (item == ModItems.CONGEAL_WIND_IRON_KNIFE.get()) {
            event.addModifier(
                    ForgeMod.ENTITY_REACH.get(),
                    new AttributeModifier(
                            CONGEAL_WIND_IRON_REACH_UUID,
                            "Congeal Wind Iron reach bonus",
                            1.0,
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
    }
}
