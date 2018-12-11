package syntal.testmod.generator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import syntal.testmod.tools.PowerStorage;

import javax.annotation.Nullable;

public class TileGenerator extends TileEntity implements ITickable
{
    public static final int MAX_POWER = 10000;
    public static final int POWER_IN_PER_TICK = 120;
    public static final int POWER_OUT_PER_TICK = 40;


    public TileGenerator()
    {

    }

    @Override
    public void update() {
        powerStorage.receiveEnergy(POWER_IN_PER_TICK, false);
        sendEnergy();
    }

    public void sendEnergy()
    {
        if (powerStorage.getEnergyStored() > 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
                {
                    IEnergyStorage handler = tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                    if (handler != null && handler.canReceive())
                    {
                        int accepted = handler.receiveEnergy(powerStorage.getEnergyStored(), false);
                        powerStorage.consumeEnergy(accepted);
                        if (powerStorage.getEnergyStored() <= 0)
                            break;
                    }
                }
            }
            markDirty();
        }
    }

    private PowerStorage powerStorage = new PowerStorage(MAX_POWER, POWER_IN_PER_TICK, POWER_OUT_PER_TICK);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        powerStorage.setEnergy(compound.getInteger("power"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("power", powerStorage.getEnergyStored());
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(powerStorage);
        return super.getCapability(capability, facing);
    }
}
