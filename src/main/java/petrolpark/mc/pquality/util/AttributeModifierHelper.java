package petrolpark.mc.pquality.util;

import java.util.UUID;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import petrolpark.mc.pquality.core.IQuality;

public class AttributeModifierHelper {
    
    public static AttributeModifier copyAndMultiply(AttributeModifier other, IQuality quality) {
        return new AttributeModifier(new UUID(other.getId().getLeastSignificantBits() ^ quality.hashCode(), other.getId().getMostSignificantBits() ^ quality.hashCode()), other.getName(), quality.multiply(other.getAmount()), other.getOperation());
    };

    public static AttributeModifier copyAndReduce(AttributeModifier other, IQuality quality) {
        return new AttributeModifier(new UUID(other.getId().getLeastSignificantBits() ^ quality.hashCode(), other.getId().getMostSignificantBits() ^ quality.hashCode()), other.getName(), quality.reduce(other.getAmount()), other.getOperation());
    };
};
