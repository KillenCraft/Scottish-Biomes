
package scottishbiomes.handler;

import static scottishbiomes.lib.Blocks.SAPLING1;
import static scottishbiomes.lib.Blocks.WOOD_SLAB_HALF;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler
{
    @Override
    public int getBurnTime(final ItemStack fuel)
    {
        if (fuel.itemID == SAPLING1.id()) return 100;
        if (fuel.itemID == WOOD_SLAB_HALF.id()) return 150;
        return 0;
    }
}
