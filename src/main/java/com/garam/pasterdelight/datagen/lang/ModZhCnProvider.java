package com.garam.pasterdelight.datagen.lang;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModBlocks;
import com.garam.pasterdelight.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhCnProvider extends LanguageProvider {
    public ModZhCnProvider(PackOutput packOutput)  {
        super(packOutput, PasterDelight.MOD_ID, "zh_cn");
    }
    @Override
    protected void addTranslations() {
        add(ModItems.TITANIUM_KNIFE.get(),"钛金刀");
        add(ModItems.COPPER_KNIFE.get(),"铜刀");
        add(ModItems.DYEDREAM_KNIFE.get(),"染梦合金刀");
        add(ModItems.MELT_DREAM_KNIFE.get(),"融梦水晶刀");

        add(ModItems.GLASS_CUP_OF_BARLEY_TEA.get(),"大麦茶");
        add("fluid.pasterdelight.barley_tea","大麦茶（流体）");

        add(ModBlocks.MOLTEN_GOLD_COOKING_POT.get(), "炙焰金厨锅");

        //创造模式物品栏
        add("itemGroup.pasterdelight.creative_mode_tab","帕斯特乐事");
    }
}
