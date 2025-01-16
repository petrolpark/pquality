package com.petrolpark.pquality.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.petrolpark.contamination.Contaminant;
import com.petrolpark.contamination.IContamination;
import com.petrolpark.contamination.ItemContamination;
import com.petrolpark.pquality.core.QualityUtil;
import com.petrolpark.recipe.contamination.IHandleContaminationMyself;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DecreaseQualityRecipe extends CustomRecipe implements IHandleContaminationMyself<CraftingContainer> {

    public static final Serializer SERIALIZER = new Serializer();

    public DecreaseQualityRecipe(ResourceLocation id) {
        super(id, CraftingBookCategory.MISC);
    };

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack firstStack = ItemStack.EMPTY;
        for (ItemStack stack : container.getItems()) {
            if (stack.isEmpty()) continue;
            if (!firstStack.isEmpty()) return false;
            if (QualityUtil.getQuality(stack) == QualityUtil.NO_QUALITY) return false;
            firstStack = stack;
        };
        return !firstStack.isEmpty();
    };

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        return container.getItems().stream().dropWhile(ItemStack::isEmpty).findAny().map(stack -> {
            ItemStack result = stack.copyWithCount(1);
            IContamination<?, ?> contamination = ItemContamination.get(result);
            Contaminant contaminant = QualityUtil.getHighestQualityContaminant(contamination);
            if (contaminant != null) contamination.decontaminateOnly(contaminant);
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
    public boolean contaminationHandled(CraftingContainer container, RegistryAccess registryAccess) {
        return true;
    };

    public static class Serializer implements RecipeSerializer<DecreaseQualityRecipe> {

        @Override
        public DecreaseQualityRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new DecreaseQualityRecipe(pRecipeId);
        };

        @Override
        public @Nullable DecreaseQualityRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new DecreaseQualityRecipe(pRecipeId);
        };

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, DecreaseQualityRecipe pRecipe) {};

    };
    
};
