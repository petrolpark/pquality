package com.petrolpark.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.ItemStack;

@Mixin(EyeOfEnder.class)
public abstract class EyeOfEnderMixin {

    @Shadow
    RandomSource random;

    @Shadow
    boolean surviveAfterDeath;

    @Shadow
    abstract ItemStack getItem();
    
    @Inject(
        method = "Lnet/minecraft/world/entity/projectile/EyeOfEnder;signalTo(Lnet/minecraft/core/BlockPos;)V",
        at = @At("RETURN")
    )
    public void inSignalTo(BlockPos pos, CallbackInfo ci) {
        if (PqualityConfig.SERVER.affectEyeOfEnder.get()) surviveAfterDeath = random.nextInt(QualityUtil.getQuality(getItem()).bigMultiply(5)) > 0;
    };
};
