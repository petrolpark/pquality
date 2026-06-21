package petrolpark.mc.pquality.common.loot;

import static petrolpark.mc.pquality.Pquality.REGISTRATE;

import com.tterrag.registrate.util.entry.RegistryEntry;

import petrolpark.mc.library.core.data.loot.numberprovider.itemstack.LootItemStackNumberProviderType;
import petrolpark.mc.pquality.common.loot.numberprovider.QualityItemStackNumberProvider;

public class PqualityNumberProviders {
    
    public static final RegistryEntry<LootItemStackNumberProviderType> QUALITY = REGISTRATE.lootItemStackNumberProviderType("quality", new QualityItemStackNumberProvider.Serializer());

    public static final void register() {};
};
