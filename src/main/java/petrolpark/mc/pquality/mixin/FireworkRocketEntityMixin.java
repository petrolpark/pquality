package petrolpark.mc.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {

    @Shadow
    public int lifetime;
    
    @Inject(
        method = "<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V",
        at = @At("RETURN")
    )
    public void inInit(Level level, double x, double y, double z, ItemStack stack, CallbackInfo ci) {
        if (PqualityConfigs.server().affectFireworkFlightTime.get()) lifetime = QualityUtil.getQuality(stack).bigMultiply(lifetime);
    };
};
