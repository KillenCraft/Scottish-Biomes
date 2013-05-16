
package scottishbiomes.configuration;

import java.io.File;

import net.minecraftforge.common.Configuration;

public final class ConfigurationHandler
{
    final Configuration cfg;

    public ConfigurationHandler(final File configFile)
    {
        cfg = new Configuration(configFile);
        cfg.load();
    }

    public BiomeIdConfig getConfigBiomeIds()
    {
        return new BiomeIdConfig(cfg).populate();
    }

    public BlockIdConfig getConfigBlockIds()
    {
        return new BlockIdConfig(cfg).populate();
    }

    public void save()
    {
        if (cfg.hasChanged())
        {
            cfg.save();
        }
    }
}
