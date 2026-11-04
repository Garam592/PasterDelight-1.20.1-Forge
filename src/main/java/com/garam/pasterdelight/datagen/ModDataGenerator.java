package com.garam.pasterdelight.datagen;


import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.datagen.lang.ModEnUsProvider;
import com.garam.pasterdelight.datagen.lang.ModZhCnProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PasterDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModItemModelsProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModEnUsProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModZhCnProvider(packOutput));
    }

}

