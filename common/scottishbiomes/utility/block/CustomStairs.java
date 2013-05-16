
package scottishbiomes.utility.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import scottishbiomes.utility.helper.NameHelper;

public class CustomStairs extends BlockStairs
{
    public CustomStairs(final int id, final Block modelBlock)
    {
        this(id, modelBlock, 0);
    }

    public CustomStairs(final int id, final Block modelBlock, final int modelMetadata)
    {
        super(id, modelBlock, modelMetadata);
    }

    @Override
    public Block setUnlocalizedName(final String name)
    {
        return super.setUnlocalizedName(NameHelper.contentName(name));
    }
}
