package petrolpark.mc.pquality.core.plugin;

import static petrolpark.mc.pquality.config.PqualityConfigs.server;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import com.simibubi.create.content.equipment.armor.BacktankItem;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import petrolpark.mc.library.compat.Mods;
import petrolpark.mc.pquality.PqualityTags;
import petrolpark.mc.pquality.core.client.effectDescription.IQualityEffectDescription;

@PQualityPlugin
public class CreatePqualityPlugin extends BuiltInPqualityPlugin {
    
    CreatePqualityPlugin() {};

    @Override
    public boolean shouldLoad() {
        return Mods.CREATE.isLoading();
    };
    
    @Override
    public void registerEffectDescriptions(Consumer<IQualityEffectDescription> adder) {
        register(adder, "stress_capacity", server().affectStressCapacity,
            StreamSupport.stream(BuiltInRegistries.BLOCK_ENTITY_TYPE.getTagOrEmpty(PqualityTags.BlockEntityTypes.QUALITY_AFFECTS_STRESS_IMPACT.tag).spliterator(), false)
                .map(Holder::value)
                .map(BlockEntityType::getValidBlocks)
                .flatMap(Set::stream)
                .map(Block::asItem)
        );
        register(adder, "backtank_capacity", server().affectBacktankCapacity, item -> item instanceof BacktankItem);
    };
};
