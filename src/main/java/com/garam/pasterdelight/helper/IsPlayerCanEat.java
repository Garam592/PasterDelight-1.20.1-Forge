package com.garam.pasterdelight.helper;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class IsPlayerCanEat
{
    public static boolean isPlayerCanEat(Player player, Item item)
    {
        return item.getFoodProperties() != null && (item.getFoodProperties().canAlwaysEat() || player.canEat(false));
    }
}
