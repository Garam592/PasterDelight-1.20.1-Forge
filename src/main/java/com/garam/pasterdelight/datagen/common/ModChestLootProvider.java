package com.garam.pasterdelight.datagen.common;

import com.garam.pasterdelight.init.ModChestLootTables;
import com.garam.pasterdelight.init.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

// 生成注入帕斯特重生箱子用的独立战利品表，里面放染梦合金刀
public class ModChestLootProvider implements LootTableSubProvider
{
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        // 染梦遗迹·普通/稀有：刀 1 : 空 50 → 1/51 ≈ 1.96%，bonus_rolls 0.15 跟原表装备池一致
        consumer.accept(ModChestLootTables.DYEDREAM_RELIC_CHEST_LOOT_COMMON, knifeTable(1, 50, 0.15F));
        consumer.accept(ModChestLootTables.DYEDREAM_RELIC_CHEST_LOOT_RARE, knifeTable(1, 50, 0.15F));
        // 融梦水晶·普通/稀有/传说：刀 3 : 空 50 → 3/53 ≈ 5.66%，原表装备池无 bonus_rolls
        consumer.accept(ModChestLootTables.MELT_DREAM_CRYSTAL_CHEST_DYEDREAM_WORLD_NORMAL, knifeTable(3, 50, 0.0F));
        consumer.accept(ModChestLootTables.MELT_DREAM_CRYSTAL_CHEST_DYEDREAM_WORLD_RARE, knifeTable(3, 50, 0.0F));
        consumer.accept(ModChestLootTables.MELT_DREAM_CRYSTAL_CHEST_DYEDREAM_WORLD_LEGEND, knifeTable(3, 50, 0.0F));
    }

    // 单次开箱掉刀概率 = knifeWeight / (knifeWeight + emptyWeight)；bonusRolls 为每点幸运额外抽的次数
    private static LootTable.Builder knifeTable(int knifeWeight, int emptyWeight, float bonusRolls)
    {
        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(ModItems.DYEDREAM_KNIFE.get()).setWeight(knifeWeight))
                .add(EmptyLootItem.emptyItem().setWeight(emptyWeight));
        if (bonusRolls > 0.0F)
        {
            pool.setBonusRolls(ConstantValue.exactly(bonusRolls));
        }
        return LootTable.lootTable().withPool(pool);
    }
}
