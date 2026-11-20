package com.garam.pasterdelight;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod.EventBusSubscriber(modid = PasterDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.DoubleValue SAN_PER_SATURATION = BUILDER
                .comment("帕斯特的食物回复san系统配置"
                        + "\n带有pasterdelight:recover_sanity标签食物每多少饱和度回复1san（保留1位小数），默认为6")
               .defineInRange("san_per_saturation", 6.0, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue MIN_EFFECTIVE_SATURATION_FOR_SAN = BUILDER
            .comment("带有pasterdelight:recover_sanity标签食物能恢复san所需的最小饱和度，默认为6")
            .defineInRange("min_effective_saturation_for_san", 6.0, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue MAX_EFFECTIVE_SATURATION_FOR_SAN = BUILDER
            .comment("带有pasterdelight:recover_sanity标签食物能恢复san限制的最大饱和度，默认为30，食物饱和度大于该值将会按照最大值对应的回san量给予回复")
            .defineInRange("max_effective_saturation_for_san", 30.0, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue COOK_BASE_SECONDS = BUILDER
            .comment("带有pasterdelight:give_cook_buff标签食物初始能获得的料理buff时长（单位：秒），默认为30")
            .defineInRange("cook_base_seconds", 30, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue COOK_SATURATION_PER_STEP = BUILDER
            .comment("带有pasterdelight:give_cook_buff标签食物每多少饱和度增加料理buff持续时间，默认为5")
            .defineInRange("cook_saturation_per_step", 5, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue COOK_SECONDS_PER_STEP = BUILDER
            .comment("带有pasterdelight:give_cook_buff标签食物每次增加多少料理buff时长（单位：秒），默认为30")
            .defineInRange("cook_seconds_per_step", 30, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue COOK_SATURATION_PER_LEVEL = BUILDER
            .comment("带有pasterdelight:give_cook_buff标签食物每多少饱和度增加料理buff效果等级，默认为15")
            .defineInRange("cook_saturation_per_level", 15, 1, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();
    public static double SanPerSaturation;
    public static double MinEffectiveSaturationForSan;
    public static double MaxEffectiveSaturationForSan;
    public static int CookBaseSeconds;
    public static int CookSaturationPerStep;
    public static int CookSecondsPerStep;
    public static int CookSaturationPerLevel;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) return;
        SanPerSaturation = SAN_PER_SATURATION.get();
        MinEffectiveSaturationForSan = MIN_EFFECTIVE_SATURATION_FOR_SAN.get();
        MaxEffectiveSaturationForSan = MAX_EFFECTIVE_SATURATION_FOR_SAN.get();
        CookBaseSeconds = COOK_BASE_SECONDS.get();
        CookSaturationPerStep = COOK_SATURATION_PER_STEP.get();
        CookSecondsPerStep = COOK_SECONDS_PER_STEP.get();
        CookSaturationPerLevel = COOK_SATURATION_PER_LEVEL.get();

    }
}
