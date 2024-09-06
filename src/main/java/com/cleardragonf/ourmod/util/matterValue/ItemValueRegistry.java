package com.cleardragonf.ourmod.util.matterValue;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = OurMod.MODID)
public class ItemValueRegistry {
    public static final Map<Item, Integer> ITEM_VALUES = new HashMap<>();

    // Example: Default value for all items
    public static final int DEFAULT_ITEM_VALUE = 10;

    public static void setupItemValues() {
        // Attach values to vanilla items
        ITEM_VALUES.put(Items.DIAMOND, 500);
        ITEM_VALUES.put(Items.GOLD_NUGGET, 55);
        ITEM_VALUES.put(Items.IRON_NUGGET, 30);
        ITEM_VALUES.put(Items.EMERALD, 600);
        ITEM_VALUES.put(Items.LAPIS_LAZULI, 300);
        ITEM_VALUES.put(Items.REDSTONE, 120);






        // Attach values to other mod items if needed
        for (Item item : ForgeRegistries.ITEMS) {
            ITEM_VALUES.putIfAbsent(item, DEFAULT_ITEM_VALUE);
        }
    }

    public static int getValueForItem(Item item) {
        return ITEM_VALUES.getOrDefault(item, DEFAULT_ITEM_VALUE);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        // Once the server has started, calculate the recipe-based values
        // Attach values to your mod items
        ITEM_VALUES.put(ModItems.RAW_DARKNESS_MATTER.get(), 100);
        ITEM_VALUES.put(ModItems.RAW_LIGHT_MATTER.get(), 100);
        ITEM_VALUES.put(ModItems.RAW_MATTER.get(), 100);
        ITEM_VALUES.put(ModItems.RAW_EARTH_MATTER.get(), 100);
        ITEM_VALUES.put(ModItems.RAW_WIND_MATTER.get(), 100);
        ITEM_VALUES.put(ModItems.RAW_FIRE_MATTER.get(), 100);
        ITEM_VALUES.put(ModItems.RAW_WATER_MATTER.get(), 100);

        System.out.print("this is working???");
        calculateSmeltingValues();
        calculateRecipeValues();
    }
    public static void calculateSmeltingValues() {
        ServerLevel serverWorld = ServerLifecycleHooks.getCurrentServer().overworld();
        if (serverWorld == null) {
            return;
        }

        RecipeManager recipeManager = serverWorld.getRecipeManager();
        List<SmeltingRecipe> smeltingRecipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING);

        for (SmeltingRecipe recipe : smeltingRecipes) {
            ItemStack output = recipe.getResultItem(serverWorld.registryAccess());
            int totalValue = 0;

            // Get the input item for the smelting recipe
            ItemStack input = recipe.getIngredients().get(0).getItems()[0];
            Item inputItem = input.getItem();

            // Calculate the value based on the input item
            totalValue += ITEM_VALUES.getOrDefault(inputItem, 78);

            // Increment the value of the resulting item based on the input item
            int currentValue = ITEM_VALUES.getOrDefault(output.getItem(), DEFAULT_ITEM_VALUE);
            ITEM_VALUES.put(output.getItem(), currentValue + totalValue);
        }
    }

    public static void calculateRecipeValues() {
        ServerLevel serverWorld = ServerLifecycleHooks.getCurrentServer().overworld();
        if (serverWorld == null) {
            return;
        }

        RecipeManager recipeManager = serverWorld.getRecipeManager();
        List<CraftingRecipe> recipes = recipeManager.getAllRecipesFor(RecipeType.CRAFTING);

        for (CraftingRecipe recipe : recipes) {
            ItemStack output = recipe.getResultItem(serverWorld.registryAccess());
            int totalValue = 0;

            for (Ingredient ingredient : recipe.getIngredients()) {
                for (ItemStack stack : ingredient.getItems()) {
                    Item item = stack.getItem();
                    totalValue += ITEM_VALUES.getOrDefault(item, 0);
                }
            }

            // If totalValue is greater than the default, use it
            if (totalValue > ITEM_VALUES.getOrDefault(output.getItem(), DEFAULT_ITEM_VALUE)) {
                ITEM_VALUES.put(output.getItem(), totalValue);
            }
        }
    }
}