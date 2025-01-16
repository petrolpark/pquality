package com.petrolpark.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.item.trading.MerchantOffer;

@Mixin(MerchantResultSlot.class)
public class MerchantResultSlotMixin {

    @Shadow
    private MerchantContainer slots;
    
    @Redirect(
        method = "Lnet/minecraft/world/inventory/MerchantResultSlot;onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;getXp()I"
        )
    )
    public int redirectOfferXp(MerchantOffer offer) {
        int xp = offer.getXp();
        if (PqualityConfig.SERVER.affectMerchantTradeXp.get()) xp = QualityUtil.getQuality(slots.getItem(0)).bigMultiply(xp);
        return xp;
    };
};
