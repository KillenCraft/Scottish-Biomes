
package scottishbiomes.tree;

import static scottishbiomes.lib.Blocks.SAPLING1;
import static scottishbiomes.lib.Items.REDWOOD_LEAVES;
import static scottishbiomes.lib.Items.REDWOOD_LOG;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.collect.ImmutableSet;

public class Sequoia extends Tree
{
    private static final int MIN_HEIGHT = 16;
    private static final int CANOPY_HEIGHT = 4;

    private final Random rng = new Random();
    private World world;

    public Sequoia()
    {
        this(false);
    }

    public Sequoia(final boolean doNotify)
    {
        super(doNotify, REDWOOD_LOG, REDWOOD_LEAVES, SAPLING1);
    }

    private void buildBranches(int x, int y, int z, final ImmutableSet<ForgeDirection> directions)
    {
        for (int branch = 0; branch < 9; branch++)
        {
            for (final ForgeDirection direction : directions)
            {
                if (rng.nextInt(3) == 0)
                {
                    x += direction.offsetX;
                }

                if (rng.nextInt(3) == 0)
                {
                    z += direction.offsetZ;
                }
            }

            placeWood(world, x, y, z);

            if (branch == 8 || rng.nextInt(6) == 0)
            {
                buildLeaves(x, y, z);
            }

            y++;
        }
    }

    private void buildLeaves(final int x, final int y, final int z)
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
                    placeLeaves(world, x + xAdj, y - 1, z + zAdj);
                }
            }
        }
    }

    protected boolean checkSoil(final World world, final int x, final int y, final int z,
            final int width, final int height)
    {
        for (int xAdj = -width; xAdj <= width; xAdj++)
        {
            for (int zAdj = -width; zAdj <= width; zAdj++)
            {
                Block soil = null;
                final int worldHorizon = (int) Math.round(world.getHorizon());
                int soilY = y;
                int heightFromSoil = height;
                while (soil == null && soilY >= worldHorizon)
                {
                    soil = Block.blocksList[world.getBlockId(x + xAdj, soilY--, z + zAdj)];
                    heightFromSoil++;
                }

                if (!canGrow(world, x, y, z, heightFromSoil)) return false;
                if (!isSoil(world, x + xAdj, soilY + 1, z + zAdj, soil)) return false;
            }
        }
        return true;
    }

    @Override
    public boolean generate(final World world, final Random rand, final int x, final int y,
            final int z)
    {
        this.world = world;
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(23) + MIN_HEIGHT;
        final int heightToCanopy = height - CANOPY_HEIGHT;
        int width = 1;
        boolean smaller = false;

        if (rng.nextInt(4) == 0)
        {
            if (rng.nextInt(3) == 0)
            {
                width = 2;
            }
        }
        else
        {
            smaller = true;
        }

        final int startY = y + 1;

        if (!smaller)
        {
            if (!checkSoil(world, x, startY - 1, z, width, height)) return false;

            boolean isBorder = false;

            for (int xAdj = -width; xAdj <= width; xAdj++)
            {
                for (int zAdj = -width; zAdj <= width; zAdj++)
                {
                    if (xAdj == width)
                    {
                        setAir(x + xAdj, startY, z + zAdj);
                    }

                    Block soil;
                    final int worldHorizon = (int) Math.round(world.getHorizon());
                    int soilY = y;
                    do
                    {
                        soil = Block.blocksList[world.getBlockId(x + xAdj, soilY--, z + zAdj)];
                    }
                    while (soil == null || soilY >= worldHorizon);
                    soil.onPlantGrow(world, x + xAdj, soilY++, z + zAdj, x, y, z);

                    for (int yWood = soilY + 1; yWood <= startY; yWood++)
                    {
                        placeWood(world, x + xAdj, yWood, z + zAdj);
                    }

                    for (int yAdj = 0; yAdj <= height; yAdj++)
                    {
                        placeWood(world, x + xAdj, startY + yAdj, z + zAdj);
                    }

                    if (width == Math.abs(xAdj) && width == Math.abs(zAdj) && width != 1)
                    {
                        for (int yAdj = height; yAdj >= 0; yAdj--)
                        {
                            setBlock(world, x + xAdj, startY + yAdj, z + zAdj, 0);

                            if (yAdj >= height / 2)
                            {
                                placeLeaves(world, x + xAdj, startY + yAdj, z + zAdj);
                            }
                        }
                    }
                }
            }

            final int originalWidth = width;
            width = width + 1;

            for (int xAdj = -width; xAdj <= width; xAdj++)
            {
                for (int zAdj = -width; zAdj <= width; zAdj++)
                {
                    if (width != Math.abs(xAdj) && width != Math.abs(zAdj))
                    {
                        continue;
                    }

                    for (int yAdj = height / 2; yAdj <= height; yAdj++)
                    {
                        placeLeaves(world, x + xAdj, startY + yAdj, z + zAdj);
                    }
                }
            }

            width = width + 1;

            for (int xAdj = -width; xAdj <= width; xAdj++)
            {
                for (int zAdj = -width; zAdj <= width; zAdj++)
                {
                    final int absZAdj = Math.abs(zAdj);
                    final int absXAdj = Math.abs(xAdj);
                    isBorder =
                            width == absZAdj || width == absXAdj || absZAdj == width - 1
                                    && absXAdj == width - 1;    // shaping
                                                             // the top
                                                             // of the
                                                             // tree

                    if (!isBorder && rng.nextInt(4) > 0)
                    {
                        placeWood(world, x + xAdj, startY + height + 2, z + zAdj);
                    }
                    else
                    {
                        isBorder = false;
                    }

                    if (width != absXAdj && width != absZAdj)
                    {
                        continue;
                    }

                    for (int yAdj = height / 2; yAdj <= height; yAdj++)
                    {
                        if (rng.nextInt(5) > 0 && yAdj > height / 2)
                        {
                            placeLeaves(world, x + xAdj, startY + yAdj, z + zAdj);
                        }
                    }
                }
            }

            width = width + 1;

            for (int xAdj = -width; xAdj <= width; xAdj++)
            {
                for (int zAdj = -width; zAdj <= width; zAdj++)
                {
                    final int absXAdj = Math.abs(xAdj);
                    final int absZAdj = Math.abs(zAdj);
                    isBorder =
                            width == absXAdj || width == absZAdj || absXAdj == width - 1
                                    && absZAdj == width - 1;    // shaping
                                                             // the top
                                                             // of the
                                                             // tree

                    if (!isBorder && rng.nextInt(4) > 0)
                    {
                        placeLeaves(world, x + xAdj, startY + height + 1, z + zAdj);
                    }
                    else
                    {
                        isBorder = false;
                    }

                    if (width != absXAdj && width != absZAdj || width == absXAdj
                            && width == absZAdj)
                    {
                        continue;
                    }

                    for (int yAdj = height / 2; yAdj <= height; yAdj++)
                    {
                        if (rng.nextInt(2) > 0 && (absXAdj != width || absZAdj != width - 1)
                                && (absXAdj != width - 1 || absZAdj != width)
                                && yAdj > height / 2 + 1)
                        {
                            placeLeaves(world, x + xAdj, startY + yAdj, z + zAdj);
                        }
                    }
                }
            }

            for (int yAdj = height / 2; yAdj <= height - 3; yAdj++)
            {
                for (final ImmutableSet<ForgeDirection> directions : BRANCH_DIRECTIONS)
                    if (rng.nextInt(2) == 0)
                    {
                        buildBranches(x - originalWidth, startY + yAdj, z, directions);
                    }
            }

            return true;
        }
        else
        {
            final int widthShrinkage = 1;

            if (!checkSoil(world, x, startY - 1, z, width - widthShrinkage, height)) return false;

            for (int xAdj = -width; xAdj <= width - widthShrinkage; xAdj++)
            {
                for (int zAdj = -width; zAdj <= width - widthShrinkage; zAdj++)
                {
                    if (xAdj == width)
                    {
                        setAir(x + xAdj, startY, z + zAdj);
                    }

                    Block soil;
                    final int worldHorizon = (int) Math.round(world.getHorizon());
                    int soilY = y;
                    do
                    {
                        soil = Block.blocksList[world.getBlockId(x + xAdj, soilY--, z + zAdj)];
                    }
                    while (soil == null || soilY >= worldHorizon);
                    soil.onPlantGrow(world, x + xAdj, soilY++, z + zAdj, x, y, z);

                    for (int yWood = soilY + 1; yWood <= startY; yWood++)
                    {
                        placeWood(world, x + xAdj, yWood, z + zAdj);
                    }

                    for (int yAdj = 1; yAdj <= heightToCanopy; yAdj++)
                    {
                        placeWood(world, x + xAdj, startY + yAdj, z + zAdj);
                    }
                }
            }

            boolean isBorder = false;
            width = width + 2;

            for (int xAdj = -width; xAdj <= width - widthShrinkage; xAdj++)
            {
                for (int zAdj = -width; zAdj <= width - widthShrinkage; zAdj++)
                {
                    // shaping the top of the tree
                    isBorder = zAdj == -3 || zAdj == 2 || xAdj == -3 || xAdj == 2;

                    if (!isBorder && rng.nextInt(4) > 0)
                    {
                        placeLeaves(world, x + xAdj, startY + heightToCanopy + 2, z + zAdj);
                    }
                    else
                    {
                        isBorder = false;
                    }

                    if ((xAdj == -1 || xAdj == 0) && (zAdj == -1 || zAdj == 0))
                    {
                        continue;
                    }

                    for (int yAdj = heightToCanopy / 2; yAdj <= heightToCanopy; yAdj++)
                    {
                        if (rng.nextInt(5) > 0 && yAdj > heightToCanopy / 2)
                        {
                            placeLeaves(world, x + xAdj, startY + yAdj, z + zAdj);
                        }
                    }
                }
            }

            isBorder = false;
            width = width + 1;

            for (int xAdj = -width; xAdj <= width - widthShrinkage; xAdj++)
            {
                for (int zAdj = -width; zAdj <= width - widthShrinkage; zAdj++)
                {
                    // shaping the top of the tree
                    isBorder = xAdj == -4 || xAdj == 3 || zAdj == -4 || zAdj == 3;

                    if (!isBorder && rng.nextInt(4) > 0)
                    {
                        placeLeaves(world, x + xAdj, startY + heightToCanopy + 1, z + zAdj);
                    }
                    else
                    {
                        isBorder = false;
                    }

                    boolean isCenter = xAdj != -4 && xAdj != 3 && zAdj != -4 && zAdj != 3;

                    if ((xAdj == -4 || xAdj == 3) && (zAdj == -4 || zAdj == 3))
                    {
                        continue;
                    }

                    if (!isCenter)
                    {
                        for (int yAdj = heightToCanopy / 2; yAdj <= heightToCanopy; yAdj++)
                        {
                            if (rng.nextInt(3) > 0 && yAdj > heightToCanopy / 2 + 1)
                            {
                                placeLeaves(world, x + xAdj, startY + yAdj, z + zAdj);
                            }
                        }
                    }
                    else
                    {
                        isCenter = false;
                    }
                }
            }

            for (int yAdj = height / 2; yAdj <= height - 6; yAdj++)
            {

                for (final ImmutableSet<ForgeDirection> directions : BRANCH_DIRECTIONS)
                    if (rng.nextInt(4) == 0)
                    {
                        buildBranches(x - 1, startY + yAdj, z, directions);
                    }
            }

            return true;
        }
    }

    private void setAir(final int x, final int y, final int z)
    {
        setBlock(world, x, y, z, 0);
    }
}
