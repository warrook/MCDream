package syntal.testmod.proxy;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import syntal.testmod.TestMod;
import syntal.testmod.gui.ContainerMortarAndPestle;
import syntal.testmod.gui.GuiMortarAndPestle;
import syntal.testmod.gui.ContainerFastFurnace;
import syntal.testmod.gui.GuiFastFurnace;
import syntal.testmod.tileentities.TileFastFurnace;
import syntal.testmod.utils.GenericContainer;
import syntal.testmod.utils.GenericTileEntity;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        GenericTileEntity te = null;
        if (GuiTypes.values()[id].checkTileEntity)
            te = (GenericTileEntity) world.getTileEntity(new BlockPos(x,y,z));

        //TestMod.LOGGER.info("Picking container with ID: " + id);
        switch(GuiTypes.values()[id])
        {
            case FAST_FURNACE:
                return new ContainerFastFurnace(player.inventory, te);
            case MORTAR_AND_PESTLE:
                return new ContainerMortarAndPestle(player.inventory, player.getHeldItemMainhand());
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        GenericTileEntity te = null;
        if (GuiTypes.values()[id].checkTileEntity)
            te = (GenericTileEntity) world.getTileEntity(new BlockPos(x,y,z));

        //TestMod.LOGGER.info("Picking gui with ID: " + id);
        switch(GuiTypes.values()[id])
        {
            case FAST_FURNACE:
                return new GuiFastFurnace(new ContainerFastFurnace(player.inventory, te), te);
            case MORTAR_AND_PESTLE:
                return new GuiMortarAndPestle(new ContainerMortarAndPestle(player.inventory, player.getHeldItemMainhand())); // No security check here might be bad
            default:
                return null;
        }
    }

    private Gui chooseGui(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        GenericTileEntity te = null;
        if (GuiTypes.values()[id].checkTileEntity)
            te = (GenericTileEntity) world.getTileEntity(new BlockPos(x,y,z));

        TestMod.LOGGER.info("Picking gui with ID: " + id);
        switch(GuiTypes.values()[id])
        {
            case FAST_FURNACE:
                return new GuiFastFurnace(new ContainerFastFurnace(player.inventory, te), te);
            case MORTAR_AND_PESTLE:
                return new GuiMortarAndPestle(new ContainerMortarAndPestle(player.inventory, player.getHeldItemMainhand()));
            default:
                return null;
        }
    }

    public enum GuiTypes {
        FAST_FURNACE,
        MORTAR_AND_PESTLE;

        public final boolean checkTileEntity;

        GuiTypes() {
            this(true);
        }

        GuiTypes(boolean checkTileEntity) {
            this.checkTileEntity = checkTileEntity;
        }
    }
}
