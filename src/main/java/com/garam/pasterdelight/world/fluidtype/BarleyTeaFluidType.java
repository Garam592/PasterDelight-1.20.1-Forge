package com.garam.pasterdelight.world.fluidtype;

import com.garam.pasterdelight.PasterDelight;
import com.pasterdream.pasterdreammod.world.fluidtype.PasterDreamBaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public class BarleyTeaFluidType extends PasterDreamBaseFluidType
{
    public static final FluidType TYPE = new BarleyTeaFluidType();

    private BarleyTeaFluidType()
    {
        super(Properties.create().density(1000).viscosity(1000).temperature(300).canExtinguish(true).canSwim(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY), ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still"), ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_flow"), 0x7FFFFF00);
    }

    @Override
    public String getDescriptionId()
    {
        return "fluid." + PasterDelight.MOD_ID + ".barley_tea";
    }
}
