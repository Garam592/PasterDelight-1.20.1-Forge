package com.garam.pasterdelight.datagen.lang;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModBlocks;
import com.garam.pasterdelight.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsProvider extends LanguageProvider {
    public ModEnUsProvider(PackOutput packOutput)  {
        super(packOutput, PasterDelight.MOD_ID, "en_us");
    }
    @Override
    protected void addTranslations() {
        add(ModItems.TITANIUM_KNIFE.get(),"Titanium Knife");
        add(ModItems.COPPER_KNIFE.get(),"Copper Knife");
        add(ModItems.DYEDREAM_KNIFE.get(),"Dyedream Knife");
        add(ModItems.MELT_DREAM_KNIFE.get(),"Melt Dream Knife");

        add(ModItems.GLASS_CUP_OF_BARLEY_TEA.get(),"Glass Cup of Barley Tea");
        add("fluid.pasterdelight.barley_tea","Barley Tea (fluid)");

        add(ModBlocks.MOLTEN_GOLD_COOKING_POT.get(), "Molten Gold Cooking Pot");

        add("itemGroup.pasterdelight.creative_mode_tab","PasterDelight");

        add("tooltip.pasterdelight.限制外部输入堆叠上限为1", "Limit External Input Stack Size To 1");
        add("tooltip.pasterdelight.不限制外部输入堆叠上限", "Unlimit External Input Stack Size");
    }
}
