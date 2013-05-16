
package scottishbiomes.recipe;

import static scottishbiomes.lib.Items.ACACIA_LOG;
import static scottishbiomes.lib.Items.ACACIA_PLANKS;
import static scottishbiomes.lib.Items.ACACIA_SLAB_HALF;
import static scottishbiomes.lib.Items.CRACKED_SAND;
import static scottishbiomes.lib.Items.CRACKED_SAND_SLAB_HALF;
import static scottishbiomes.lib.Items.CRACKED_SAND_STAIRS;
import static scottishbiomes.lib.Items.POPLAR_LOG;
import static scottishbiomes.lib.Items.POPLAR_PLANKS;
import static scottishbiomes.lib.Items.POPLAR_SLAB_HALF;
import static scottishbiomes.lib.Items.REDCOBBLE;
import static scottishbiomes.lib.Items.REDCOBBLE_SLAB_HALF;
import static scottishbiomes.lib.Items.REDCOBBLE_STAIRS;
import static scottishbiomes.lib.Items.REDCOBBLE_WALL;
import static scottishbiomes.lib.Items.REDROCK;
import static scottishbiomes.lib.Items.REDROCK_BRICK;
import static scottishbiomes.lib.Items.REDROCK_BRICK_SLAB_HALF;
import static scottishbiomes.lib.Items.REDROCK_BRICK_STAIRS;
import static scottishbiomes.lib.Items.REDROCK_SLAB_HALF;
import static scottishbiomes.lib.Items.REDROCK_STAIRS;
import static scottishbiomes.lib.Items.REDWOOD_LOG;
import static scottishbiomes.lib.Items.REDWOOD_PLANKS;
import static scottishbiomes.lib.Items.REDWOOD_SLAB_HALF;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import scottishbiomes.Loader;
import scottishbiomes.lib.Items;
import scottishbiomes.utility.proxy.CommonProxy;

public final class Recipes
{
    private static final int NUM_BRICKS_FROM_BLOCK = 4;
    private static final int NUM_STAIRS_FROM_BLOCK = 4;
    private static final int NUM_SLABS_FROM_BLOCK = 6;
    private static final int NUM_WALLS_FROM_BLOCK = 6;
    private static final int NUM_PLANKS_FROM_LOG = 4;

    private static final String[] SLABS_PATTERN = new String[] { "###" };
    private static final String[] STAIRS_PATTERN = new String[] { "#  ", "## ", "###" };
    private static final String[] WALL_PATTERN = new String[] { "###", "###" };

    private final ItemStack acaciaLog = ACACIA_LOG.value();
    private final ItemStack acaciaPlanks = ACACIA_PLANKS.value();
    private final ItemStack charcoal = new ItemStack(Item.coal, 1, 1);
    private final ItemStack crackedSand = CRACKED_SAND.value();
    private final ItemStack firLog = POPLAR_LOG.value();
    private final ItemStack firPlanks = POPLAR_PLANKS.value();
    private final ItemStack redCobble = REDCOBBLE.value();
    private final ItemStack redRock = REDROCK.value();
    private final ItemStack redRockBrick = REDROCK_BRICK.value();
    private final ItemStack redwoodLog = REDWOOD_LOG.value();
    private final ItemStack redwoodPlanks = REDWOOD_PLANKS.value();
    private final ItemStack sandStone = new ItemStack(Block.sandStone);

    private IRecipe acaciaLogToPlanks()
    {
        final ItemStack result = ACACIA_PLANKS.value();
        result.stackSize = NUM_PLANKS_FROM_LOG;
        return new ShapelessOreRecipe(result, acaciaLog);
    }

    private IRecipe acaciaToSlabs()
    {
        final ItemStack result = ACACIA_SLAB_HALF.value();
        result.stackSize = NUM_SLABS_FROM_BLOCK;
        return new ShapedOreRecipe(result, SLABS_PATTERN, '#', acaciaPlanks);
    }

    private IRecipe acaciaToStairs()
    {
        final ItemStack result = Items.ACACIA_STAIRS.value();
        result.stackSize = NUM_STAIRS_FROM_BLOCK;
        return new ShapedOreRecipe(result, STAIRS_PATTERN, '#', acaciaPlanks);
    }

    private IRecipe crackedSandToSlabs()
    {
        final ItemStack result = CRACKED_SAND_SLAB_HALF.value();
        result.stackSize = NUM_SLABS_FROM_BLOCK;
        return new ShapedOreRecipe(result, SLABS_PATTERN, '#', crackedSand);
    }

    private IRecipe crackedSandToStairs()
    {
        final ItemStack result = CRACKED_SAND_STAIRS.value();
        result.stackSize = NUM_STAIRS_FROM_BLOCK;
        return new ShapedOreRecipe(result, STAIRS_PATTERN, '#', crackedSand);
    }

    private IRecipe firLogToPlanks()
    {
        final ItemStack result = POPLAR_PLANKS.value();
        result.stackSize = NUM_PLANKS_FROM_LOG;
        return new ShapelessOreRecipe(result, firLog);
    }

    private IRecipe firToSlabs()
    {
        final ItemStack result = POPLAR_SLAB_HALF.value();
        result.stackSize = NUM_SLABS_FROM_BLOCK;
        return new ShapedOreRecipe(result, SLABS_PATTERN, '#', firPlanks);
    }

    private IRecipe firToStairs()
    {
        final ItemStack result = Items.POPLAR_STAIRS.value();
        result.stackSize = NUM_STAIRS_FROM_BLOCK;
        return new ShapedOreRecipe(result, STAIRS_PATTERN, '#', firPlanks);
    }

    private IRecipe redCobbleToSlabs()
    {
        final ItemStack result = REDCOBBLE_SLAB_HALF.value();
        result.stackSize = NUM_SLABS_FROM_BLOCK;
        return new ShapedOreRecipe(result, SLABS_PATTERN, '#', redCobble);
    }

    private IRecipe redCobbleToStairs()
    {
        final ItemStack result = REDCOBBLE_STAIRS.value();
        result.stackSize = NUM_STAIRS_FROM_BLOCK;
        return new ShapedOreRecipe(result, STAIRS_PATTERN, '#', redCobble);
    }

    private IRecipe redCobbleToWalls()
    {
        final ItemStack result = REDCOBBLE_WALL.value();
        result.stackSize = NUM_WALLS_FROM_BLOCK;
        return new ShapedOreRecipe(result, WALL_PATTERN, '#', redCobble);
    }

    private IRecipe redRockBrickToSlabs()
    {
        final ItemStack result = REDROCK_BRICK_SLAB_HALF.value();
        result.stackSize = NUM_SLABS_FROM_BLOCK;
        return new ShapedOreRecipe(result, SLABS_PATTERN, '#', redRockBrick);
    }

    private IRecipe redRockBrickToStairs()
    {
        final ItemStack result = REDROCK_BRICK_STAIRS.value();
        result.stackSize = NUM_STAIRS_FROM_BLOCK;
        return new ShapedOreRecipe(result, STAIRS_PATTERN, '#', redRockBrick);
    }

    private IRecipe redRockToRedRockBrick()
    {
        final ItemStack result = REDROCK_BRICK.value();
        result.stackSize = NUM_BRICKS_FROM_BLOCK;
        return new ShapedOreRecipe(result, new String[] { "##", "##" }, '#', redRock);
    }

    private IRecipe redRockToSlabs()
    {
        final ItemStack result = REDROCK_SLAB_HALF.value();
        result.stackSize = NUM_SLABS_FROM_BLOCK;
        return new ShapedOreRecipe(result, SLABS_PATTERN, '#', redRock);
    }

    private IRecipe redRockToStairs()
    {
        final ItemStack result = REDROCK_STAIRS.value();
        result.stackSize = NUM_STAIRS_FROM_BLOCK;
        return new ShapedOreRecipe(result, STAIRS_PATTERN, '#', redRock);
    }

    private IRecipe redwoodLogToPlanks()
    {
        final ItemStack result = REDWOOD_PLANKS.value();
        result.stackSize = NUM_PLANKS_FROM_LOG;
        return new ShapelessOreRecipe(result, redwoodLog);
    }

    private IRecipe redwoodToSlabs()
    {
        final ItemStack result = REDWOOD_SLAB_HALF.value();
        result.stackSize = NUM_SLABS_FROM_BLOCK;
        return new ShapedOreRecipe(result, SLABS_PATTERN, '#', redwoodPlanks);
    }

    private IRecipe redwoodToStairs()
    {
        final ItemStack result = Items.REDWOOD_STAIRS.value();
        result.stackSize = NUM_STAIRS_FROM_BLOCK;
        return new ShapedOreRecipe(result, STAIRS_PATTERN, '#', redwoodPlanks);
    }

    public void register()
    {
        final CommonProxy proxy = Loader.proxy;

        proxy.addRecipe(redRockToRedRockBrick());
        proxy.addRecipe(acaciaLogToPlanks());
        proxy.addRecipe(firLogToPlanks());
        proxy.addRecipe(redwoodLogToPlanks());
        proxy.addRecipe(acaciaToStairs());
        proxy.addRecipe(acaciaToSlabs());
        proxy.addRecipe(crackedSandToStairs());
        proxy.addRecipe(crackedSandToSlabs());
        proxy.addRecipe(firToStairs());
        proxy.addRecipe(firToSlabs());
        proxy.addRecipe(redCobbleToStairs());
        proxy.addRecipe(redCobbleToSlabs());
        proxy.addRecipe(redCobbleToWalls());
        proxy.addRecipe(redRockToStairs());
        proxy.addRecipe(redRockToSlabs());
        proxy.addRecipe(redRockBrickToStairs());
        proxy.addRecipe(redRockBrickToSlabs());
        proxy.addRecipe(redwoodToStairs());
        proxy.addRecipe(redwoodToSlabs());

        proxy.addSmelting(redCobble, redRock, 0F);
        proxy.addSmelting(acaciaLog, charcoal, 0.15F);
        proxy.addSmelting(firLog, charcoal, 0.15F);
        proxy.addSmelting(redwoodLog, charcoal, 0.15F);
        proxy.addSmelting(sandStone, crackedSand, 0.15F);
    }
}
