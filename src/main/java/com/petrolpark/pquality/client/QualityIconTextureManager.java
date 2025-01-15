package com.petrolpark.pquality.client;

import com.petrolpark.PetrolparkRegistries;
import com.petrolpark.pquality.Pquality;
import com.petrolpark.pquality.core.RegisteredQuality;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
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
        super(textureManager, Pquality.asResource("textures/atlas/qualities.png"), Pquality.asResource("qualities"));
    };

    public TextureAtlasSprite get(RegisteredQuality quality) {
        return getSprite(PetrolparkRegistries.getDataRegistry(Pquality.QUALITY_REGISTRY).getKey(quality));
    };
    
};
