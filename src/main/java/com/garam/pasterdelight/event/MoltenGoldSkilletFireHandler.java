package com.garam.pasterdelight.event;

import com.garam.pasterdelight.world.block.pasterdelightskillet.moltengoldskillet.MoltenGoldSkilletBlock;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vectorwing.farmersdelight.common.item.SkilletItem;

//炙焰金煎锅的攻击效果：击中点燃目标，若目标已处于燃烧状态则额外 +20% 伤害

public class MoltenGoldSkilletFireHandler
{
    private static final int BURN_SECONDS = 7;
    private static final float BURNING_BONUS_DAMAGE = 0.2F;

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide)
        {
            return;
        }
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getEntity();
        if (!(sourceEntity instanceof LivingEntity attacker))
        {
            return;
        }
        ItemStack mainHand = attacker.getMainHandItem();
        if (!(mainHand.getItem() instanceof SkilletItem skilletItem)
                || !(skilletItem.getBlock() instanceof MoltenGoldSkilletBlock))
        {
            return;
        }
        if (target.isOnFire())
        {
            event.setAmount(event.getAmount() * (1.0F + BURNING_BONUS_DAMAGE));
        }
        target.setSecondsOnFire(BURN_SECONDS);
    }
}
