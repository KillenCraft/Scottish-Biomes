
package scottishbiomes.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class Wasteland extends BiomeGenBase
{

    public Wasteland(final int id)
    {
        super(id);

        waterColorMultiplier = 0xF08000;

        spawnableCreatureList.clear();

        theBiomeDecorator.grassPerChunk = 1;
        theBiomeDecorator.deadBushPerChunk = 3;
    }

}
