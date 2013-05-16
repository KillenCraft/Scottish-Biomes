
package scottishbiomes.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class MountainRidge extends BiomeGenBase
{
    public MountainRidge(final int id)
    {
        super(id);

        theBiomeDecorator.treesPerChunk = 0;
        theBiomeDecorator.grassPerChunk = 12;
    }

}
