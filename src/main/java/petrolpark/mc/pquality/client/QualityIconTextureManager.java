package petrolpark.mc.pquality.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import petrolpark.mc.library.registry.PetrolparkRegistries;
import petrolpark.mc.pquality.Pquality;
import petrolpark.mc.pquality.core.RegisteredQuality;

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
