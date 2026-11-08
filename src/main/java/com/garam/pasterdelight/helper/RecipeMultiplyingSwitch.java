package com.garam.pasterdelight.helper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class RecipeMultiplyingSwitch
{
    public static boolean getStatus(Player player)
    {
        CompoundTag pasterDelightPersistentData = getPasterDelightPersistentData(player);
        if(pasterDelightPersistentData.contains("isCuttingRecipeMultiplyingEnable"))
        {
            return pasterDelightPersistentData.getBoolean("isCuttingRecipeMultiplyingEnable");
        }
            else
            {
                return false;
            }
    }

    public static void setStatus(Player player, boolean status)
    {
        getPasterDelightPersistentData(player).putBoolean("isCuttingRecipeMultiplyingEnable", status);
        player.displayClientMessage(Component.translatable(status ? "message.pasterdelight.cutting_recipe_multiplying_enable" : "message.pasterdelight.cutting_recipe_multiplying_disable"), true);
    }

    public static CompoundTag getPasterDelightPersistentData(Player player)
    {
        CompoundTag persistentData = player.getPersistentData();

        CompoundTag pasterDelightPersistentData;
        if(persistentData.contains("pasterDelight"))
        {
            pasterDelightPersistentData = persistentData.getCompound("pasterDelight");
        }
            else
            {
                pasterDelightPersistentData = new CompoundTag();
                persistentData.put("pasterDelight", pasterDelightPersistentData);
            }

        return pasterDelightPersistentData;
    }
}
