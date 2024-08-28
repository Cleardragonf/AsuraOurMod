package com.cleardragonf.ourmod.datagen;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OurMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.RAW_MATTER);
        simpleItem(ModItems.PURIFIED_WATER_MATTER);
        simpleItem(ModItems.RAW_WATER_MATTER);
        simpleItem(ModItems.PURIFIED_WIND_MATTER);
        simpleItem(ModItems.RAW_WIND_MATTER);
        simpleItem(ModItems.RAW_FIRE_MATTER);
        simpleItem(ModItems.PURIFIED_WIND_MATTER);
        simpleItem(ModItems.PURIFIED_EARTH_MATTER);
        simpleItem(ModItems.RAW_EARTH_MATTER);
        simpleItem(ModItems.PURIFIED_DARKNESS_MATTER);
        simpleItem(ModItems.RAW_DARKNESS_MATTER);
        simpleItem(ModItems.RAW_LIGHT_MATTER);
        simpleItem(ModItems.PURIFIED_LIGHT_MATTER);
        simpleItem(ModItems.CONDENSED_MATTER);
        simpleItem(ModItems.MATTER_DETECTOR);

        withExistingParent(ModItems.JACKOSURPRISE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(OurMod.MODID, "item/" + item.getId().getPath()));
    }
}
