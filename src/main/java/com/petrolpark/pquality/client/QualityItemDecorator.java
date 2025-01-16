package com.petrolpark.pquality.client;

import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

public class QualityItemDecorator implements IItemDecorator {

    public static final QualityItemDecorator INSTANCE = new QualityItemDecorator();

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        if (PqualityConfig.CLIENT.shiftToSeeQuality.get() && !Screen.hasShiftDown()) return false;
        return QualityUtil.getQuality(stack).render(guiGraphics, font, stack, xOffset, yOffset);
    };
    
};
