package petrolpark.mc.pquality;


import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.objectweb.asm.Type;

import mezz.jei.neoforge.startup.ForgePluginFinder;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.ModFileScanData;
import petrolpark.mc.pquality.core.QualityUtil;
import petrolpark.mc.pquality.core.plugin.IPqualityPlugin;
import petrolpark.mc.pquality.core.plugin.PQualityPlugin;

public class PqualityPluginManager {

	private static final List<IPqualityPlugin> plugins = new ArrayList<>();

    static final void init() {

		plugins.addAll(findPlugins());

        for (IPqualityPlugin plugin : plugins) {
            plugin.acceptFlagPoleModifiers(
                (fp, b) -> QualityUtil.fetchQuality(fp).multiply(b), (fp, b) -> QualityUtil.fetchQuality(fp).bigMultiply(b), (fp, b) -> QualityUtil.fetchQuality(fp).reduce(b),
                (fp, b) -> QualityUtil.fetchQuality(fp).multiply(b), (fp, b) -> QualityUtil.fetchQuality(fp).bigMultiply(b), (fp, b) -> QualityUtil.fetchQuality(fp).reduce(b),
                (fp, b) -> QualityUtil.fetchQuality(fp).multiply(b), (fp, b) -> QualityUtil.fetchQuality(fp).bigMultiply(b), (fp, b) -> QualityUtil.fetchQuality(fp).reduce(b),
				(fp, b) -> QualityUtil.fetchQuality(fp).multiply(b), (fp, b) -> QualityUtil.fetchQuality(fp).bigMultiply(b), (fp, b) -> QualityUtil.fetchQuality(fp).reduce(b)
            );
            plugin.acceptItemStackModifiers(
                (s, b) -> QualityUtil.getQuality(s).multiply(b), (s, b) -> QualityUtil.getQuality(s).bigMultiply(b), (s, b) -> QualityUtil.getQuality(s).reduce(b),
                (s, b) -> QualityUtil.getQuality(s).multiply(b), (s, b) -> QualityUtil.getQuality(s).bigMultiply(b), (s, b) -> QualityUtil.getQuality(s).reduce(b), 
                (s, b) -> QualityUtil.getQuality(s).multiply(b), (s, b) -> QualityUtil.getQuality(s).bigMultiply(b), (s, b) -> QualityUtil.getQuality(s).reduce(b),
				(s, b) -> QualityUtil.getQuality(s).multiply(b), (s, b) -> QualityUtil.getQuality(s).bigMultiply(b), (s, b) -> QualityUtil.getQuality(s).reduce(b)
            );
        };
    };
    
    /**
     * Copied from {@link ForgePluginFinder}
     */
    private static final List<IPqualityPlugin> findPlugins() {
        Pquality.LOGGER.info("Searching for pquality plugins");
        final Type annotationType = Type.getType(PQualityPlugin.class);
        final List<ModFileScanData> allScanData = ModList.get().getAllScanData();
		final Set<String> pluginClassNames = new LinkedHashSet<>();
		for (ModFileScanData scanData : allScanData) {
			Iterable<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();
			for (ModFileScanData.AnnotationData a : annotations) {
				if (Objects.equals(a.annotationType(), annotationType)) {
					String memberName = a.memberName();
					pluginClassNames.add(memberName);
				};
			};
		}
		List<IPqualityPlugin> instances = new ArrayList<>();
		for (String className : pluginClassNames) {
			try {
				Class<?> asmClass = Class.forName(className);
				Class<? extends IPqualityPlugin> asmInstanceClass = asmClass.asSubclass(IPqualityPlugin.class);
				Constructor<? extends IPqualityPlugin> constructor = asmInstanceClass.getDeclaredConstructor();
				IPqualityPlugin instance = constructor.newInstance();
				if (instance.shouldLoad()) instances.add(instance);
			} catch (ReflectiveOperationException | LinkageError e) {
				Pquality.LOGGER.error("Failed to load: {}", className, e);
			};
		};
		return instances;
    };

	public static final Stream<IPqualityPlugin> streamPlugins() {
		return plugins.stream();
	};
};
