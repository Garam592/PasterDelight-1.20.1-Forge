package com.garam.pasterdelight.datagen;


import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.datagen.client.ModBlockModelsProvider;
import com.garam.pasterdelight.datagen.common.ModBlockLootTablesProvider;
import com.garam.pasterdelight.datagen.client.ModItemModelsProvider;
import com.garam.pasterdelight.datagen.common.ModBlockTagsProvider;
import com.garam.pasterdelight.datagen.common.ModItemTagsProvider;
import com.garam.pasterdelight.datagen.common.ModRecipeProvider;
import com.garam.pasterdelight.datagen.lang.ModEnUsProvider;
import com.garam.pasterdelight.datagen.lang.ModZhCnProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = PasterDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //配方
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));

        //方块，物品Tag
        BlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

        //战利品
        //generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(), List.of
        //        (
        //            new LootTableProvider.SubProviderEntry(ModBlockLootTablesProvider::new, LootContextParamSets.BLOCK)
        //        )));

        //物品，方块模型
        generator.addProvider(event.includeClient(), new ModItemModelsProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockModelsProvider(packOutput, existingFileHelper));

        //语言文件
        generator.addProvider(event.includeClient(), new ModEnUsProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModZhCnProvider(packOutput));
    }

}

