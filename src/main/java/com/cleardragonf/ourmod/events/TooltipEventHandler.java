package com.cleardragonf.ourmod.events;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.util.matterValue.ItemValueRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OurMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipEventHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        // Get the value of the item from the ItemValueRegistry
        int value = ItemValueRegistry.getValueForItem(item);

        // Customize tooltip text appearance (optional)
        Component valueText = Component.literal("Value: " + value).withStyle(ChatFormatting.GOLD);

        // Add the value to the tooltip
        event.getToolTip().add(valueText);
    }
}
