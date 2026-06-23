package petrolpark.mc.pquality.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.google.common.base.Suppliers;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(IItemStackExtension.class)
public interface IItemStackExtensionMixin {

    @ModifyReturnValue(
        method = "getFoodProperties",
        at = @At("RETURN")
    )
    public default FoodProperties pquality$modifyFoodProperties(FoodProperties original, @Nullable LivingEntity livingEntity) {
        if (original == null || !PqualityConfigs.server().isFoodAffected()) return original;
        final IQuality quality = QualityUtil.getQuality((ItemStack)(Object)this);
        return new FoodProperties(
            PqualityConfigs.server().affectFoodHunger.get()
                ? quality.multiply(original.nutrition())
                : original.nutrition(),
            PqualityConfigs.server().affectFoodSaturation.get()
                ? quality.multiply(original.saturation())
                : original.saturation(),
            original.canAlwaysEat(),
            PqualityConfigs.server().affectFoodEatTime.get()
                ? quality.reduce(original.eatSeconds())
                : original.eatSeconds(),
            original.usingConvertsTo(),
            PqualityConfigs.server().areFoodEffectsAffected()
                ? original.effects().stream()
                    .map(possibleEffect -> 
                        new PossibleEffect(
                            Suppliers.memoize(() -> QualityUtil.modifyEffectInstance(quality, possibleEffect.effect(), PqualityConfigs.server().affectFoodEffectLength.get(), PqualityConfigs.server().affectFoodEffectLevel.get())),
                            PqualityConfigs.server().affectFoodEffectProbability.get() && possibleEffect.probability() != 1f
                                ? Mth.clamp(
                                    possibleEffect.effect().getEffect().value().getCategory() == MobEffectCategory.HARMFUL
                                        ? quality.reduce(possibleEffect.probability())
                                        : quality.multiply(possibleEffect.probability()),
                                    0f, 1f)
                                : possibleEffect.probability()
                        )
                    ).toList()
                : original.effects()
        );
    };
};
