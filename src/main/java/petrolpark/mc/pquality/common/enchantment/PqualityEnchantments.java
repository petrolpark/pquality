package petrolpark.mc.pquality.common.enchantment;

import static petrolpark.mc.pquality.Pquality.REGISTRATE;

import com.tterrag.registrate.util.entry.RegistryEntry;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Rarity;

public class PqualityEnchantments {
    
    public static final RegistryEntry<EnhancementEnchantment> ENHANCEMENT = REGISTRATE.object("enhancement")
        .enchantment(PqualityEnchantmentCategories.TOOL_OR_WEAPON, EnhancementEnchantment::new)
        .addSlots(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)
        .rarity(Rarity.UNCOMMON)
        .register();

    public static final void register() {};
};
