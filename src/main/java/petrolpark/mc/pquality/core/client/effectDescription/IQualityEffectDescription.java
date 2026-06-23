package petrolpark.mc.pquality.core.client.effectDescription;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import petrolpark.mc.pquality.core.IQuality;

public interface IQualityEffectDescription {

    public ResourceLocation id();
    
    public boolean isEnabled();

    public Stream<ItemStack> streamApplicableItemStacks();

    public List<FormattedText> getDescription(ItemStack stack, IQuality quality);
};
