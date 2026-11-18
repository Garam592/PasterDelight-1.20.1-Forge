package com.garam.pasterdelight.event;

import com.garam.pasterdelight.tags.ModItemTags;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FoodValueTooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class TagsFoodSanHandler
{
    public static final double SAN_PER_SATURATION = 4.8;
    public static final double MIN_EFFECTIVE_SATURATION_FOR_SAN = 6;

    public static boolean shouldRecoverSanity(ItemStack stack)
    {
        if (!stack.is(ModItemTags.PASTERDELIGHT_RECOVER_SANITY))
        {
            return false;
        }
        FoodProperties food = stack.getItem().getFoodProperties();
        return food != null && 2.0 * food.getNutrition() * food.getSaturationModifier() >= MIN_EFFECTIVE_SATURATION_FOR_SAN;
    }

    public static double computeSan(FoodProperties food)
    {
        return 2.0 * food.getNutrition() * food.getSaturationModifier() / SAN_PER_SATURATION;
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (!shouldRecoverSanity(stack))
        {
            return;
        }
        FoodProperties food = stack.getItem().getFoodProperties();
        if (food == null)
        {
            return;
        }

        Component sanComponent = buildSanComponent(computeSan(food));
        if (sanComponent == null)
        {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        int itemIdIndex = findItemIdIndex(tooltip, stack);
        if (itemIdIndex >= 0)
        {
            tooltip.add(itemIdIndex, sanComponent);
        }
        else
        {
            tooltip.add(sanComponent);
        }
    }

    @SubscribeEvent
    public static void onFinishUsingItem(LivingEntityUseItemEvent.Finish event)
    {
        if (event.getEntity().level().isClientSide)
        {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player))
        {
            return;
        }
        ItemStack stack = event.getItem();
        if (!shouldRecoverSanity(stack))
        {
            return;
        }
        FoodProperties food = stack.getItem().getFoodProperties();
        if (food == null)
        {
            return;
        }
        SanHelper.addPlayerSanAndSync(player, computeSan(food));
    }

    // 复用帕斯特的 san tooltip 文案与格式化，但不直接追加，以便插入到物品 id 上方
    private static Component buildSanComponent(double san)
    {
        List<Component> tmp = new ArrayList<>(1);
        FoodValueTooltips.appendSanTooltip(tmp, san);
        return tmp.isEmpty() ? null : tmp.get(0);
    }

    // 高级显示框(F3+H)会在 tooltip 末尾追加物品 id（形如 "farmersdelight:beef_stew"），
    // 从后往前找到该行，把回 san 提示插到它上面
    private static int findItemIdIndex(List<Component> tooltip, ItemStack stack)
    {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null)
        {
            return -1;
        }
        String idString = id.toString();
        for (int i = tooltip.size() - 1; i >= 0; i--)
        {
            if (idString.equals(tooltip.get(i).getString()))
            {
                return i;
            }
        }
        return -1;
    }
}
