
package scottishbiomes.block;

import java.util.List;

import scottishbiomes.utility.block.MultiFacetedBlock;

import net.minecraft.block.material.Material;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Wood extends MultiFacetedBlock
{
    protected Wood(final int id, final ImmutableList<String> types)
    {
        super(id, Material.wood);
        setSubBlockCount(types.size());

        final List<String> iconNames = Lists.newArrayListWithCapacity(types.size());
        for (final String type : types)
        {
            iconNames.add(String.format("wood_%s", type));
        }
        setIconNames(iconNames);

        MultiFacetedBlock.addTypeNames(id, types);
    }

}
