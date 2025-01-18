package com.petrolpark.pquality.common.enchantment;

import com.petrolpark.pquality.PqualityConfig;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;

public class EnhancementEnchantment extends Enchantment {

    protected EnhancementEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    };

    @Override
    public int getMaxLevel() {
        return PqualityConfig.SERVER.enhancementEnchantmentMaxLevel.get();
    };

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return !PqualityConfig.SERVER.affectedAttributes.get().contains(ForgeRegistries.ENCHANTMENTS.getKey(other).toString());
    };
    
};
