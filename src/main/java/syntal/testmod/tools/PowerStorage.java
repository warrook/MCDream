package syntal.testmod.tools;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.EnergyStorage;

public class PowerStorage extends EnergyStorage
{
    public PowerStorage(int capacity, int maxReceive) {
        super(capacity, maxReceive, 0);
    }

    public PowerStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public void setEnergy(int energy)
    {
        this.energy = energy;
    }

    public void consumeEnergy(int energy)
    {
        this.energy -= energy;
        if (this.energy < 0)
            this.energy = 0;
    }
}
