package com.petrolpark.pquality.core;

import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.petrolpark.PetrolparkRegistries;
import com.petrolpark.contamination.Contaminant;
import com.petrolpark.pquality.Pquality;
import com.petrolpark.pquality.client.QualityIconTextureManager;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RegisteredQuality implements IQuality {

    protected static final Map<Contaminant, RegisteredQuality> CONTAMINANT_QUALITIES = new HashMap<>();
    protected static SortedSet<Contaminant> ORDERED_CONTAMINANTS = new TreeSet<>();

    public static final Codec<RegisteredQuality> CODEC = ExtraCodecs.catchDecoderException(RecordCodecBuilder.create(instance -> 
        instance.group(
            Codec.intRange(Integer.MIN_VALUE, Integer.MAX_VALUE).fieldOf("priority").forGetter(RegisteredQuality::getPriority),
            Codec.doubleRange(1d, Double.MAX_VALUE).fieldOf("multiplier").forGetter(RegisteredQuality::getMultiplier),
            Codec.doubleRange(1d, Double.MAX_VALUE).fieldOf("bigMultiplier").forGetter(RegisteredQuality::getBigMultiplier),
            Codec.doubleRange(0d, 1d).fieldOf("reducer").forGetter(RegisteredQuality::getReducer),
            ResourceLocation.CODEC.fieldOf("contaminant").forGetter(quality -> quality.contaminantLocation)
        ).apply(instance, RegisteredQuality::new)
    ));
    
    protected final int priority;
    protected final double multiplier;
    protected final double bigMultiplier;
    protected final double reducer;
    private final ResourceLocation contaminantLocation;

    protected Contaminant contaminant;

    protected RegisteredQuality(int priority, double multiplier, double bigMultiplier, double reducer, ResourceLocation contaminantLocation) {
        this.priority = priority;
        this.multiplier = multiplier;
        this.bigMultiplier = bigMultiplier;
        this.reducer = reducer;
        this.contaminantLocation = contaminantLocation;
    };

    public int getPriority() {
        return priority;
    };

    public double getMultiplier() {
        return multiplier;
    };

    public double getBigMultiplier() {
        return bigMultiplier;
    };

    public double getReducer() {
        return reducer;
    };

    public Contaminant getContaminant() {
        if (contaminant == null) {
            throw new IllegalStateException(String.format("Quality Contaminant '%s' does not exist", contaminantLocation.toString()));
        };
        return contaminant;
    };

    public int compareTo(RegisteredQuality quality) {
        return priority - quality.priority;
    };

    @Override
    public double multiply(double base) {
        return base * getMultiplier();
    };

    @Override
    public double bigMultiply(double base) {
        return base * getBigMultiplier();
    };

    @Override
    public double reduce(double base) {
        return base * getReducer();
    };

    @Override
    public int multiply(int base) {
        return (int)(getMultiplier() * base);
    };

    @Override
    public int bigMultiply(int base) {
        return (int)(getBigMultiplier() * base);
    };

    @Override
    public int reduce(int base) {
        return Math.max(1, reduceToZero(base));
    };

    @Override
    public int reduceToZero(int base) {
        return (int)(getReducer() * base);
    };

    @Override
    public float multiply(float base) {
        return (float)(getMultiplier() * base);
    };

    @Override
    public float bigMultiply(float base) {
        return (float)(getBigMultiplier() * base);
    };

    @Override
    public float reduce(float base) {
        return (float)(getReducer() * base);
    };

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        PoseStack ms = guiGraphics.pose();
        ms.pushPose();
        ms.translate(xOffset, yOffset, 0f);
        guiGraphics.blit(0, 0, 200, 16, 16, QualityIconTextureManager.getInstance().get(this));
        ms.popPose();
        return false;
    };

    public static class ReloadListener implements ResourceManagerReloadListener {

        protected final RegistryAccess registryAccess;

        public ReloadListener(RegistryAccess registryAccess) {
            this.registryAccess = registryAccess;  
        };

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
            CONTAMINANT_QUALITIES.clear();
            registryAccess.registryOrThrow(Pquality.QUALITY_REGISTRY).forEach(quality -> {
                quality.contaminant = registryAccess.registryOrThrow(PetrolparkRegistries.Keys.CONTAMINANT).get(quality.contaminantLocation);
                if (quality.contaminant == null) throw new JsonSyntaxException(String.format("Could not find Quality Contaminant: %s", quality.contaminantLocation.toString()));
                CONTAMINANT_QUALITIES.put(quality.contaminant, quality);
            });
            ORDERED_CONTAMINANTS = new TreeSet<>((c1, c2) -> {
                return CONTAMINANT_QUALITIES.get(c2).getPriority() - CONTAMINANT_QUALITIES.get(c1).getPriority();
            });
            ORDERED_CONTAMINANTS.addAll(CONTAMINANT_QUALITIES.keySet());
        };
        
    };

};
