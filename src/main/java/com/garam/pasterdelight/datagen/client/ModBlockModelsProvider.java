package com.garam.pasterdelight.datagen.client;

import com.garam.pasterdelight.PasterDelight;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelsProvider extends BlockStateProvider
{
    public ModBlockModelsProvider(PackOutput output, ExistingFileHelper exFileHelper)
    {
        super(output, PasterDelight.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        //在这里添加方块模型
    }
}
