package com.petrolpark.pquality.core;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemDecorator;

public interface IQuality {
    
    public double multiply(double base);

    public double bigMultiply(double base);

    public double reduce(double base);

    public int multiply(int base);

    public int bigMultiply(int base);

    public int reduce(int base);

    public int reduceToZero(int base);

    public float multiply(float base);

    public float bigMultiply(float base);

    public float reduce(float base);

    /**
     * @param guiGraphics
     * @param font
     * @param stack
     * @param xOffset
     * @param yOffset
     * @return {@code true} if the Render State needs resetting
     * @see IItemDecorator#render(GuiGraphics, Font, ItemStack, int, int)
     */
    @OnlyIn(Dist.CLIENT)
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset);
};
