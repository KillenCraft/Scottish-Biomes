
package scottishbiomes.utility.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import scottishbiomes.utility.helper.NameHelper;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class CustomSlab extends BlockHalfSlab
{
    private final ImmutableList<String> types;

    public CustomSlab(final int id, final boolean isDouble, final Material material,
            final List<String> types)
    {
        super(id, isDouble, material);
        setLightOpacity(0);
        setCreativeTab(CreativeTabs.tabBlock);
        this.types = ImmutableList.copyOf(types);
    }

    @Override
    protected ItemStack createStackedBlock(final int metadata)
    {
        return new ItemStack(getHalfSlab(), 2, metadata & 7);
    }

    public abstract BlockHalfSlab getFullSlab();

    @Override
    public String getFullSlabName(final int metadata)
    {
        return super.getUnlocalizedName() + "." + types.get(metadata & 7);
    }

    public abstract BlockHalfSlab getHalfSlab();

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final int id, final CreativeTabs tab, final List subBlocks)
    {
        if (id != getFullSlab().blockID)
        {
            for (int metadata = 0; metadata < types.size(); ++metadata)
            {
                subBlocks.add(new ItemStack(id, 1, metadata));
            }
        }
    }

    @Override
    public int idDropped(final int metadata, final Random rng, final int fortune)
    {
        return getHalfSlab().blockID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister)
    {}

    @Override
    public Block setUnlocalizedName(final String name)
    {
        return super.setUnlocalizedName(NameHelper.contentName(name));
    }

}
