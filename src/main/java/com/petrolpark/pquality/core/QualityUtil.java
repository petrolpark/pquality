package com.petrolpark.pquality.core;

import com.petrolpark.contamination.Contaminant;
import com.petrolpark.contamination.IContamination;
import com.petrolpark.contamination.ItemContamination;

import net.minecraft.world.item.ItemStack;

public class QualityUtil {

    public static final NoQuality NO_QUALITY = new NoQuality();

    public static IQuality getQuality(ItemStack stack) {
        return getQualityItemStack(stack).getQuality();
    };
    
    public static IQuality fetchQuality(ItemStack stack) {
        return fetchQuality(ItemContamination.get(stack));
    };

    public static IQuality fetchQuality(IContamination<?, ?> contamination) {
        for (Contaminant contaminant : RegisteredQuality.ORDERED_CONTAMINANTS) {
            if (contamination.has(contaminant)) return RegisteredQuality.CONTAMINANT_QUALITIES.get(contaminant);
        };
        return NO_QUALITY;
    };

    public static IQualityItemStack getQualityItemStack(ItemStack stack) {
        return (IQualityItemStack)(Object)stack;
    };
};
