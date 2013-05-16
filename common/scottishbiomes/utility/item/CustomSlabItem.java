
package scottishbiomes.utility.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import scottishbiomes.utility.block.CustomSlab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomSlabItem extends ItemBlock
{
    private final boolean isFullBlock;
    private final BlockHalfSlab halfSlab;
    private final BlockHalfSlab fullSlab;

    public CustomSlabItem(final int id)
    {
        super(id);
        final int shiftedID = id + 256;
        final CustomSlab block = (CustomSlab) Block.blocksList[shiftedID];
        halfSlab = block.getHalfSlab();
        fullSlab = block.getFullSlab();
        isFullBlock = shiftedID == fullSlab.blockID;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canPlaceItemBlockOnSide(final World world, int x, int y, int z, final int side,
            final EntityPlayer player, final ItemStack itemStack)
    {
        final int startX = x;
        final int startY = y;
        final int startZ = z;
        int id = world.getBlockId(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        int type = metadata & 7;
        boolean isPlacedAtTop = (metadata & 8) != 0;

        if ((side == 1 && !isPlacedAtTop || side == 0 && isPlacedAtTop) && id == halfSlab.blockID
                && type == itemStack.getItemDamage())
            return true;
        else
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }

            id = world.getBlockId(x, y, z);
            metadata = world.getBlockMetadata(x, y, z);
            type = metadata & 7;
            isPlacedAtTop = (metadata & 8) != 0;
            return id == halfSlab.blockID && type == itemStack.getItemDamage() ? true
                    : super.canPlaceItemBlockOnSide(world, startX, startY, startZ, side, player,
                            itemStack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(final int damage)
    {
        return Block.blocksList[itemID].getIcon(2, damage);
    }

    @Override
    public int getMetadata(final int metadata)
    {
        return metadata;
    }

    @Override
    public String getUnlocalizedName(final ItemStack item)
    {
        return halfSlab.getFullSlabName(item.getItemDamage());
    }

    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer player,
            final World world, final int x, final int y, final int z, final int side,
            final float vecX, final float vecY, final float vecZ)
    {
        if (isFullBlock)
            return super.onItemUse(itemStack, player, world, x, y, z, side, vecX, vecY, vecZ);
        else if (itemStack.stackSize == 0)
            return false;
        else if (!player.canPlayerEdit(x, y, z, side, itemStack))
            return false;
        else
        {
            final int id = world.getBlockId(x, y, z);
            final int metadata = world.getBlockMetadata(x, y, z);
            final int type = metadata & 7;
            final boolean isPlacedAtTop = (metadata & 8) != 0;

            if ((side == 1 && !isPlacedAtTop || side == 0 && isPlacedAtTop)
                    && id == halfSlab.blockID && type == itemStack.getItemDamage())
            {
                if (world.checkNoEntityCollision(fullSlab.getCollisionBoundingBoxFromPool(world, x,
                        y, z)) && world.setBlock(x, y, z, fullSlab.blockID, type, 3))
                {
                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F,
                            fullSlab.stepSound.getPlaceSound(),
                            (fullSlab.stepSound.getVolume() + 1.0F) / 2.0F,
                            fullSlab.stepSound.getPitch() * 0.8F);
                    --itemStack.stackSize;
                }

                return true;
            }
            else
                return place(itemStack, player, world, x, y, z, side) ? true : super.onItemUse(
                        itemStack, player, world, x, y, z, side, vecX, vecY, vecZ);
        }
    }

    private boolean place(final ItemStack itemStack, final EntityPlayer player, final World world,
            int x, int y, int z, final int side)
    {
        if (side == 0)
        {
            --y;
        }

        if (side == 1)
        {
            ++y;
        }

        if (side == 2)
        {
            --z;
        }

        if (side == 3)
        {
            ++z;
        }

        if (side == 4)
        {
            --x;
        }

        if (side == 5)
        {
            ++x;
        }

        final int id = world.getBlockId(x, y, z);
        final int metadata = world.getBlockMetadata(x, y, z);
        final int type = metadata & 7;

        if (id == halfSlab.blockID && type == itemStack.getItemDamage())
        {
            if (world.checkNoEntityCollision(fullSlab.getCollisionBoundingBoxFromPool(world, x, y,
                    z)) && world.setBlock(x, y, z, fullSlab.blockID, type, 3))
            {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F,
                        fullSlab.stepSound.getPlaceSound(),
                        (fullSlab.stepSound.getVolume() + 1.0F) / 2.0F,
                        fullSlab.stepSound.getPitch() * 0.8F);
                --itemStack.stackSize;
            }

            return true;
        }
        else
            return false;
    }

}
