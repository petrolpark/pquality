package petrolpark.mc.pquality.mixin.compat.create;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import petrolpark.mc.library.compat.create.core.world.block.entity.behaviour.FlagPoleBehaviour;
import petrolpark.mc.pquality.PqualityTags;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(KineticBlockEntity.class)
public abstract class KineticBlockEntityMixin extends SmartBlockEntity {

    @Shadow
    float lastStressApplied;
    
    public KineticBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        throw new AssertionError();
    };

    @ModifyReturnValue(
        method = "Lcom/simibubi/create/content/kinetics/base/KineticBlockEntity;calculateStressApplied()F",
        at = @At("RETURN"),
        remap = false
    )
    private float pquality$reduceStressImpact(float original) {
        if (PqualityConfigs.server().affectStressCapacity.get() && PqualityTags.BlockEntityTypes.QUALITY_AFFECTS_STRESS_IMPACT.matches(getType())) {
            FlagPoleBehaviour behaviour = getBehaviour(FlagPoleBehaviour.TYPE);
            if (behaviour != null) return QualityUtil.fetchQuality(behaviour.getFlagPole()).reduce(original);
        };
        return lastStressApplied = original;
    };
};
