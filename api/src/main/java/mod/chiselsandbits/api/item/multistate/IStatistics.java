package mod.chiselsandbits.api.item.multistate;

import mod.chiselsandbits.api.blockinformation.BlockInformation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import mod.chiselsandbits.api.util.INBTSerializable;

/**
 * The statistics of a multi state itemstack.
 */
public interface IStatistics extends INBTSerializable<CompoundTag>
{

    /**
     * The primary state of the mutli state itemstacks this statistics object
     * belongs to.
     *
     * @return The primary blockstate.
     */
    BlockInformation getPrimaryState();

    /**
     * Indicates if the multistate object is empty.
     *
     * @return {@code true} for an empty multi state object.
     */
    boolean isEmpty();
}
