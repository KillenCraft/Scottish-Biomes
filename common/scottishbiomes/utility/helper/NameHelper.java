
package scottishbiomes.utility.helper;

import scottishbiomes.Loader;

public abstract class NameHelper
{
    public static String contentName(final String name)
    {
        return String.format("%s.%s", Loader.MODID.toLowerCase(), name);
    }

    public static String fullBlockName(final String blockName)
    {
        return String.format("tile.%s.name", contentName(blockName));
    }

    private NameHelper()
    {}
}
