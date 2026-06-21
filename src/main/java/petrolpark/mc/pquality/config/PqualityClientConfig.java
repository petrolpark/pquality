package petrolpark.mc.pquality.config;

import net.createmod.catnip.config.ConfigBase;

public class PqualityClientConfig extends ConfigBase {

    public final ConfigBool shiftToSeeQuality = b(false, "shiftToSeeQuality", "The Quality icon is only shown if the shift key is held down");

    @Override
    public String getName() {
        return "client";
    };
    
};
