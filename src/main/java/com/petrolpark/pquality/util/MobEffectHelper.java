package com.petrolpark.pquality.util;

import com.petrolpark.pquality.core.IQuality;
import com.petrolpark.pquality.mixin.accessor.MobEffectInstanceAccessor;

import net.minecraft.world.effect.MobEffectInstance;

public class MobEffectHelper {
    
    public static MobEffectInstance copyAndExtend(MobEffectInstance other, IQuality quality) {
        return new MobEffectInstance(other.getEffect(), other.isInfiniteDuration() ? MobEffectInstance.INFINITE_DURATION : quality.multiply(other.getDuration()), other.getAmplifier(), other.isAmbient(), other.isVisible(), other.showIcon(), ((MobEffectInstanceAccessor) other).getHiddenEffect(), other.getFactorData());
    };
};
