package syntal.testmod.furnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFastFurnace extends Container
{
    private TileFastFurnace te;

    public ContainerFastFurnace(IInventory playerInventory, TileFastFurnace te) {
        this.te = te;
        addTESlots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory)
    {
        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = 8 + col * 18;
                int y = row * 18 + 73;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Player hotbar
        for (int row = 0; row < 9; row++) {
            int x = 8 + row * 18;
            int y = 58 + 73;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addTESlots()
    {
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 8;
        int y = 28;

        int slotIndex = 0;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        x = 116;

        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack trystack = slot.getStack();
            itemStack = trystack.copy();

            if (index < TileFastFurnace.SIZE) // If slots inside the furnace are selected
            {
                if (!this.mergeItemStack(trystack, TileFastFurnace.SIZE, this.inventorySlots.size(), true)) // Clicked stack and go into player inventory
                    return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(trystack, 0, TileFastFurnace.SIZE, false)) // Clicked stack can go into furnace inventory
                return ItemStack.EMPTY;

            if (trystack.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemStack;

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.CanInteractWith(playerIn);
    }
}
