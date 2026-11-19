package com.garam.pasterdelight.world.item.pasterdelightknife.knive;

/**
 * 暗影合金刀「自带背刺II」的共享状态。
 * 屏蔽内置背刺等级，避免 Farmer's Delight 的背刺事件对技能伤害二次触发。
 */
public final class ShadowAlloyKnifeBackstabbing {

    /** 内置背刺等级（背刺II） */
    public static final int BUILT_IN_LEVEL = 2;

    private static final ThreadLocal<Boolean> SUPPRESS = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private ShadowAlloyKnifeBackstabbing() {
    }

    /** 战技期间调用，屏蔽内置背刺等级 */
    public static void suppress(boolean suppress) {
        SUPPRESS.set(suppress);
    }

    /** 是否处于战技屏蔽期间 */
    public static boolean isSuppressed() {
        return SUPPRESS.get();
    }
}
