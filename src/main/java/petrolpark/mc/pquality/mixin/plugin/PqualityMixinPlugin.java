package petrolpark.mc.pquality.mixin.plugin;

import petrolpark.mc.library.mixin.plugin.PetrolparkMixinPlugin;

public class PqualityMixinPlugin extends PetrolparkMixinPlugin {
    
    protected String getMixinPackage() {
        return "petrolpark.mc.pquality.mixin";
    };

    @Override
    public void onLoad(String mixinPackage) {
        // Do nowt
    };
};
