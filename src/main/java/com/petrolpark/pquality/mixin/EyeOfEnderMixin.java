package com.petrolpark.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(EyeOfEnder.class)
public abstract class EyeOfEnderMixin extends Entity {

    public EyeOfEnderMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        throw new AssertionError();
    };

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
