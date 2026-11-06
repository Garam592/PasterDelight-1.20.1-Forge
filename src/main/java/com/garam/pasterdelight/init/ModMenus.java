package com.garam.pasterdelight.init;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot.MoltenGoldCookingPotBlockEntity;
import com.garam.pasterdelight.world.block.pasterdelightcookingpot.moltengoldcookingpot.MoltenGoldCookingPotMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus
{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PasterDelight.MOD_ID);

    public static final RegistryObject<MenuType<MoltenGoldCookingPotMenu>> MOLTEN_GOLD_COOKING_POT = MENUS.register("molten_gold_cooking_pot", () -> IForgeMenuType.create((windowId, playerInventory, buffer) ->
    {
        MoltenGoldCookingPotBlockEntity blockEntity = (MoltenGoldCookingPotBlockEntity)(playerInventory.player.level().getBlockEntity(buffer.readBlockPos()));
        return new MoltenGoldCookingPotMenu(windowId, playerInventory, blockEntity, blockEntity.getCookingPotData());
    }));

    public static void register(IEventBus eventBus)
    {
        MENUS.register(eventBus);
    }
}
