package syntal.testmod.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class GenericContainer extends Container
{
    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
