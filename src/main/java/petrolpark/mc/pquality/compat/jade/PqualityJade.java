package petrolpark.mc.pquality.compat.jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class PqualityJade implements IWailaPlugin {
    
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        //registration.registerBlockComponent(new QualityBlockComponentProvider(), Block.class);
    };
};
