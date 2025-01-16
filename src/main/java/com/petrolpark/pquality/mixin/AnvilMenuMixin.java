package com.petrolpark.pquality.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    
    public AnvilMenuMixin(MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(pType, pContainerId, pPlayerInventory, pAccess);
        throw new AssertionError();
    };

    @Redirect(
        method = "Lnet/minecraft/world/inventory/AnvilMenu;createResult()V",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/Math;min(II)I"
        )
    )
    public int getDamageRepaired(int remainingDamage, int maxRepair) {
        if (!PqualityConfig.SERVER.affectAnvilRepair.get()) return Math.min(remainingDamage, maxRepair);
        ItemStack repairItemStack = inputSlots.getItem(1);
        return Math.min(remainingDamage, QualityUtil.getQuality(repairItemStack).multiply(maxRepair));
    };
};
