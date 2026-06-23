package petrolpark.mc.pquality.core.client;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.blaze3d.vertex.PoseStack;

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
        QualityUtil.getQualityHolder(stack).ifPresent(holder -> {
            final PoseStack ms = guiGraphics.pose();
            ms.pushPose();
            ms.translate(xOffset, yOffset, 0f);
            guiGraphics.blit(0, 0, 200, 16, 16, QualityIconTextureManager.getInstance().get(holder));
            ms.popPose();
        });
        return false;
    };
    
};
