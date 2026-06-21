package petrolpark.mc.pquality.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;
import petrolpark.mc.pquality.PqualityConfig;

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
