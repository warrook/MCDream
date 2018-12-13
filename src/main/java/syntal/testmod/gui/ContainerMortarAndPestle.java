package syntal.testmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import syntal.testmod.TestMod;
import syntal.testmod.items.ItemMortarAndPestle;
import syntal.testmod.tileentities.TileFastFurnace;
import syntal.testmod.utils.GenericContainer;

import javax.annotation.Nonnull;

public class ContainerMortarAndPestle extends GenericContainer
{
    private ItemStack tool;
    private final InventoryCrafting craftingMatrix = new InventoryCrafting(this, 2, 2);

    private static final int INPUT_SLOTS = 4;
    private static final int OUTPUT_SLOTS = 1;
    private static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    /*private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS)
    {
        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }
    };
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS)
    {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }

    };
    private CombinedInvWrapper combinedWrapper = new CombinedInvWrapper(inputHandler, outputHandler);*/

    public ContainerMortarAndPestle(IInventory playerInventory, ItemStack tool) {
        //TestMod.LOGGER.info("Setting up mortar and pestle...");

        this.tool = tool;

        addObjectSlots();
        addPlayerSlots(playerInventory);

        //TestMod.LOGGER.info("Done!");
    }

    private void addObjectSlots()
    {
        IItemHandler itemHandler = tool.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 39;
        int y = 26;

        int slotIndex = 0;

        // Output
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, 115, 35));
        slotIndex++;

        // Input
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x + 18 * col, y + 18 * row));
                slotIndex++;
            }
        }
    }

    // These are shifted by 1 for some reason!
    private void addPlayerSlots(IInventory playerInventory)
    {
        int offsetX = 8;
        int offsetY = 84;

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = offsetX + col * 18;
                int y = row * 18 + offsetY;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Player hotbar
        for (int slot = 0; slot < 9; slot++) {
            int x = offsetX + slot * 18;
            int y = 58 + offsetY;
            this.addSlotToContainer(new Slot(playerInventory, slot, x, y));
        }
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack tryStack = slot.getStack();
            itemStack = tryStack.copy();

            if (index == 0) { // Output slot
                tryStack.getItem().onCreated(tryStack, playerIn.world, playerIn);
                if (!this.mergeItemStack(tryStack, SIZE, this.inventorySlots.size(), true)) // If it can go into player inventory
                    return ItemStack.EMPTY;
                slot.onSlotChange(tryStack, itemStack);
            }
            else if (index < SIZE) { // Matrix slot
                if (!this.mergeItemStack(tryStack, SIZE, this.inventorySlots.size(), false)) // Same as previous
                    return ItemStack.EMPTY;
            }
            else { // Player slot
                if (!this.mergeItemStack(tryStack, 1, SIZE, false)) // If it can go into mortar matrix
                    return ItemStack.EMPTY;
            }

            if (tryStack.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemStack;
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemMortarAndPestle;
    }

    /*@Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        TestMod.LOGGER.info("SlotID: " + slotId + " Item: " + tool.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(slotId).getItem());
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }*/

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        this.clearContainer(playerIn, playerIn.world, playerIn.inventory);

    }

    @Override
    protected void clearContainer(EntityPlayer playerIn, World worldIn, IInventory inventoryIn) {
        IItemHandler itemHandler = tool.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if (itemHandler.getSlots() != 0) {
            for (int slot = 1; slot < itemHandler.getSlots(); slot++) {
                ItemStack itemStack = itemHandler.getStackInSlot(slot);
                //ItemStack itemStack1 = itemHandler.getStackInSlot(slot);

                if (!playerIn.isEntityAlive() || playerIn instanceof EntityPlayerMP && ((EntityPlayerMP)playerIn).hasDisconnected()) {
                    playerIn.dropItem(itemStack, false);
                } else {
                    playerIn.inventory.placeItemBackInInventory(worldIn, itemStack);
                }
            }

        }
    }

    /*
    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.slotNumber > 0; // Don't put anything in output
    }*/
}
