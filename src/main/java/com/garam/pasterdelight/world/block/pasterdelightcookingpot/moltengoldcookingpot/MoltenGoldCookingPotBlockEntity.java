package com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModBlockEntities;
import com.garam.pasterdelight.world.block.pasterdelightcookingpot.PasterDelightCookingPotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MoltenGoldCookingPotBlockEntity extends PasterDelightCookingPotBlockEntity
{
    public MoltenGoldCookingPotBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlockEntities.MOLTEN_GOLD_COOKING_POT.get(), blockPos, blockState);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity)
    {
        return new MoltenGoldCookingPotMenu(id, player, this, this.cookingPotData);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDelight.MOD_ID + ".molten_gold_cooking_pot");
    }

    @Override
    public boolean isHeated(Level level, BlockPos blockPosition)
    {
        return true;
    }

    @Override
    public boolean isHeated()
    {
        return true;
    }
}
