
package scottishbiomes.block;

import static com.google.common.base.Preconditions.checkState;
import static net.minecraftforge.common.EnumPlantType.Plains;

import java.util.List;
import java.util.Random;

import scottishbiomes.utility.block.MultiFacetedBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Flower extends MultiFacetedBlock implements IPlantable
{
    protected Flower(final int id, final ImmutableList<String> types)
    {
        this(id, Material.plants, types);
    }

    public Flower(final int id, final Material material, final ImmutableList<String> types)
    {
        super(id, material);
        setTickRandomly(true);
        final float size = 0.2F;
        setBlockBounds(0.5F - size, 0.0F, 0.5F - size, 0.5F + size, size * 3.0F, 0.5F + size);
        setCreativeTab(CreativeTabs.tabDecorations);

        checkState(!types.isEmpty() && types.size() <= 16);

        setSubBlockCount(types.size());
        final List<String> iconNames = Lists.newArrayList();
        for (final String type : types)
        {
            iconNames.add(String.format("flower_%s", type.replace('.', '_')));
        }
        setIconNames(iconNames);

        MultiFacetedBlock.addTypeNames(id, types);
    }

    @Override
    public boolean canBlockStay(final World world, final int x, final int y, final int z)
    {
        final Block soil = blocksList[world.getBlockId(x, y - 1, z)];
        return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z))
                && soil != null
                && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int x, final int y, final int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
    }

    protected boolean canThisPlantGrowOnThisBlockID(final int blockId)
    {
        return blockId == Block.grass.blockID || blockId == Block.dirt.blockID
                || blockId == Block.tilledField.blockID;
    }

    protected final void
            checkFlowerChange(final World world, final int x, final int y, final int z)
    {
        if (!canBlockStay(world, x, y, z))
        {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x,
            final int y, final int z)
    {
        return null;
    }

    @Override
    public int getPlantID(final World world, final int x, final int y, final int z)
    {
        return blockID;
    }

    @Override
    public int getPlantMetadata(final World world, final int x, final int y, final int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public EnumPlantType getPlantType(final World world, final int x, final int y, final int z)
    {
        return Plains;
    }

    @Override
    public int getRenderType()
    {
        return 1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int x, final int y, final int z,
            final int neighborBlockId)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlockId);
        checkFlowerChange(world, x, y, z);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public void updateTick(final World world, final int x, final int y, final int z,
            final Random rng)
    {
        checkFlowerChange(world, x, y, z);
    }

}
