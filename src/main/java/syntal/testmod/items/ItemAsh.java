package syntal.testmod.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import syntal.testmod.TestMod;
import syntal.testmod.utils.GenericItem;

// NYI
public class ItemAsh extends GenericItem
{
    public static final String NAME = "ash";
    public static final ResourceLocation R_NAME = new ResourceLocation(TestMod.MODID, NAME);

    public ItemAsh() {
        super(NAME, R_NAME);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
