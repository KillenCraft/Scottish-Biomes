
package scottishbiomes.tree;

import static scottishbiomes.lib.Blocks.SAPLING1;
import static scottishbiomes.lib.Items.BROWN_AUTUMN_LEAVES;
import static scottishbiomes.lib.Items.ORANGE_AUTUMN_LEAVES;
import static scottishbiomes.lib.Items.PURPLE_AUTUMN_LEAVES;
import static scottishbiomes.lib.Items.YELLOW_AUTUMN_LEAVES;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import scottishbiomes.lib.Items;

import com.google.common.collect.ImmutableList;

public class AutumnOak extends Tree
{
    private final int minTreeHeight;
    private static ImmutableList<Items> AUTUMN_LEAVES = ImmutableList.copyOf(Arrays.asList(
            BROWN_AUTUMN_LEAVES, ORANGE_AUTUMN_LEAVES, PURPLE_AUTUMN_LEAVES, YELLOW_AUTUMN_LEAVES));

    public AutumnOak(final boolean fromSapling, final int leavesMetadata)
    {
        this(fromSapling, 4, leavesMetadata);
    }

    protected AutumnOak(final boolean fromSapling, final int minTreeHeight, final int leavesMetadata)
    {

        super(fromSapling, null, AUTUMN_LEAVES.get(leavesMetadata), SAPLING1);
        this.minTreeHeight = minTreeHeight;
    }

    private void buildCanopy(final World world, final Random rng, final int x, final int y,
            final int z, final int height)
    {
        final int canopyHeight = 3;

        for (int canopyY = y - canopyHeight + height; canopyY <= y + height; ++canopyY)
        {
            final int canopyLayer = canopyY - (y + height);
            final int canopyWidth = 1 - canopyLayer / 2;

            for (int canopyX = x - canopyWidth; canopyX <= x + canopyWidth; ++canopyX)
            {
                final int xDistanceFromCenter = canopyX - x;

                for (int canopyZ = z - canopyWidth; canopyZ <= z + canopyWidth; ++canopyZ)
                {
                    final int zDistanceFromCenter = canopyZ - z;

                    if (Math.abs(xDistanceFromCenter) != canopyWidth
                            || Math.abs(zDistanceFromCenter) != canopyWidth || rng.nextInt(2) != 0
                            && canopyLayer != 0)
                    {
                        placeLeaves(world, canopyX, canopyY, canopyZ);
                    }
                }
            }
        }
    }

    private void buildTrunk(final World world, final int x, final int y, final int z,
            final int height)
    {
        for (int trunkLayer = 0; trunkLayer < height; ++trunkLayer)
        {
            placeWood(world, x, y + trunkLayer, z);
        }
    }

    @Override
    public boolean generate(final World world, final Random rng, final int x, final int y,
            final int z)
    {
        final int height = rng.nextInt(3) + minTreeHeight;

        if (!canGrow(world, x, y, z, height)) return false;

        final int soilID = world.getBlockId(x, y - 1, z);
        final Block soil = Block.blocksList[soilID];
        if (!isSoil(world, x, y, z, soil)) return false;

        soil.onPlantGrow(world, x, y - 1, z, x, y, z);
        buildCanopy(world, rng, x, y, z, height);
        buildTrunk(world, x, y, z, height);
        return true;
    }

    @Override
    protected boolean placeWood(final World world, final int x, final int y, final int z)
    {
        return placeWood(world, x, y, z, 0);
    }

    protected boolean placeWood(final World world, final int x, final int y, final int z,
            final int metadata)
    {
        final Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block == null || block.isLeaves(world, x, y, z))
        {
            setBlockAndMetadata(world, x, y, z, Block.wood.blockID, metadata);
            return true;
        }
        return false;
    }
}
