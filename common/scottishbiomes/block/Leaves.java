
package scottishbiomes.block;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import scottishbiomes.utility.block.MultiFacetedBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class Leaves extends MultiFacetedBlock implements IShearable
{
    private static final byte LEAF_DECAY_RANGE = 1;
    private static final byte TICK_NOTIFY_RANGE = 4;

    protected static int calcSmoothedBiomeFoliageColor(final IBlockAccess blockAccess, final int x,
            final int z)
    {
        int red = 0;
        int blue = 0;
        int green = 0;

        for (int zDelta = -1; zDelta <= 1; ++zDelta)
        {
            for (int xDelta = -1; xDelta <= 1; ++xDelta)
            {
                final int foliageColor =
                        blockAccess.getBiomeGenForCoords(x + xDelta, z + zDelta)
                                .getBiomeFoliageColor();
                red += (foliageColor & 16711680) >> 16;
                blue += (foliageColor & 65280) >> 8;
                green += foliageColor & 255;
            }
        }

        return (red / 9 & 255) << 16 | (blue / 9 & 255) << 8 | green / 9 & 255;
    }

    protected static int limitToValidMetadata(final int metaData)
    {
        return metaData & 3;
    }

    private int[] adjacentTreeBlocks;

    protected Leaves(final int id, final ImmutableList<String> types)
    {
        super(id, Material.leaves);
        setTickRandomly(true);
        setCreativeTab(CreativeTabs.tabDecorations);

        checkState(!types.isEmpty() && types.size() < 5);
        setSubBlockCount(types.size());

        final List<String> iconNames = Lists.newArrayListWithCapacity(types.size() * 2);
        for (final String type : types)
        {
            iconNames.add(String.format("leaves_%s", type));
            iconNames.add(String.format("leaves_%s_fast", type));
        }
        setIconNames(iconNames);

        MultiFacetedBlock.addTypeNames(id, types);
    }

    @Override
    public void beginLeavesDecay(final World world, final int x, final int y, final int z)
    {
        world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 4);
    }

    @Override
    public void breakBlock(final World world, final int x, final int y, final int z,
            final int blockId, final int metaData)
    {
        final int outerDecayRange = LEAF_DECAY_RANGE + 1;

        if (world.checkChunksExist(x - outerDecayRange, y - outerDecayRange, z - outerDecayRange, x
                + outerDecayRange, y + outerDecayRange, z + outerDecayRange))
        {
            for (int xDelta = -LEAF_DECAY_RANGE; xDelta <= LEAF_DECAY_RANGE; ++xDelta)
            {
                for (int yDelta = -LEAF_DECAY_RANGE; yDelta <= LEAF_DECAY_RANGE; ++yDelta)
                {
                    for (int zDelta = -LEAF_DECAY_RANGE; zDelta <= LEAF_DECAY_RANGE; ++zDelta)
                    {
                        final int id = world.getBlockId(x + xDelta, y + yDelta, z + zDelta);

                        if (Block.blocksList[id] != null)
                        {
                            Block.blocksList[id].beginLeavesDecay(world, x + xDelta, y + yDelta, z
                                    + zDelta);
                        }
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public abstract int colorMultiplier(final IBlockAccess blockAccess, final int x, final int y,
            final int z);

    @Override
    protected ItemStack createStackedBlock(final int metaData)
    {
        return new ItemStack(blockID, 1, limitToValidMetadata(metaData));
    }

    @Override
    public int damageDropped(final int metaData)
    {
        return limitToValidMetadata(metaData);
    }

    /**
     * Drops the block items with a specified chance of dropping the
     * specified items
     */
    @Override
    public abstract void dropBlockAsItemWithChance(final World world, final int x, final int y,
            final int z, final int metaData, final float chance, final int fortune);

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        final double temperature = 0.5D;
        final double humidity = 1.0D;
        return ColorizerFoliage.getFoliageColor(temperature, humidity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int metaData)
    {
        return getIcon(limitToValidMetadata(metaData) * 2 + (leaves.graphicsLevel ? 0 : 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public abstract int getRenderColor(final int metaData);

    @Override
    public abstract int idDropped(final int metaData, final Random rng, final int fortune);

    @Override
    public boolean isLeaves(final World world, final int x, final int y, final int z)
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return !leaves.graphicsLevel;
    }

    @Override
    public boolean isShearable(final ItemStack item, final World world, final int x, final int y,
            final int z)
    {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(final ItemStack item, final World world, final int x,
            final int y, final int z, final int fortune)
    {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
        return ret;
    }

    @Override
    public int quantityDropped(final Random rng)
    {
        return rng.nextInt(20) == 0 ? 1 : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int x, final int y, final int z,
            final Random rng)
    {
        if (world.canLightningStrikeAt(x, y + 1, z)
                && !world.doesBlockHaveSolidTopSurface(x, y - 1, z) && rng.nextInt(15) == 1)
        {
            final double xParticle = x + rng.nextFloat();
            final double yParticle = y - 0.05D;
            final double zParticle = z + rng.nextFloat();
            world.spawnParticle("dripWater", xParticle, yParticle, zParticle, 0.0D, 0.0D, 0.0D);
        }
    }

    private void removeLeaves(final World world, final int x, final int y, final int z)
    {
        dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlockToAir(x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int x, final int y,
            final int z, final int side)
    {
        final int id = blockAccess.getBlockId(x, y, z);
        return !leaves.graphicsLevel && id == blockID ? false : super.shouldSideBeRendered(
                blockAccess, x, y, z, side);
    }

    @Override
    public void updateTick(final World world, final int x, final int y, final int z,
            final Random rng)
    {
        if (!world.isRemote)
        {
            final int metaData = world.getBlockMetadata(x, y, z);

            if ((metaData & 8) != 0 && (metaData & 4) == 0)
            {
                final int outerNotifyRange = TICK_NOTIFY_RANGE + 1;
                final byte b1 = 32;
                final int j1 = b1 * b1;
                final int k1 = b1 / 2;

                if (adjacentTreeBlocks == null)
                {
                    adjacentTreeBlocks = new int[b1 * b1 * b1];
                }

                int xDelta;

                if (world.checkChunksExist(x - outerNotifyRange, y - outerNotifyRange, z
                        - outerNotifyRange, x + outerNotifyRange, y + outerNotifyRange, z
                        + outerNotifyRange))
                {
                    int yDelta;
                    int zDelta;
                    int id;

                    for (xDelta = -TICK_NOTIFY_RANGE; xDelta <= TICK_NOTIFY_RANGE; ++xDelta)
                    {
                        for (yDelta = -TICK_NOTIFY_RANGE; yDelta <= TICK_NOTIFY_RANGE; ++yDelta)
                        {
                            for (zDelta = -TICK_NOTIFY_RANGE; zDelta <= TICK_NOTIFY_RANGE; ++zDelta)
                            {
                                id = world.getBlockId(x + xDelta, y + yDelta, z + zDelta);

                                final Block block = Block.blocksList[id];

                                if (block != null
                                        && block.canSustainLeaves(world, x + xDelta, y + yDelta, z
                                                + zDelta))
                                {
                                    adjacentTreeBlocks[(xDelta + k1) * j1 + (yDelta + k1) * b1
                                            + zDelta + k1] = 0;
                                }
                                else if (block != null
                                        && block.isLeaves(world, x + xDelta, y + yDelta, z + zDelta))
                                {
                                    adjacentTreeBlocks[(xDelta + k1) * j1 + (yDelta + k1) * b1
                                            + zDelta + k1] = -2;
                                }
                                else
                                {
                                    adjacentTreeBlocks[(xDelta + k1) * j1 + (yDelta + k1) * b1
                                            + zDelta + k1] = -1;
                                }
                            }
                        }
                    }

                    for (xDelta = 1; xDelta <= 4; ++xDelta)
                    {
                        for (yDelta = -TICK_NOTIFY_RANGE; yDelta <= TICK_NOTIFY_RANGE; ++yDelta)
                        {
                            for (zDelta = -TICK_NOTIFY_RANGE; zDelta <= TICK_NOTIFY_RANGE; ++zDelta)
                            {
                                for (id = -TICK_NOTIFY_RANGE; id <= TICK_NOTIFY_RANGE; ++id)
                                {
                                    if (adjacentTreeBlocks[(yDelta + k1) * j1 + (zDelta + k1) * b1
                                            + id + k1] == xDelta - 1)
                                    {
                                        if (adjacentTreeBlocks[(yDelta + k1 - 1) * j1
                                                + (zDelta + k1) * b1 + id + k1] == -2)
                                        {
                                            adjacentTreeBlocks[(yDelta + k1 - 1) * j1
                                                    + (zDelta + k1) * b1 + id + k1] = xDelta;
                                        }

                                        if (adjacentTreeBlocks[(yDelta + k1 + 1) * j1
                                                + (zDelta + k1) * b1 + id + k1] == -2)
                                        {
                                            adjacentTreeBlocks[(yDelta + k1 + 1) * j1
                                                    + (zDelta + k1) * b1 + id + k1] = xDelta;
                                        }

                                        if (adjacentTreeBlocks[(yDelta + k1) * j1
                                                + (zDelta + k1 - 1) * b1 + id + k1] == -2)
                                        {
                                            adjacentTreeBlocks[(yDelta + k1) * j1
                                                    + (zDelta + k1 - 1) * b1 + id + k1] = xDelta;
                                        }

                                        if (adjacentTreeBlocks[(yDelta + k1) * j1
                                                + (zDelta + k1 + 1) * b1 + id + k1] == -2)
                                        {
                                            adjacentTreeBlocks[(yDelta + k1) * j1
                                                    + (zDelta + k1 + 1) * b1 + id + k1] = xDelta;
                                        }

                                        if (adjacentTreeBlocks[(yDelta + k1) * j1 + (zDelta + k1)
                                                * b1 + id + k1 - 1] == -2)
                                        {
                                            adjacentTreeBlocks[(yDelta + k1) * j1 + (zDelta + k1)
                                                    * b1 + id + k1 - 1] = xDelta;
                                        }

                                        if (adjacentTreeBlocks[(yDelta + k1) * j1 + (zDelta + k1)
                                                * b1 + id + k1 + 1] == -2)
                                        {
                                            adjacentTreeBlocks[(yDelta + k1) * j1 + (zDelta + k1)
                                                    * b1 + id + k1 + 1] = xDelta;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                xDelta = adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];

                if (xDelta >= 0)
                {
                    world.setBlockMetadataWithNotify(x, y, z, metaData & -9, 4);
                }
                else
                {
                    removeLeaves(world, x, y, z);
                }
            }
        }
    }
}
