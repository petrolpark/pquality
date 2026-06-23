package petrolpark.mc.pquality;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import petrolpark.mc.library.util.Lang;

public class PqualityTags {

    public enum Attributes {

        UNAFFECTED_BY_QUALITY;

        public final TagKey<Attribute> tag;

        Attributes() {
            tag = TagKey.create(Registries.ATTRIBUTE, Pquality.asResource(Lang.asId(name())));
        };
    };
  
    public enum BlockEntityTypes {

        QUALITY_AFFECTS_STRESS_IMPACT;

        public final TagKey<BlockEntityType<?>> tag;

        BlockEntityTypes() {
            tag = TagKey.create(Registries.BLOCK_ENTITY_TYPE, Pquality.asResource(Lang.asId(name())));
        };


        @SuppressWarnings("null")
        public boolean matches(BlockEntityType<?> blockEntityType) {
            return blockEntityType.builtInRegistryHolder().is(tag);
        };
    };

    public enum Items {

        DECREASES_QUALITY;

        public final TagKey<Item> tag;

        Items() {
            tag = TagKey.create(Registries.ITEM, Pquality.asResource(name()));  
        };

        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        };
    };
};
