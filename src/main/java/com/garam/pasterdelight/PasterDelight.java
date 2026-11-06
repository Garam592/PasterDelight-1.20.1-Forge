package com.garam.pasterdelight;

import com.garam.pasterdelight.init.ModFluidContainerRelation;
import com.mojang.logging.LogUtils;
import com.garam.pasterdelight.init.ModItems;
import com.garam.pasterdelight.init.ModCreativeModeTabs;
import com.garam.pasterdelight.init.ModFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
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
        ModCreativeModeTabs.register(modEventBus);
        ModFluids.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModFluidContainerRelation.ResisterModFluidContainerRelation();
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

}
