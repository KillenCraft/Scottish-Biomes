
package scottishbiomes.utility.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import scottishbiomes.utility.block.MultiFacetedBlock;

public class MultiFacetedItem extends ItemBlockWithMetadata
{
    public MultiFacetedItem(final int id)
    {
        super(id, Block.blocksList[id + 256]);
    }

    @Override
    public String getUnlocalizedName(final ItemStack itemStack)
    {
        String typeName =
                MultiFacetedBlock.getTypeName(itemStack.itemID, itemStack.getItemDamage());
        if (!typeName.isEmpty())
        {
            typeName = "." + typeName;
        }
        return super.getUnlocalizedName() + typeName;
    }

}
