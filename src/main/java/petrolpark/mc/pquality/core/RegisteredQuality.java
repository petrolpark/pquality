package petrolpark.mc.pquality.core;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import petrolpark.mc.library.registry.PetrolparkRegistries;
import petrolpark.mc.pquality.Pquality;
import petrolpark.mc.pquality.client.QualityIconTextureManager;

public class RegisteredQuality implements IQuality {

    protected static final Map<Flag, RegisteredQuality> FLAG_QUALITIES = new HashMap<>();
    protected static SortedSet<Flag> ORDERED_FLAGS = new TreeSet<>();

    public static final Codec<RegisteredQuality> CODEC = ExtraCodecs.catchDecoderException(RecordCodecBuilder.create(instance -> 
        instance.group(
            Codec.intRange(Integer.MIN_VALUE, Integer.MAX_VALUE).fieldOf("priority").forGetter(RegisteredQuality::getPriority),
            Codec.doubleRange(1d, Double.MAX_VALUE).fieldOf("multiplier").forGetter(RegisteredQuality::getMultiplier),
            Codec.doubleRange(1d, Double.MAX_VALUE).fieldOf("bigMultiplier").forGetter(RegisteredQuality::getBigMultiplier),
            Codec.doubleRange(0d, 1d).fieldOf("reducer").forGetter(RegisteredQuality::getReducer),
            ResourceLocation.CODEC.fieldOf("flag").forGetter(quality -> quality.flagLocation)
        ).apply(instance, RegisteredQuality::new)
    ));
    
    protected final int priority;
    protected final double multiplier;
    protected final double bigMultiplier;
    protected final double reducer;
    private final ResourceLocation flagLocation;

    protected Flag flag;

    protected RegisteredQuality(int priority, double multiplier, double bigMultiplier, double reducer, ResourceLocation flagLocation) {
        this.priority = priority;
        this.multiplier = multiplier;
        this.bigMultiplier = bigMultiplier;
        this.reducer = reducer;
        this.flagLocation = flagLocation;
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

    public Flag getFlag() {
        if (flag == null) {
            throw new IllegalStateException(String.format("Quality Flag '%s' does not exist", flagLocation.toString()));
        };
        return flag;
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
            FLAG_QUALITIES.clear();
            registryAccess.registryOrThrow(Pquality.QUALITY_REGISTRY).forEach(quality -> {
                quality.flag = registryAccess.registryOrThrow(PetrolparkRegistries.Keys.FLAG).get(quality.flagLocation);
                if (quality.flag == null) throw new JsonSyntaxException(String.format("Could not find Quality Flag: %s", quality.flagLocation.toString()));
                FLAG_QUALITIES.put(quality.flag, quality);
            });
            ORDERED_FLAGS = new TreeSet<>((c1, c2) -> {
                return FLAG_QUALITIES.get(c2).getPriority() - FLAG_QUALITIES.get(c1).getPriority();
            });
            ORDERED_FLAGS.addAll(FLAG_QUALITIES.keySet());
        };
        
    };

};
