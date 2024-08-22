package com.cleardragonf.ourmod.item;

import com.cleardragonf.ourmod.OurMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
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
            () -> new Item(new Item.Properties().durability(100 )));


    public static final RegistryObject<Item> MATTER_DETECTOR = ITEMS.register("matter_detector",
            () -> new MatterDetectorItem(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
