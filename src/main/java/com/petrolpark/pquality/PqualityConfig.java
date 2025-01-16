package com.petrolpark.pquality;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = Pquality.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PqualityConfig {

    public static class Server {

        public final BooleanValue affectAnvilRepair;
        public final BooleanValue affectEyeOfEnder;
        public final BooleanValue affectFireworkFlightTime;
        public final BooleanValue affectFoodNutrition;
        public final BooleanValue affectFoodEffectLength;
        public final BooleanValue affectFuelBurnTime;
        public final BooleanValue affectItemDurability;
        public final BooleanValue affectMerchantTradeReward;
        public final BooleanValue affectMerchantTradeXp;
        public final BooleanValue affectPotionLength;

        public final ConfigValue<List<? extends String>> affectedAttributes;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Pquality world-specific Configs")
                   .push("server");

            affectAnvilRepair = builder
                .comment("Quality items will repair more durability with an Anvil")
                .worldRestart()
                .define("affectAnvilRepair", true);

            affectEyeOfEnder = builder
                .comment("Quality Eyes of Ender have a lower chance of breaking")
                .worldRestart()
                .define("affectEyesOfEnder", true);

            affectFireworkFlightTime = builder
                .comment("Quality Firework Rockets will last longer", "This also affects how far players with Elytra are propelled")
                .worldRestart()
                .define("affectFireworkFlightTime", true);

            affectFoodNutrition = builder
                .comment("Quality food restores more hunger")
                .worldRestart()
                .define("affectsFoodNutrition", true);

            affectFoodEffectLength = builder
                .comment("Quality food Mob Effects will last longer")
                .worldRestart()
                .define("affectsFoodEffectLength", true);

            affectFuelBurnTime = builder
                .comment("Quality items will burn for longer")
                .worldRestart()
                .define("affectFuelBurnTime", true);

            affectItemDurability = builder
                .comment("Quality items will have their durability multiplied by the Quality's regular multiplier")
                .worldRestart()
                .define("affectItemDurability", true);

            affectMerchantTradeReward = builder
                .comment("Villagers and Wandering Traders will pay more for Quality items")
                .worldRestart()
                .define("affectMerchantTradeReward", true);

            affectMerchantTradeXp = builder
                .comment("Villagers will earn more XP when trading Quality items")
                .worldRestart()
                .define("affectMerchantTradeXp", true);

            affectPotionLength = builder
                .comment("Quality Potions will last longer")
                .worldRestart()
                .define("affectPotionLength", true);

            builder.push("attributes"); {

                affectedAttributes = builder
                    .comment(
                        "Attributes affected by the Quality",
                        "Only default Attribute Modifiers are affected (not custom ones, stored in item NBT)"
                    ).defineListAllowEmpty("affectAttributes", List.of("minecraft:generic.attack_damage"), PqualityConfig::validateAttributeName);

            } builder.pop();

            builder.pop();
        };

        public boolean affectFood() {
            return affectFoodEffectLength.get() || affectFoodNutrition.get();
        };
    };

    private static final boolean validateAttributeName(Object obj) {
        return obj instanceof final String attributeName && ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation(attributeName));
    };

    protected static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;
    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    };
};
