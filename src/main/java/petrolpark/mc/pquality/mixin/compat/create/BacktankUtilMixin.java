package petrolpark.mc.pquality.mixin.compat.create;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.equipment.armor.BacktankUtil;

import net.minecraft.world.item.ItemStack;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(BacktankUtil.class)
public class BacktankUtilMixin {
    
    @ModifyReturnValue(
        method = "maxAir(Lnet/minecraft/world/item/ItemStack;)I",
        at = @At("RETURN")
    )
    private static int pquality$increaseCapacity(int original, ItemStack stack) {
        return PqualityConfigs.server().affectBacktankCapacity.get() ? QualityUtil.getQuality(stack).multiply(original) : original;
    };
};
