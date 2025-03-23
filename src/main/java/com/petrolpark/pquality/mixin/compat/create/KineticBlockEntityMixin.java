package com.petrolpark.pquality.mixin.compat.create;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.petrolpark.compat.create.block.entity.behaviour.ContaminationBehaviour;
import com.petrolpark.pquality.PqualityTags;
import com.petrolpark.pquality.core.QualityUtil;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(KineticBlockEntity.class)
public abstract class KineticBlockEntityMixin extends SmartBlockEntity {
    
    public KineticBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        throw new AssertionError();
    };

    // @Redirect(
    //     method = "Lcom/simibubi/create/content/kinetics/base/KineticBlockEntity;calculateStressApplied()F",
    //     at = @At(
    //         value = "INVOKE",
    //         target = "Lcom/simibubi/create/api/stress/BlockStressValues;(Lnet/minecraft/world/level/block/Block;)D"
    //     ),
    //     remap = false
    // )
    @WrapMethod(
        method = "Lcom/simibubi/create/content/kinetics/base/KineticBlockEntity;calculateStressApplied()F",
        remap = false
    )
    private float wrapCalculateStressImpact(Operation<Float> original) {
        float base = original.call();
        if (PqualityTags.BlockEntityTypes.QUALITY_AFFECTS_STRESS_IMPACT.matches(getType())) {
            ContaminationBehaviour behaviour = getBehaviour(ContaminationBehaviour.TYPE);
            if (behaviour != null) return QualityUtil.fetchQuality(behaviour.getContamination()).reduce(base);
        };
        return base;
    };
};
