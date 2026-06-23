package petrolpark.mc.pquality.compat.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import petrolpark.mc.pquality.Pquality;
import petrolpark.mc.pquality.PqualityPluginManager;
import petrolpark.mc.pquality.core.client.effectDescription.IQualityEffectDescription;

@JeiPlugin
@ParametersAreNonnullByDefault
public class PqualityJEIPlugin implements IModPlugin {

    public static final ResourceLocation ID = Pquality.asResource("jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new QualityEffectDescriptionCategory());
    };

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        final List<IQualityEffectDescription> effectDescriptions = new ArrayList<>();
        PqualityPluginManager.streamPlugins().forEach(plugin -> plugin.registerEffectDescriptions(effectDescriptions::add));
        registration.addRecipes(QualityEffectDescriptionCategory.RECIPE_TYPE, effectDescriptions);
    };

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    };
    
};
