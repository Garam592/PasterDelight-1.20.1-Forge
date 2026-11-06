package com.garam.pasterdelight.world.fluid;

import com.garam.pasterdelight.init.ModFluids;
import com.pasterdream.pasterdreammod.world.fluid.PasterDreamBaseFluid;
import net.minecraftforge.fluids.FluidType;

public class BarleyTeaFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.BARLEY_TEA_TYPE.get();
    }
}
