
package scottishbiomes.block;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Random;

import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Sapling extends Flower
{
    private static ArrayListMultimap<Integer, WorldGenerator> treeGens = ArrayListMultimap.create();

    protected static void addTreeGens(final int id,
            final Iterable<? extends WorldGenerator> treeGens)
    {
        Sapling.treeGens.replaceValues(id, treeGens);
    }

    public static WorldGenerator getTreeGen(final int id, final int metaData)
    {
        return getTreeGens(id).get(metaData);
    }

    public static ImmutableList<WorldGenerator> getTreeGens(final int id)
    {
        return ImmutableList.copyOf(treeGens.get(id));
    }

    private static boolean isMarkedForGrowth(final int metadata)
    {
        return (metadata & 8) != 0;
    }

    private static int limitToValidMetadata(final int metadata)
    {
        return metadata & 7;
    }

    private static int markForGrowth(final int metadata)
    {
        return metadata | 8;
    }

    public Sapling(final int id, final ImmutableList<String> types)
    {
        super(id, types);

        checkState(!types.isEmpty() && types.size() < 9);

        final float size = 0.4F;
        setBlockBounds(0.5F - size, 0.0F, 0.5F - size, 0.5F + size, size * 2.0F, 0.5F + size);

        final List<String> iconNames = Lists.newArrayList();
        for (final String type : types)
        {
            iconNames.add(String.format("sapling_%s", type.replace('.', '_')));
        }
        setIconNames(iconNames);
    }

    @Override
    public int damageDropped(final int metadata)
    {
        return limitToValidMetadata(metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, int metadata)
    {
        metadata = limitToValidMetadata(metadata);
        return super.getIcon(side, metadata);
    }

    public void
            growTree(final World world, final int x, final int y, final int z, final Random rng)
    {
        if (!TerrainGen.saplingGrowTree(world, rng, x, y, z)) return;

        final int metadata = limitToValidMetadata(world.getBlockMetadata(x, y, z));
        final WorldGenerator treeGen = getTreeGen(blockID, metadata);

        world.setBlockToAir(x, y, z);

        if (!treeGen.generate(world, rng, x, y, z))
        {
            world.setBlock(x, y, z, blockID, metadata, 3);
        }
    }

    public void markOrGrowMarked(final World world, final int x, final int y, final int z,
            final Random rng)
    {
        final int metadata = world.getBlockMetadata(x, y, z);

        if (!isMarkedForGrowth(metadata))
        {
            world.setBlockMetadataWithNotify(x, y, z, markForGrowth(metadata), 3);
        }
        else
        {
            growTree(world, x, y, z, rng);
        }
    }

    @Override
    public void updateTick(final World world, final int x, final int y, final int z,
            final Random rng)
    {
        if (!world.isRemote)
        {
            super.updateTick(world, x, y, z, rng);

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rng.nextInt(7) == 0)
            {
                markOrGrowMarked(world, x, y, z, rng);
            }
        }
    }
}
