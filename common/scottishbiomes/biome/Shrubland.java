
package scottishbiomes.biome;

import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.google.common.base.Optional;

public class Shrubland extends BiomeGenBase
{
    private static Optional<? extends WorldGenerator> treeGen = Optional.absent();

    public Shrubland(final int id)
    {
        super(id);

        theBiomeDecorator.treesPerChunk = 0;
        theBiomeDecorator.flowersPerChunk = 3;
        theBiomeDecorator.grassPerChunk = 1;
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
        treeGen = Optional.of(new WorldGenShrub(0, 0));
    }
}
