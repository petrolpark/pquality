package petrolpark.mc.pquality.mixin;

import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;
import petrolpark.mc.pquality.core.mixinInterfaces.IQualityBundleContentsMutable;

@Mixin(BundleItem.class)
public class BundleItemMixin {
    
    @ModifyExpressionValue(
        method = "*",
        at = @At(
            value = "NEW",
            target = "Lnet/minecraft/world/item/component/BundleContents$Mutable;"
        )
    )
    public BundleContents.Mutable pquality$attachQuality(BundleContents.Mutable original, @Local(ordinal = 0) ItemStack stack) {
        if (PqualityConfigs.server().affectBundleSize.get()) ((IQualityBundleContentsMutable)(original)).setQuality(QualityUtil.getQuality(stack));
        return original;
    };

    @WrapOperation(
        method = "appendHoverText",
        at = @At(
            value = "INVOKE",
            target = "mulAndTruncate"
        )
    )
    public int pquality$increaseDisplayedMaxContents(Fraction fraction, int factor, Operation<Integer> original, ItemStack stack) {
        return original.call(fraction, PqualityConfigs.server().affectBundleSize.get() ? 
    };
};
