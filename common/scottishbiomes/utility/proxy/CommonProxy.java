
package scottishbiomes.utility.proxy;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CommonProxy
{
    public void addBiome(final BiomeGenBase biome)
    {
        final WorldType[] worldTypes = { WorldType.DEFAULT, WorldType.LARGE_BIOMES };
        for (final WorldType worldType : worldTypes)
        {
            worldType.addNewBiome(biome);
        }
    }

    @SuppressWarnings("unchecked")
    public void addRecipe(final IRecipe recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(recipe);
    }

    /**
     * A metadata sensitive version of adding a furnace recipe.
     */
    public void addSmelting(final int itemID, final int metadata, final ItemStack itemstack,
            final float experience)
    {
        FurnaceRecipes.smelting().addSmelting(itemID, metadata, itemstack, experience);
    }

    public void addSmelting(final ItemStack input, final ItemStack output, final float experience)
    {
        addSmelting(input.itemID, input.getItemDamage(), output, experience);
    }

    public void addSpawnBiome(final BiomeGenBase biome)
    {
        BiomeManager.addSpawnBiome(biome);
    }

    public void addStringLocalization(final String key, final String value)
    {
        LanguageRegistry.instance().addStringLocalization(key, value);
    }

    public void addStrongholdBiome(final BiomeGenBase biome)
    {
        BiomeManager.addStrongholdBiome(biome);
    }

    public void addVillageBiome(final BiomeGenBase biome)
    {
        BiomeManager.addVillageBiome(biome, true);
    }

    public Logger getFMLLogger()
    {
        return FMLLog.getLogger();
    }

    public void loadColorBuffers()
    {}

    /**
     * Register a block with the world, with the specified item class
     * and block name
     * 
     * @param block
     *            The block to register
     * @param itemclass
     *            The item type to register with it
     * @param name
     *            The mod-unique name to register it with
     */
    public void registerBlock(final Block block, final Class<? extends ItemBlock> itemclass,
            final String name)
    {
        GameRegistry.registerBlock(block, itemclass, name);
    }

    /**
     * Register a block with the specified mod specific name : overrides
     * the standard type based name
     * 
     * @param block
     *            The block to register
     * @param name
     *            The mod-unique name to register it as
     */
    public void registerBlock(final Block block, final String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    /**
     * Register a block to be harvested by a tool class. By default,
     * this sets the block class as effective against that type.
     * 
     * @param block
     *            The block to register.
     * @param toolClass
     *            The tool class to register as able to remove this
     *            block. You may register the same block multiple times
     *            with different tool classes, if multiple tool types
     *            can be used to harvest this block.
     * @param harvestLevel
     *            The minimum tool harvest level required to
     *            successfully harvest the block.
     * @see MinecraftForge#setToolClass for details on tool classes.
     */
    public void setBlockHarvestLevel(final Block block, final String toolClass,
            final int harvestLevel)
    {
        MinecraftForge.setBlockHarvestLevel(block, toolClass, harvestLevel);
    }

    /**
     * Sets up the burn values of blocks.
     * 
     * @param id
     *            The block id
     * @param encouragement
     *            How much the block encourages fire to spread
     * @param flammability
     *            how easy a block is to catch fire
     */
    public void setBurnProperties(final int id, final int encouragement, final int flammability)
    {
        Block.setBurnProperties(id, encouragement, flammability);
    }
}
