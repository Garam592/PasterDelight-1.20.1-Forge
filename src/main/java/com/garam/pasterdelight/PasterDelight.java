package com.garam.pasterdelight;

import com.garam.pasterdelight.init.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PasterDelight.MOD_ID)
public class PasterDelight
{
    public static final String MOD_ID = "pasterdelight";
    private static final Logger LOGGER = LogUtils.getLogger();

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
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModFluidContainerRelation.ResisterModFluidContainerRelation();
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        ModScreens.register(event);
    }
}
