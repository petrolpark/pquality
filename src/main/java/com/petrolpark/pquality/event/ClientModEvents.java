package com.petrolpark.pquality.event;

import com.petrolpark.pquality.Pquality;
import com.petrolpark.pquality.client.QualityIconTextureManager;
import com.petrolpark.pquality.client.QualityItemDecorator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

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
