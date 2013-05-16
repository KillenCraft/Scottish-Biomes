
package scottishbiomes.biome;

import java.util.Random;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BirchForest extends BiomeGenBase
{
    @SuppressWarnings("unchecked")
    public BirchForest(final int id)
    {
        super(id);

        spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));

        theBiomeDecorator.treesPerChunk = 7;
        theBiomeDecorator.grassPerChunk = 1;
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(final Random par1Random)
    {
        return par1Random.nextInt(100) == 0 ? worldGeneratorBigTree : worldGeneratorForest;
    }

}
