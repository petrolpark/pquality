package petrolpark.mc.pquality.core.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import petrolpark.mc.pquality.core.client.QualityIconTextureManager;
import petrolpark.mc.pquality.core.client.QualityItemDecorator;

@EventBusSubscriber
public class ClientModEvents {

    @SubscribeEvent
    public static final void onAddItemDecorators(RegisterItemDecorationsEvent event) {
        BuiltInRegistries.ITEM.stream().forEach(item -> event.register(item, QualityItemDecorator.INSTANCE)); // Can't filter for actually Contaminable items as tags aren't loaded yet
    };

    @SubscribeEvent
    public static final void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(QualityIconTextureManager.getInstance());
    };
    

};
