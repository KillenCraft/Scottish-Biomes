
package scottishbiomes.lib;

import static scottishbiomes.lib.Blocks.ROCK_SLAB;
import static scottishbiomes.lib.Blocks.ROCK_SLAB_HALF;
import static scottishbiomes.lib.Blocks.WOOD_SLAB;
import static scottishbiomes.lib.Blocks.WOOD_SLAB_HALF;
import net.minecraft.item.ItemStack;

public enum Items
{
    ACACIA_LEAVES, ACACIA_LOG, ACACIA_PLANKS, ACACIA_SAPLING, ACACIA_SLAB, ACACIA_SLAB_HALF,
    ACACIA_STAIRS, BROWN_AUTUMN_LEAVES, BROWN_AUTUMN_SAPLING, CRACKED_SAND, CRACKED_SAND_SLAB,
    CRACKED_SAND_SLAB_HALF, CRACKED_SAND_STAIRS, ORANGE_AUTUMN_LEAVES, ORANGE_AUTUMN_SAPLING,
    POPLAR_LEAVES, POPLAR_LOG, POPLAR_PLANKS, POPLAR_SAPLING, POPLAR_SLAB, POPLAR_SLAB_HALF,
    POPLAR_STAIRS, PURPLE_AUTUMN_LEAVES, PURPLE_AUTUMN_SAPLING, REDCOBBLE, REDCOBBLE_SLAB,
    REDCOBBLE_SLAB_HALF, REDCOBBLE_STAIRS, REDCOBBLE_WALL, REDROCK, REDROCK_BRICK,
    REDROCK_BRICK_SLAB, REDROCK_BRICK_SLAB_HALF, REDROCK_BRICK_STAIRS, REDROCK_SLAB,
    REDROCK_SLAB_HALF, REDROCK_STAIRS, REDWOOD_LEAVES, REDWOOD_LOG, REDWOOD_PLANKS,
    REDWOOD_SAPLING, REDWOOD_SLAB, REDWOOD_SLAB_HALF, REDWOOD_STAIRS, YELLOW_AUTUMN_LEAVES,
    YELLOW_AUTUMN_SAPLING;

    private static void populateItems()
    {
        CRACKED_SAND.itemID = Blocks.CRACKED_SAND.id();
        CRACKED_SAND_STAIRS.itemID = Blocks.CRACKED_SAND_STAIRS.id();

        REDROCK.itemID = REDCOBBLE.itemID = REDROCK_BRICK.itemID = Blocks.REDROCK.id();
        REDCOBBLE.damage = 1;
        REDROCK_BRICK.damage = 2;
        REDROCK_STAIRS.itemID = Blocks.REDROCK_STAIRS.id();
        REDCOBBLE_STAIRS.itemID = Blocks.REDCOBBLE_STAIRS.id();
        REDROCK_BRICK_STAIRS.itemID = Blocks.REDROCK_BRICK_STAIRS.id();

        REDROCK_SLAB.itemID =
                REDCOBBLE_SLAB.itemID =
                        REDROCK_BRICK_SLAB.itemID = CRACKED_SAND_SLAB.itemID = ROCK_SLAB.id();
        REDROCK_SLAB_HALF.itemID =
                REDCOBBLE_SLAB_HALF.itemID =
                        REDROCK_BRICK_SLAB_HALF.itemID =
                                CRACKED_SAND_SLAB_HALF.itemID = ROCK_SLAB_HALF.id();
        REDCOBBLE_SLAB.damage = REDCOBBLE_SLAB_HALF.damage = 1;
        REDROCK_BRICK_SLAB.damage = REDROCK_BRICK_SLAB_HALF.damage = 2;
        CRACKED_SAND_SLAB.damage = CRACKED_SAND_SLAB_HALF.damage = 3;

        REDCOBBLE_WALL.itemID = Blocks.REDCOBBLE_WALL.id();

        ACACIA_LOG.itemID = REDWOOD_LOG.itemID = POPLAR_LOG.itemID = Blocks.LOG1.id();
        POPLAR_LOG.damage = 1;
        REDWOOD_LOG.damage = 2;

        ACACIA_PLANKS.itemID = REDWOOD_PLANKS.itemID = POPLAR_PLANKS.itemID = Blocks.PLANKS1.id();
        POPLAR_PLANKS.damage = 1;
        REDWOOD_PLANKS.damage = 2;
        ACACIA_STAIRS.itemID = Blocks.ACACIA_STAIRS.id();
        POPLAR_STAIRS.itemID = Blocks.POPLAR_STAIRS.id();
        REDWOOD_STAIRS.itemID = Blocks.REDWOOD_STAIRS.id();

        ACACIA_SLAB.itemID = POPLAR_SLAB.itemID = REDWOOD_SLAB.itemID = WOOD_SLAB.id();
        ACACIA_SLAB_HALF.itemID =
                POPLAR_SLAB_HALF.itemID = REDWOOD_SLAB_HALF.itemID = WOOD_SLAB_HALF.id();
        POPLAR_SLAB.damage = POPLAR_SLAB_HALF.damage = 1;
        REDWOOD_SLAB.damage = REDWOOD_SLAB_HALF.damage = 2;

        BROWN_AUTUMN_LEAVES.itemID =
                ORANGE_AUTUMN_LEAVES.itemID =
                        PURPLE_AUTUMN_LEAVES.itemID =
                                YELLOW_AUTUMN_LEAVES.itemID = Blocks.AUTUMN_LEAVES.id();
        ORANGE_AUTUMN_LEAVES.damage = 1;
        PURPLE_AUTUMN_LEAVES.damage = 2;
        YELLOW_AUTUMN_LEAVES.damage = 3;

        BROWN_AUTUMN_SAPLING.itemID =
                ORANGE_AUTUMN_SAPLING.itemID =
                        PURPLE_AUTUMN_SAPLING.itemID =
                                YELLOW_AUTUMN_SAPLING.itemID =
                                        ACACIA_SAPLING.itemID =
                                                POPLAR_SAPLING.itemID =
                                                        REDWOOD_SAPLING.itemID =
                                                                Blocks.SAPLING1.id();
        ORANGE_AUTUMN_SAPLING.damage = 1;
        PURPLE_AUTUMN_SAPLING.damage = 2;
        YELLOW_AUTUMN_SAPLING.damage = 3;
        ACACIA_SAPLING.damage = 4;
        POPLAR_SAPLING.damage = 5;
        REDWOOD_SAPLING.damage = 6;

        ACACIA_LEAVES.itemID =
                POPLAR_LEAVES.itemID = REDWOOD_LEAVES.itemID = Blocks.GREENLEAVES.id();
        POPLAR_LEAVES.damage = 1;
        REDWOOD_LEAVES.damage = 2;
    }

    private int damage = 0;

    private int itemID = 0;

    public ItemStack value()
    {
        if (itemID == 0)
        {
            populateItems();
        }
        return new ItemStack(itemID, 1, damage);
    }
}