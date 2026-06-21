package petrolpark.mc.pquality.client;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;
import petrolpark.mc.pquality.config.PqualityConfigs;
import petrolpark.mc.pquality.core.QualityUtil;

@ParametersAreNonnullByDefault
public class QualityItemDecorator implements IItemDecorator {

    public static final QualityItemDecorator INSTANCE = new QualityItemDecorator();

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        if (PqualityConfigs.client().shiftToSeeQuality.get() && !Screen.hasShiftDown()) return false;
        return QualityUtil.getQuality(stack).render(guiGraphics, font, stack, xOffset, yOffset);
    };
    
};
