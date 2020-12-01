package mod.chiselsandbits.utils;

import mod.chiselsandbits.core.ChiselsAndBits;

import java.util.LinkedHashMap;

public class SimpleMaxSizedCache<K, V>
{

    private final LinkedHashMap<K, V> cache = new LinkedHashMap<>();

    private final long maxSize;

    public SimpleMaxSizedCache(final long maxSize) {this.maxSize = maxSize;}

    private void evictFromCacheIfNeeded() {
        if (cache.size() == maxSize) {
            cache.remove(cache.keySet().iterator().next());
        }
    }

    public V get(final K key) {
        return cache.get(key);
    }

    public void put(final K key, final V value) {
        if (!cache.containsKey(key))
            evictFromCacheIfNeeded();

        cache.put(key, value);
    }
}
