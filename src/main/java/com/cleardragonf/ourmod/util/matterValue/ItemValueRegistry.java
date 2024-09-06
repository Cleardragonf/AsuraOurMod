package com.cleardragonf.ourmod.util.matterValue;

import com.cleardragonf.ourmod.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ItemValueRegistry {
    public static final Map<Item, Integer> ITEM_VALUES = new HashMap<>();

    // Example: Default value for all items
    public static final int DEFAULT_ITEM_VALUE = 10;

    public static void setupItemValues() {
        // Attach values to vanilla items
        ITEM_VALUES.put(Items.DIAMOND, 500);
        ITEM_VALUES.put(Items.GOLD_INGOT, 300);
        ITEM_VALUES.put(Items.IRON_INGOT, 200);

        // Attach values to your mod items


        // Attach values to other mod items here if needed
        for(Item item : ForgeRegistries.ITEMS){
            ITEM_VALUES.putIfAbsent(item, DEFAULT_ITEM_VALUE);
        }
    }

    public static int getValueForItem(Item item) {
        return ITEM_VALUES.getOrDefault(item, DEFAULT_ITEM_VALUE);
    }
}
