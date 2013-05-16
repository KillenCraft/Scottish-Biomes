
package scottishbiomes.item;

import scottishbiomes.utility.item.MultiFacetedItem;

public class LeavesItem extends MultiFacetedItem
{
    public LeavesItem(final int id)
    {
        super(id);
    }

    @Override
    public int getMetadata(final int metadata)
    {
        return metadata | 4;
    }
}
