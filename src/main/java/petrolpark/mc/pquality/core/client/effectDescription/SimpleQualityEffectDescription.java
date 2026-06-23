package petrolpark.mc.pquality.core.client.effectDescription;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import petrolpark.mc.library.util.Lang;
import petrolpark.mc.pquality.core.IQuality;

public record SimpleQualityEffectDescription(ResourceLocation id, Supplier<Boolean> condition, List<ItemStack> applicableStacks) implements IQualityEffectDescription {

    @Override
    public boolean isEnabled() {
        return condition().get();
    };

    @Override
    public Stream<ItemStack> streamApplicableItemStacks() {
        return applicableStacks().stream();
    };

    @Override
    public List<FormattedText> getDescription(ItemStack stack, IQuality quality) {
        return Collections.singletonList(Component.translatable(
            id().toLanguageKey("pquality_effect"),
            Lang.TWO_DP_DF.format(quality.multiply(1f)),
            Lang.TWO_DP_DF.format(quality.bigMultiply(1f)),
            Lang.TWO_DP_DF.format(quality.reduce(1f))
        ));
    };
    
};
