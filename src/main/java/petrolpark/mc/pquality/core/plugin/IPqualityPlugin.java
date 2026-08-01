package petrolpark.mc.pquality.core.plugin;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.apache.commons.lang3.math.Fraction;

import mezz.jei.api.JeiPlugin;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import petrolpark.mc.library.compat.pquality.PetrolparkPqualityPlugin;
import petrolpark.mc.library.core.flags.IFlagPole;
import petrolpark.mc.library.util.function.ObjDouble2DoubleFunction;
import petrolpark.mc.library.util.function.ObjFloat2FloatFunction;
import petrolpark.mc.library.util.function.ObjInt2IntFunction;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.client.effectDescription.IQualityEffectDescription;

/**
 * <p>The main interface to implement to create a <b>pquality</b> plugin.</p>
 * 
 * <p>Instances will recieve a bunch of references to functions that accept an ItemStack (or {@link IFlagPole}, if the Petrolpark Library is a dependent)
 * and number ({@code double}, {@code int} or {@code float}) and return those numbers according to the multiplier, big multiplier or reducer of the {@link IQuality} of that ItemStack.
 * As the implementations of {@link IQuality#multiply(int)} etc. are not contractual, you should use the matching function for each primitive.
 * 
 * <p>IPqualityModPlugins must have the {@link JeiPlugin} annotation to get loaded by <b>pquality</b>.</p>
 * 
 * @see PetrolparkPqualityPlugin Example implementation
 */
public interface IPqualityPlugin {

    public default boolean shouldLoad() {
        return true;
    };

    public default void acceptFlagPoleModifiers(
        ObjDouble2DoubleFunction<IFlagPole<?, ?>> doubleMultiplier, ObjDouble2DoubleFunction<IFlagPole<?, ?>> doubleBigMultiplier, ObjDouble2DoubleFunction<IFlagPole<?, ?>> doubleReducer,
        ObjInt2IntFunction<IFlagPole<?, ?>> intMultiplier, ObjInt2IntFunction<IFlagPole<?, ?>> intBigMultiplier, ObjInt2IntFunction<IFlagPole<?, ?>> intReducer,
        ObjFloat2FloatFunction<IFlagPole<?, ?>> floatMultiplier, ObjFloat2FloatFunction<IFlagPole<?, ?>> floatBigMultiplier, ObjFloat2FloatFunction<IFlagPole<?, ?>> floatReducer,
        BiFunction<IFlagPole<?, ?>, Fraction, Fraction> fractionMultiplier, BiFunction<IFlagPole<?, ?>, Fraction, Fraction> fractionBigMultiplier, BiFunction<IFlagPole<?, ?>, Fraction, Fraction> fractionReducer
    ) {};

    public default void acceptItemStackModifiers(
        ObjDouble2DoubleFunction<ItemStack> doubleMultiplier, ObjDouble2DoubleFunction<ItemStack> doubleBigMultiplier, ObjDouble2DoubleFunction<ItemStack> doubleReducer,
        ObjInt2IntFunction<ItemStack> intMultiplier, ObjInt2IntFunction<ItemStack> intBigMultiplier, ObjInt2IntFunction<ItemStack> intReducer,
        ObjFloat2FloatFunction<ItemStack> floatMultiplier, ObjFloat2FloatFunction<ItemStack> floatBigMultiplier, ObjFloat2FloatFunction<ItemStack> floatReducer,
        BiFunction<ItemStack, Fraction, Fraction> fractionMultiplier, BiFunction<ItemStack, Fraction, Fraction> fractionBigMultiplier, BiFunction<ItemStack, Fraction, Fraction> fractionReducer
    ) {};

    @OnlyIn(Dist.CLIENT)
    public default void registerEffectDescriptions(Consumer<IQualityEffectDescription> adder) {};
};
