package com.petrolpark.pquality.compat.jade;

import java.util.Optional;

import com.petrolpark.PetrolparkTags;
import com.petrolpark.RequiresCreate;
import com.petrolpark.compat.CompatMods;
import com.petrolpark.compat.create.block.entity.behaviour.ContaminationBehaviour;
import com.petrolpark.contamination.Contaminant;
import com.petrolpark.pquality.Pquality;
import com.petrolpark.pquality.core.QualityUtil;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class QualityBlockComponentProvider implements IBlockComponentProvider {

    public static final ResourceLocation UID = Pquality.asResource("quality");

    @Override
    public ResourceLocation getUid() {
        return UID;
    };

    // @Override
    // public @Nullable IElement getIcon(BlockAccessor accessor, IPluginConfig config, IElement currentIcon) {
    //     ItemStack stack = accessor.getFakeBlock().copy();
    //     return getQualityContaminant(accessor).map(contaminant -> {
    //         ItemContamination.get(stack).contaminate(contaminant);
    //         return ItemStackElement.of(stack);
    //     }).orElse(null);
    // };

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig pluginConfig) {
        getQualityContaminant(blockAccessor).ifPresent(contaminant -> {
            if (PetrolparkTags.Contaminants.HIDDEN.matches(contaminant)) tooltip.add(contaminant.getNameColored()); // Add it if it is hidden, as otherwise it is automatically added anyway
        });
    };

    protected Optional<Contaminant> getQualityContaminant(BlockAccessor blockAccessor) {
        if (CompatMods.CREATE.isLoaded()) {
            return getCreateQualityContaminant(blockAccessor);
        } else {
            return Optional.empty();
        }
    };

    @RequiresCreate
    protected Optional<Contaminant> getCreateQualityContaminant(BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof SmartBlockEntity sbe) {
            ContaminationBehaviour behaviour = sbe.getBehaviour(ContaminationBehaviour.TYPE);
            if (behaviour != null) return Optional.ofNullable(QualityUtil.getHighestQualityContaminant(behaviour.getContamination()));
        };
        return Optional.empty();
    };
    
};
