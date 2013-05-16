
package scottishbiomes.biome;

import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import scottishbiomes.tree.AcaciaRandom;

import com.google.common.base.Optional;

public class Savanna extends BiomeGenBase
{
    private static Optional<? extends WorldGenerator> treeGen = Optional.absent();

    public Savanna(final int id)
    {
        super(id);

        theBiomeDecorator.treesPerChunk = 0;
        theBiomeDecorator.flowersPerChunk = 1;
        theBiomeDecorator.grassPerChunk = 17;
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(final Random par1Random)
    {
        if (!treeGen.isPresent())
        {
            populateTreeGen();
        }
        return treeGen.get();
    }

    private void populateTreeGen()
    {
        treeGen = Optional.of(new AcaciaRandom());
    }
}
