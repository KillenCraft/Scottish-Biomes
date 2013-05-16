
package scottishbiomes.handler;

import net.minecraft.block.Block;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockMeta;
import scottishbiomes.lib.Biomes;
import scottishbiomes.lib.Items;

public class VillageBlockHandler
{
    @ForgeSubscribe
    public void onGetVillageBlockId(final GetVillageBlockID event)
    {
        if (event.getResult() != Result.DENY)
        {
            if (event.getResult() != Result.DENY)
            {
                if (event.original == Block.wood.blockID)
                    if (event.biome == Biomes.REDWOODFOREST.value())
                    {
                        event.replacement = Items.REDWOOD_LOG.value().itemID;
                        event.setResult(Result.DENY);
                    }
                    else if (event.biome == Biomes.SAVANNA.value())
                    {
                        event.replacement = Items.ACACIA_LOG.value().itemID;
                        event.setResult(Result.DENY);
                    }
            }
        }
    }

    @ForgeSubscribe
    public void onGetVillageBlockMeta(final GetVillageBlockMeta event)
    {
        if (event.getResult() != Result.DENY)
        {
            if (event.original == Block.wood.blockID)
                if (event.biome == Biomes.BIRCHFOREST.value()
                        || event.biome == Biomes.BIRCHHILLS.value())
                {
                    event.replacement = 2; // Birch
                    event.setResult(Result.DENY);
                }
                else if (event.biome == Biomes.REDWOODFOREST.value())
                {
                    event.replacement = Items.REDWOOD_LOG.value().getItemDamage();
                    event.setResult(Result.DENY);
                }
                else if (event.biome == Biomes.SAVANNA.value())
                {
                    event.replacement = Items.ACACIA_LOG.value().getItemDamage();
                    event.setResult(Result.DENY);
                }
        }
    }
}
