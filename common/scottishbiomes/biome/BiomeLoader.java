
package scottishbiomes.biome;

import static com.google.common.base.Preconditions.checkState;
import static scottishbiomes.lib.Biomes.AUTUMNHILLS;
import static scottishbiomes.lib.Biomes.AUTUMNWOODS;
import static scottishbiomes.lib.Biomes.BIRCHFOREST;
import static scottishbiomes.lib.Biomes.BIRCHHILLS;
import static scottishbiomes.lib.Biomes.MOUNTAINRIDGE;
import static scottishbiomes.lib.Biomes.REDWOODFOREST;
import static scottishbiomes.lib.Biomes.SAVANNA;
import static scottishbiomes.lib.Biomes.SHRUBLAND;
import static scottishbiomes.lib.Biomes.WASTELAND;
import static scottishbiomes.lib.Blocks.CRACKED_SAND;
import static scottishbiomes.lib.Blocks.REDROCK;
import net.minecraft.world.biome.BiomeGenBase;
import scottishbiomes.Loader;
import scottishbiomes.configuration.BiomeIdConfig;
import scottishbiomes.lib.Biomes;
import scottishbiomes.utility.proxy.CommonProxy;

public class BiomeLoader
{
    private static boolean hasRegistered = false;

    private static void register(final Biomes biome)
    {
        final CommonProxy proxy = Loader.proxy;
        if (biome != AUTUMNHILLS && biome != AUTUMNWOODS && biome != BIRCHFOREST
                && biome != BIRCHHILLS && biome != WASTELAND)
        {
            proxy.addBiome(biome.value());
        }
        proxy.addSpawnBiome(biome.value());
        proxy.addStrongholdBiome(biome.value());

        if (biome != WASTELAND)
        {
            proxy.addVillageBiome(biome.value());
        }
    }

    private final BiomeIdConfig biomeIdConfig;

    public BiomeLoader(final BiomeIdConfig biomeIdConfig)
    {
        this.biomeIdConfig = biomeIdConfig;
    }

    private void createBiomes()
    {
        AUTUMNWOODS.set(makeAutumnWoods());
        AUTUMNHILLS.set(makeAutumnHills());
        BIRCHFOREST.set(makeBirchForest());
        BIRCHHILLS.set(makeBirchHills());
        MOUNTAINRIDGE.set(makeMountainRidge());
        REDWOODFOREST.set(makeRedwoodForest());
        SAVANNA.set(makeSavanna());
        SHRUBLAND.set(makeShrubland());
        WASTELAND.set(makeWasteland());
    }

    private BiomeGenBase makeAutumnHills()
    {
        return new AutumnWoods(biomeIdConfig.getConfigId(AUTUMNHILLS)).setBiomeName("AutumnHills")
                .setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(1.3F, 2.1F);
    }

    private BiomeGenBase makeAutumnWoods()
    {
        return new AutumnWoods(biomeIdConfig.getConfigId(AUTUMNWOODS)).setBiomeName("AutumnWoods")
                .setTemperatureRainfall(0.7F, 0.8F);
    }

    private BiomeGenBase makeBirchForest()
    {
        return new BirchForest(biomeIdConfig.getConfigId(BIRCHFOREST)).setBiomeName("BirchForest")
                .setTemperatureRainfall(0.7F, 0.8F);
    }

    private BiomeGenBase makeBirchHills()
    {
        return new BirchForest(biomeIdConfig.getConfigId(BIRCHHILLS)).setBiomeName("BirchHills")
                .setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.2F, 0.5F);
    }

    private BiomeGenBase makeMountainRidge()
    {
        final BiomeGenBase biome =
                new MountainRidge(biomeIdConfig.getConfigId(MOUNTAINRIDGE))
                        .setBiomeName("MountainRidge").setMinMaxHeight(1.7F, 1.7F)
                        .setTemperatureRainfall(2.0F, 0);
        biome.topBlock = (byte) REDROCK.id();
        return biome;
    }

    private BiomeGenBase makeRedwoodForest()
    {
        return new RedwoodForest(biomeIdConfig.getConfigId(REDWOODFOREST))
                .setBiomeName("RedwoodForest").setMinMaxHeight(0.9F, 1.5F)
                .setTemperatureRainfall(1.1F, 1.4F);
    }

    private BiomeGenBase makeSavanna()
    {
        return new Savanna(biomeIdConfig.getConfigId(SAVANNA)).setBiomeName("Savanna")
                .setMinMaxHeight(0, 0.1F).setTemperatureRainfall(2.0F, 0);
    }

    private BiomeGenBase makeShrubland()
    {
        return new Shrubland(biomeIdConfig.getConfigId(SHRUBLAND)).setBiomeName("Shrubland")
                .setMinMaxHeight(0.1F, 0.3F).setTemperatureRainfall(0.4F, 0.6F);
    }

    private BiomeGenBase makeWasteland()
    {
        final BiomeGenBase biome =
                new Wasteland(biomeIdConfig.getConfigId(WASTELAND)).setBiomeName("Wasteland")
                        .setMinMaxHeight(0, 0).setTemperatureRainfall(2.0F, 0);
        biome.topBlock = (byte) CRACKED_SAND.id();
        return biome;
    }

    public void register()
    {
        checkState(!hasRegistered);

        createBiomes();
        registerBiomesWithGame();

        hasRegistered = true;
    }

    private void registerBiomesWithGame()
    {
        for (final Biomes biome : Biomes.values())
        {
            register(biome);
        }
    }
}
