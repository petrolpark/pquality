package com.petrolpark.pquality.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@Mixin(BundleItem.class)
public abstract class BundleItemMixin {

    @Shadow
    private static int getContentWeight(ItemStack stack) {return 0;};

    @Shadow
    private static int getWeight(ItemStack stack) {return 0;};

    private static int getCapacity(ItemStack stack) {
        return QualityUtil.getQuality(stack).multiply(64);
    };
    
    @ModifyVariable(
        method = "Lnet/minecraft/world/item/BundleItem;overrideStackedOnOther",
        at = @At("STORE"),
        ordinal = 0
    )
    public int inOverrideStackedOnOther(int i, ItemStack stack, Slot slot, ClickAction action, Player player) {
        return (getCapacity(stack) - getContentWeight(stack)) / getWeight(slot.getItem());
    };

    @Overwrite // An injection would have to replace the only line anyway
    public int getBarWidth(ItemStack stack) {
        return Math.min(1 + 12 * getContentWeight(stack) / getCapacity(stack), 3);
    };

    @ModifyVariable(
        method = "Lnet/minecraft/world/item/BundleItem;add",
        at = @At("STORE"),
        ordinal = 2
    )
    private static int inAdd(int k, ItemStack bundleStack, ItemStack insertedStack) {
        return Math.min(insertedStack.getCount(), (getCapacity(bundleStack) - getContentWeight(bundleStack)) / getWeight(insertedStack));
    };

    @Redirect(
        method = "Lnet/minecraft/world/item/BundleItem;appendHoverText",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/chat/Component;translatable"
        )
    )
    public MutableComponent inAppendHoverText(String key, Object[] args, ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        return Component.translatable(key, args[0], getCapacity(stack));
    };
};
