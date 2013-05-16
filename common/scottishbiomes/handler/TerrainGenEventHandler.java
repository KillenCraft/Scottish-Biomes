
package scottishbiomes.handler;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import scottishbiomes.lib.Biomes;
import scottishbiomes.terraingen.TerrainGenInjector;
import scottishbiomes.tree.Poplar;

public class TerrainGenEventHandler
{
    private static void generatePoplarGrove(final World world, final Random rng, final int chunkX,
            final int chunkZ)
    {
        int count = rng.nextInt(5) + rng.nextInt(4) + 1;
        for (; count > 0; count--)
        {
            final WorldGenerator tree = new Poplar();
            final int x = chunkX + rng.nextInt(12) + 6;
            final int z = chunkZ + rng.nextInt(12) + 6;
            final int y = world.getHeightValue(x, z);
            tree.generate(world, rng, x, y, z);
        }
    }

    @ForgeSubscribe
    public void onDecorate(final Decorate e)
    {
        // Triggered by BIG_SHROOM event because it happens
        // immediately after vanilla tree gen
        if (e.type == DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM)
        {
            final BiomeGenBase biome = e.world.getBiomeGenForCoords(e.chunkX, e.chunkZ);
           if (biome == BiomeGenBase.plains || biome == BiomeGenBase.extremeHillsEdge || biome == BiomeGenBase.extremeHills)
            {
                if (e.rand.nextInt(32) == 0)
                {
                    generatePoplarGrove(e.world, e.rand, e.chunkX, e.chunkZ);
                }
            }
            if (biome == Biomes.SHRUBLAND.value())
            {
                if (e.rand.nextInt(64) == 0)
                {
                    generatePoplarGrove(e.world, e.rand, e.chunkX, e.chunkZ);
                }
            }
        }
    }

    @ForgeSubscribe
    public void onInitBiomeGens(final WorldTypeEvent.InitBiomeGens e)
    {
        e.newBiomeGens = TerrainGenInjector.assembleModdedBiomeGenerators(e.seed, e.worldType);
    }
}
