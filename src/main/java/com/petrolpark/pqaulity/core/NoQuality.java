package com.petrolpark.pqaulity.core;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class NoQuality implements IQuality {

    @Override
    public double multiply(double base) {
        return base;
    };

    @Override
    public double bigMultiply(double base) {
        return base;
    };

    @Override
    public double reduce(double base) {
        return base;
    };

    @Override
    public int multiply(int base) {
        return base;
    };

    @Override
    public int bigMultiply(int base) {
        return base;
    };

    @Override
    public int reduce(int base) {
        return base;
    };

    @Override
    public int reduceToZero(int base) {
        return base;
    };

    @Override
    public float multiply(float base) {
        return base;
    };

    @Override
    public float bigMultiply(float base) {
        return base;
    };

    @Override
    public float reduce(float base) {
        return base;
    };

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        return false;
    };
    
};
