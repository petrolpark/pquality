package com.petrolpark.pquality.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.effect.MobEffectInstance;

@Mixin(MobEffectInstance.class)
public interface MobEffectInstanceAccessor {
    
    @Accessor("hiddenEffect")
    public MobEffectInstance getHiddenEffect();
};
