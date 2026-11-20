package com.garam.pasterdelight.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import java.util.function.Supplier;

import static net.minecraft.world.level.storage.loot.LootTable.createStackSplitter;

// 参照农夫乐事 AddLootTableModifier：把一张独立箱子表合并进目标战利品表
public class AddLootTableModifier extends LootModifier
{
    public static final Supplier<Codec<AddLootTableModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter(m -> m.lootTable))
                    .apply(inst, AddLootTableModifier::new)));

    private final ResourceLocation lootTable;

    public AddLootTableModifier(LootItemCondition[] conditions, ResourceLocation lootTable)
    {
        super(conditions);
        this.lootTable = lootTable;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        LootTable extraTable = context.getResolver().getLootTable(this.lootTable);
        extraTable.getRandomItemsRaw(context, createStackSplitter(context.getLevel(), generatedLoot::add));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC.get();
    }
}
