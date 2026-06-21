package petrolpark.mc.pquality.event;

import net.minecraftforge.registries.ForgeRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import petrolpark.mc.pquality.Pquality;
import petrolpark.mc.pquality.client.QualityIconTextureManager;
import petrolpark.mc.pquality.client.QualityItemDecorator;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Pquality.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void onAddItemDecorators(RegisterItemDecorationsEvent event) {
        ForgeRegistries.ITEMS.getValues().stream().forEach(item -> event.register(item, QualityItemDecorator.INSTANCE)); // Can't filter for actually Contaminable items as tags aren't loaded yet
    };

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(QualityIconTextureManager.getInstance());
    };
    

};
