package mod.chiselsandbits.forge.platform.distribution;

import mod.chiselsandbits.platforms.core.dist.Dist;
import mod.chiselsandbits.platforms.core.dist.IDistributionManager;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ForgeDistributionManager implements IDistributionManager
{
    private static final ForgeDistributionManager INSTANCE = new ForgeDistributionManager();

    public static ForgeDistributionManager getInstance()
    {
        return INSTANCE;
    }

    private ForgeDistributionManager()
    {
    }

    @Override
    public Dist getCurrentDistribution()
    {
        return switch (FMLEnvironment.dist) {
            case CLIENT -> Dist.CLIENT;
            case DEDICATED_SERVER -> Dist.DEDICATED_SERVER;
        };
    }

    @Override
    public boolean isProduction()
    {
        return FMLEnvironment.production;
    }
}
