
package scottishbiomes.tree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AutumnOakLarge extends AutumnOak
{
    static private final byte[] otherCoordPairs = new byte[] { (byte) 2, (byte) 0, (byte) 0,
            (byte) 1, (byte) 2, (byte) 1 };

    private final Random rng = new Random();
    private World world;
    private final int[] basePos = new int[] { 0, 0, 0 };
    private int heightLimit = 0;
    private int height;
    private final double heightAttenuation = 0.618D;
    private final double branchSlope = 0.381D;
    private double scaleWidth = 1.0D;
    private double leafDensity = 1.0D;
    private int heightLimitLimit = 12;
    private int leafDistanceLimit = 4;
    private int[][] leafNodes;

    public AutumnOakLarge(final boolean fromSapling, final int metaLeaves)
    {
        super(fromSapling, metaLeaves);
    }

    private int checkBlockLine(final int[] startPos, final int[] endPos)
    {
        final int[] lengthVector = new int[] { 0, 0, 0 };
        byte axis = 0;
        byte axis2;

        for (axis2 = 0; axis < 3; ++axis)
        {
            lengthVector[axis] = endPos[axis] - startPos[axis];

            if (Math.abs(lengthVector[axis]) > Math.abs(lengthVector[axis2]))
            {
                axis2 = axis;
            }
        }

        if (lengthVector[axis2] == 0)
            return -1;
        else
        {
            final byte b2 = otherCoordPairs[axis2];
            final byte b3 = otherCoordPairs[axis2 + 3];
            byte b4;

            if (lengthVector[axis2] > 0)
            {
                b4 = 1;
            }
            else
            {
                b4 = -1;
            }

            final double d0 = (double) lengthVector[b2] / (double) lengthVector[axis2];
            final double d1 = (double) lengthVector[b3] / (double) lengthVector[axis2];
            final int[] aint3 = new int[] { 0, 0, 0 };
            int i = 0;
            int j;

            for (j = lengthVector[axis2] + b4; i != j; i += b4)
            {
                aint3[axis2] = startPos[axis2] + i;
                aint3[b2] = MathHelper.floor_double(startPos[b2] + i * d0);
                aint3[b3] = MathHelper.floor_double(startPos[b3] + i * d1);

                final Block block =
                        Block.blocksList[world.getBlockId(aint3[0], aint3[1], aint3[2])];
                if (block != null && !block.isLeaves(world, aint3[0], aint3[1], aint3[2]))
                {
                    break;
                }
            }

            return i == j ? -1 : Math.abs(i);
        }
    }

    @Override
    public boolean generate(final World world, final Random rand, final int x,
            final int y, final int par5)
    {
        this.world = world;
        rng.setSeed(rand.nextLong());
        basePos[0] = x;
        basePos[1] = y;
        basePos[2] = par5;

        if (heightLimit == 0)
        {
            heightLimit = 5 + rng.nextInt(heightLimitLimit);
        }

        if (!validTreeLocation())
            return false;
        else
        {
            generateLeafNodeList();
            generateLeaves();
            generateTrunk();
            generateLeafNodeBases();
            return true;
        }
    }

    private void generateLeafNode(final int par1, final int par2, final int par3)
    {
        int l = par2;

        for (final int i1 = par2 + leafDistanceLimit; l < i1; ++l)
        {
            final float f = leafSize(l - par2);
            genTreeLayer(par1, l, par3, f, (byte) 1);
        }
    }

    private void generateLeafNodeBases()
    {
        int i = 0;
        final int j = leafNodes.length;

        for (final int[] aint = new int[] { basePos[0], basePos[1], basePos[2] }; i < j; ++i)
        {
            final int[] aint1 = leafNodes[i];
            final int[] aint2 = new int[] { aint1[0], aint1[1], aint1[2] };
            aint[1] = aint1[3];
            final int k = aint[1] - basePos[1];

            if (leafNodeNeedsBase(k))
            {
                placeBlockLine(aint, aint2);
            }
        }
    }

    private void generateLeafNodeList()
    {
        height = (int) (heightLimit * heightAttenuation);

        if (height >= heightLimit)
        {
            height = heightLimit - 1;
        }

        int numLeafNodesPerLevel =
                (int) (1.382D + Math.pow(leafDensity * heightLimit / 13.0D, 2.0D));

        if (numLeafNodesPerLevel < 1)
        {
            numLeafNodesPerLevel = 1;
        }

        final int[][] leafNodes = new int[numLeafNodesPerLevel * heightLimit][4];
        int j = basePos[1] + heightLimit - leafDistanceLimit;
        int node = 1;
        final int l = basePos[1] + height;
        int i1 = j - basePos[1];
        leafNodes[0][0] = basePos[0];
        leafNodes[0][1] = j;
        leafNodes[0][2] = basePos[2];
        leafNodes[0][3] = l;
        --j;

        while (i1 >= 0)
        {
            int j1 = 0;
            final float f = layerSize(i1);

            if (f < 0.0F)
            {
                --j;
                --i1;
            }
            else
            {
                for (final double d0 = 0.5D; j1 < numLeafNodesPerLevel; ++j1)
                {
                    final double d1 = scaleWidth * f * (rng.nextFloat() + 0.328D);
                    final double d2 = rng.nextFloat() * 2.0D * Math.PI;
                    final int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + basePos[0] + d0);
                    final int l1 = MathHelper.floor_double(d1 * Math.cos(d2) + basePos[2] + d0);
                    final int[] aint1 = new int[] { k1, j, l1 };
                    final int[] aint2 = new int[] { k1, j + leafDistanceLimit, l1 };

                    if (checkBlockLine(aint1, aint2) == -1)
                    {
                        final int[] aint3 = new int[] { basePos[0], basePos[1], basePos[2] };
                        final double d3 =
                                Math.sqrt(Math.pow(Math.abs(basePos[0] - aint1[0]), 2.0D)
                                        + Math.pow(Math.abs(basePos[2] - aint1[2]), 2.0D));
                        final double d4 = d3 * branchSlope;

                        if (aint1[1] - d4 > l)
                        {
                            aint3[1] = l;
                        }
                        else
                        {
                            aint3[1] = (int) (aint1[1] - d4);
                        }

                        if (checkBlockLine(aint3, aint1) == -1)
                        {
                            leafNodes[node][0] = k1;
                            leafNodes[node][1] = j;
                            leafNodes[node][2] = l1;
                            leafNodes[node][3] = aint3[1];
                            ++node;
                        }
                    }
                }

                --j;
                --i1;
            }
        }

        this.leafNodes = new int[node][4];
        System.arraycopy(leafNodes, 0, this.leafNodes, 0, node);
    }

    private void generateLeaves()
    {
        int i = 0;

        for (final int j = leafNodes.length; i < j; ++i)
        {
            final int k = leafNodes[i][0];
            final int l = leafNodes[i][1];
            final int i1 = leafNodes[i][2];
            generateLeafNode(k, l, i1);
        }
    }

    private void generateTrunk()
    {
        final int i = basePos[0];
        final int j = basePos[1];
        final int k = basePos[1] + height;
        final int l = basePos[2];
        final int[] aint = new int[] { i, j, l };
        final int[] aint1 = new int[] { i, k, l };
        placeBlockLine(aint, aint1);
    }

    private void genTreeLayer(final int par1, final int par2, final int par3, final float par4,
            final byte par5)
    {
        final int i1 = (int) (par4 + 0.618D);
        final byte b1 = otherCoordPairs[par5];
        final byte b2 = otherCoordPairs[par5 + 3];
        final int[] aint = new int[] { par1, par2, par3 };
        final int[] aint1 = new int[] { 0, 0, 0 };
        int j1 = -i1;
        int k1 = -i1;

        for (aint1[par5] = aint[par5]; j1 <= i1; ++j1)
        {
            aint1[b1] = aint[b1] + j1;
            k1 = -i1;

            while (k1 <= i1)
            {
                final double d0 =
                        Math.pow(Math.abs(j1) + 0.5D, 2.0D) + Math.pow(Math.abs(k1) + 0.5D, 2.0D);

                if (d0 > par4 * par4)
                {
                    ++k1;
                }
                else
                {
                    aint1[b2] = aint[b2] + k1;

                    placeLeaves(world, aint1[0], aint1[1], aint1[2]);
                    ++k1;
                }
            }
        }
    }

    private float layerSize(final int par1)
    {
        if (par1 < heightLimit * 0.3D)
            return -1.618F;
        else
        {
            final float f = heightLimit / 2.0F;
            final float f1 = heightLimit / 2.0F - par1;
            float f2;

            if (f1 == 0.0F)
            {
                f2 = f;
            }
            else if (Math.abs(f1) >= f)
            {
                f2 = 0.0F;
            }
            else
            {
                f2 = (float) Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));
            }

            f2 *= 0.5F;
            return f2;
        }
    }

    private boolean leafNodeNeedsBase(final int par1)
    {
        return par1 >= heightLimit * 0.2D;
    }

    private float leafSize(final int par1)
    {
        return par1 >= 0 && par1 < leafDistanceLimit ? par1 != 0 && par1 != leafDistanceLimit - 1
                ? 3.0F : 2.0F : -1.0F;
    }

    private void placeBlockLine(final int[] par1ArrayOfInteger, final int[] par2ArrayOfInteger)
    {
        final int[] aint2 = new int[] { 0, 0, 0 };
        byte b0 = 0;
        byte b1;

        for (b1 = 0; b0 < 3; ++b0)
        {
            aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];

            if (Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
            {
                b1 = b0;
            }
        }

        if (aint2[b1] != 0)
        {
            final byte b2 = otherCoordPairs[b1];
            final byte b3 = otherCoordPairs[b1 + 3];
            byte b4;

            if (aint2[b1] > 0)
            {
                b4 = 1;
            }
            else
            {
                b4 = -1;
            }

            final double d0 = (double) aint2[b2] / (double) aint2[b1];
            final double d1 = (double) aint2[b3] / (double) aint2[b1];
            final int[] aint3 = new int[] { 0, 0, 0 };
            int j = 0;

            for (final int k = aint2[b1] + b4; j != k; j += b4)
            {
                aint3[b1] = MathHelper.floor_double(par1ArrayOfInteger[b1] + j + 0.5D);
                aint3[b2] = MathHelper.floor_double(par1ArrayOfInteger[b2] + j * d0 + 0.5D);
                aint3[b3] = MathHelper.floor_double(par1ArrayOfInteger[b3] + j * d1 + 0.5D);
                byte b5 = 0;
                final int l = Math.abs(aint3[0] - par1ArrayOfInteger[0]);
                final int i1 = Math.abs(aint3[2] - par1ArrayOfInteger[2]);
                final int j1 = Math.max(l, i1);

                if (j1 > 0)
                {
                    if (l == j1)
                    {
                        b5 = 4;
                    }
                    else if (i1 == j1)
                    {
                        b5 = 8;
                    }
                }

                placeWood(world, aint3[0], aint3[1], aint3[2], b5);
            }
        }
    }

    @Override
    public void setScale(final double par1, final double par3, final double par5)
    {
        heightLimitLimit = (int) (par1 * 12.0D);

        if (par1 > 0.5D)
        {
            leafDistanceLimit = 5;
        }

        scaleWidth = par3;
        leafDensity = par5;
    }

    private boolean validTreeLocation()
    {
        final int[] startCoords = new int[] { basePos[0], basePos[1], basePos[2] };
        final int[] endCoords = new int[] { basePos[0], basePos[1] + heightLimit - 1, basePos[2] };

        final int soilId = world.getBlockId(basePos[0], basePos[1] - 1, basePos[2]);
        final Block soil = Block.blocksList[soilId];
        if (!isSoil(world, basePos[0], basePos[1], basePos[2], soil)) return false;

        final int newHeight = checkBlockLine(startCoords, endCoords);

        if (newHeight == -1)
            return true;
        else if (newHeight < 6)
            return false;
        else
        {
            heightLimit = newHeight;
            return true;
        }
    }
}
