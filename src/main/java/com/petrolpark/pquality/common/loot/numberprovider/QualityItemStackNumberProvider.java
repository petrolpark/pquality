package com.petrolpark.pquality.common.loot.numberprovider;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.petrolpark.data.loot.numberprovider.itemstack.ItemStackNumberProvider;
import com.petrolpark.data.loot.numberprovider.itemstack.LootItemStackNumberProviderType;
import com.petrolpark.pquality.common.loot.PqualityNumberProviders;
import com.petrolpark.pquality.core.IQuality;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class QualityItemStackNumberProvider implements ItemStackNumberProvider {

    public final QualityValue value;

    public QualityItemStackNumberProvider(QualityValue value) {
        this.value = value;
    };

    @Override
    public float getFloat(ItemStack stack, LootContext lootContext) {
        return value.get(QualityUtil.getQuality(stack));
    };

    @Override
    public LootItemStackNumberProviderType getType() {
        return PqualityNumberProviders.QUALITY.get();
    };

    public static enum QualityValue {

        MULTIPLIER("multiplier") {
            @Override
            public float get(IQuality quality) {
                return quality.multiply(1f);
            };
        },
        BIG_MULTIPLIER("bigMultiplier") {
            @Override
            public float get(IQuality quality) {
                return quality.bigMultiply(1f);
            };
        },
        REDUCER("reducer") {
            @Override
            public float get(IQuality quality) {
                return quality.reduce(1f);
            };
        };

        public final String name;

        public abstract float get(IQuality quality);

        QualityValue(String name) {
            this.name = name;
        };

        public static QualityValue getByName(String name) {
            for (QualityValue value : values()) if (value.name.equals(name)) return value;
            throw new IllegalArgumentException("Invalid entity target " + name);
        };
    };

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<QualityItemStackNumberProvider> {

        @Override
        public void serialize(JsonObject json, QualityItemStackNumberProvider value, JsonSerializationContext serializationContext) {
            json.addProperty("value", value.value.name);
        };

        @Override
        public QualityItemStackNumberProvider deserialize(JsonObject json, JsonDeserializationContext serializationContext) {
            return new QualityItemStackNumberProvider(QualityValue.getByName(GsonHelper.getAsString(json, "value")));
        };

    };
    
};
