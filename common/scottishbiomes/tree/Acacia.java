
package scottishbiomes.tree;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;
import static scottishbiomes.lib.Blocks.SAPLING1;
import static scottishbiomes.lib.Items.ACACIA_LEAVES;
import static scottishbiomes.lib.Items.ACACIA_LOG;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.collect.ImmutableSet;

public class Acacia extends Tree
{
    private static final int MIN_HEIGHT = 5;

    public Acacia(final boolean fromSapling)
    {
        super(fromSapling, ACACIA_LOG, ACACIA_LEAVES, SAPLING1);
    }

    private void buildBranch(final World world, final Random random, final int height, int x,
            final int y, final int level, int z, final ImmutableSet<ForgeDirection> directions)
    {
        int index = 0;
        final int yTop = y + height;
        int yStart = level + y;

        while (yStart < yTop)
        {
            if (directions.contains(WEST) && random.nextInt(3) > 0)
            {
                x--;

                if (directions.size() == 1 && random.nextInt(4) == 0)
                {
                    z = z + random.nextInt(3) - 1;
                }
            }

            if (directions.contains(EAST) && random.nextInt(3) > 0)
            {
                x++;

                if (directions.size() == 1 && random.nextInt(4) == 0)
                {
                    z = z + random.nextInt(3) - 1;
                }
            }

            if (directions.contains(NORTH) && random.nextInt(3) > 0)
            {
                z--;

                if (directions.size() == 1 && random.nextInt(4) == 0)
                {
                    x = x + random.nextInt(3) - 1;
                }
            }

            if (directions.contains(SOUTH) && random.nextInt(3) > 0)
            {
                z++;

                if (directions.size() == 1 && random.nextInt(4) == 0)
                {
                    x = x + random.nextInt(3) - 1;
                }
            }

            placeWood(world, x, yStart, z);

            if (random.nextInt(3) > 0)
            {
                yStart++;
            }

            if (yStart == yTop)
            {
                placeWood(world, x, yStart, z);
                buildLeaves(world, x, yStart, z);
            }

            index++;

            if (index == 6)
            {
                final EnumSet<ForgeDirection> opposites = EnumSet.noneOf(ForgeDirection.class);
                for (final ForgeDirection direction : directions)
                {
                    opposites.add(direction.getOpposite());
                }

                buildBranch(world, random, height, x, y, yStart - y, z,
                        ImmutableSet.copyOf(opposites));
            }
        }
    }

    private void buildLeaves(final World world, final int x, final int y, final int z)
    {
        for (int xAdj = -3; xAdj <= 3; xAdj++)
        {
            for (int zAdj = -3; zAdj <= 3; zAdj++)
            {
                final int absXAdj = Math.abs(xAdj);
                final int absZAdj = Math.abs(zAdj);

                if ((absXAdj != 3 || absZAdj != 3) && (absXAdj != 2 || absZAdj != 3)
                        && (absXAdj != 3 || absZAdj != 2))
                {
                    placeLeaves(world, x + xAdj, y, z + zAdj);
                }

                if (absXAdj < 3 && absZAdj < 3 && (absXAdj != 2 || absZAdj != 2))
                {
                    placeLeaves(world, x + xAdj, y + 1, z + zAdj);
                }
            }
        }
    }

    @Override
    public boolean generate(final World world, final Random rng, final int x, final int y,
            final int z)
    {
        final int height = getHeight(rng);

        if (!canGrow(world, x, y, z, height + 1)) return false;

        final int soilID = world.getBlockId(x, y - 1, z);
        final Block soil = Block.blocksList[soilID];
        if (!isSoil(world, x, y, z, soil)) return false;

        soil.onPlantGrow(world, x, y - 1, z, x, y, z);

        for (int level = 0; level <= height; level++)
        {
            placeWood(world, x, y + level, z);

            if (level == height)
            {
                buildLeaves(world, x, y + level, z);
            }

            if (level > 1 & level < height)
            {
                for (final ImmutableSet<ForgeDirection> directions : BRANCH_DIRECTIONS)
                    if (rng.nextInt(6) == 0)
                    {
                        buildBranch(world, rng, height, x, y, level, z, directions);
                    }
            }
        }

        return true;
    }

    protected int getHeight(final Random rng)
    {
        return rng.nextInt(3) + MIN_HEIGHT;
    }
}
