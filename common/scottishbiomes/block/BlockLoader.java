
package scottishbiomes.block;

import static com.google.common.base.Preconditions.checkState;
import static scottishbiomes.lib.Blocks.ACACIA_STAIRS;
import static scottishbiomes.lib.Blocks.AUTUMN_LEAVES;
import static scottishbiomes.lib.Blocks.CRACKED_SAND;
import static scottishbiomes.lib.Blocks.CRACKED_SAND_STAIRS;
import static scottishbiomes.lib.Blocks.GREENLEAVES;
import static scottishbiomes.lib.Blocks.LOG1;
import static scottishbiomes.lib.Blocks.PLANKS1;
import static scottishbiomes.lib.Blocks.POPLAR_STAIRS;
import static scottishbiomes.lib.Blocks.REDCOBBLE_STAIRS;
import static scottishbiomes.lib.Blocks.REDCOBBLE_WALL;
import static scottishbiomes.lib.Blocks.REDROCK;
import static scottishbiomes.lib.Blocks.REDROCK_BRICK_STAIRS;
import static scottishbiomes.lib.Blocks.REDROCK_STAIRS;
import static scottishbiomes.lib.Blocks.REDWOOD_STAIRS;
import static scottishbiomes.lib.Blocks.ROCK_SLAB;
import static scottishbiomes.lib.Blocks.ROCK_SLAB_HALF;
import static scottishbiomes.lib.Blocks.SAPLING1;
import static scottishbiomes.lib.Blocks.WOOD_SLAB;
import static scottishbiomes.lib.Blocks.WOOD_SLAB_HALF;
import static scottishbiomes.utility.helper.NameHelper.fullBlockName;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import scottishbiomes.Loader;
import scottishbiomes.configuration.BlockIdConfig;
import scottishbiomes.item.LeavesItem;
import scottishbiomes.utility.block.CustomBlock;
import scottishbiomes.utility.block.CustomStairs;
import scottishbiomes.utility.block.CustomWall;
import scottishbiomes.utility.item.CustomSlabItem;
import scottishbiomes.utility.item.MultiFacetedItem;
import scottishbiomes.utility.proxy.CommonProxy;

import com.google.common.collect.ImmutableList;

public class BlockLoader
{
    private static final ImmutableList<String> LOG1_TYPES = ImmutableList.of("acacia", "poplar",
            "redwood");

    private static final ImmutableList<String> PLANK1_TYPES = ImmutableList.of("acacia", "poplar",
            "redwood");

    private static boolean hasRegistered = false;

    private static void setDisplayNames()
    {
        final CommonProxy proxy = Loader.proxy;

        proxy.addStringLocalization(fullBlockName("crackedsand"), "Cracked Sand");
        proxy.addStringLocalization(fullBlockName("stairs.crackedsand"), "Cracked Sand Stairs");
        proxy.addStringLocalization(fullBlockName("slab.crackedsand"), "Cracked Sand Slab");

        proxy.addStringLocalization(fullBlockName("redrock"), "Red Rock");
        proxy.addStringLocalization(fullBlockName("redrock.cobble"), "Red Cobblestone");
        proxy.addStringLocalization(fullBlockName("redrock.brick"), "Red Rock Brick");

        proxy.addStringLocalization(fullBlockName("wall.redrock.cobble"), "Red Cobblestone Wall");

        proxy.addStringLocalization(fullBlockName("stairs.redrock"), "Red Rock Stairs");
        proxy.addStringLocalization(fullBlockName("stairs.redrock.cobble"),
                "Red Cobblestone Stairs");
        proxy.addStringLocalization(fullBlockName("stairs.redrock.brick"), "Red Rock Brick Stairs");

        proxy.addStringLocalization(fullBlockName("log.acacia"), "Acacia Wood");
        proxy.addStringLocalization(fullBlockName("log.poplar"), "Poplar Wood");
        proxy.addStringLocalization(fullBlockName("log.redwood"), "Sequoia");

        proxy.addStringLocalization(fullBlockName("wood.acacia"), "Acacia Planks");
        proxy.addStringLocalization(fullBlockName("wood.poplar"), "Poplar Planks");
        proxy.addStringLocalization(fullBlockName("wood.redwood"), "Sequoia Planks");

        proxy.addStringLocalization(fullBlockName("stairs.acacia"), "Acacia Stairs");
        proxy.addStringLocalization(fullBlockName("stairs.poplar"), "Poplar Stairs");
        proxy.addStringLocalization(fullBlockName("stairs.redwood"), "Sequoia Stairs");

        proxy.addStringLocalization(fullBlockName("leaves.autumn.brown"), "Brown Autumn Leaves");
        proxy.addStringLocalization(fullBlockName("leaves.autumn.orange"), "Orange Autumn Leaves");
        proxy.addStringLocalization(fullBlockName("leaves.autumn.purple"), "Purple Autumn Leaves");
        proxy.addStringLocalization(fullBlockName("leaves.autumn.yellow"), "Yellow Autumn Leaves");

        proxy.addStringLocalization(fullBlockName("leaves.acacia"), "Acacia Leaves");
        proxy.addStringLocalization(fullBlockName("leaves.poplar"), "Poplar Leaves");
        proxy.addStringLocalization(fullBlockName("leaves.redwood"), "Sequoia Leaves");

        proxy.addStringLocalization(fullBlockName("sapling.autumn.brown"), "Brown Autumn Sapling");
        proxy.addStringLocalization(fullBlockName("sapling.autumn.orange"), "Orange Autumn Sapling");
        proxy.addStringLocalization(fullBlockName("sapling.autumn.purple"), "Purple Autumn Sapling");
        proxy.addStringLocalization(fullBlockName("sapling.autumn.yellow"), "Yellow Autumn Sapling");
        proxy.addStringLocalization(fullBlockName("sapling.acacia"), "Acacia Sapling");
        proxy.addStringLocalization(fullBlockName("sapling.poplar"), "Poplar Sapling");
        proxy.addStringLocalization(fullBlockName("sapling.redwood"), "Sequoia Sapling");

        proxy.addStringLocalization(fullBlockName("slab.redrock"), "Red Rock Slab");
        proxy.addStringLocalization(fullBlockName("slab.redrock.cobble"), "Red Cobblestone Slab");
        proxy.addStringLocalization(fullBlockName("slab.redrock.brick"), "Red Rock Brick Slab");
        proxy.addStringLocalization(fullBlockName("slab.acacia"), "Acacia Slab");
        proxy.addStringLocalization(fullBlockName("slab.poplar"), "Poplar Slab");
        proxy.addStringLocalization(fullBlockName("slab.redwood"), "Sequoia Slab");
    }

    private final BlockIdConfig blockIdConfig;

    public BlockLoader(final BlockIdConfig blockIdConfig)
    {
        this.blockIdConfig = blockIdConfig;
    }

    private void createBasicBlocks()
    {
        CRACKED_SAND.set(makeCrackedSand());
        REDROCK.set(makeRedRock());
        LOG1.set(makeLog1());
        PLANKS1.set(makePlanks1());
        AUTUMN_LEAVES.set(makeAutumnLeaves());
        GREENLEAVES.set(makeGreenLeaves());
        SAPLING1.set(makeAutumnSapling());
    }

    private void createBlocks()
    {
        createBasicBlocks();
        createStairsAndSlabs();
    }

    private void createStairsAndSlabs()
    {
        CRACKED_SAND_STAIRS.set(makeCrackedSandStairs());
        REDCOBBLE_STAIRS.set(makeRedCobbleStairs());
        REDCOBBLE_WALL.set(makeRedCobbleWall());
        REDROCK_STAIRS.set(makeRedRockStairs());
        REDROCK_BRICK_STAIRS.set(makeRedRockBrickStairs());
        ACACIA_STAIRS.set(makeAcaciaStairs());
        POPLAR_STAIRS.set(makePoplarStairs());
        REDWOOD_STAIRS.set(makeRedwoodStairs());
        ROCK_SLAB.set(makeRedRockSlab(true));
        ROCK_SLAB_HALF.set(makeRedRockSlab(false));
        WOOD_SLAB.set(makeWoodSlab(true));
        WOOD_SLAB_HALF.set(makeWoodSlab(false));
    }

    private Block makeAcaciaStairs()
    {
        final Block block =
                new CustomStairs(blockIdConfig.getConfigId(ACACIA_STAIRS), PLANKS1.value());
        return block.setUnlocalizedName("stairs.acacia");
    }

    private Block makeAutumnLeaves()
    {
        final Block block = new AutumnLeaves(blockIdConfig.getConfigId(AUTUMN_LEAVES));
        return block.setUnlocalizedName("leaves.autumn").setHardness(0.2F).setLightOpacity(1)
                .setStepSound(Block.soundGrassFootstep);
    }

    private Block makeAutumnSapling()
    {
        final Block block = new Sapling1(blockIdConfig.getConfigId(SAPLING1));
        return block.setUnlocalizedName("sapling").setHardness(0)
                .setStepSound(Block.soundGrassFootstep);
    }

    private Block makeCrackedSand()
    {
        final Block block = new CustomBlock(blockIdConfig.getConfigId(CRACKED_SAND), Material.rock);
        return block.setUnlocalizedName("crackedsand").setHardness(1.2F)
                .setStepSound(Block.soundStoneFootstep);
    }

    private Block makeCrackedSandStairs()
    {
        final Block block =
                new CustomStairs(blockIdConfig.getConfigId(CRACKED_SAND_STAIRS),
                        CRACKED_SAND.value());
        return block.setUnlocalizedName("stairs.crackedsand");
    }

    private Block makeGreenLeaves()
    {
        final Block block = new GreenLeaves(blockIdConfig.getConfigId(GREENLEAVES));
        return block.setUnlocalizedName("leaves").setHardness(0.2F).setLightOpacity(1)
                .setStepSound(Block.soundGrassFootstep);
    }

    private Block makeLog1()
    {
        final Block block = new Log(blockIdConfig.getConfigId(LOG1), LOG1_TYPES);
        return block.setUnlocalizedName("log").setHardness(2.0F)
                .setStepSound(Block.soundWoodFootstep);
    }

    private Block makePlanks1()
    {
        final Block block = new Wood(blockIdConfig.getConfigId(PLANKS1), PLANK1_TYPES);
        return block.setUnlocalizedName("wood").setHardness(2.0F).setResistance(5.0F)
                .setStepSound(Block.soundWoodFootstep);
    }

    private Block makePoplarStairs()
    {
        final Block block =
                new CustomStairs(blockIdConfig.getConfigId(POPLAR_STAIRS), PLANKS1.value(), 1);
        return block.setUnlocalizedName("stairs.poplar");
    }

    private Block makeRedCobbleStairs()
    {
        final Block block =
                new CustomStairs(blockIdConfig.getConfigId(REDCOBBLE_STAIRS), REDROCK.value(), 1);
        return block.setUnlocalizedName("stairs.redrock.cobble");
    }

    private Block makeRedCobbleWall()
    {
        final Block block =
                new CustomWall(blockIdConfig.getConfigId(REDCOBBLE_WALL), REDROCK.value(), 1);
        return block.setUnlocalizedName("wall.redrock.cobble");
    }

    private Block makeRedRock()
    {
        final Block block = new RedRock(blockIdConfig.getConfigId(REDROCK));
        return block.setUnlocalizedName("redrock").setHardness(1.5F).setResistance(2.0F);
    }

    private Block makeRedRockBrickStairs()
    {
        final Block block =
                new CustomStairs(blockIdConfig.getConfigId(REDROCK_BRICK_STAIRS), REDROCK.value(),
                        2);
        return block.setUnlocalizedName("stairs.redrock.brick");
    }

    private Block makeRedRockSlab(final boolean isDouble)
    {
        final Block block =
                new RockSlab(blockIdConfig.getConfigId(isDouble ? ROCK_SLAB : ROCK_SLAB_HALF),
                        isDouble);
        return block.setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep)
                .setUnlocalizedName("slab");
    }

    private Block makeRedRockStairs()
    {
        final Block block =
                new CustomStairs(blockIdConfig.getConfigId(REDROCK_STAIRS), REDROCK.value());
        return block.setUnlocalizedName("stairs.redrock");
    }

    private Block makeRedwoodStairs()
    {
        final Block block =
                new CustomStairs(blockIdConfig.getConfigId(REDWOOD_STAIRS), PLANKS1.value(), 2);
        return block.setUnlocalizedName("stairs.redwood");
    }

    private Block makeWoodSlab(final boolean isDouble)
    {
        final Block block =
                new WoodSlab(blockIdConfig.getConfigId(isDouble ? WOOD_SLAB : WOOD_SLAB_HALF),
                        isDouble);
        return block.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep)
                .setUnlocalizedName("slab");
    }

    public void register()
    {
        checkState(!hasRegistered);

        createBlocks();
        registerBlocksWithGame();
        setBurnProperties();
        setDisplayNames();
        setOreDictionaryDefinitions();

        hasRegistered = true;
    }

    private void registerBlocksWithGame()
    {
        final CommonProxy proxy = Loader.proxy;

        Block block = CRACKED_SAND.value();
        proxy.registerBlock(block, "blockCrackedSand");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = CRACKED_SAND_STAIRS.value();
        proxy.registerBlock(block, "blockCrackedSandStairs");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = REDROCK.value();
        proxy.registerBlock(block, MultiFacetedItem.class, "blockRedRock");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = ROCK_SLAB.value();
        proxy.registerBlock(block, CustomSlabItem.class, "blockRedRockSlab");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = ROCK_SLAB_HALF.value();
        proxy.registerBlock(block, CustomSlabItem.class, "blockRedRockSlabHalf");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = REDROCK_STAIRS.value();
        proxy.registerBlock(block, "blockRedRockStairs");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = REDCOBBLE_STAIRS.value();
        proxy.registerBlock(block, "blockRedCobbleStairs");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = REDCOBBLE_WALL.value();
        proxy.registerBlock(block, "blockRedCobbleWall");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = REDROCK_BRICK_STAIRS.value();
        proxy.registerBlock(block, "blockRedRockBrickStairs");
        proxy.setBlockHarvestLevel(block, "pickaxe", 0);

        block = LOG1.value();
        proxy.registerBlock(block, MultiFacetedItem.class, "blockLog1");
        proxy.setBlockHarvestLevel(block, "axe", 0);

        block = PLANKS1.value();
        proxy.registerBlock(block, MultiFacetedItem.class, "blockPlanks1");
        proxy.setBlockHarvestLevel(block, "axe", 0);

        block = ACACIA_STAIRS.value();
        proxy.registerBlock(block, "blockAcaciaStairs");
        proxy.setBlockHarvestLevel(block, "axe", 0);

        block = POPLAR_STAIRS.value();
        proxy.registerBlock(block, "blockFirStairs");
        proxy.setBlockHarvestLevel(block, "axe", 0);

        block = REDWOOD_STAIRS.value();
        proxy.registerBlock(block, "blockRedwoodStairs");
        proxy.setBlockHarvestLevel(block, "axe", 0);

        block = AUTUMN_LEAVES.value();
        proxy.registerBlock(block, LeavesItem.class, "blockAutumnLeaves");

        block = GREENLEAVES.value();
        proxy.registerBlock(block, LeavesItem.class, "blockGreenLeaves");

        block = SAPLING1.value();
        proxy.registerBlock(block, MultiFacetedItem.class, "blockAutumnSapling");

        block = WOOD_SLAB.value();
        proxy.registerBlock(block, CustomSlabItem.class, "blockWoodSlab");
        proxy.setBlockHarvestLevel(block, "axe", 0);

        block = WOOD_SLAB_HALF.value();
        proxy.registerBlock(block, CustomSlabItem.class, "blockWoodSlabHalf");
        proxy.setBlockHarvestLevel(block, "axe", 0);
    }

    private void setBurnProperties()
    {
        final CommonProxy proxy = Loader.proxy;
        proxy.setBurnProperties(LOG1.id(), 5, 5);
        proxy.setBurnProperties(PLANKS1.id(), 5, 20);
        proxy.setBurnProperties(AUTUMN_LEAVES.id(), 30, 60);
        proxy.setBurnProperties(GREENLEAVES.id(), 30, 60);
        proxy.setBurnProperties(WOOD_SLAB.id(), 5, 20);
        proxy.setBurnProperties(WOOD_SLAB_HALF.id(), 5, 20);
        proxy.setBurnProperties(ACACIA_STAIRS.id(), 5, 20);
        proxy.setBurnProperties(POPLAR_STAIRS.id(), 5, 20);
        proxy.setBurnProperties(REDWOOD_STAIRS.id(), 5, 20);
    }

    private void setOreDictionaryDefinitions()
    {
        OreDictionary.registerOre("logWood", new ItemStack(LOG1.id(), 1,
                OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("plankWood", new ItemStack(PLANKS1.id(), 1,
                OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("stairWood", ACACIA_STAIRS.value());
        OreDictionary.registerOre("stairWood", POPLAR_STAIRS.value());
        OreDictionary.registerOre("stairWood", REDWOOD_STAIRS.value());
        OreDictionary.registerOre("treeLeaves", new ItemStack(AUTUMN_LEAVES.id(), 1,
                OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(GREENLEAVES.id(), 1,
                OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("treeSapling", new ItemStack(SAPLING1.id(), 1,
                OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("slabWood", new ItemStack(WOOD_SLAB_HALF.id(), 1,
                OreDictionary.WILDCARD_VALUE));
    }
}
