
package scottishbiomes.block;

import static scottishbiomes.lib.Blocks.REDROCK;
import static scottishbiomes.lib.Blocks.ROCK_SLAB;
import static scottishbiomes.lib.Blocks.ROCK_SLAB_HALF;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import scottishbiomes.Loader;
import scottishbiomes.lib.Blocks;
import scottishbiomes.utility.block.CustomSlab;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RockSlab extends CustomSlab
{
    @SideOnly(Side.CLIENT)
    private Icon theIcon;

    private static final ImmutableList<String> TYPES = ImmutableList.of("redrock",
            "redrock.cobble", "redrock.brick", "crackedsand");

    public RockSlab(final int id, final boolean isDouble)
    {
        super(id, isDouble, Material.rock, TYPES);
    }

    @Override
    public BlockHalfSlab getFullSlab()
    {
        return (BlockHalfSlab) ROCK_SLAB.value();
    }

    @Override
    public BlockHalfSlab getHalfSlab()
    {
        return (BlockHalfSlab) ROCK_SLAB_HALF.value();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, final int metadata)
    {
        final int type = metadata & 7;

        if (isDoubleSlab && (metadata & 8) != 0)
        {
            side = 1;
        }

        return type == 0 ? side >= 2 ? theIcon : blockIcon : type == 3 ? Blocks.CRACKED_SAND
                .value().getIcon(side, type) : REDROCK.value().getIcon(side, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister)
    {
        blockIcon =
                iconRegister.registerIcon(String.format("%s:%s", Loader.MODID, "redrockslab_top"));
        theIcon =
                iconRegister.registerIcon(String.format("%s:%s", Loader.MODID, "redrockslab_side"));
    }
}
