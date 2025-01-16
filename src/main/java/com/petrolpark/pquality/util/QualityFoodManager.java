package com.petrolpark.pquality.util;

import com.petrolpark.pquality.PqualityConfig;
import com.petrolpark.pquality.core.IQuality;
import com.petrolpark.pquality.core.ItemQualityHashMap;
import com.petrolpark.pquality.core.QualityUtil;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class QualityFoodManager extends ItemQualityHashMap<FoodProperties> {

    protected static final ItemQualityHashMap<FoodProperties> FOODS = new ItemQualityHashMap<>();

    @SuppressWarnings("deprecation")
    public static FoodProperties get(Item item, IQuality quality) {
        return FOODS.computeIfAbsent(item, quality, () -> {
            FoodProperties baseProperties = item.getFoodProperties();
            if (quality == QualityUtil.NO_QUALITY) return baseProperties;
            FoodProperties.Builder builder = new FoodProperties.Builder()
                .nutrition(PqualityConfig.SERVER.affectFoodNutrition.get() ? quality.multiply(baseProperties.getNutrition()) : baseProperties.getNutrition())
                .saturationMod(baseProperties.getSaturationModifier());
            if (baseProperties.isMeat()) builder.meat();
            if (baseProperties.canAlwaysEat()) builder.alwaysEat();
            if (baseProperties.isFastFood()) builder.fast();
            if (PqualityConfig.SERVER.affectFoodEffectLength.get()) {
                baseProperties.getEffects().stream().map(pair -> pair.mapFirst(effect -> MobEffectHelper.copyAndExtend(effect, quality))).forEach(pair -> builder.effect(pair::getFirst, pair.getSecond()));
            } else {
                baseProperties.getEffects().stream().forEach(pair -> builder.effect(pair::getFirst, pair.getSecond()));
            };
            return builder.build();
        });
    };
};
