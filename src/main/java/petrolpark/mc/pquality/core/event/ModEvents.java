package petrolpark.mc.pquality.core.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import petrolpark.mc.pquality.Pquality;
import petrolpark.mc.pquality.core.RegisteredQuality;

@EventBusSubscriber
public class ModEvents {
    
    @SubscribeEvent
    public static void addDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Pquality.QUALITY_REGISTRY, RegisteredQuality.DIRECT_CODEC, RegisteredQuality.DIRECT_CODEC);
    };

    @SubscribeEvent
    public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {

    };
};
