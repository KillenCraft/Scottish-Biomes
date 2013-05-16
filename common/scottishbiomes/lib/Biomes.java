
package scottishbiomes.lib;

import static com.google.common.base.Preconditions.checkState;
import net.minecraft.world.biome.BiomeGenBase;

public enum Biomes
{
    AUTUMNHILLS, AUTUMNWOODS, BIRCHFOREST, BIRCHHILLS, MOUNTAINRIDGE, REDWOODFOREST, SAVANNA,
    SHRUBLAND, WASTELAND;

    private int biomeId = 0;

    public int id()
    {
        return biomeId;
    }

    public void set(final BiomeGenBase biome)
    {
        checkState(biomeId == 0);
        biomeId = biome.biomeID;
    }

    public BiomeGenBase value()
    {
        checkState(biomeId != 0);
        return BiomeGenBase.biomeList[biomeId];
    }
}
