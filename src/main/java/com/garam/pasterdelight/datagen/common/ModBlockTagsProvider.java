package com.garam.pasterdelight.datagen.common;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider
{
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, PasterDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("farmersdelight", "pies")))
                .add(ModBlocks.DYEDREAM_FRUIT_PIE.get())
                .add(ModBlocks.FIG_PIE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("create", "brittle")))
                .add(ModBlocks.DYEDREAM_FRUIT_PIE.get())
                .add(ModBlocks.FIG_PIE.get());
        tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("farmersdelight", "mineable/knife")))
                .add(ModBlocks.DYEDREAM_FRUIT_PIE.get())
                .add(ModBlocks.FIG_PIE.get());
    }
}
