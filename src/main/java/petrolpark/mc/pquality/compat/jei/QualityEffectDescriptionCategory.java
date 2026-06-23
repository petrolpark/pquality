package petrolpark.mc.pquality.compat.jei;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.Nullable;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.common.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import petrolpark.mc.library.compat.jei.ItemStackDrawable;
import petrolpark.mc.library.compat.jei.ingredient.FlagIngredientType;
import petrolpark.mc.library.compat.jei.ingredient.FlagIngredientType.FlagHolderHolder;
import petrolpark.mc.library.core.flags.ItemFlagPole;
import petrolpark.mc.pquality.Pquality;
import petrolpark.mc.pquality.core.QualityUtil;
import petrolpark.mc.pquality.core.RegisteredQuality;
import petrolpark.mc.pquality.core.client.effectDescription.IQualityEffectDescription;

@ParametersAreNonnullByDefault
public class QualityEffectDescriptionCategory extends AbstractRecipeCategory<IQualityEffectDescription> {

    private static final IDrawable ICON = new ItemStackDrawable(() -> {
        final ItemStack stack = new ItemStack(Items.APPLE);
        final List<RegisteredQuality> qualities = QualityUtil.streamOrderedRegisteredQualities().toList();
        for (int i = 2; i >= 0; i--) { // Try to apply Gold Quality - if not, whatever the third Quality is
            if (qualities.size() >= i) {
                ItemFlagPole.get(stack).flag(qualities.get(i).flag());
                break;
            };
        };
        return stack;
    });

    public static final RecipeType<IQualityEffectDescription> RECIPE_TYPE = RecipeType.create(Pquality.MOD_ID, "effects", IQualityEffectDescription.class);

    public QualityEffectDescriptionCategory() {
        super(RECIPE_TYPE, Pquality.translate("gui.jei.category.effects"), ICON, 170, 55);
    };

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IQualityEffectDescription recipe, IFocusGroup focuses) {
        final List<ItemStack> stacks = recipe.streamApplicableItemStacks().toList();
        final List<FlagHolderHolder> qualityFlags = QualityUtil.streamOrderedRegisteredQualities()
            .map(RegisteredQuality::flag)
            .map(FlagHolderHolder::new)
            .toList();
        
        builder.addInputSlot(0, 0)
            .addItemStacks(stacks)
            .setStandardSlotBackground()
            .setSlotName("item");

        builder.addInputSlot(20, 3)
            .addIngredients(FlagIngredientType.TYPE, qualityFlags)
            .setBackground(FlagIngredientType.BACKGROUND, -1, -1)
            .setCustomRenderer(FlagIngredientType.TYPE, FlagIngredientType.FULL_RENDERER)
            .setSlotName("flag");
    };

    @Override
    public void draw(IQualityEffectDescription recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        final Font font = Minecraft.getInstance().font;
        final Language language = Language.getInstance();
        recipeSlotsView.findSlotByName("flag")
            .flatMap(IRecipeSlotView::getDisplayedIngredient)
            .flatMap(ingredient -> ingredient.getIngredient(FlagIngredientType.TYPE))
            .map(FlagHolderHolder::holder)
            .flatMap(QualityUtil::fromFlag)
            .ifPresent(quality -> {
                int yOffset = 0;
                for (FormattedText line : StringUtil.splitLines(
                        Minecraft.getInstance().font,
                        recipe.getDescription(
                            recipeSlotsView.findSlotByName("item").get()
                                .getDisplayedIngredient().get()
                                .getIngredient(VanillaTypes.ITEM_STACK).get(),
                            quality
                        ), 170, 4
                    ).first()
                ) {
                    guiGraphics.drawString(font, language.getVisualOrder(line), 0, 20 + yOffset, 0xFF808080, false);
                    yOffset += font.lineHeight;
                }}
            );
    };

    @Override
    public boolean isHandled(IQualityEffectDescription recipe) {
        return recipe.isEnabled();
    };

    @Override
    public @Nullable ResourceLocation getRegistryName(IQualityEffectDescription recipe) {
        return recipe.id();
    };
    
};
