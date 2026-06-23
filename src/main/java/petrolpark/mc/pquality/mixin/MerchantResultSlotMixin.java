package petrolpark.mc.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantResultSlot;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(MerchantResultSlot.class)
public class MerchantResultSlotMixin {

    @Shadow
    private MerchantContainer slots;
    
    @ModifyExpressionValue(
        method = "Lnet/minecraft/world/inventory/MerchantResultSlot;onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;getXp()I"
        )
    )
    public int redirectOfferXp(int original) {
        if (PqualityConfigs.server().affectMerchantTradeXp.get()) original = QualityUtil.getHighestQuality(slots.getItem(0), slots.getItem(1)).bigMultiply(original);
        return original;
    };
};
