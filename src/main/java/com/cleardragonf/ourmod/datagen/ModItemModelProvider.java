package com.cleardragonf.ourmod.datagen;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OurMod.MODID, existingFileHelper);
    }
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static{
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.1F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.1F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.1F);
        trimMaterials.put(TrimMaterials.COPPER, 0.1F);
        trimMaterials.put(TrimMaterials.GOLD, 0.1F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.1F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.1F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.1F);
        trimMaterials.put(TrimMaterials.AMETHYST, 0.1F);
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

        trimmedArmorItem(ModItems.JACKOSURPRISE_BOOTS);
        trimmedArmorItem(ModItems.JACKOSURPRISE_CHESTPLATE);
        trimmedArmorItem(ModItems.JACKOSURPRISE_LEGGINGS);
        trimmedArmorItem(ModItems.JACKOSURPRISE_HELMET);

        withExistingParent(ModItems.JACKOSURPRISE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(OurMod.MODID, "item/" + item.getId().getPath()));
    }

    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject){
        final String MOD_ID = OurMod.MODID;

        if(itemRegistryObject.get() instanceof ArmorItem armorItem){
            trimMaterials.entrySet().forEach(entry ->{
                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch(armorItem.getEquipmentSlot()){
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" +trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath);
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                this.withExistingParent(itemRegistryObject.getId().getPath(), mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID, "item/" + itemRegistryObject.get().asItem()));
            });
        }
    }
}
