
package scottishbiomes.tree;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class AutumnOakAnyColor extends WorldGenerator
{
    private Optional<? extends List<? extends WorldGenerator>> treeGens = Optional.absent();
    private static Optional<? extends WorldGenerator> bigOak = Optional.absent();
    private static Optional<? extends WorldGenerator> oak = Optional.absent();
    private boolean fromSapling = false;

    public AutumnOakAnyColor()
    {
        this(false);
    }

    public AutumnOakAnyColor(final boolean fromSapling)
    {
        super(fromSapling);
        this.fromSapling = fromSapling;
    }

    @Override
    public boolean generate(final World world, final Random rng, final int x, final int y,
            final int z)
    {
        return generate(world, rng, x, y, z, rng.nextInt(5));
    }

    public boolean generate(final World world, final Random rng, final int x, final int y,
            final int z, final int type)
    {
        if (!treeGens.isPresent())
        {
            populateTreeGens();
        }

        WorldGenerator treeGen;

        if (type < 4)
        {
            treeGen = treeGens.get().get(type);
        }
        else
        {
            treeGen = rng.nextInt(10) == 0 ? bigOak.get() : oak.get();
        }
        return treeGen.generate(world, rng, x, y, z);
    }

    private void populateTreeGens()
    {
        final List<AutumnOakRandom> treeGens = Lists.newArrayListWithCapacity(4);
        for (int metadata = 0; metadata < 4; metadata++)
        {
            treeGens.add(new AutumnOakRandom(fromSapling, metadata));
        }
        this.treeGens = Optional.of(ImmutableList.copyOf(treeGens));

        if (!bigOak.isPresent())
        {
            bigOak = Optional.of(new WorldGenBigTree(false));
        }
        if (!oak.isPresent())
        {
            oak = Optional.of(new WorldGenTrees(false));
        }
    }

}
