package mod.chiselsandbits.neighborhood;

import mod.chiselsandbits.api.blockinformation.BlockInformation;
import mod.chiselsandbits.api.multistate.accessor.IAreaAccessor;
import mod.chiselsandbits.api.neighborhood.IBlockNeighborhood;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Objects;

public final class BlockNeighborhood implements IBlockNeighborhood
{
    private final EnumMap<Direction, BlockNeighborhoodEntry> neighborhoodMap;

    public BlockNeighborhood(final EnumMap<Direction, BlockNeighborhoodEntry> neighborhoodMap) {this.neighborhoodMap = neighborhoodMap;}

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof BlockNeighborhood))
        {
            return false;
        }

        final BlockNeighborhood that = (BlockNeighborhood) o;

        return Objects.equals(neighborhoodMap, that.neighborhoodMap);
    }

    @Override
    public int hashCode()
    {
        return neighborhoodMap != null ? neighborhoodMap.hashCode() : 0;
    }

    @Override
    public @NotNull BlockInformation getBlockInformation(final Direction direction)
    {
        return neighborhoodMap.get(direction).getBlockInformation();
    }

    @Override
    public @Nullable IAreaAccessor getAreaAccessor(final Direction direction)
    {
        return neighborhoodMap.get(direction).getAccessor();
    }
}
