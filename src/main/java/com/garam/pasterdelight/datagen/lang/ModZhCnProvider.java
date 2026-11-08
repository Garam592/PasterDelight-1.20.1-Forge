package com.garam.pasterdelight.datagen.lang;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModBlocks;
import com.garam.pasterdelight.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
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

        //染梦系列刀
        add(ModItems.MELT_DREAM_KNIFE.get(),"融梦水晶刀");
        add("tooltip.pasterdelight.melt_dream_knife.quick_cutting","§7▪ §9使用该刀在砧板处理菜品时，一次处理4个，潜行右键以开关该功能");
        add("tooltip.pasterdelight.melt_dream_knife.skill_name","战技：§b碎梦刺击");
        add("tooltip.pasterdelight.melt_dream_knife.desc1","§7▪ §9攻击时，击中一次实体，额外造成150%的魔法伤害");
        add("tooltip.pasterdelight.melt_dream_knife.desc2","§7▪ §9冷却：2秒");

        //风旅系列刀
        add(ModItems.CONGEAL_WIND_IRON_KNIFE.get(),"凝风铁刀");
        add("tooltip.pasterdelight.congeal_wind_iron_knife.quick_cutting","§7▪ §9使用该刀在砧板处理菜品时，一次处理8个，潜行右键以开关该功能");
        add(ModItems.FLUFFY_WIND_ALLOY_KNIFE.get(),"萦风合金刀");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.quick_cutting","§7▪ §9使用该刀在砧板处理菜品时，一次处理16个，潜行右键以开关该功能");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.skill_name","战技：§b风袭速攻");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.desc1","§7▪ §9攻击时，击中一次实体，额外造成2次伤害");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.desc2","§7▪ §9冷却：3秒");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive_name","被动：§b其疾如风");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive1","§7▪ §9该工具自带抢夺II效果，可以和附魔叠加");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive2","§7▪ §9移动速度越快，挖掘速度越快");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.passive3","§7▪ §9破坏方块时，掉落物会被风卷至身边");
        add("tooltip.pasterdelight.fluffy_wind_alloy_knife.custom_tip","§7§o--疾如风，徐如林，掠如火，难知如阴");

        //灯影系列刀
        add(ModItems.SHADOW_EROSION_KNIFE.get(),"影蚀刀");
        add("tooltip.pasterdelight.shadow_erosion_knife.quick_cutting","§7▪ §9使用该刀在砧板处理菜品时，一次处理8个，潜行右键以开关该功能");

        add("message.pasterdelight.cutting_recipe_multiplying_enable", "§a[批量砧板配方处理] 开启");
        add("message.pasterdelight.cutting_recipe_multiplying_disable", "§c[批量砧板配方处理] 关闭");

        add(ModItems.GLASS_CUP_OF_BARLEY_TEA.get(),"大麦茶");
        add("fluid.pasterdelight.barley_tea","大麦茶（流体）");

        add(ModBlocks.MOLTEN_GOLD_COOKING_POT.get(), "炙焰金厨锅");
        add("tooltip.pasterdelight.molten_gold_cooking_pot.1", "§7▪ §9无需热源并提升所有菜肴和饮品的烹饪速度的厨锅");
        add("tooltip.pasterdelight.molten_gold_cooking_pot.2", "§7▪ §9允许调整外部输入堆叠上限");
        add("tooltip.pasterdelight.molten_gold_cooking_pot.3", "§7§o--炙手可热");
        add("tooltip.pasterdelight.限制外部输入堆叠上限为1", "限制外部输入堆叠上限为1");
        add("tooltip.pasterdelight.不限制外部输入堆叠上限", "不限制外部输入堆叠上限");

        //创造模式物品栏
        add("itemGroup.pasterdelight.creative_mode_tab","帕斯特乐事");
    }
}
