package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.world.fluid.BarleyTeaFluid;
import com.garam.pasterdelight.world.fluidtype.BarleyTeaFluidType;
import com.pasterdream.pasterdreammod.world.fluid.AppleJuiceFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PasterDelight.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, PasterDelight.MOD_ID);

    public static final RegistryObject<FluidType> BARLEY_TEA_TYPE = FLUID_TYPES.register("barley_tea", () -> BarleyTeaFluidType.TYPE);
    public static final RegistryObject<Fluid> BARLEY_TEA = FLUIDS.register("barley_tea", BarleyTeaFluid::new);

    public static void register(IEventBus eventBus)
    {

        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
