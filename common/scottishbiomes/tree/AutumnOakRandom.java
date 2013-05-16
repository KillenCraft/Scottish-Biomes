
package scottishbiomes.tree;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.google.common.base.Optional;

public class AutumnOakRandom extends WorldGenerator
{
    private static final int BIG_VARIANT_RARITY = 10;
    private final boolean fromSapling;
    private final int metaLeaves;
    private Optional<? extends WorldGenerator> normalTree = Optional.absent();
    private Optional<? extends WorldGenerator> largeTree = Optional.absent();

    public AutumnOakRandom(final boolean fromSapling, final int metaLeaves)
    {
        super(fromSapling);
        this.fromSapling = fromSapling;
        this.metaLeaves = metaLeaves;
    }

    @Override
    public boolean generate(final World world, final Random rng, final int x, final int y,
            final int z)
    {
        if (rng.nextInt(BIG_VARIANT_RARITY) == 0) return growLargeTree(world, rng, x, y, z);
        return growNormalTree(world, rng, x, y, z);
    }

    private boolean growLargeTree(final World world, final Random rng, final int x, final int y,
            final int z)
    {
        if (!largeTree.isPresent())
        {
            populateLargeTree();
        }
        return largeTree.get().generate(world, rng, x, y, z);
    }

    private boolean growNormalTree(final World world, final Random rng, final int x, final int y,
            final int z)
    {
        if (!normalTree.isPresent())
        {
            populateNormalTree();
        }
        return normalTree.get().generate(world, rng, x, y, z);
    }

    private void populateLargeTree()
    {
        largeTree = Optional.of(new AutumnOakLarge(fromSapling, metaLeaves));
    }

    private void populateNormalTree()
    {
        normalTree = Optional.of(new AutumnOak(fromSapling, metaLeaves));
    }
}
