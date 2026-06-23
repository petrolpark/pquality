package petrolpark.mc.pquality.core.recipe;

import java.util.function.Predicate;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import petrolpark.mc.library.core.flags.Flag;
import petrolpark.mc.library.core.flags.IFlagPole;
import petrolpark.mc.library.core.flags.ItemFlagPole;
import petrolpark.mc.library.core.flags.recipe.IHandleFlagsMyselfRecipe;
import petrolpark.mc.pquality.PqualityTags;
import petrolpark.mc.pquality.core.QualityUtil;

@ParametersAreNonnullByDefault
public class DecreaseQualityRecipe extends CustomRecipe implements IHandleFlagsMyselfRecipe<CraftingInput> {

    public static final DecreaseQualityRecipe INSTANCE = new DecreaseQualityRecipe();
    public static final MapCodec<DecreaseQualityRecipe> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DecreaseQualityRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<DecreaseQualityRecipe> SERIALIZER = new RecipeSerializer<>() {

        @Override
        public MapCodec<DecreaseQualityRecipe> codec() {
            return CODEC;
        };

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DecreaseQualityRecipe> streamCodec() {
            return STREAM_CODEC;
        };
        
    };

    DecreaseQualityRecipe() {
        super(CraftingBookCategory.MISC);
    };

    @Override
    public boolean matches(CraftingInput input, Level level) {
        boolean catalystFound = false;
        ItemStack firstStack = ItemStack.EMPTY;
        for (ItemStack stack : input.items()) {
            if (stack.isEmpty()) continue;
            if (PqualityTags.Items.DECREASES_QUALITY.matches(stack)) {
                if (catalystFound) return false;
                catalystFound = true;
                continue;
            }
            if (!firstStack.isEmpty()) return false;
            if (QualityUtil.getQuality(stack) == QualityUtil.NO_QUALITY) return false;
            firstStack = stack;
        };
        return !firstStack.isEmpty();
    };

    @Override
    public ItemStack assemble(CraftingInput container, HolderLookup.Provider registryAccess) {
        return container.items().stream()
            .filter(Predicate.not(ItemStack::isEmpty))
            .filter(Predicate.not(PqualityTags.Items.DECREASES_QUALITY::matches))
            .findAny()
            .map(stack -> {
                final ItemStack result = stack.copyWithCount(1);
                final IFlagPole<?, ?> flagpole = ItemFlagPole.get(result);
                final Holder<Flag> flag = QualityUtil.getHighestQualityFlag(flagpole);
                if (flag != null) flagpole.unflagOnly(flag);
                return result;
            }).orElse(ItemStack.EMPTY);
    };

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    };

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    };

    @Override
    public boolean areFlagsHandled(CraftingInput container, HolderLookup.Provider registryAccess) {
        return true;
    };
    
};
