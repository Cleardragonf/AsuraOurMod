package com.cleardragonf.ourmod.item;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, OurMod.MODID);

    public static final RegistryObject<Item> CONDENSED_MATTER = ITEMS.register("condensed_matter",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_MATTER = ITEMS.register("raw_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_FIRE_MATTER = ITEMS.register("raw_fire_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_EARTH_MATTER = ITEMS.register("raw_earth_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_WIND_MATTER = ITEMS.register("raw_wind_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_WATER_MATTER = ITEMS.register("raw_water_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_DARKNESS_MATTER = ITEMS.register("raw_darkness_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_LIGHT_MATTER = ITEMS.register("raw_light_matter",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PURIFIED_WATER_MATTER = ITEMS.register("purified_water_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURIFIED_WIND_MATTER = ITEMS.register("purified_wind_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURIFIED_EARTH_MATTER = ITEMS.register("purified_earth_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURIFIED_FIRE_MATTER = ITEMS.register("purified_fire_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURIFIED_DARKNESS_MATTER = ITEMS.register("purified_darkness_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURIFIED_LIGHT_MATTER = ITEMS.register("purified_light_matter",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> JACKOSURPRISE_EGG = ITEMS.register("jackosurprise_egg", () ->
            new ForgeSpawnEggItem(ModEntities.JACKOSURPRISE, 0x7e9680, 0xc5d1c5,
                    new Item.Properties()));


    public static final RegistryObject<Item> MATTER_DETECTOR = ITEMS.register("matter_detector",
            () -> new MatterDetectorItem(new Item.Properties().durability(100 )));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
