
package scottishbiomes.utility.proxy;

import net.minecraft.client.Minecraft;
import scottishbiomes.Loader;
import scottishbiomes.render.AutumnColorizer;

public class ClientProxy extends CommonProxy
{
    @Override
    public void loadColorBuffers()
    {
        AutumnColorizer.setBuffer(Minecraft.getMinecraft().renderEngine.getTextureContents("/mods/"
                + Loader.MODID.toLowerCase() + "/textures/misc/autumncolor.png"));
    }
}
