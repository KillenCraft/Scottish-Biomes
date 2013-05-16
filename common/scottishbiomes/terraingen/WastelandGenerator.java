
package scottishbiomes.terraingen;

import static scottishbiomes.lib.Biomes.AUTUMNHILLS;
import static scottishbiomes.lib.Biomes.BIRCHHILLS;
import static scottishbiomes.lib.Biomes.MOUNTAINRIDGE;
import static scottishbiomes.lib.Biomes.WASTELAND;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WastelandGenerator extends GenLayer
{

    public WastelandGenerator(final long size, final GenLayer genLayer)
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

                if (nextInt(25) == 0)
                {
                    int newBiomeId = currentBiomeId;

                    if (currentBiomeId != BiomeGenBase.desertHills.biomeID
                            && currentBiomeId != BiomeGenBase.extremeHills.biomeID
                            && currentBiomeId != BiomeGenBase.forestHills.biomeID
                            && currentBiomeId != BiomeGenBase.jungleHills.biomeID
                            && currentBiomeId != BiomeGenBase.mushroomIsland.biomeID
                            && currentBiomeId != BiomeGenBase.taigaHills.biomeID
                            && currentBiomeId != AUTUMNHILLS.id()
                            && currentBiomeId != BIRCHHILLS.id()
                            && currentBiomeId != MOUNTAINRIDGE.id())
                    {
                        newBiomeId = WASTELAND.id();
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
