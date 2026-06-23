package petrolpark.mc.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(Animal.class)
public abstract class AnimalMixin extends AgeableMob {

    protected AnimalMixin(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
        throw new AssertionError();
    };

    @ModifyExpressionValue(
        method = "mobInteract",
        at = @At(
            value = "INVOKE",
            target = "getSpeedUpSecondsWhenFeeding(I)I"
        )
    )
    public int pquality$increaseGrowth(int original, @Local(ordinal = 0) ItemStack itemstack) {
        return PqualityConfigs.server().affectBabyAnimalGrowth.get() ? QualityUtil.getQuality(itemstack).bigMultiply(original) : original;
    };
    
};
