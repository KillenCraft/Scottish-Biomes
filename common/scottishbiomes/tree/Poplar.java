
package scottishbiomes.tree;

import static scottishbiomes.lib.Blocks.SAPLING1;
import static scottishbiomes.lib.Items.POPLAR_LEAVES;
import static scottishbiomes.lib.Items.POPLAR_LOG;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class Poplar extends Tree
{
    private final Random rng = new Random();

    public Poplar()
    {
        this(false);
    }

    public Poplar(final boolean doNotify)
    {
        super(doNotify, POPLAR_LOG, POPLAR_LEAVES, SAPLING1);
    }

    @Override
    public boolean generate(final World world, final Random rand, final int x, final int y,
            final int z)
    {
        rng.setSeed(rand.nextLong());

        int size = 1;

        for (int index = 0; index < 3; index++)
            if (rng.nextInt(7) < 2)
            {
                size++;
            }

        final int fullHeight = 6 * size;

        if (!canGrow(world, x, y, z, fullHeight + 1)) return false;

        final int soilID = world.getBlockId(x, y - 1, z);
        final Block soil = Block.blocksList[soilID];
        if (!isSoil(world, x, y, z, soil)) return false;

        soil.onPlantGrow(world, x, y - 1, z, x, y, z);

        for (int level = 0; level <= fullHeight + 1; level++)
        {
            if (level != fullHeight + 1)
            {
                placeWood(world, x, y + level, z);
            }                                                                                                                                                   //

            if (size == 1 && level > 2)
            {
                for (int xAdj = -1; xAdj <= 1; xAdj++)
                {
                    final int absXAdj = Math.abs(xAdj);
                    for (int zAdj = -1; zAdj <= 1; zAdj++)
                    {
                        if (absXAdj != 1 || Math.abs(zAdj) != 1)
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }                                                                                                                               //
                    }                                                                                                                                   //
                }                                                                                                                                       // ////////
            }                                                                                                                                           // ////////

            if (size == 2 && level > 2)
            {
                for (int xAdj = -2; xAdj <= 2; xAdj++)
                {
                    final int absXAdj = Math.abs(xAdj);
                    for (int zAdj = -2; zAdj <= 2; zAdj++)
                    {
                        final int absZAdj = Math.abs(zAdj);
                        if (absXAdj <= 1 && absZAdj <= 1 && (absXAdj != 1 || absZAdj != 1))
                        {

                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }

                        if (absXAdj <= 1 && absZAdj <= 1 && level == 7)
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }

                        if ((absXAdj != 2 || absZAdj != 2) && (absXAdj != 2 || absZAdj != 1)
                                && (absXAdj != 1 || absZAdj != 2) && level <= fullHeight - 1
                                && level > 3)
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }
                    }
                }
            }

            if (size == 3 & level > 2)
            {
                for (int xAdj = -2; xAdj <= 2; xAdj++)
                {
                    final int absXAdj = Math.abs(xAdj);
                    for (int zAdj = -2; zAdj <= 2; zAdj++)
                    {
                        final int absZAdj = Math.abs(zAdj);
                        if (absXAdj <= 1 && absZAdj <= 1 && (absXAdj != 1 || absZAdj != 1))
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }

                        if ((absXAdj != 2 || absZAdj != 2) && (absXAdj != 2 || absZAdj != 1)
                                && (absXAdj != 1 || absZAdj != 2) && level <= fullHeight
                                && level > 3)
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }
                    }
                }
            }

            if (size == 4 & level > 2)
            {
                for (int xAdj = -3; xAdj <= 3; xAdj++)
                {
                    final int absXAdj = Math.abs(xAdj);
                    for (int zAdj = -3; zAdj <= 3; zAdj++)
                    {
                        final int absZAdj = Math.abs(zAdj);
                        if (absXAdj <= 1 && absZAdj <= 1 && (absXAdj != 1 || absZAdj != 1))
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }

                        if (absXAdj <= 1 && absZAdj <= 1 && level <= 14 && level >= 2)
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }

                        if (absXAdj <= 2 && absZAdj <= 2 && (absXAdj != 2 || absZAdj != 2)
                                && level == fullHeight | level == 5)
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }

                        if ((absXAdj != 3 || absZAdj != 3) && (absXAdj != 3 || absZAdj != 2)
                                && (absXAdj != 2 || absZAdj != 3) && level <= fullHeight - 1
                                && level > 5)
                        {
                            placeLeaves(world, x + xAdj, y + level, z + zAdj);
                        }
                    }
                }
            }
        }

        return true;
    }
}
