package petrolpark.mc.pquality.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import petrolpark.mc.pquality.PqualityConfig;
import petrolpark.mc.pquality.core.QualityUtil;
import petrolpark.mc.pquality.util.QualityFoodManager;

@Mixin(IForgeItem.class)
public interface IForgeItemMixin {

    @Shadow
    Item self();
    
    @Overwrite(remap = false)
    @SuppressWarnings("deprecation")
    default FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (PqualityConfig.SERVER.affectFood()) return QualityFoodManager.get(self(), QualityUtil.getQuality(stack));
        return self().getFoodProperties();
    };
};
