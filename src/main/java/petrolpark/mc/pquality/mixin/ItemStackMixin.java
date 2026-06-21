package petrolpark.mc.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.ItemStack;
import petrolpark.mc.pquality.PqualityConfig;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.IQualityItemStack;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IQualityItemStack {

    @Unique
    private IQuality quality;

    @Override
    public IQuality getQuality() {
        if (quality == null) quality = QualityUtil.fetchQuality(self());
        return quality;
    };

    @Override
    public void refreshQuality() {
        quality = null;
    };

    private ItemStack self() {
        return (ItemStack)(Object)this;
    };

    @Inject(
        method = "getMaxDamage",
        at = @At("RETURN"),
        cancellable = true
    )
    public void inGetMaxDamage(CallbackInfoReturnable<Integer> cir) {
        if (PqualityConfig.SERVER.affectItemDurability.get()) cir.setReturnValue(getQuality().multiply(cir.getReturnValueI()));
    };
};
