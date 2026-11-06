package com.garam.pasterdelight.init;

import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;

public class ModFluidContainerRelation
{

    public static void ResisterModFluidContainerRelation(){
        FluidContainerRegistry.register(com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP.get(), ModFluids.BARLEY_TEA.get(), 250, ModItems.GLASS_CUP_OF_BARLEY_TEA.get());
    }
}
