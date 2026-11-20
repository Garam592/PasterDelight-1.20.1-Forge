package com.garam.pasterdelight.datagen.common;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModChestLootTables;
import com.garam.pasterdelight.loot.AddLootTableModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

// 生成全局战利品修改器 JSON：把 pasterdelight 的箱子表注入到帕斯特重生的 5 个箱子
public class ModLootModifierProvider extends GlobalLootModifierProvider
{
    public ModLootModifierProvider(PackOutput output)
    {
        super(output, PasterDelight.MOD_ID);
    }

    @Override
    protected void start()
    {
        this.add("add_loot_dyedream_relic_chest_loot_common",
                this.addNewLootPool(pasterdream("chests/dyedream_relic_chest_loot_common"),
                        ModChestLootTables.DYEDREAM_RELIC_CHEST_LOOT_COMMON));
        this.add("add_loot_dyedream_relic_chest_loot_rare",
                this.addNewLootPool(pasterdream("chests/dyedream_relic_chest_loot_rare"),
                        ModChestLootTables.DYEDREAM_RELIC_CHEST_LOOT_RARE));
        this.add("add_loot_melt_dream_crystal_chest_dyedream_world_normal",
                this.addNewLootPool(pasterdream("chests/melt_dream_crystal_chest_dyedream_world_normal"),
                        ModChestLootTables.MELT_DREAM_CRYSTAL_CHEST_DYEDREAM_WORLD_NORMAL));
        this.add("add_loot_melt_dream_crystal_chest_dyedream_world_rare",
                this.addNewLootPool(pasterdream("chests/melt_dream_crystal_chest_dyedream_world_rare"),
                        ModChestLootTables.MELT_DREAM_CRYSTAL_CHEST_DYEDREAM_WORLD_RARE));
        this.add("add_loot_melt_dream_crystal_chest_dyedream_world_legend",
                this.addNewLootPool(pasterdream("chests/melt_dream_crystal_chest_dyedream_world_legend"),
                        ModChestLootTables.MELT_DREAM_CRYSTAL_CHEST_DYEDREAM_WORLD_LEGEND));
    }

    private AddLootTableModifier addNewLootPool(ResourceLocation lootToAddTo, ResourceLocation newPool)
    {
        return new AddLootTableModifier(new LootItemCondition[]{LootTableIdCondition.builder(lootToAddTo).build()}, newPool);
    }

    private static ResourceLocation pasterdream(String path)
    {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", path);
    }
}
