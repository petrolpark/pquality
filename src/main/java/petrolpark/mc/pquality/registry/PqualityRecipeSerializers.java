package petrolpark.mc.pquality.registry;

import static petrolpark.mc.pquality.Pquality.REGISTRATE;

import com.tterrag.registrate.util.entry.RegistryEntry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import petrolpark.mc.pquality.core.recipe.DecreaseQualityRecipe;

public class PqualityRecipeSerializers {

    public static final RegistryEntry<RecipeSerializer<?>, RecipeSerializer<DecreaseQualityRecipe>> DECREASE_QUALITY = REGISTRATE.recipeSerializer("decrease_quality", () -> DecreaseQualityRecipe.SERIALIZER);
    
    public static final void register() {};
};

