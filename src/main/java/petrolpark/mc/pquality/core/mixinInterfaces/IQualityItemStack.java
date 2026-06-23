package petrolpark.mc.pquality.core.mixinInterfaces;

import java.util.Optional;

import net.minecraft.core.Holder;
import petrolpark.mc.pquality.core.IQuality;
import petrolpark.mc.pquality.core.RegisteredQuality;

public interface IQualityItemStack {

    public Optional<Holder<RegisteredQuality>> getQualityHolder();

    public IQuality getQuality();
    
    public void refreshQuality();
};
