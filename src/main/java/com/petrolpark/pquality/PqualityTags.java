package com.petrolpark.pquality;

import com.petrolpark.util.Lang;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class PqualityTags {
  
    public enum BlockEntityTypes {

        QUALITY_AFFECTS_STRESS_IMPACT;

        public final TagKey<BlockEntityType<?>> tag;

        BlockEntityTypes() {
            tag = TagKey.create(Registries.BLOCK_ENTITY_TYPE, Pquality.asResource(Lang.asId(name())));
        };

        public boolean matches(BlockEntityType<?> blockEntityType) {
            return ForgeRegistries.BLOCK_ENTITY_TYPES.getHolder(blockEntityType).orElseThrow().is(tag);
        };
    };
};
