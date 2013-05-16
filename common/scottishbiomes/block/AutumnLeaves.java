
package scottishbiomes.block;

import static scottishbiomes.lib.Blocks.SAPLING1;

import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import scottishbiomes.render.AutumnColorizer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AutumnLeaves extends Leaves
{
    private static final int APPLE_RARITY = 200;
    private static final int BEST_APPLE_RARITY = 40;

    private static final int SAPLING_RARITY = 20;
    private static final int BEST_SAPLING_RARITY = 10;
    
    private static final ImmutableList<String> TYPES = ImmutableList.of("brown", "orange", "purple", "yellow");

    public AutumnLeaves(final int id)
    {
        super(id, TYPES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int
            colorMultiplier(final IBlockAccess blockAccess, final int x, final int y, final int z)
    {
        final int metaData = blockAccess.getBlockMetadata(x, y, z);
        return getRenderColor(limitToValidMetadata(metaData));
    }

    @Override
    public void dropBlockAsItemWithChance(final World world, final int x, final int y, final int z,
            final int metaData, final float chance, final int fortune)
    {
        if (!world.isRemote)
        {
            int dropRarity = SAPLING_RARITY;

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

            dropRarity = APPLE_RARITY;

            if (fortune > 0)
            {
                dropRarity -= 10 << fortune;

                if (dropRarity < BEST_APPLE_RARITY)
                {
                    dropRarity = BEST_APPLE_RARITY;
                }
            }

            if (world.rand.nextInt(dropRarity) == 0)
            {
                dropBlockAsItem_do(world, x, y, z, new ItemStack(Item.appleRed, 1, 0));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int metaData)
    {
        return leaves.getIcon(side, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int metaData)
    {
        switch (limitToValidMetadata(metaData))
        {
            case 0:
                return AutumnColorizer.getBrown();
            case 1:
                return AutumnColorizer.getOrange();
            case 2:
                return AutumnColorizer.getPurple();
            default:
                return AutumnColorizer.getYellow();
        }
    }

    @Override
    public int idDropped(final int metaData, final Random rng, final int fortune)
    {
        return SAPLING1.id();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister)
    {}

}
