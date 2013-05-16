
package scottishbiomes.terraingen;

import static scottishbiomes.lib.Biomes.BIRCHFOREST;
import static scottishbiomes.lib.Biomes.BIRCHHILLS;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import scottishbiomes.lib.Biomes;

public class GroveGenerator extends GenLayer
{
    public GroveGenerator(final long size, final GenLayer genLayer)
    {
        super(size);
        parent = genLayer;
    }

    @Override
    public int[] getInts(final int x, final int y, final int width, final int length)
    {
        final int[] inputBiomeIDs = parent.getInts(x - 1, y - 1, width + 2, length + 2);
        final int[] outputBiomeIDs = IntCache.getIntCache(width * length);

        for (int yIter = 0; yIter < length; ++yIter)
        {
            for (int xIter = 0; xIter < width; ++xIter)
            {
                initChunkSeed(xIter + x, yIter + y);
                final int currentBiomeId = inputBiomeIDs[xIter + 1 + (yIter + 1) * (width + 2)];

                if (currentBiomeId == BiomeGenBase.forest.biomeID
                        || currentBiomeId == BiomeGenBase.forestHills.biomeID)
                {
                    int newBiomeId = currentBiomeId;

                    final int rand = nextInt(6);
                    if (rand == 0)
                    {
                        newBiomeId =
                                currentBiomeId == BiomeGenBase.forest.biomeID ? Biomes.AUTUMNWOODS
                                        .id() : Biomes.AUTUMNHILLS.id();
                    }
                    else if (rand == 1)
                    {
                        newBiomeId =
                                currentBiomeId == BiomeGenBase.forest.biomeID ? BIRCHFOREST.id()
                                        : BIRCHHILLS.id();
                    }

                    outputBiomeIDs[xIter + yIter * width] = newBiomeId;
                }
                else
                {
                    outputBiomeIDs[xIter + yIter * width] = currentBiomeId;
                }
            }
        }

        return outputBiomeIDs;
    }

}
