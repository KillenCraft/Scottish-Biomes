
package scottishbiomes.configuration;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;
import java.util.Set;

import net.minecraftforge.common.Configuration;
import scottishbiomes.lib.Blocks;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class BlockIdConfig
{
    private static final Set<Blocks> TERRAIN_BLOCKS = Sets.immutableEnumSet(Blocks.CRACKED_SAND,
            Blocks.REDROCK);

    private final Map<Blocks, Integer> settings = Maps.newEnumMap(Blocks.class);

    private int defaultTerrainBlockId = 255;
    private int defaultBlockId = 256;

    final Configuration cfg;

    BlockIdConfig(final Configuration cfg)
    {
        this.cfg = cfg;
    }

    public int getConfigId(final Blocks tag)
    {
        checkState(settings.size() != 0);
        return settings.get(tag);
    }

    public BlockIdConfig populate()
    {
        checkState(settings.size() == 0);

        for (final Blocks tag : Blocks.values())
        {
            final String key = tag.toString().replace('_', '.').toLowerCase() + ".id";
            int id;
            if (TERRAIN_BLOCKS.contains(tag))
            {
                final int defaultId = defaultTerrainBlockId--;
                id =
                        cfg.getTerrainBlock(Configuration.CATEGORY_BLOCK, key, defaultId, null)
                                .getInt(defaultId);
            }
            else
            {
                final int defaultId = defaultBlockId++;
                id = cfg.getBlock(key, defaultId).getInt(defaultId);
            }
            settings.put(tag, id);
        }

        return this;
    }
}
