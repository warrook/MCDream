package syntal.testmod.furnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import scala.tools.nsc.interpreter.Power;
import syntal.testmod.tools.PowerStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileFastFurnace extends TileEntity implements ITickable
{
    public static final int INPUT_SLOTS = 3;
    public static final int OUTPUT_SLOTS = 3;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_PROGRESS = 40;
    public static final int MAX_POWER = 10000;
    public static final int POWER_IN_PER_TICK = 120;
    public static final int POWER_PER_TICK = 20;

    private int progress = 0;
    private int clientProgress = -1;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getClientProgress() {
        return clientProgress;
    }

    public void setClientProgress(int clientProgress) {
        this.clientProgress = clientProgress;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (powerStorage.getEnergyStored() < POWER_PER_TICK)
                return;

            if (progress > 0)
            {
                powerStorage.consumeEnergy(POWER_PER_TICK);
                progress--;
                if (progress <= 0)
                    attemptSmelt();
                markDirty();
            } else
                startSmelt();
        }
    }

    private boolean tryOutput(ItemStack output, boolean simulate) {
        for (int i = 0; i < OUTPUT_SLOTS; i++) {
            ItemStack remaining = outputHandler.insertItem(i, output, simulate);
            if (remaining.isEmpty())
                return true;
        }
        return false;
    }

    private void startSmelt() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                if (tryOutput(result.copy(), true))
                {
                    progress = MAX_PROGRESS;
                    markDirty();
                }
                break;
            }
        }
    }

    private void attemptSmelt() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty())
            {
                if (tryOutput(result.copy(), false))
                {
                    inputHandler.extractItem(i, 1, false);
                    break;
                }
            }
        }
    }

    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS)
    {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
            return !result.isEmpty();
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileFastFurnace.this.markDirty();
        }
    };

    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS)
    {
        @Override
        protected void onContentsChanged(int slot) {
            TileFastFurnace.this.markDirty();
        }
    };

    // ------------------------------------------------------------------------------------------------

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    private PowerStorage powerStorage = new PowerStorage(MAX_POWER, POWER_IN_PER_TICK);

    // ------------------------------------------------------------------------------------------------

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn"))
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        if (compound.hasKey("itemsOut"))
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        progress = compound.getInteger("progress");
        powerStorage.setEnergy(compound.getInteger("power"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("progress", progress);
        compound.setInteger("power", powerStorage.getEnergyStored());
        return compound;
    }

    // ------------------------------------------------------------------------------------------------

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityEnergy.ENERGY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) //Internal use face
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            else if (facing == EnumFacing.UP)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            else
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
        }
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(powerStorage);
        return super.getCapability(capability, facing);
    }

    public boolean CanInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}
