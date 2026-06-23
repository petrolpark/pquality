// package petrolpark.mc.pquality.compat.jade;

// import java.util.Optional;

// import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

// import net.minecraft.resources.ResourceLocation;
// import petrolpark.mc.compat.CompatMods;
// import petrolpark.mc.library.PetrolparkTags;
// import petrolpark.mc.library.compat.create.RequiresCreate;
// import petrolpark.mc.library.compat.create.core.world.block.entity.behaviour.FlagPoleBehaviour;
// import petrolpark.mc.library.mixin.accessor.BlockAccessor;
// import petrolpark.mc.pquality.Pquality;
// import petrolpark.mc.pquality.core.QualityUtil;
// import snownee.jade.api.IBlockComponentProvider;
// import snownee.jade.api.ITooltip;
// import snownee.jade.api.config.IPluginConfig;

// public class QualityBlockComponentProvider implements IBlockComponentProvider {

//     public static final ResourceLocation UID = Pquality.asResource("quality");

//     @Override
//     public ResourceLocation getUid() {
//         return UID;
//     };

//     // @Override
//     // public @Nullable IElement getIcon(BlockAccessor accessor, IPluginConfig config, IElement currentIcon) {
//     //     ItemStack stack = accessor.getFakeBlock().copy();
//     //     return getQualityFlag(accessor).map(flag -> {
//     //         ItemFlagPole.get(stack).flag(flag);
//     //         return ItemStackElement.of(stack);
//     //     }).orElse(null);
//     // };

//     @Override
//     public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig pluginConfig) {
//         getQualityFlag(blockAccessor).ifPresent(flag -> {
//             if (PetrolparkTags.Flags.HIDDEN.matches(flag)) tooltip.add(flag.getNameColored()); // Add it if it is hidden, as otherwise it is automatically added anyway
//         });
//     };

//     protected Optional<Flag> getQualityFlag(BlockAccessor blockAccessor) {
//         if (CompatMods.CREATE.isLoaded()) {
//             return getCreateQualityFlag(blockAccessor);
//         } else {
//             return Optional.empty();
//         }
//     };

//     @RequiresCreate
//     protected Optional<Flag> getCreateQualityFlag(BlockAccessor blockAccessor) {
//         if (blockAccessor.getBlockEntity() instanceof SmartBlockEntity sbe) {
//             FlagPoleBehaviour behaviour = sbe.getBehaviour(FlagPoleBehaviour.TYPE);
//             if (behaviour != null) return Optional.ofNullable(QualityUtil.getHighestQualityFlag(behaviour.getFlagPole()));
//         };
//         return Optional.empty();
//     };
    
// };
