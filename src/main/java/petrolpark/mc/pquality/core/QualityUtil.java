package petrolpark.mc.pquality.core;

import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import petrolpark.mc.library.core.flags.Flag;
import petrolpark.mc.library.core.flags.IFlagPole;
import petrolpark.mc.library.core.flags.ItemFlagPole;
import petrolpark.mc.pquality.core.mixinInterfaces.IQualityItemStack;

public class QualityUtil {

    public static final NoQuality NO_QUALITY = new NoQuality();

    public static final IQuality getQuality(ItemStack stack) {
        return getQualityItemStack(stack).getQuality();
    };

    public static final Optional<Holder<RegisteredQuality>> getQualityHolder(ItemStack stack) {
        return getQualityItemStack(stack).getQualityHolder();
    };

    public static final IQuality getHighestQuality(ItemStack ... stacks) {
        return getHighestQuality(Stream.of(stacks));
    };

    public static final IQuality getHighestQuality(Stream<ItemStack> stacks) {
        return stacks.map(QualityUtil::getQuality).max(IQuality::compareTo).orElse(NO_QUALITY);
    };
    
    public static final IQuality fetchQuality(ItemStack stack) {
        return fetchQuality(ItemFlagPole.get(stack));
    };

    public static final IQuality fetchQuality(IFlagPole<?, ?> flagPole) {
        return fetchQualityHolder(flagPole).<IQuality>map(Holder::value).orElse(NO_QUALITY);
    };

    public static final Optional<Holder<RegisteredQuality>> fetchQualityHolder(ItemStack stack) {
        return fetchQualityHolder(ItemFlagPole.get(stack));
    };

    public static final Optional<Holder<RegisteredQuality>> fetchQualityHolder(IFlagPole<?, ?> flagpole) {
        final Holder<Flag> flag = getHighestQualityFlag(flagpole);
        if (flag == null) return Optional.empty();
        return Optional.ofNullable(RegisteredQuality.FLAG_QUALITIES.get(flag));
    };

    public static final Optional<IQuality> fromFlag(Holder<Flag> flag) {
        return Optional.ofNullable(RegisteredQuality.FLAG_QUALITIES.get(flag)).map(Holder::value);
    };

    public static final Holder<Flag> getHighestQualityFlag(IFlagPole<?, ?> flagpole) {
        for (Holder<Flag> flag : RegisteredQuality.ORDERED_FLAGS) if (flagpole.has(flag)) return flag;
        return null;
    };

    public static final IQualityItemStack getQualityItemStack(ItemStack stack) {
        return (IQualityItemStack)(Object)stack;
    };

    public static final Stream<RegisteredQuality> streamOrderedRegisteredQualities() {
        return RegisteredQuality.FLAG_QUALITIES.values().stream()
            .map(Holder::value)
            .sorted();
    };

    public static final MobEffectInstance modifyEffectInstance(IQuality quality, @Nullable MobEffectInstance instance, boolean affectLength, boolean affectAmplifier) {
        if (instance == null || (!affectLength && !affectAmplifier)) return instance;
        return new MobEffectInstance(
            instance.getEffect(),
            affectLength ? modifyEffectLength(quality, instance.getEffect(), instance.getDuration()) : instance.getDuration(),
            affectAmplifier ? modifyEffectAmplifier(quality, instance.getEffect(), instance.getAmplifier()) : instance.getAmplifier(),
            instance.isAmbient(),
            instance.isVisible(),
            instance.showIcon(),
            modifyEffectInstance(quality, instance.hiddenEffect, affectLength, affectAmplifier)
        );
    };

    public static final int modifyEffectLength(IQuality quality, Holder<MobEffect> effect, int length) {
        return Math.min(1, effect.value().getCategory() == MobEffectCategory.HARMFUL ? quality.reduce(length) : quality.multiply(length));
    };

    public static final int modifyEffectAmplifier(IQuality quality, Holder<MobEffect> effect, int amplifier) {
        return Math.min(1, effect.value().getCategory() == MobEffectCategory.HARMFUL ? quality.reduce(amplifier + 1) : quality.multiply(amplifier + 1)) - 1;
    };
};
