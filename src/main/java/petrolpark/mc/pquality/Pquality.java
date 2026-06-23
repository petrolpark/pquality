package petrolpark.mc.pquality;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.RegisteredQuality;
import petrolpark.mc.pquality.registry.PqualityRecipeSerializers;

@Mod(Pquality.MOD_ID)
public class Pquality {
    
    public static final String MOD_ID = "pquality";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final PqualityRegistrate REGISTRATE = new PqualityRegistrate();

    public static final ResourceKey<Registry<RegisteredQuality>> QUALITY_REGISTRY = ResourceKey.createRegistryKey(Pquality.asResource("quality"));
    
    public static final ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    };

    public static final Component translate(String keyEnd, Object ... args) {
        return Component.translatable(MOD_ID + "." + keyEnd, args);
    };

    public Pquality(IEventBus modEventBus, ModContainer modContainer) {

        PqualityPluginManager.init();
        
        REGISTRATE.registerEventListeners(modEventBus);

        // Registration
        PqualityRecipeSerializers.register();

        // Config
        PqualityConfigs.register(ModLoadingContext.get(), modContainer);
    };

};
