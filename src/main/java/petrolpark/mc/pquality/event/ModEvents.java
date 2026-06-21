package petrolpark.mc.pquality.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import petrolpark.mc.pquality.Pquality;
import petrolpark.mc.pquality.core.RegisteredQuality;

@EventBusSubscriber(modid = Pquality.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    
    @SubscribeEvent
    public static void addDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Pquality.QUALITY_REGISTRY, RegisteredQuality.CODEC, RegisteredQuality.CODEC);
    };

    @SubscribeEvent
    public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {

    };
};
