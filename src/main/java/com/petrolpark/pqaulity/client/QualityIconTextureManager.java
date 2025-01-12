package com.petrolpark.pqaulity.client;

import com.petrolpark.PetrolparkRegistries;
import com.petrolpark.pqaulity.Pquality;
import com.petrolpark.pqaulity.core.RegisteredQuality;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QualityIconTextureManager extends TextureAtlasHolder {

    protected static QualityIconTextureManager instance = null;

    public static QualityIconTextureManager getInstance() {
        if (instance == null) {
            Minecraft mc = Minecraft.getInstance();
            instance = new QualityIconTextureManager(mc.textureManager);
        };
        return instance;
    };

    public QualityIconTextureManager(TextureManager textureManager) {
        super(textureManager, new ResourceLocation("textures/atlas/quality.png"), new ResourceLocation("quality"));
    };

    public TextureAtlasSprite get(RegisteredQuality quality) {
        return getSprite(PetrolparkRegistries.getDataRegistry(Pquality.QUALITY_REGISTRY).getKey(quality));
    };
    
};
