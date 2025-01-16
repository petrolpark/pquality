package com.petrolpark.pquality.event;

import java.util.List;

import com.petrolpark.contamination.ItemContaminationSavedEvent;
import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.IQuality;
import com.petrolpark.pquality.core.QualityUtil;
import com.petrolpark.pquality.core.RegisteredQuality;
import com.petrolpark.pquality.util.AttributeModifierHelper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class CommonEvents {

    // CORE

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RegisteredQuality.ReloadListener(event.getRegistryAccess()));
    };
    
    @SubscribeEvent
    public static void onItemContaminationSaved(ItemContaminationSavedEvent event) {
        QualityUtil.getQualityItemStack(event.stack).refreshQuality();
    };

    // GAMEPLAY

    @SubscribeEvent
    public static void onGetFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (PqualityConfig.SERVER.affectFuelBurnTime.get()) event.setBurnTime(QualityUtil.getQuality(event.getItemStack()).bigMultiply(event.getBurnTime()));
    };

    @SubscribeEvent
    public static void onItemAttributeModification(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasTag() && stack.getTag().contains("AttributeModifiers")) return; // Don't override already overridden modifiers
        IQuality quality = QualityUtil.getQuality(stack);
        if (quality == QualityUtil.NO_QUALITY) return;
        List<? extends String> affectedAttributes = PqualityConfig.SERVER.affectedAttributes.get();
        if (affectedAttributes.isEmpty()) return; // Don't override already overriden modifiers
        event.getOriginalModifiers().forEach((attribute, modifier) -> {
            if (modifier.getOperation() != AttributeModifier.Operation.ADDITION) return;
            ResourceLocation rl = ForgeRegistries.ATTRIBUTES.getKey(attribute);
            if (affectedAttributes.contains(rl.toString()) && event.removeModifier(attribute, modifier)) event.addModifier(attribute, AttributeModifierHelper.copyAndMultiply(modifier, quality));
        });
    };
};
