package mod.chiselsandbits.api.chiseling.metadata;

import mod.chiselsandbits.api.chiseling.IChiselingContext;
import mod.chiselsandbits.platforms.core.registries.IChiselsAndBitsRegistryEntry;

/**
 * Represents a key passed to an instance of {@link IChiselingContext} so that additional data can be stored
 * on the context.
 * @param <T> The internal type.
 */
public interface IMetadataKey<T> extends IChiselsAndBitsRegistryEntry
{
    /**
     * Creates a snapshot of the value that is passed to it.
     * The primary task of this method is to create a deep copy of the given value.
     *
     * @param value The value.
     * @return The deep copied value.
     */
    T snapshot(T value);
}
