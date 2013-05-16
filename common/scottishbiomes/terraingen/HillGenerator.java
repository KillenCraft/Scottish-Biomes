
package scottishbiomes.terraingen;

import static scottishbiomes.lib.Biomes.AUTUMNHILLS;
import static scottishbiomes.lib.Biomes.AUTUMNWOODS;
import static scottishbiomes.lib.Biomes.BIRCHFOREST;
import static scottishbiomes.lib.Biomes.BIRCHHILLS;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class HillGenerator extends GenLayer
{
    public HillGenerator(final long seedAdjustment, final GenLayer genLayer)
    {
        super(seedAdjustment);
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

                if (nextInt(3) == 0)
                {
                    int newBiomeId = currentBiomeId;

                    if (currentBiomeId == BiomeGenBase.desert.biomeID)
                    {
                        newBiomeId = BiomeGenBase.desertHills.biomeID;
                    }
                    else if (currentBiomeId == BiomeGenBase.forest.biomeID)
                    {
                        newBiomeId = BiomeGenBase.forestHills.biomeID;
                    }
                    else if (currentBiomeId == BiomeGenBase.taiga.biomeID)
                    {
                        newBiomeId = BiomeGenBase.taigaHills.biomeID;
                    }
                    else if (currentBiomeId == BiomeGenBase.plains.biomeID)
                    {
                        newBiomeId = BiomeGenBase.forest.biomeID;
                    }
                    else if (currentBiomeId == BiomeGenBase.icePlains.biomeID)
                    {
                        newBiomeId = BiomeGenBase.iceMountains.biomeID;
                    }
                    else if (currentBiomeId == BiomeGenBase.jungle.biomeID)
                    {
                        newBiomeId = BiomeGenBase.jungleHills.biomeID;
                    }
                    else if (currentBiomeId == AUTUMNWOODS.id())
                    {
                        newBiomeId = AUTUMNHILLS.id();
                    }
                    else if (currentBiomeId == BIRCHFOREST.id())
                    {
                        newBiomeId = BIRCHHILLS.id();
                    }

                    if (newBiomeId == currentBiomeId)
                    {
                        outputBiomeIDs[xIter + yIter * width] = currentBiomeId;
                    }
                    else
                    {
                        final int neighbor1 =
                                inputBiomeIDs[xIter + 1 + (yIter + 1 - 1) * (width + 2)];
                        final int neighbor2 =
                                inputBiomeIDs[xIter + 1 + 1 + (yIter + 1) * (width + 2)];
                        final int neighbor3 =
                                inputBiomeIDs[xIter + 1 - 1 + (yIter + 1) * (width + 2)];
                        final int neighbor4 =
                                inputBiomeIDs[xIter + 1 + (yIter + 1 + 1) * (width + 2)];

                        if (neighbor1 == currentBiomeId && neighbor2 == currentBiomeId
                                && neighbor3 == currentBiomeId && neighbor4 == currentBiomeId)
                        {
                            outputBiomeIDs[xIter + yIter * width] = newBiomeId;
                        }
                        else
                        {
                            outputBiomeIDs[xIter + yIter * width] = currentBiomeId;
                        }
                    }
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
