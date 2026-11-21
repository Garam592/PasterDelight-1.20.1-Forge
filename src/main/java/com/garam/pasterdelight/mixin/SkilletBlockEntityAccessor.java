package com.garam.pasterdelight.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

import java.util.Optional;

@Mixin(value = SkilletBlockEntity.class, remap = false)
public interface SkilletBlockEntityAccessor
{
    @Accessor("inventory")
    ItemStackHandler inventoryAccessor();

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

    @Mutable
    @Accessor("lastRecipeID")
    void setLastRecipeIDAccessor(ResourceLocation resourceLocation);

    @Accessor("fireAspectLevel")
    int fireAspectLevelAccessor();

    @Invoker("getMatchingRecipe")
    Optional<CampfireCookingRecipe> getMatchingRecipeInvoker(Container container);
}
