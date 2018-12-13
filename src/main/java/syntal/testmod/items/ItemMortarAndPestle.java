package syntal.testmod.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import syntal.testmod.ModItems;
import syntal.testmod.TestMod;
import syntal.testmod.gui.ContainerMortarAndPestle;
import syntal.testmod.proxy.GuiHandler;
import syntal.testmod.utils.GenericItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class ItemMortarAndPestle extends GenericItem
{
    public static final String NAME = "mortar_and_pestle";
    public static final ResourceLocation R_NAME = new ResourceLocation(TestMod.MODID, NAME);

    private static final int INPUT_SLOTS = 4;
    private static final int OUTPUT_SLOTS = 1;
    private static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public ItemMortarAndPestle()
    {
        super(NAME, R_NAME);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            BlockPos pos = playerIn.getPosition();
            playerIn.openGui(TestMod.instance, GuiHandler.GuiTypes.MORTAR_AND_PESTLE.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilitiesMortarAndPestle();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public class CapabilitiesMortarAndPestle implements ICapabilityProvider
    {
        private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS);
        private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS)
        {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return false;
            }
        };
        private CombinedInvWrapper combinedWrapper = new CombinedInvWrapper(outputHandler, inputHandler);

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return facing == null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (hasCapability(capability, facing)) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedWrapper);
            }
            return null;
        }
    }
}
