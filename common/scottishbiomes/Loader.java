
package scottishbiomes;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import scottishbiomes.biome.BiomeLoader;
import scottishbiomes.block.BlockLoader;
import scottishbiomes.configuration.ConfigurationHandler;
import scottishbiomes.handler.BonemealEventHandler;
import scottishbiomes.handler.FuelHandler;
import scottishbiomes.handler.TerrainGenEventHandler;
import scottishbiomes.handler.VillageBlockHandler;
import scottishbiomes.recipe.Recipes;
import scottishbiomes.utility.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Loader.MODID, name = "Scottish Biomes",
        dependencies = "required-after:Forge@[7.0,);required-after:FML@[5.0.5,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Loader
{
    public static final String MODID = "scottishBiomes";
    @SidedProxy(clientSide = "scottishbiomes.utility.proxy.ClientProxy",
            serverSide = "scottishbiomes.utility.proxy.CommonProxy")
    public static CommonProxy proxy;
    @Instance(MODID)
    public static Loader instance;

    @PreInit
    public void onBegin(final FMLPreInitializationEvent event)
    {
        final ConfigurationHandler config =
                new ConfigurationHandler(event.getSuggestedConfigurationFile());

        final BlockLoader blockLoader = new BlockLoader(config.getConfigBlockIds());
        blockLoader.register();

        final BiomeLoader biomes = new BiomeLoader(config.getConfigBiomeIds());
        biomes.register();

        config.save();
    }

    @Init
    public void onLoad(final FMLInitializationEvent event)
    {
        registerRecipes();
    }

    @PostInit
    public void onTerminate(final FMLPostInitializationEvent event)
    {
        MinecraftForge.TERRAIN_GEN_BUS.register(new TerrainGenEventHandler());
        MinecraftForge.TERRAIN_GEN_BUS.register(new VillageBlockHandler());
        MinecraftForge.EVENT_BUS.register(new BonemealEventHandler());
        GameRegistry.registerFuelHandler(new FuelHandler());
        proxy.loadColorBuffers();
        BiomeDictionary.registerAllBiomes();
    }

    private void registerRecipes()
    {
        final Recipes recipes = new Recipes();
        recipes.register();
    }
}
