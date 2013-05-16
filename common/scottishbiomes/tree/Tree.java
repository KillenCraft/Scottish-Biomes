
package scottishbiomes.tree;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import scottishbiomes.lib.Blocks;
import scottishbiomes.lib.Items;
import scottishbiomes.utility.terraingen.SafeWorldGenerator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

abstract class Tree extends SafeWorldGenerator
{
    protected static final ImmutableList<ImmutableSet<ForgeDirection>> BRANCH_DIRECTIONS =
            initializeBranchDirections();

    private static ImmutableList<ImmutableSet<ForgeDirection>> initializeBranchDirections()
    {
        final List<ImmutableSet<ForgeDirection>> list = Lists.newArrayList();

        list.add(ImmutableSet.copyOf(EnumSet.of(WEST)));
        list.add(ImmutableSet.copyOf(EnumSet.of(EAST)));
        list.add(ImmutableSet.copyOf(EnumSet.of(NORTH)));
        list.add(ImmutableSet.copyOf(EnumSet.of(SOUTH)));
        list.add(ImmutableSet.copyOf(EnumSet.of(WEST, NORTH)));
        list.add(ImmutableSet.copyOf(EnumSet.of(WEST, SOUTH)));
        list.add(ImmutableSet.copyOf(EnumSet.of(EAST, NORTH)));
        list.add(ImmutableSet.copyOf(EnumSet.of(EAST, SOUTH)));

        return ImmutableList.copyOf(list);
    }

    private final Items trunk;
    private final Items leaves;
    private final Blocks sapling;

    private int trunkId = 0;
    private int trunkMetadata = 0;
    private int leavesId = 0;
    private int leavesMetadata = 0;

    protected Tree(final boolean doNotify, final Items trunk, final Items leaves,
            final Blocks sapling)
    {
        super(doNotify);

        this.trunk = trunk;
        this.leaves = leaves;
        this.sapling = sapling;
    }

    protected boolean canGrow(final World world, final int x, final int y, final int z,
            final int height)
    {
        final int worldHeight = world.getHeight();
        if (y < 1 || y + height + 1 > worldHeight) return false;

        for (int layer = y; layer <= y + 1 + height; layer++)
        {
            final byte clearance = getClearanceForLayer(height, layer - y);

            for (int x1 = x - clearance; x1 <= x + clearance; x1++)
            {
                for (int z1 = z - clearance; z1 <= z + clearance; z1++)
                    if (layer >= 0 && layer < worldHeight)
                    {
                        final int id = world.getBlockId(x1, layer, z1);
                        final Block block = Block.blocksList[id];
                        if (block != null && !block.isLeaves(world, x1, layer, z1)
                                && !block.isBlockReplaceable(world, x1, layer, z1)
                                && id != Block.grass.blockID && id != Block.dirt.blockID
                                && id != Block.tallGrass.blockID
                                && !block.isWood(world, x1, layer, z1)) return false;

                    }
                    else
                        return false;
            }
        }
        return true;
    }

    protected byte getClearanceForLayer(final int height, final int layer)
    {
        if (layer == 0) return 0;

        if (layer >= 1 + height - 2) return 2;
        return 1;
    }

    protected boolean isSoil(final World world, final int x, final int y, final int z,
            final Block soil)
    {
        return soil != null
                && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP,
                        (IPlantable) sapling.value());
    }

    protected boolean placeLeaves(final World world, final int x, final int y, final int z)
    {
        if (chunkExists(world, x, z))
        {
            final Block block = Block.blocksList[world.getBlockId(x, y, z)];
            if (block == null || block.canBeReplacedByLeaves(world, x, y, z))
            {
                if (leavesId == 0)
                {
                    final ItemStack leavesItem = leaves.value();
                    leavesId = leavesItem.itemID;
                    leavesMetadata = leavesItem.getItemDamage();
                }
                setBlockAndMetadata(world, x, y, z, leavesId, leavesMetadata);
                return true;
            }
        }
        return false;
    }

    protected boolean placeWood(final World world, final int x, final int y, final int z)
    {
        if (chunkExists(world, x, z))
        {
            final Block block = Block.blocksList[world.getBlockId(x, y, z)];
            if (block == null || block.isLeaves(world, x, y, z) || block.isBlockReplaceable(world, x, y, z))
            {
                if (leavesId == 0)
                {
                    final ItemStack trunkItem = trunk.value();
                    trunkId = trunkItem.itemID;
                    trunkMetadata = trunkItem.getItemDamage();
                }
                setBlockAndMetadata(world, x, y, z, trunkId, trunkMetadata);
                return true;
            }
        }
        return false;
    }

}
