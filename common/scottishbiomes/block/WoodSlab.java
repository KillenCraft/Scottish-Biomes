
package scottishbiomes.block;

import static scottishbiomes.lib.Blocks.PLANKS1;
import static scottishbiomes.lib.Blocks.WOOD_SLAB;
import static scottishbiomes.lib.Blocks.WOOD_SLAB_HALF;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import scottishbiomes.utility.block.CustomSlab;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WoodSlab extends CustomSlab
{
    @SideOnly(Side.CLIENT)
    private Icon theIcon;

    private static final ImmutableList<String> TYPES = ImmutableList.of("acacia", "poplar", "redwood");

    public WoodSlab(final int id, final boolean isDouble)
    {
        super(id, isDouble, Material.wood, TYPES);
    }

    @Override
    public BlockHalfSlab getFullSlab()
    {
        return (BlockHalfSlab) WOOD_SLAB.value();
    }

    @Override
    public BlockHalfSlab getHalfSlab()
    {
        return (BlockHalfSlab) WOOD_SLAB_HALF.value();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int metadata)
    {
        return PLANKS1.value().getIcon(side, metadata & 7);
    }
}
