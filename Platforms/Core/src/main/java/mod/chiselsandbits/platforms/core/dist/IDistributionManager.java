package mod.chiselsandbits.platforms.core.dist;

import mod.chiselsandbits.platforms.core.IChiselsAndBitsPlatformCore;

/**
 * Manages the properties of the current distribution and gives access to the current distribution.
 */
public interface IDistributionManager
{
    /**
     * Gives access to the distribution manager.
     *
     * @return The distribution manager.
     */
    static IDistributionManager getInstance() {
        return IChiselsAndBitsPlatformCore.getInstance().getDistributionManager();
    }

    /**
     * The current distribution.
     *
     * @return The current distribution.
     */
    Dist getCurrentDistribution();

    /**
     * Indicates if the current platform is running in production mode or not.
     *
     * @return True if the current platform is running in production mode. False when not.
     */
    boolean isProduction();
}
