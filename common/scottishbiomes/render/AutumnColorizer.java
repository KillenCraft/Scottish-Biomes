
package scottishbiomes.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AutumnColorizer
{
    private static int[] buffer = new int[65536];

    public static int getYellow()
    {
        return getColor(.25, .25);
    }

    private static int getColor(final double run, final double rise)
    {
        final int x = (int) ((1.0D - run) * 255.0D);
        final int y = (int) ((1.0D - rise) * 255.0D);
        return buffer[y << 8 | x];
    }

    public static int getOrange()
    {
        return getColor(.25, .75);
    }

    public static int getPurple()
    {
        return getColor(.75, .25);
    }

    public static int getBrown()
    {
        return getColor(.75, .75);
    }

    public static void setBuffer(final int[] par0ArrayOfInteger)
    {
        buffer = par0ArrayOfInteger;
    }

    private AutumnColorizer()
    {}
}
