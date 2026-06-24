package petrolpark.mc.pquality.config;

import net.createmod.catnip.config.ConfigBase;

public class PqualityServerConfig extends ConfigBase {

    public final ConfigBool affectAnvilRepair = b(true, "affectAnvilRepair", "Quality items will repair more durability with an Anvil");
    public final ConfigBool affectAttributes = b(true, "affectAttributes", "Quality items have positive Attribute Modifiers increased and negative ones decreased");
    public final ConfigBool affectBabyAnimalGrowth = b(true, "affectBabyAnimalGrowth", "Quality animal food will cause babies to grow up quicker");
    public final ConfigBool affectBoneMeal = b(true, "affectBonemeal", "Quality bonemeals will have a greater chance of growing things");
    public final ConfigBool affectBottleOEnchanting = b(true, "affectBottleOEnchanting", "Quality Bottles o' Enchanting give more experience");
    public final ConfigBool affectBundleSize = b(true, "affectBundleSize", "Quality Bundles can hold more items");
    public final ConfigBool affectComposting = b(true, "affectComposting", "Quality items have a higher chance of adding compost layers");
    public final ConfigBool affectEyeOfEnder = b(true, "affectEyeOfEnder", "Quality Eyes of Ender have a lower chance of breaking");
    public final ConfigBool affectFireworkFlightTime = b(true, "affectFireworkFlightTime", "Quality Firework Rockets will last longer", "This also affects how far players with Elytra are propelled");
    public final ConfigBool affectFoodHunger = b(true, "affectFoodHunger", "Quality food restores more hunger");
    public final ConfigBool affectFoodSaturation = b(true, "affectFoodSaturation", "Quality food gives more saturation");
    public final ConfigBool affectFoodEatTime = b(true, "affectFoodEatTime", "Quality food takes less time to eat");
    public final ConfigBool affectFoodEffectProbability = b(true, "affectFoodEffectProbability", "Mob effects from quality food have a higher chance of triggering if beneficial or neutral and lower if harmful");
    public final ConfigBool affectFoodEffectLevel = b(true, "affectFoodEffectLevel", "Mob effects from quality food will be a higher level if beneficial or neutral and lower if harmful");
    public final ConfigBool affectFoodEffectLength = b(true, "affectFoodEffectLength", "Mob Effects from quality food will last longer if beneficial or neutral and shorter if harmful");
    public final ConfigBool affectFuelBurnTime = b(true, "affectFuelBurnTime", "Quality items will burn for longer");
    public final ConfigBool affectItemDurability = b(true, "affectItemDurability", "Quality items will have their durability multiplied by the Quality's regular multiplier");
    public final ConfigBool affectMerchantTradeReward = b(true, "affectMerchantTradeReward", "Villagers and Wandering Traders will pay more for Quality items");
    public final ConfigBool affectMerchantTradeXp = b(true, "affectMerchantTradeXp", "Villagers will earn more XP when trading Quality items");
    public final ConfigBool affectPotionLength = b(true, "affectPotionLength", "Quality Potions will last longer");

    public final ConfigGroup compatibility = group(0, "compatibility");
        public final ConfigGroup create = group(1, "create");
            public final ConfigBool affectStressCapacity = b(true, "affectStressCapacity", "Quality Kinetic Blocks will require less Stress Capacity");
            public final ConfigBool affectBacktankCapacity = b(true, "affectBacktankCapacity", "Quality Backtanks have a higher capacity");

    public boolean isFoodAffected() {
        return affectFoodHunger.get() || affectFoodEatTime.get() || affectFoodSaturation.get() || areFoodEffectsAffected();
    };

    public boolean areFoodEffectsAffected() {
        return affectFoodEffectProbability.get() || affectFoodEffectLength.get() || affectFoodEffectLevel.get();
    };

    @Override
    public String getName() {
        return "name";
    };
    
};
