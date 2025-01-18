package com.petrolpark.pquality.common.enchantment;

import static com.petrolpark.pquality.Pquality.REGISTRATE;

import com.tterrag.registrate.util.entry.RegistryEntry;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class PqualityEnchantments {
    
    public static final RegistryEntry<EnhancementEnchantment> ENHANCEMENT = REGISTRATE.object("enhancement")
        .enchantment(PqualityEnchantmentCategories.TOOL_OR_WEAPON, EnhancementEnchantment::new)
        .addSlots(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)
        .rarity(Rarity.UNCOMMON)
        .register();

    public static final void register() {};
};
