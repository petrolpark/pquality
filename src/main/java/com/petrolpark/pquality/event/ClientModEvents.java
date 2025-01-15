package com.petrolpark.pquality.event;

import com.petrolpark.contamination.Contaminables;
import com.petrolpark.pquality.Pquality;
import com.petrolpark.pquality.client.QualityIconTextureManager;
import com.petrolpark.pquality.client.QualityItemDecorator;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Pquality.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onAddItemDecorators(RegisterItemDecorationsEvent event) {
        BuiltInRegistries.ITEM.stream().filter(Contaminables.ITEM::isContaminable).forEach(item -> event.register(item, QualityItemDecorator.INSTANCE));
    };

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(QualityIconTextureManager.getInstance());
    };
    

};
