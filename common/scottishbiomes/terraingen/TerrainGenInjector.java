
package scottishbiomes.terraingen;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerSwampRivers;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public final class TerrainGenInjector
{
    public static GenLayer[] assembleModdedBiomeGenerators(final long seed,
            final WorldType worldType)
    {
        GenLayerAddIsland islandGenerator =
                new GenLayerAddIsland(1L, new GenLayerFuzzyZoom(2000L, new GenLayerIsland(1L)));
        GenLayerZoom zoomGenerator = new GenLayerZoom(2001L, islandGenerator);
        islandGenerator = new GenLayerAddIsland(2L, zoomGenerator);
        zoomGenerator = new GenLayerZoom(2002L, new GenLayerAddSnow(2L, islandGenerator));
        islandGenerator = new GenLayerAddIsland(3L, zoomGenerator);
        zoomGenerator = new GenLayerZoom(2003L, islandGenerator);
        islandGenerator = new GenLayerAddIsland(4L, zoomGenerator);
        final GenLayerAddMushroomIsland shroomLandGenerator =
                new GenLayerAddMushroomIsland(5L, islandGenerator);
        byte biomeSize = 4;

        if (worldType == WorldType.LARGE_BIOMES)
        {
            biomeSize = 6;
        }
        biomeSize = getModdedBiomeSize(worldType, biomeSize);

        GenLayer layerGenerator = GenLayerZoom.magnify(1000L, shroomLandGenerator, 0);
        layerGenerator =
                GenLayerZoom.magnify(1000L, new GenLayerRiverInit(100L, layerGenerator),
                        biomeSize + 2);
        GenLayer biomeGenerator = GenLayerZoom.magnify(1000L, shroomLandGenerator, 0);
        biomeGenerator =
                GenLayerZoom.magnify(1000L, new GenLayerBiome(200L, biomeGenerator, worldType), 2);
        biomeGenerator = new GenLayerHills(1000L, biomeGenerator);
        biomeGenerator = GenLayerZoom.magnify(1000L, new GroveGenerator(1000L, biomeGenerator), 0);

        biomeGenerator = new WastelandGenerator(1500L, biomeGenerator);

        for (int j = 0; j < biomeSize; ++j)
        {
            biomeGenerator = new GenLayerZoom(1000 + j, biomeGenerator);

            if (j == 0)
            {
                biomeGenerator = new GenLayerAddIsland(3L, biomeGenerator);
            }

            if (j == 1)
            {
                biomeGenerator = new GenLayerShore(1000L, biomeGenerator);
            }

            if (j == 1)
            {
                biomeGenerator = new GenLayerSwampRivers(1000L, biomeGenerator);
            }
        }

        final GenLayerRiverMix terrainGenerator =
                new GenLayerRiverMix(100L, new GenLayerSmooth(1000L, biomeGenerator),
                        new GenLayerSmooth(1000L, new GenLayerRiver(1L, layerGenerator)));
        final GenLayerVoronoiZoom noiseGenerator = new GenLayerVoronoiZoom(10L, terrainGenerator);
        terrainGenerator.initWorldGenSeed(seed);
        noiseGenerator.initWorldGenSeed(seed);
        return new GenLayer[] { terrainGenerator, noiseGenerator, terrainGenerator };
    }

    private static byte getModdedBiomeSize(final WorldType worldType, final byte original)
    {
        final WorldTypeEvent.BiomeSize event = new WorldTypeEvent.BiomeSize(worldType, original);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);
        return event.newSize;
    }

    private TerrainGenInjector()
    {}

}
