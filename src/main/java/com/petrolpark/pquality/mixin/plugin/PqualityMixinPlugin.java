package com.petrolpark.pquality.mixin.plugin;

import com.petrolpark.mixin.plugin.PetrolparkMixinPlugin;

public class PqualityMixinPlugin extends PetrolparkMixinPlugin {
    
    protected String getMixinPackage() {
        return "com.petrolpark.pquality.mixin";
    };

    @Override
    public void onLoad(String mixinPackage) {
        // Do nowt
    };
};
