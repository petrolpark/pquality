package com.petrolpark.pqaulity.event;

import com.petrolpark.contamination.Contaminables;
import com.petrolpark.pqaulity.Pquality;
import com.petrolpark.pqaulity.client.QualityItemDecorator;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = Pquality.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onAddItemDecorators(RegisterItemDecorationsEvent event) {
        BuiltInRegistries.ITEM.stream().filter(Contaminables.ITEM::isContaminable).forEach(item -> event.register(item, QualityItemDecorator.INSTANCE));
    };
};
