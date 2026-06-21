package petrolpark.mc.pquality.event;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import petrolpark.mc.contamination.ItemContaminationSavedEvent;
import petrolpark.mc.pquality.PqualityConfig;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.QualityUtil;
import petrolpark.mc.pquality.core.RegisteredQuality;
import petrolpark.mc.pquality.util.AttributeModifierHelper;

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
            if (affectedAttributes.contains(rl.toString()) && event.removeModifier(attribute, modifier)) event.addModifier(attribute, modifier.getAmount() > 0f ? AttributeModifierHelper.copyAndMultiply(modifier, quality) : AttributeModifierHelper.copyAndReduce(modifier, quality));
        });
    };
};
