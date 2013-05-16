
package scottishbiomes.block;

import static scottishbiomes.lib.Blocks.SAPLING1;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GreenLeaves extends Leaves
{

    private static final int SAPLING_RARITY = 20;
    private static final int REDWOOD_SAPLING_RARITY = 40;
    private static final int BEST_SAPLING_RARITY = 10;

    private static final ImmutableList<String> TYPES = ImmutableList.of("acacia", "poplar", "redwood");

    public GreenLeaves(final int id)
    {
        super(id, TYPES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int
            colorMultiplier(final IBlockAccess blockAccess, final int x, final int y, final int z)
    {
        final int metadata = limitToValidMetadata(blockAccess.getBlockMetadata(x, y, z));
        if (metadata != 2) return getRenderColor(metadata);
        return calcSmoothedBiomeFoliageColor(blockAccess, x, z);
    }

    @Override
    public int damageDropped(final int metaData)
    {
        return super.damageDropped(metaData) + 4;
    }

    @Override
    public void dropBlockAsItemWithChance(final World world, final int x, final int y, final int z,
            final int metaData, final float chance, final int fortune)
    {
        if (!world.isRemote)
        {
            int dropRarity =
                    metaData != 1 && metaData != 2 ? SAPLING_RARITY : REDWOOD_SAPLING_RARITY;

            if (fortune > 0)
            {
                dropRarity -= 2 << fortune;

                if (dropRarity < BEST_SAPLING_RARITY)
                {
                    dropRarity = BEST_SAPLING_RARITY;
                }
            }

            if (world.rand.nextInt(dropRarity) == 0)
            {
                final int k1 = idDropped(metaData, world.rand, fortune);
                dropBlockAsItem_do(world, x, y, z, new ItemStack(k1, 1, damageDropped(metaData)));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int metaData)
    {
        metaData = limitToValidMetadata(metaData);

        return metaData == 0 ? ColorizerFoliage.getFoliageColor(0.9F, 0.1F) : metaData == 1
                ? ColorizerFoliage.getFoliageColorPine() : ColorizerFoliage.getFoliageColorBasic();
    }

    @Override
    public int idDropped(final int metaData, final Random rng, final int fortune)
    {
        return SAPLING1.id();
    }

}
