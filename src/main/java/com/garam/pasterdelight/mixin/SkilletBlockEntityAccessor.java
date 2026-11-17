package com.garam.pasterdelight.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

import java.util.Optional;

@Mixin(value = SkilletBlockEntity.class, remap = false)
public interface SkilletBlockEntityAccessor
{
    @Accessor("skilletStack")
    ItemStack skilletStackAccessor();

    @Accessor("cookingTime")
    int getCookingTimeAccessor();

    @Mutable
    @Accessor("cookingTime")
    void setCookingTimeAccessor(int cookingTime);

    @Accessor("cookingTimeTotal")
    int cookingTimeTotalAccessor();

    @Mutable
    @Accessor("cookingTimeTotal")
    void setCookingTimeTotalAccessor(int cookingTimeTotal);

    @Accessor("fireAspectLevel")
    int fireAspectLevelAccessor();

    @Invoker("getMatchingRecipe")
    Optional<CampfireCookingRecipe> getMatchingRecipeInvoker(Container container);
}
