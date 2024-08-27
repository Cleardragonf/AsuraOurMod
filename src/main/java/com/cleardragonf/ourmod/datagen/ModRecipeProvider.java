package com.cleardragonf.ourmod.datagen;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    private static final List<ItemLike> MATTER_SMELTABLES = List.of(ModItems.RAW_MATTER.get(),
            ModBlocks.MATTER_ORE.get(), ModBlocks.WATER_MATTER_ORE.get(), ModBlocks.DEEPSLATE_MATTER_ORE.get(), ModBlocks.NETHER_MATTER_ORE.get(), ModBlocks.END_STONE_MATTER_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }
//TODO: Add Recipes for the other ORES that were just added.
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreBlasting(pWriter, MATTER_SMELTABLES, RecipeCategory.MISC, ModItems.CONDENSED_MATTER.get(), 0.25F, 100, "matter");
        oreSmelting(pWriter, MATTER_SMELTABLES, RecipeCategory.MISC, ModItems.CONDENSED_MATTER.get(), 0.25F, 100, "matter");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MATTER_BLOCK.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ModItems.CONDENSED_MATTER.get())
                .unlockedBy(getHasName(ModItems.CONDENSED_MATTER.get()), has(ModItems.CONDENSED_MATTER.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CONDENSED_MATTER.get(), 9)
                .requires(ModBlocks.MATTER_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.MATTER_BLOCK.get()), has(ModBlocks.MATTER_BLOCK.get()))
                .save(pWriter);

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> recipeConsumer, List<ItemLike> itemLikes, RecipeCategory category, ItemLike itemLike, float Experience, int cookingTime, String group) {
        oreCooking(recipeConsumer, RecipeSerializer.SMELTING_RECIPE, itemLikes, category, itemLike, Experience, cookingTime, group, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> p_248775_, List<ItemLike> p_251504_, RecipeCategory category, ItemLike itemLike, float pExperience, int cookingTime, String group) {
        oreCooking(p_248775_, RecipeSerializer.BLASTING_RECIPE, p_251504_, category, itemLike, pExperience, cookingTime, group, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> serializer, List<ItemLike> itemLikes, RecipeCategory category, ItemLike itemLike, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        Iterator var9 = itemLikes.iterator();

        while(var9.hasNext()) {
            ItemLike itemlike = (ItemLike)var9.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, itemLike, p_251871_, p_251316_, serializer)
                    .group(p_251450_).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(finishedRecipeConsumer, OurMod.MODID + ":" + getItemName(itemLike) + p_249236_ + "_" + getItemName(itemlike));
        }

    }


}
