
package scottishbiomes.utility.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import scottishbiomes.Loader;
import scottishbiomes.utility.helper.NameHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomBlock extends Block
{
    public CustomBlock(final int id, final Material material)
    {
        super(id, material);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister)
    {
        final String name = getUnlocalizedName2();
        blockIcon =
                iconRegister.registerIcon(String.format("%s:%s", Loader.MODID,
                        name.substring(name.lastIndexOf('.') + 1)));
    }

    @Override
    public Block setUnlocalizedName(final String name)
    {
        return super.setUnlocalizedName(NameHelper.contentName(name));
    }
}
