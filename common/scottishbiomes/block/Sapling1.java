
package scottishbiomes.block;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import scottishbiomes.tree.AcaciaRandom;
import scottishbiomes.tree.AutumnOakAnyColor;
import scottishbiomes.tree.Sequoia;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public final class Sapling1 extends Sapling
{

    private static final ImmutableList<String> TYPES = ImmutableList.of("autumn.brown",
            "autumn.orange", "autumn.purple", "autumn.yellow", "acacia", "poplar", "redwood");
    private static Optional<AutumnOakAnyColor> autumnTreeGen = Optional.absent();
    private static Optional<AcaciaRandom> acaciaTreeGen = Optional.absent();
    private static Optional<Sequoia> redwoodTreeGen = Optional.absent();

    private static void populateTreeGen()
    {
        autumnTreeGen = Optional.of(new AutumnOakAnyColor(true));
        acaciaTreeGen = Optional.of(new AcaciaRandom(true));
        redwoodTreeGen = Optional.of(new Sequoia(true));
    }

    public Sapling1(final int id)
    {
        super(id, TYPES);
    }

    @Override
    public void
            growTree(final World world, final int x, final int y, final int z, final Random rng)
    {
        if (!TerrainGen.saplingGrowTree(world, rng, x, y, z)) return;

        if (!autumnTreeGen.isPresent())
        {
            populateTreeGen();
        }

        final int metadata = world.getBlockMetadata(x, y, z) & 7;

        world.setBlockToAir(x, y, z);

        boolean isTreeGenSuccessful;

        switch (metadata)
        {
            case 4:
                isTreeGenSuccessful = acaciaTreeGen.get().generate(world, rng, x, y, z);
                break;
            case 6:
                isTreeGenSuccessful = redwoodTreeGen.get().generate(world, rng, x, y, z);
                break;
            default:
                isTreeGenSuccessful = autumnTreeGen.get().generate(null, rng, x, y, z, metadata);
        }

        if (!isTreeGenSuccessful)
        {
            world.setBlock(x, y, z, blockID, metadata, 3);
        }
    }
}
