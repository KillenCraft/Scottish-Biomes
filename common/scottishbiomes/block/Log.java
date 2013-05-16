
package scottishbiomes.block;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Random;

import scottishbiomes.utility.block.MultiFacetedBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Log extends MultiFacetedBlock
{
    private static final byte LEAF_DECAY_RANGE = 4;

    private static int limitToValidMetadata(final int metaData)
    {
        return metaData & 3;
    }

    protected Log(final int id, final ImmutableList<String> types)
    {
        super(id, Material.wood);

        checkState(!types.isEmpty() && types.size() < 5);
        setSubBlockCount(types.size());

        final List<String> iconNames = Lists.newArrayListWithCapacity(types.size() * 2);
        for (final String type : types)
        {
            iconNames.add(String.format("log_%s_top", type));
            iconNames.add(String.format("log_%s_side", type));
        }
        setIconNames(iconNames);

        MultiFacetedBlock.addTypeNames(id, types);
    }

    @Override
    public void breakBlock(final World world, final int x, final int y, final int z,
            final int blockId, final int metaData)
    {
        final int outerDecayRange = LEAF_DECAY_RANGE + 1;

        if (world.checkChunksExist(x - outerDecayRange, y - outerDecayRange, z - outerDecayRange, x
                + outerDecayRange, y + outerDecayRange, z + outerDecayRange))
        {
            for (int x1 = -LEAF_DECAY_RANGE; x1 <= LEAF_DECAY_RANGE; ++x1)
            {
                for (int y1 = -LEAF_DECAY_RANGE; y1 <= LEAF_DECAY_RANGE; ++y1)
                {
                    for (int z1 = -LEAF_DECAY_RANGE; z1 <= LEAF_DECAY_RANGE; ++z1)
                    {
                        final int testBlockID = world.getBlockId(x + x1, y + y1, z + z1);

                        if (Block.blocksList[testBlockID] != null)
                        {
                            Block.blocksList[testBlockID].beginLeavesDecay(world, x + x1, y + y1, z
                                    + z1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canSustainLeaves(final World world, final int x, final int y, final int z)
    {
        return true;
    }

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

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int metaData)
    {
        final int direction = metaData & 12;
        final int type = limitToValidMetadata(metaData);
        final int index = type * 2;
        return direction == 0 && (side == 1 || side == 0) ? getIcon(index) : direction == 4
                && (side == 5 || side == 4) ? getIcon(index) : direction == 8
                && (side == 2 || side == 3) ? getIcon(index) : getIcon(index + 1);
    }

    @Override
    public int getRenderType()
    {
        return Block.wood.getRenderType();
    }

    @Override
    public int idDropped(final int metaData, final Random rng, final int fortune)
    {
        return blockID;
    }

    @Override
    public boolean isWood(final World world, final int x, final int y, final int z)
    {
        return true;
    }

    @Override
    public int
            onBlockPlaced(final World world, final int x, final int y, final int z, final int side,
                    final float hitX, final float hitY, final float hitZ, final int metaData)
    {
        final int type = limitToValidMetadata(metaData);
        byte direction = 0;

        switch (side)
        {
            case 0:
            case 1:
                direction = 0;
                break;
            case 2:
            case 3:
                direction = 8;
                break;
            case 4:
            case 5:
                direction = 4;
        }

        return type | direction;
    }

    @Override
    public int quantityDropped(final Random par1Random)
    {
        return 1;
    }
}
