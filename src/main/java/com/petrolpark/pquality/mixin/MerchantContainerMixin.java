package com.petrolpark.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

@Mixin(MerchantContainer.class)
public abstract class MerchantContainerMixin implements Container {
    
    @Redirect(
        method = "Lnet/minecraft/world/inventory/MerchantContainer;updateSellItem()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;assemble()Lnet/minecraft/world/item/ItemStack;"
        )
    )
    public ItemStack redirectAssembleOffer(MerchantOffer offer) {
        ItemStack stack = offer.assemble();
        if (PqualityConfig.SERVER.affectMerchantTradeReward.get()) {
            stack.setCount(Math.min(stack.getMaxStackSize(), QualityUtil.getQuality(getItem(0)).multiply(stack.getCount())));
        };
        return stack;
    };

    @Redirect(
        method = "Lnet/minecraft/world/inventory/MerchantContainer;updateSellItem()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;getXp()I"
        )
    )
    public int redirectOfferXp(MerchantOffer offer) {
        int xp = offer.getXp();
        if (PqualityConfig.SERVER.affectMerchantTradeXp.get()) xp = QualityUtil.getQuality(getItem(0)).bigMultiply(xp);
        return xp;
    };
};
