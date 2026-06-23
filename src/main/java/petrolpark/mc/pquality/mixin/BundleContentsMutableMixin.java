package petrolpark.mc.pquality.mixin;

import org.apache.commons.lang3.math.Fraction;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.world.item.component.BundleContents;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.QualityUtil;
import petrolpark.mc.pquality.core.mixinInterfaces.IQualityBundleContentsMutable;

@Mixin(BundleContents.Mutable.class)
public class BundleContentsMutableMixin implements IQualityBundleContentsMutable {
    
    @Unique
    protected IQuality quality = QualityUtil.NO_QUALITY;

    @Override
    public void setQuality(IQuality quality) {
        this.quality = quality;
    };

    @ModifyExpressionValue(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/component/BundleContents;getWeight(Lnet/minecraft/world/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;"
        )
    )
    public Fraction pquality$itemsWeighLessInQualityBundles(Fraction original) {
        return PqualityConfigs.server().affectBundleSize.get() ? Fraction.getFraction(original.getNumerator(), quality.multiply(original.getDenominator())) : original;
    };
};
