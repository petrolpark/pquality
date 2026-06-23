package petrolpark.mc.pquality.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.QualityUtil;
import petrolpark.mc.pquality.core.RegisteredQuality;
import petrolpark.mc.pquality.core.mixinInterfaces.IQualityItemStack;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IQualityItemStack {

    @Unique
    private Optional<Holder<RegisteredQuality>> quality;

    @Override
    public Optional<Holder<RegisteredQuality>> getQualityHolder() {
        if (quality == null) quality = QualityUtil.fetchQualityHolder(self());
        return quality;
    };

    @Override
    public IQuality getQuality() {
        return getQualityHolder().<IQuality>map(Holder::value).orElse(QualityUtil.NO_QUALITY);
    };

    @Override
    public void refreshQuality() {
        quality = null;
    };

    private ItemStack self() {
        return (ItemStack)(Object)this;
    };

    @ModifyReturnValue(
        method = "getMaxDamage",
        at = @At("RETURN")
    )
    public int pquality$affectMaxDamage(int original) {
        return PqualityConfigs.server().affectItemDurability.get() ? getQuality().multiply(original) : original;
    };
};
