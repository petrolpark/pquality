package com.petrolpark.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {

    @Shadow
    public int lifetime;
    
    @Inject(
        method = "<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V",
        at = @At("RETURN")
    )
    public void inInit(Level level, double x, double y, double z, ItemStack stack, CallbackInfo ci) {
        if (PqualityConfig.SERVER.affectFireworkFlightTime.get()) lifetime = QualityUtil.getQuality(stack).bigMultiply(lifetime);
    };
};
