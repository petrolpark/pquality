package petrolpark.mc.pquality.core.plugin;

import static petrolpark.mc.pquality.config.PqualityConfigs.server;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.createmod.catnip.config.ConfigBase.ConfigBool;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import petrolpark.mc.library.PetrolparkTags;
import petrolpark.mc.library.util.ItemHelper;
import petrolpark.mc.pquality.Pquality;
import petrolpark.mc.pquality.core.client.effectDescription.IQualityEffectDescription;
import petrolpark.mc.pquality.core.client.effectDescription.SimpleQualityEffectDescription;

@PQualityPlugin
public class BuiltInPqualityPlugin implements IPqualityPlugin {

    BuiltInPqualityPlugin() {};

    private final Predicate<Item> isFood = item -> item.components().has(DataComponents.FOOD);
    private final Predicate<Item> isEffectFood = item -> {
        final FoodProperties food = item.components().get(DataComponents.FOOD);
        return food != null && !food.effects().isEmpty();
    };
    
    @Override
    @SuppressWarnings("deprecation")
    public void registerEffectDescriptions(Consumer<IQualityEffectDescription> adder) {
        register(adder, "anvil_repair", server().affectAnvilRepair, ItemHelper.getKnownAnvilRepairItems().stream());
        register(adder, "attributes", server().affectAttributes, item -> !item.components().getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY).modifiers().isEmpty());
        register(adder, "baby_animal_growth", server().affectBabyAnimalGrowth, ItemHelper.getKnownAnimalFoods(Minecraft.getInstance().level).stream());
        register(adder, "bone_meal", server().affectBoneMeal, item -> item instanceof BoneMealItem);
        register(adder, "bundle_size", server().affectBundleSize, item -> item instanceof BundleItem);
        register(adder, "composting", server().affectComposting, item -> item.builtInRegistryHolder().getData(NeoForgeDataMaps.COMPOSTABLES) != null);
        register(adder, "durability", server().affectItemDurability, item -> item.components().has(DataComponents.MAX_DAMAGE));
        register(adder, "eye_of_ender", server().affectEyeOfEnder, Items.ENDER_EYE);
        register(adder, "firework_flight_time", server().affectFireworkFlightTime, Items.FIREWORK_ROCKET);
        register(adder, "food_hunger", server().affectFoodHunger, isFood);
        register(adder, "food_saturation", server().affectFoodSaturation, isFood);
        register(adder, "food_eat_time", server().affectFoodEatTime, isFood);
        register(adder, "food_effect_probability", server().affectFoodEffectProbability, isEffectFood);
        register(adder, "food_effect_length", server().affectFoodEffectLength, isEffectFood);
        register(adder, "food_effect_level", server().affectFoodEffectLevel, isEffectFood);
        register(adder, "fuel_burn_time", server().affectFuelBurnTime, item -> new ItemStack(item).getBurnTime(RecipeType.SMELTING) > 0);
        register(adder, "merchant_trade_reward", server().affectMerchantTradeReward, Items.EMERALD);
        register(adder, "merchant_trade_xp", server().affectMerchantTradeXp, Items.EMERALD);
        register(adder, "potion_length", server().affectPotionLength, Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);
    };

    protected void register(Consumer<IQualityEffectDescription> adder, String name, ConfigBool config, Item ... items) {
        register(adder, name, config, Stream.of(items));
    };

    protected void register(Consumer<IQualityEffectDescription> adder, String name, ConfigBool config, Stream<Item> items) {
        adder.accept(new SimpleQualityEffectDescription(
            Pquality.asResource(name),
            config::get,
            items.map(ItemStack::new).toList()
        ));
    };

    protected void register(Consumer<IQualityEffectDescription> adder, String name, ConfigBool config, Predicate<Item> items) {
        adder.accept(new SimpleQualityEffectDescription(
            Pquality.asResource(name),
            config::get,
            BuiltInRegistries.ITEM.stream()
                .filter(PetrolparkTags.Items.FLAGGABLE::matches)
                .filter(items)
                .map(ItemStack::new)
                .toList()
        ));
    };
};
