package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot.MoltenGoldCookingPotBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PasterDelight.MOD_ID);

    public static final RegistryObject<BlockEntityType<MoltenGoldCookingPotBlockEntity>> MOLTEN_GOLD_COOKING_POT = BLOCK_ENTITIES.register("molten_gold_cooking_pot", () -> BlockEntityType.Builder.of(MoltenGoldCookingPotBlockEntity::new, ModBlocks.MOLTEN_GOLD_COOKING_POT.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
