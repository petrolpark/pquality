package com.petrolpark.pquality.event;

import com.petrolpark.pquality.Pquality;
import com.petrolpark.pquality.core.RegisteredQuality;

import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DataPackRegistryEvent;

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
