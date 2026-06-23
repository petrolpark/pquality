package petrolpark.mc.pquality.core;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.math.Fraction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ExtraCodecs;
import petrolpark.mc.library.core.flags.Flag;
import petrolpark.mc.library.util.codec.CodecHelper;
import petrolpark.mc.pquality.Pquality;

public record RegisteredQuality(int priority, double multiplier, double bigMultiplier, double reducer, Holder<Flag> flag) implements IQuality {

    protected static final Map<Holder<Flag>, Holder<RegisteredQuality>> FLAG_QUALITIES = new HashMap<>();
    protected static final SortedSet<Holder<Flag>> ORDERED_FLAGS = new TreeSet<>((c1, c2) -> {
            return FLAG_QUALITIES.get(c2).value().priority() - FLAG_QUALITIES.get(c1).value().priority();
        });

    public static final Codec<RegisteredQuality> DIRECT_CODEC = ExtraCodecs.catchDecoderException(RecordCodecBuilder.create(instance -> 
        instance.group(
            Codec.INT.fieldOf("priority").forGetter(RegisteredQuality::priority),
            CodecHelper.POS_DOUBLE.fieldOf("multiplier").forGetter(RegisteredQuality::multiplier),
            CodecHelper.POS_DOUBLE.fieldOf("big_multiplier").forGetter(RegisteredQuality::bigMultiplier),
            CodecHelper.POS_DOUBLE.fieldOf("reducer").forGetter(RegisteredQuality::reducer),
            Flag.CODEC.fieldOf("flag").forGetter(RegisteredQuality::flag)
        ).apply(instance, RegisteredQuality::new)
    ));

    @Override
    public double multiply(double base) {
        return base * multiplier();
    };

    @Override
    public double bigMultiply(double base) {
        return base * bigMultiplier();
    };

    @Override
    public double reduce(double base) {
        return base * reducer();
    };

    @Override
    public int multiply(int base) {
        return (int)(multiplier() * base);
    };

    @Override
    public int bigMultiply(int base) {
        return (int)(bigMultiplier() * base);
    };

    @Override
    public int reduce(int base) {
        return Math.max(1, reduceToZero(base));
    };

    @Override
    public int reduceToZero(int base) {
        return (int)(reducer() * base);
    };

    @Override
    public float multiply(float base) {
        return (float)(multiplier() * base);
    };

    @Override
    public float bigMultiply(float base) {
        return (float)(bigMultiplier() * base);
    };

    @Override
    public float reduce(float base) {
        return (float)(reducer() * base);
    };

    @Override
    public Fraction multiply(Fraction base) {
        return base.multiplyBy(Fraction.getFraction(multiplier()));
    };

    @Override
    public Fraction bigMultiply(Fraction base) {
        return base.multiplyBy(Fraction.getFraction(bigMultiplier()));
    };

    @Override
    public Fraction reduce(Fraction base) {
        return base.multiplyBy(Fraction.getFraction(reducer()));
    };

    public static class ReloadListener implements ResourceManagerReloadListener {

        protected final RegistryAccess registryAccess;

        public ReloadListener(RegistryAccess registryAccess) {
            this.registryAccess = registryAccess;  
        };

        @Override
        public void onResourceManagerReload(@Nonnull ResourceManager resourceManager) {
            FLAG_QUALITIES.clear();
            ORDERED_FLAGS.clear();
            registryAccess.registryOrThrow(Pquality.QUALITY_REGISTRY).holders().forEach(quality -> {
                FLAG_QUALITIES.put(quality.value().flag(), quality);
            });
            ORDERED_FLAGS.addAll(FLAG_QUALITIES.keySet());
        };
        
    };

};
