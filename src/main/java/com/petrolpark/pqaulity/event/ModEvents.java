package com.petrolpark.pqaulity.event;

import com.petrolpark.pqaulity.Pquality;
import com.petrolpark.pqaulity.core.RegisteredQuality;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = Pquality.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    
    @SubscribeEvent
    public static void addDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Pquality.QUALITY_REGISTRY, RegisteredQuality.CODEC, RegisteredQuality.CODEC);
    };
};
