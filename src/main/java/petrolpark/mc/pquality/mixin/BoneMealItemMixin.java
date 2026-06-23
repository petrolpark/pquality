package petrolpark.mc.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
    
    @WrapOperation(
        method = "applyBonemeal",
        at = @At(
            value = "INVOKE",
            target = "isBonemealSuccess"
        )
    )
    private static boolean pquality$retryBonemeal(BonemealableBlock block, Level level, RandomSource random, BlockPos pos, BlockState state, Operation<Boolean> original, ItemStack stack) {
        final IQuality quality = QualityUtil.getQuality(stack);
        if (!PqualityConfigs.server().affectBoneMeal.get() || quality == QualityUtil.NO_QUALITY) return original.call(block, level, random, pos, state);
        final float attempts = quality.bigMultiply(1f);
        final int wholeAttempts = (int)attempts;
        for (int i = 0; i < wholeAttempts; i++) {
            if (original.call(block, level, random, pos, state)) return true;
        };
        if (random.nextFloat() < attempts - wholeAttempts) return original.call(block, level, random, pos, state);
        return false;
    };
};
