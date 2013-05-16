
package scottishbiomes.lib;

import static com.google.common.base.Preconditions.checkState;
import net.minecraft.block.Block;

public enum Blocks
{
    ACACIA_STAIRS, AUTUMN_LEAVES, CRACKED_SAND, CRACKED_SAND_STAIRS, POPLAR_STAIRS, GREENLEAVES, LOG1,
    PLANKS1, REDCOBBLE_STAIRS, REDCOBBLE_WALL, REDROCK, REDROCK_BRICK_STAIRS, ROCK_SLAB,
    ROCK_SLAB_HALF, REDROCK_STAIRS, REDWOOD_STAIRS, SAPLING1, WOOD_SLAB, WOOD_SLAB_HALF;

    private int blockId = 0;

    public int id()
    {
        return blockId;
    }

    public void set(final Block block)
    {
        checkState(blockId == 0);
        blockId = block.blockID;
    }

    public Block value()
    {
        checkState(blockId != 0);
        return Block.blocksList[blockId];
    }
}
