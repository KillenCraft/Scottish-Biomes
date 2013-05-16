
package scottishbiomes.utility.terraingen;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class SafeWorldGenerator extends WorldGenerator
{
    public SafeWorldGenerator(final boolean doNotify)
    {
        super(doNotify);
    }

    protected boolean chunkExists(final World world, final int x, final int z)
    {
        return world.getChunkProvider().chunkExists(x >> 4, z >> 4);
    }

    @Override
    protected void setBlockAndMetadata(final World world, final int x, final int y, final int z,
            final int blockId, final int metadata)
    {
        // Prevents "Already Decorating" exception, but costs
        // performance
        if (chunkExists(world, x, z))
        {
            super.setBlockAndMetadata(world, x, y, z, blockId, metadata);
        }
    }
}
