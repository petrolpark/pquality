package com.petrolpark.pqaulity.core;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;

public class ItemQualityHashMap<T> extends HashMap<Item, Map<IQuality, T>>  {
    
    private static final List<SoftReference<ItemQualityHashMap<?>>> ALL_MAPS = new ArrayList<>();

    public ItemQualityHashMap() {
        super();
        ALL_MAPS.add(new SoftReference<>(this));
    };

    public T computeIfAbsent(Item item, IQuality quality, Supplier<T> newValue) {
        return computeIfAbsent(item, i -> new HashMap<>()).computeIfAbsent(quality, q -> newValue.get());
    };

    public static void clearAll() {
        ALL_MAPS.forEach(ref -> Optional.ofNullable(ref.get()).ifPresent(ItemQualityHashMap::clear));
    };

    /**
     * @deprecated Use {@link ItemQualityHashMap#computeIfAbsent(Item, IQuality, Supplier)}
     */
    @Override
    @Deprecated
    public Map<IQuality, T> get(Object key) {
        return super.get(key);
    };
};
