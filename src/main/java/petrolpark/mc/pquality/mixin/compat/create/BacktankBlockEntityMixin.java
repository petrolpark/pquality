package petrolpark.mc.pquality.mixin.compat.create;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.equipment.armor.BacktankBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import petrolpark.mc.library.compat.create.core.world.block.entity.behaviour.FlagPoleBehaviour;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(BacktankBlockEntity.class)
public abstract class BacktankBlockEntityMixin extends KineticBlockEntity {
    
    public BacktankBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        throw new AssertionError();
    };

    @ModifyExpressionValue(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lcom/simibubi/create/content/equipment/armor/BacktankUtil;maxAir(I)I"
        )
    )
    public int pquality$increaseCapacity(int original) {
        return PqualityConfigs.server().affectBacktankCapacity.get() ? Optional.ofNullable(getBehaviour(FlagPoleBehaviour.TYPE)).map(FlagPoleBehaviour::getFlagPole).map(QualityUtil::fetchQuality).orElse(QualityUtil.NO_QUALITY).multiply(original) : original;
    };
};
