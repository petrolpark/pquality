package petrolpark.mc.pquality;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import petrolpark.mc.pquality.common.enchantment.PqualityEnchantmentCategories;
import petrolpark.mc.pquality.common.enchantment.PqualityEnchantments;
import petrolpark.mc.pquality.common.loot.PqualityNumberProviders;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.RegisteredQuality;
import petrolpark.mc.pquality.recipe.PqualityRecipeTypes;

@Mod(Pquality.MOD_ID)
public class Pquality {
    
    public static final String MOD_ID = "pquality";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final PqualityRegistrate REGISTRATE = new PqualityRegistrate();

    public static final ResourceKey<Registry<RegisteredQuality>> QUALITY_REGISTRY = ResourceKey.createRegistryKey(Pquality.asResource("quality"));

    static {
        PqualityEnchantmentCategories.register();
    };
    
    public static final ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    };

    public Pquality(IEventBus modEventBus, ModContainer modContainer) {
        
        REGISTRATE.registerEventListeners(modEventBus);

        // Registration
        PqualityRecipeTypes.register(modEventBus);
        PqualityEnchantments.register();
        PqualityNumberProviders.register();

        // Config
        PqualityConfigs.register(ModLoadingContext.get(), modContainer);
    };

};
