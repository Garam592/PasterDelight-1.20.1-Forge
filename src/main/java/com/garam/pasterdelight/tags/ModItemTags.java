package com.garam.pasterdelight.tags;

import com.garam.pasterdelight.PasterDelight;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    // 带有该标签的食物将会走新系统恢复san
    public static final TagKey<Item> RECOVER_SANITY = bind("recover_sanity");

    public static final TagKey<Item> GIVE_COOK_BUFF = bind("give_cook_buff");

    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PasterDelight.MOD_ID, pName));
    }

    private static TagKey<Item> createForgeTag(String pName){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", pName));
    }

}
