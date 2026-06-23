package petrolpark.mc.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.ItemStack;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@Mixin(MerchantContainer.class)
public abstract class MerchantContainerMixin implements Container {
    
    @ModifyExpressionValue(
        method = "Lnet/minecraft/world/inventory/MerchantContainer;updateSellItem()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;assemble()Lnet/minecraft/world/item/ItemStack;"
        )
    )
    public ItemStack pquality$modifySoldItems(ItemStack original) {
        if (PqualityConfigs.server().affectMerchantTradeReward.get() && original.getCount() > 1) { // Don't multiply if there is only 1, as this probably like an Enchantment trade that could lead to duplication bugs
            original.setCount(Math.min(original.getMaxStackSize(), QualityUtil.getHighestQuality(getItem(0), getItem(1)).multiply(original.getCount())));
        };
        return original;
    };

    @ModifyExpressionValue(
        method = "Lnet/minecraft/world/inventory/MerchantContainer;updateSellItem()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;getXp()I"
        )
    )
    public int pquality$modifyOfferXp(int original) {
        if (PqualityConfigs.server().affectMerchantTradeXp.get()) original = QualityUtil.getHighestQuality(getItem(0), getItem(1)).bigMultiply(original);
        return original;
    };
};
