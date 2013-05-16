
package scottishbiomes.biome;

import java.util.Random;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;
import scottishbiomes.tree.AutumnOakAnyColor;

import com.google.common.base.Optional;

public class AutumnWoods extends BiomeGenBase
{
    private static Optional<? extends WorldGenerator> treeGen = Optional.absent();

    @SuppressWarnings("unchecked")
    public AutumnWoods(final int id)
    {
        super(id);

        spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));

        theBiomeDecorator.treesPerChunk = 9;
        theBiomeDecorator.grassPerChunk = 6;
        theBiomeDecorator.mushroomsPerChunk = 3;
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
        treeGen = Optional.of(new AutumnOakAnyColor());
    }
}
