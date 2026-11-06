package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.world.block.pasterdreamcookingpot.moltengoldcookingpot.MoltenGoldCookingPotBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PasterDelight.MOD_ID);

    public static final RegistryObject<Block> MOLTEN_GOLD_COOKING_POT = BLOCKS.register("molten_gold_cooking_pot", MoltenGoldCookingPotBlock::new);

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
