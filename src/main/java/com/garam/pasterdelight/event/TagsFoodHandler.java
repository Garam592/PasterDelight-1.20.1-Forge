package com.garam.pasterdelight.event;

import com.garam.pasterdelight.Config;
import com.garam.pasterdelight.tags.ModItemTags;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FoodValueTooltips;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class TagsFoodHandler
{
    // 饱食度：两套系统同口径，使用 2 * 饥饿值 * 饱和度系数
    private static double computeSaturation(FoodProperties food)
    {
        return 2.0 * food.getNutrition() * food.getSaturationModifier();
    }

    // 带 pasterdelight_recover_sanity 标签且饱食度达到阈值才回 san
    private static boolean shouldRecoverSanity(ItemStack stack)
    {
        if (!stack.is(ModItemTags.RECOVER_SANITY))
        {
            return false;
        }
        FoodProperties food = stack.getItem().getFoodProperties();
        return food != null && computeSaturation(food) >= Config.MinEffectiveSaturationForSan;
    }

    private static double computeSan(FoodProperties food)
    {
        double MaxSanRecover = Config.MaxEffectiveSaturationForSan/Config.SanPerSaturation;
        return Math.min(computeSaturation(food) / Config.SanPerSaturation,MaxSanRecover);
    }

    // 带 give_cook_buff 标签的食物给予料理
    private static boolean shouldGiveCookBuff(ItemStack stack)
    {
        if (!stack.is(ModItemTags.GIVE_COOK_BUFF))
        {
            return false;
        }
        return stack.getItem().getFoodProperties() != null;
    }

    private static int computeCookSeconds(FoodProperties food)
    {
        return Config.CookBaseSeconds + Config.CookSecondsPerStep * (int) (computeSaturation(food) / Config.CookSaturationPerStep);
    }

    private static int computeCookAmplifier(FoodProperties food)
    {
        return (int) (computeSaturation(food) / Config.CookSaturationPerLevel);
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        FoodProperties food = stack.getItem().getFoodProperties();
        if (food == null)
        {
            return;
        }

        List<Component> extra = new ArrayList<>(2);

        if (shouldRecoverSanity(stack))
        {
            List<Component> tmp = new ArrayList<>(1);
            FoodValueTooltips.appendSanTooltip(tmp, computeSan(food));
            if (!tmp.isEmpty())
            {
                extra.add(tmp.get(0));
            }
        }

        if (shouldGiveCookBuff(stack))
        {
            List<Component> tmp = new ArrayList<>(1);
            FoodValueTooltips.appendCookTooltip(tmp, computeCookSeconds(food) * 20, computeCookAmplifier(food));
            if (!tmp.isEmpty())
            {
                extra.add(tmp.get(0));
            }
        }

        if (extra.isEmpty())
        {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        int itemIdIndex = findItemIdIndex(tooltip, stack);
        if (itemIdIndex >= 0)
        {
            tooltip.addAll(itemIdIndex, extra);
        }
        else
        {
            tooltip.addAll(extra);
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
        FoodProperties food = stack.getItem().getFoodProperties();
        if (food == null)
        {
            return;
        }

        if (shouldRecoverSanity(stack))
        {
            SanHelper.addPlayerSanAndSync(player, computeSan(food));
        }
        if (shouldGiveCookBuff(stack))
        {
            player.addEffect(new MobEffectInstance(ModEffects.COOK.get(), computeCookSeconds(food) * 20, computeCookAmplifier(food)));
        }
    }

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
