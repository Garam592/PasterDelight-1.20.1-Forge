package com.garam.pasterdelight.world.fluid;

import com.garam.pasterdelight.init.ModFluids;
import com.pasterdream.pasterdreammod.world.fluid.PasterDreamBaseFluid;
import net.minecraftforge.fluids.FluidType;

public class AxolotlSpecialDrinkFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.AXOLOTL_SPECIAL_DRINK_TYPE.get();
    }
}
