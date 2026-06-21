package petrolpark.mc.pquality.util;

import net.minecraft.world.effect.MobEffectInstance;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.mixin.accessor.MobEffectInstanceAccessor;

public class MobEffectHelper {
    
    public static MobEffectInstance copyAndExtend(MobEffectInstance other, IQuality quality) {
        return new MobEffectInstance(other.getEffect(), other.isInfiniteDuration() ? MobEffectInstance.INFINITE_DURATION : quality.multiply(other.getDuration()), other.getAmplifier(), other.isAmbient(), other.isVisible(), other.showIcon(), ((MobEffectInstanceAccessor) other).getHiddenEffect(), other.getFactorData());
    };
};
