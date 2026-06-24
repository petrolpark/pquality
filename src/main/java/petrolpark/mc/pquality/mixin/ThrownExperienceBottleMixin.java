package petrolpark.mc.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.level.Level;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(ThrownExperienceBottle.class)
public abstract class ThrownExperienceBottleMixin extends ThrowableItemProjectile {

    public ThrownExperienceBottleMixin(EntityType<? extends ThrowableItemProjectile> entityType, double x, double y, double z, Level level) {
        super(entityType, x, y, z, level);
        throw new AssertionError();
    };

    @WrapOperation(
        method = "onHit",
        at = @At(
            value = "INVOKE",
            target = "nextInt"
        )
    )
    public int pquality$increaseXPGained(RandomSource random, int bound, Operation<Integer> original) {
        if (!PqualityConfigs.server().affectBottleOEnchanting.get()) return original.call(random, bound);
        final IQuality quality = QualityUtil.getQuality(getItem());
        return original.call(random, quality.bigMultiply(bound));
    };
    
};
