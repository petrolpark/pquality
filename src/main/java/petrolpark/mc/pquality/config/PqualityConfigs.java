package petrolpark.mc.pquality.config;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import net.createmod.catnip.config.ConfigBase;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber
public class PqualityConfigs {
    
    private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

	private static PqualityClientConfig client;
	// private static PetrolparkCommonConfig common;
	private static PqualityServerConfig server;

	public static PqualityClientConfig client() {
		return client;
	};

	// public static PetrolparkCommonConfig common() {
	// 	return common;
	// };

	public static PqualityServerConfig server() {
		return server;
	};

	public static ConfigBase byType(ModConfig.Type type) {
		return CONFIGS.get(type);
	};

	private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
		Pair<T, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(builder -> {
			T config = factory.get();
			config.registerAll(builder);
			return config;
		});

		T config = specPair.getLeft();
		config.specification = specPair.getRight();
		CONFIGS.put(side, config);
		return config;
	};

	public static void register(ModLoadingContext context, ModContainer container) {
		client = register(PqualityClientConfig::new, ModConfig.Type.CLIENT);
		// common = register(PetrolparkCommonConfig::new, ModConfig.Type.COMMON);
		server = register(PqualityServerConfig::new, ModConfig.Type.SERVER);

		for (Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet()) container.registerConfig(pair.getKey(), pair.getValue().specification);
	};

	@SubscribeEvent
	public static void onLoad(ModConfigEvent.Loading event) {
		for (ConfigBase config : CONFIGS.values()) if (config.specification == event.getConfig().getSpec()) config.onLoad();
	};

	@SubscribeEvent
	public static void onReload(ModConfigEvent.Reloading event) {
		for (ConfigBase config : CONFIGS.values()) if (config.specification == event.getConfig().getSpec()) config.onReload();
	};
};