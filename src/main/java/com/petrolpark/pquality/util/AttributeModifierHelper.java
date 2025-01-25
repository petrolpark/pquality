package com.petrolpark.pquality.util;

import java.util.UUID;

import com.petrolpark.pquality.core.IQuality;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttributeModifierHelper {
    
    public static AttributeModifier copyAndMultiply(AttributeModifier other, IQuality quality) {
        return new AttributeModifier(new UUID(other.getId().getLeastSignificantBits() ^ quality.hashCode(), other.getId().getMostSignificantBits() ^ quality.hashCode()), other.getName(), quality.multiply(other.getAmount()), other.getOperation());
    };

    public static AttributeModifier copyAndReduce(AttributeModifier other, IQuality quality) {
        return new AttributeModifier(new UUID(other.getId().getLeastSignificantBits() ^ quality.hashCode(), other.getId().getMostSignificantBits() ^ quality.hashCode()), other.getName(), quality.reduce(other.getAmount()), other.getOperation());
    };
};
