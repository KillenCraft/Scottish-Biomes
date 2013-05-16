
package scottishbiomes.utility.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import scottishbiomes.utility.helper.NameHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomWall extends BlockWall
{
    private final Block modelBlock;
    private final int modelMetadata;

    public CustomWall(final int id, final Block modelBlock)
    {
        this(id, modelBlock, 0);
    }

    public CustomWall(final int id, final Block modelBlock, final int modelMetadata)
    {
        super(id, modelBlock);
        this.modelBlock = modelBlock;
        this.modelMetadata = modelMetadata;
    }

    @Override
    public boolean canPlaceTorchOnTop(final World world, final int x, final int y, final int z)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int metadata)
    {
        return modelBlock.getIcon(side, modelMetadata);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final int id, final CreativeTabs tab, final List subBlocks)
    {
        subBlocks.add(new ItemStack(id, 1, 0));
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
