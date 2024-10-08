package com.cleardragonf.ourmod.screens;

import com.cleardragonf.ourmod.OurMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, OurMod.MODID);

    public static final RegistryObject<MenuType<MatterConversionMenu>> MATTER_CONVERSION_MENU =
            registerMenuType("matter_conversion_menu", MatterConversionMenu::new);
    public static final RegistryObject<MenuType<MatterGeneratorMenu>> MATTER_GENERATOR_MENU =
            registerMenuType("matter_generator_menu", MatterGeneratorMenu::new);
    public static final RegistryObject<MenuType<MatterCollectionMenu>> MATTER_COLLECTION_MENU =
            registerMenuType("matter_collection_menu", MatterCollectionMenu::new);
    public static final RegistryObject<MenuType<ManaBatteryMenu>> MANA_BATTERY_MENU =
            registerMenuType("mana_battery_menu", ManaBatteryMenu::new);


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus bus){
        MENUS.register(bus);
    }
}
