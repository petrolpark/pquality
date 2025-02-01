package com.petrolpark.pquality.common.loot;

import static com.petrolpark.pquality.Pquality.REGISTRATE;

import com.petrolpark.data.loot.numberprovider.itemstack.LootItemStackNumberProviderType;
import com.petrolpark.pquality.common.loot.numberprovider.QualityItemStackNumberProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class PqualityNumberProviders {
    
    public static final RegistryEntry<LootItemStackNumberProviderType> QUALITY = REGISTRATE.lootItemStackNumberProviderType("quality", new QualityItemStackNumberProvider.Serializer());

    public static final void register() {};
};
