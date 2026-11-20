package com.garam.pasterdelight.datagen.common;

import com.garam.pasterdelight.init.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider
{
    public ModBlockLootTablesProvider()
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate()
    {
        dropNone(ModBlocks.DYEDREAM_FRUIT_PIE.get());
        dropNone(ModBlocks.FIG_PIE.get());

        dropSelf(ModBlocks.MOLTEN_GOLD_COOKING_POT.get());
        dropSelf(ModBlocks.MOLTEN_GOLD_SKILLET.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected void dropNone(Block block)
    {
        this.add(block, LootTable.lootTable());
    }
}
