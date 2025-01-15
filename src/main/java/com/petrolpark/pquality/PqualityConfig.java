package com.petrolpark.pquality;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Pquality.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PqualityConfig {

    public static class Server {
        public final BooleanValue affectItemDurability;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Pquality world-specific Configs")
                   .push("server");

            affectItemDurability = builder
                .comment("Quality items will have their durability multiplied by the Quality's regular multiplier")
                .worldRestart()
                .define("affectItemDurability", true);

            builder.pop();
        };
    };

    protected static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;
    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    };
};
