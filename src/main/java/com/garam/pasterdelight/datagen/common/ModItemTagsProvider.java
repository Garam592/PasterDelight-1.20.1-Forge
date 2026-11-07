package com.garam.pasterdelight.datagen.common;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, PasterDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // 为刀子注册原版农夫乐事的完备标签
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "tools")))
                .add(ModItems.TITANIUM_KNIFE.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.DYEDREAM_KNIFE.get())
                .add(ModItems.MELT_DREAM_KNIFE.get())
                .add(ModItems.CONGEAL_WIND_IRON_KNIFE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("farmersdelight", "straw_harvesters")))
                .add(ModItems.TITANIUM_KNIFE.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.DYEDREAM_KNIFE.get())
                .add(ModItems.MELT_DREAM_KNIFE.get())
                .add(ModItems.CONGEAL_WIND_IRON_KNIFE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "tools/knives")))
                .add(ModItems.TITANIUM_KNIFE.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.DYEDREAM_KNIFE.get())
                .add(ModItems.MELT_DREAM_KNIFE.get())
                .add(ModItems.CONGEAL_WIND_IRON_KNIFE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("farmersdelight", "tools/knives")))
                .add(ModItems.TITANIUM_KNIFE.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.DYEDREAM_KNIFE.get())
                .add(ModItems.MELT_DREAM_KNIFE.get())
                .add(ModItems.CONGEAL_WIND_IRON_KNIFE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get());

        tag(ItemTags.BREAKS_DECORATED_POTS)
                .add(ModItems.TITANIUM_KNIFE.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.DYEDREAM_KNIFE.get())
                .add(ModItems.MELT_DREAM_KNIFE.get())
                .add(ModItems.CONGEAL_WIND_IRON_KNIFE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get());

        tag(ItemTags.TOOLS)
                .add(ModItems.TITANIUM_KNIFE.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.DYEDREAM_KNIFE.get())
                .add(ModItems.MELT_DREAM_KNIFE.get())
                .add(ModItems.CONGEAL_WIND_IRON_KNIFE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("pasterdream", "skill_cooldown")))
                .add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get());
    }
}
