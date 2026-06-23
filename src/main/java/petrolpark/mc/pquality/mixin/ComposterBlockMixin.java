package petrolpark.mc.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ComposterBlock;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin {
    
    @ModifyReturnValue(
        method = "getValue",
        at = @At("RETURN")
    )
    private static float pquality$increaaseCompositingChance(float original, ItemStack item) {
        if (original == -1f || PqualityConfigs.server().affectComposting.get()) return original;
        return Mth.clamp(QualityUtil.getQuality(item).bigMultiply(original), 0f, 1f);
    };
};
