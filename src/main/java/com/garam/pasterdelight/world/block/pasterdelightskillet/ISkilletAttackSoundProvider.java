package com.garam.pasterdelight.world.block.pasterdelightskillet;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * 供其它模组实现，让自己的「煎锅」物品被本模组识别，并可自由定制其击中音效。
 * 附属模组只需重写该方法即可替换成自己的声音。
 */
public interface ISkilletAttackSoundProvider
{
    /**
     * 返回命中目标时要播放的音效。
     *
     * @param stack    手中持有的物品（即实现了本接口的煎锅）
     * @param attacker 发起攻击的实体
     * @param strong   是否为强力攻击（攻击蓄力超过 0.8，或攻击者并非玩家）
     * @return 要播放的音效；默认返回农夫乐事的强击/弱击音效，重写可换成自己的声音
     */
    default SoundEvent getSkilletAttackSound(ItemStack stack, LivingEntity attacker, boolean strong)
    {
        return strong
                ? vectorwing.farmersdelight.common.registry.ModSounds.ITEM_SKILLET_ATTACK_STRONG.get()
                : vectorwing.farmersdelight.common.registry.ModSounds.ITEM_SKILLET_ATTACK_WEAK.get();
    }
}
