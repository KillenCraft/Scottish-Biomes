
package scottishbiomes.handler;

import static scottishbiomes.lib.Blocks.SAPLING1;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import scottishbiomes.block.Sapling;

public final class BonemealEventHandler
{
    @ForgeSubscribe
    public void onBonemealEvent(final BonemealEvent e)
    {
        if (!e.world.isRemote)
        {
            if (e.getResult() == Result.DEFAULT && e.ID == SAPLING1.id())
            {
                if (!e.world.isRemote)
                {
                    ((Sapling) SAPLING1.value()).growTree(e.world, e.X, e.Y, e.Z, e.world.rand);
                }
                e.setResult(Result.ALLOW);
            }
        }
    }
}
