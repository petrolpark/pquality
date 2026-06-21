package petrolpark.mc.pquality.core;

import net.minecraft.world.item.ItemStack;
import petrolpark.mc.contamination.IContamination;
import petrolpark.mc.contamination.ItemContamination;

public class QualityUtil {

    public static final NoQuality NO_QUALITY = new NoQuality();

    public static IQuality getQuality(ItemStack stack) {
        return getQualityItemStack(stack).getQuality();
    };
    
    public static IQuality fetchQuality(ItemStack stack) {
        return fetchQuality(ItemContamination.get(stack));
    };

    public static IQuality fetchQuality(IContamination<?, ?> contamination) {
        Flag flag = getHighestQualityFlag(contamination);
        if (flag == null) return NO_QUALITY;
        return RegisteredQuality.FLAG_QUALITIES.get(flag);
    };

    public static Flag getHighestQualityFlag(IContamination<?, ?> contamination) {
        for (Flag flag : RegisteredQuality.ORDERED_FLAGS) if (contamination.has(flag)) return flag;
        return null;
    };

    public static IQualityItemStack getQualityItemStack(ItemStack stack) {
        return (IQualityItemStack)(Object)stack;
    };
};
