
package scottishbiomes.configuration;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import scottishbiomes.lib.Biomes;

import com.google.common.collect.Maps;

public class BiomeIdConfig
{
    private final Map<Biomes, Integer> settings = Maps.newEnumMap(Biomes.class);
    private int defaultBiomeId = 64;

    final Configuration cfg;

    BiomeIdConfig(final Configuration cfg)
    {
        this.cfg = cfg;
    }

    public int getConfigId(final Biomes tag)
    {
        checkState(settings.size() != 0);
        return settings.get(tag);
    }

    public BiomeIdConfig populate()
    {
        checkState(settings.size() == 0);

        for (final Biomes tag : Biomes.values())
        {
            final String key = tag.toString().replace('_', '.').toLowerCase() + ".id";
            final int defaultId = defaultBiomeId++;
            final int id = cfg.get("biome", key, defaultId).getInt(defaultId);
            settings.put(tag, id);
            if (BiomeGenBase.biomeList[id] != null)
                throw new IllegalArgumentException(
                        String.format(
                                "Biome id %d is already in use by %s when adding %s. Please review the configuration file.",
                                id, BiomeGenBase.biomeList[id].biomeName, tag.toString()));
        }

        return this;
    }
}
