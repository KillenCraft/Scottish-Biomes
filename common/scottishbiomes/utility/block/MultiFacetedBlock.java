
package scottishbiomes.utility.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import scottishbiomes.Loader;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class MultiFacetedBlock extends CustomBlock
{
    protected static void addTypeNames(final int id, final Iterable<? extends String> typeNames)
    {
        MultiFacetedBlock.typeNames.replaceValues(id, typeNames);
    }

    public static String getTypeName(final int id, final int metaData)
    {
        return getTypeNames(id).get(metaData);
    }

    public static List<String> getTypeNames(final int id)
    {
        return ImmutableList.copyOf(typeNames.get(id));
    }

    @SideOnly(Side.CLIENT)
    private List<Icon> icons;
    @SideOnly(Side.CLIENT)
    private List<String> iconNames;
    private int subBlockCount;
    private static ArrayListMultimap<Integer, String> typeNames = ArrayListMultimap.create();

    protected MultiFacetedBlock(final int id, final Material material)
    {
        super(id, material);
    }

    @Override
    public int damageDropped(final int par1)
    {
        return par1;
    }

    public Icon getIcon(final int index)
    {
        return icons.get(index);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int metadata)
    {
        return icons.get(metadata % icons.size());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final int id, final CreativeTabs tab, final List listItemStack)
    {
        for (int i = 0; i < subBlockCount; ++i)
        {
            listItemStack.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister)
    {
        icons = Lists.newArrayListWithCapacity(iconNames.size());
        for (final String name : iconNames)
        {
            icons.add(iconRegister.registerIcon(String.format("%s:%s", Loader.MODID, name)));
        }
    }

    @SideOnly(Side.CLIENT)
    protected void setIconNames(final List<String> iconNames)
    {
        this.iconNames = ImmutableList.copyOf(iconNames);
    }

    protected void setSubBlockCount(final int subBlockCount)
    {
        this.subBlockCount = subBlockCount;
    }
}