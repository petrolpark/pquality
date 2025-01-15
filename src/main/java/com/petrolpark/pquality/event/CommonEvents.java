package com.petrolpark.pquality.event;

import com.petrolpark.contamination.ItemContaminationSavedEvent;
import com.petrolpark.pquality.core.QualityUtil;
import com.petrolpark.pquality.core.RegisteredQuality;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class CommonEvents {

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RegisteredQuality.ReloadListener(event.getRegistryAccess()));
    };
    
    @SubscribeEvent
    public static void onItemContaminationSaved(ItemContaminationSavedEvent event) {
        QualityUtil.getQualityItemStack(event.stack).refreshQuality();
    };
};
