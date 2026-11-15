package com.garam.pasterdelight.init;

import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;

public class ModFluidContainerRelation
{

    public static void ResisterModFluidContainerRelation(){
        FluidContainerRegistry.register(com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP.get(), ModFluids.BARLEY_TEA.get(), 250, ModItems.GLASS_CUP_OF_BARLEY_TEA.get());
        FluidContainerRegistry.register(com.pasterdream.pasterdreammod.init.ModItems.GLASS_CUP.get(), ModFluids.FIG_JUICE.get(), 250, ModItems.GLASS_CUP_OF_FIG_JUICE.get());
        FluidContainerRegistry.register(com.pasterdream.pasterdreammod.init.ModItems.MARTINI_GLASS.get(), ModFluids.AXOLOTL_SPECIAL_DRINK.get(), 250, ModItems.MARTINI_GLASS_OF_AXOLOTL_SPECIAL_DRINK.get());
    }
}
