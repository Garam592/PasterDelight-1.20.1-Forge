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

        // Dyedream Knife
        add(ModItems.DYEDREAM_KNIFE.get(),"Dyedream Knife");
        add(ModItems.MELT_DREAM_KNIFE.get(),"Melt Dream Knife");
        add("tooltip.pasterdelight.melt_dream_knife.quick_cutting","§7▪ §9When using this knife to prep food on the cutting board, handle 16 at a time. Sneak and right-click to toggle this feature");
        add("tooltip.pasterdelight.melt_dream_knife.skill_name","Combat Art: §bFragdream Strike");
        add("tooltip.pasterdelight.melt_dream_knife.desc1","§7▪ §9When attacking, hitting a target once deals an additional 150% magic damage");
        add("tooltip.pasterdelight.melt_dream_knife.desc2","§7▪ §9Cooldown: 2s");

        // Wind Journey Knife
        add(ModItems.CONGEAL_WIND_IRON_KNIFE.get(),"Congeal Wind Iron Knife");
        add("tooltip.pasterdelight.congeal_wind_iron_knife.quick_cutting","§7▪ §9When using this knife to prep food on the cutting board, handle 8 at a time. Sneak and right-click to toggle this feature");
        add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get(),"Fluffy Wind Alloy Knife");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.quick_cutting","§7▪ §9When using this knife to prep food on the cutting board, handle 16 at a time. Sneak and right-click to toggle this feature");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.skill_name","Combat Art:§bWind Assault");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.desc1","§7▪ §9When attacking, hitting a target once deals damage two additional times");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.desc2","§7▪ §9Cooldown: 3s");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive_name","Passive:§bSwift as the wind");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive1","§7▪ §9This tool comes with Looting II built-in and can stack with enchantments");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive2","§7▪ §9The faster you move, the faster you mine");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive3","§7▪ §9Drops are swept to you by the wind when breaking blocks");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.custom_tip","§7§o--Swift as the wind, orderly as the forest, fierce as fire, unfathomable as darkness.");

        // Lamp Shadow Knife
        add(ModItems.SHADOW_EROSION_KNIFE.get(),"Shadow Erosion Knife");
        add("tooltip.pasterdelight.shadow_erosion_knife.quick_cutting","§7▪ §9When using this knife to prep food on the cutting board, handle 8 at a time. Sneak and right-click to toggle this feature");

        add("tooltip.pasterdelight.multi_cut_enabled","§a[Quick Cutting] Feature: On");
        add("tooltip.pasterdelight.multi_cut_disabled","§c[Quick Cutting] Feature: Off");

        add("message.pasterdelight.cutting_recipe_multiplying_enable", "§a[Cutting Recipe Multiplying] Enable");
        add("message.pasterdelight.cutting_recipe_multiplying_disable", "§c[Cutting Recipe Multiplying] Disable");

        add(ModItems.GLASS_CUP_OF_BARLEY_TEA.get(),"Glass Cup of Barley Tea");
        add("fluid.pasterdelight.barley_tea","Barley Tea (fluid)");

        add(ModBlocks.MOLTEN_GOLD_COOKING_POT.get(), "Molten Gold Cooking Pot");
        add("tooltip.pasterdelight.molten_gold_cooking_pot.1", "§7▪ §9A cooking pot that doesn’t need a heat source and speeds up the cooking of all meals and drinks.");
        add("tooltip.pasterdelight.molten_gold_cooking_pot.2", "§7▪ §9Allow adjusting the external input stack limit.");
        add("tooltip.pasterdelight.molten_gold_cooking_pot.3", "§7§o--Too hot to handle.");
        add("tooltip.pasterdelight.限制外部输入堆叠上限为1", "Limit External Input Stack Size To 1");
        add("tooltip.pasterdelight.不限制外部输入堆叠上限", "Unlimit External Input Stack Size");

        add("itemGroup.pasterdelight.creative_mode_tab","PasterDelight");

    }
}
