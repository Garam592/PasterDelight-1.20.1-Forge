package com.garam.pasterdelight.world.fluid;

import com.garam.pasterdelight.init.ModFluids;
import com.pasterdream.pasterdreammod.world.fluid.PasterDreamBaseFluid;
import net.minecraftforge.fluids.FluidType;

public class ShroomclawEnergyFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.SHROOMCLAW_ENERGY_TYPE.get();
    }
}
