package com.garam.pasterdelight.datagen.client;

import com.garam.pasterdelight.PasterDelight;
import com.garam.pasterdelight.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PasterDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(ModItems.TITANIUM_KNIFE);
        handheldItem(ModItems.COPPER_KNIFE);
        handheldItem(ModItems.DYEDREAM_KNIFE);
        handheldItem(ModItems.MELT_DREAM_KNIFE);

        basicItem(ModItems.GLASS_CUP_OF_BARLEY_TEA.get());
    }

    @Override
    public ItemModelBuilder basicItem(Item item) {
        return basicItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));


    }

    @Override
    public ItemModelBuilder basicItem(ResourceLocation item) {
        String path = item.getPath();

        if (path.contains("/")) {
            path = folder + "/" + path;
        }
        return getBuilder(path)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.tryParse(item.getNamespace() + ":" + "item/" + item.getPath()));
    }

    public ItemModelBuilder itemModelWithResource(Item item, String resource)
    {
        String path = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        if (path.contains("/"))
        {
            path = folder + "/" + path;
        }
        return getBuilder(path).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", ResourceLocation.tryParse(PasterDelight.MOD_ID + ":" + "item/" + resource));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(PasterDelight.MOD_ID, "item/" + item.getId().getPath()));
    }
}
