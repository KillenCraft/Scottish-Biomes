
package scottishbiomes.tree;

import java.util.Random;

public final class AcaciaLarge extends Acacia
{
    private static final int MIN_HEIGHT = 7;

    public AcaciaLarge(final boolean fromSapling)
    {
        super(fromSapling);
    }

    @Override
    protected int getHeight(final Random rng)
    {
        return rng.nextInt(7) + MIN_HEIGHT;
    }

}
