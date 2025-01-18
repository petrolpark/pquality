package com.petrolpark.pquality.common.enchantment;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PqualityEnchantmentCategories {
    
    public static final EnchantmentCategory TOOL_OR_WEAPON = EnchantmentCategory.create("tool_or_weapon", item -> {
        return item instanceof SwordItem || item instanceof DiggerItem || item instanceof FishingRodItem || item instanceof BowItem || item instanceof CrossbowItem;
    });

    public static final void register() {};
};
