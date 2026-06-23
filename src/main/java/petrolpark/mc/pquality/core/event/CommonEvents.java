package petrolpark.mc.pquality.core.event;

import java.util.Optional;
import java.util.function.Function;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import petrolpark.mc.library.core.flags.ItemFlagPoleSavedEvent;
import petrolpark.mc.library.core.world.item.bundle.BundleSizeEvent;
import petrolpark.mc.pquality.PqualityTags;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.QualityUtil;
import petrolpark.mc.pquality.core.RegisteredQuality;

@EventBusSubscriber
public class CommonEvents {

    // CORE

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RegisteredQuality.ReloadListener(event.getRegistryAccess()));
    };
    
    @SubscribeEvent
    public static void onItemFlagPoleSaved(ItemFlagPoleSavedEvent event) {
        QualityUtil.getQualityItemStack(event.stack).refreshQuality();
    };

    // GAMEPLAY

    @SubscribeEvent
    public static final void onGetFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (PqualityConfigs.server().affectFuelBurnTime.get()) event.setBurnTime(QualityUtil.getQuality(event.getItemStack()).bigMultiply(event.getBurnTime()));
    };

    @SubscribeEvent
    public static final void onItemAttributeModification(ItemAttributeModifierEvent event) {
        final IQuality quality = QualityUtil.getQuality(event.getItemStack());
        if (quality == QualityUtil.NO_QUALITY) return;
        final ItemAttributeModifiers stackModifiers = Optional.ofNullable(event.getItemStack().getComponentsPatch().get(DataComponents.ATTRIBUTE_MODIFIERS)).<ItemAttributeModifiers>flatMap(Function.identity()).orElse(ItemAttributeModifiers.EMPTY);
        for (ItemAttributeModifiers.Entry entry : event.getModifiers()) {
            if (stackModifiers.modifiers().stream().map(ItemAttributeModifiers.Entry::attribute).anyMatch(entry.attribute()::equals)) continue; // Don't do anything to modifiers from the ItemStack
            if (entry.attribute().value().sentiment == Attribute.Sentiment.NEUTRAL || entry.attribute().is(PqualityTags.Attributes.UNAFFECTED_BY_QUALITY.tag)) continue;
            if (event.removeModifier(entry.attribute(), entry.modifier().id()))
                event.addModifier(
                    entry.attribute(),
                    new AttributeModifier(
                        entry.modifier().id(),
                        entry.attribute().value().sentiment == Attribute.Sentiment.POSITIVE
                            ? quality.multiply(entry.modifier().amount())
                            : quality.reduce(entry.modifier().amount()),
                        entry.modifier().operation()
                    ),
                    entry.slot()
                );
        };
    };

    @SubscribeEvent
    public static final void onBundleSize(BundleSizeEvent event) {
        if (PqualityConfigs.server().affectBundleSize.get()) event.setSize(QualityUtil.getQuality(event.getStack()).multiply(event.getSize()));
    };
};
