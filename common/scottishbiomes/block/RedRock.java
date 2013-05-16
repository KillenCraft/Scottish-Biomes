
package scottishbiomes.block;

import java.util.List;

import scottishbiomes.utility.block.MultiFacetedBlock;

import net.minecraft.block.material.Material;

import com.google.common.collect.ImmutableList;

public final class RedRock extends MultiFacetedBlock
{
    private static final List<String> ICON_NAMES = ImmutableList.of("redrock", "redrockcobble",
            "redrockbrick");
    private static final List<String> BLOCK_NAMES = ImmutableList.of("", "cobble", "brick");
    private static final int NUMSUBBLOCKS = 3;

    public static String getTypeName(final int metaData)
    {
        return BLOCK_NAMES.get(metaData % NUMSUBBLOCKS);
    }

    public RedRock(final int id)
    {
        super(id, Material.rock);

        setIconNames(ICON_NAMES);
        setSubBlockCount(NUMSUBBLOCKS);

        MultiFacetedBlock.addTypeNames(id, BLOCK_NAMES);
    }
}
