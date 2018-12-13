package syntal.testmod;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import syntal.testmod.items.ItemCaleaLeaf;
import syntal.testmod.items.ItemMortarAndPestle;

public class ModItems
{
    @GameRegistry.ObjectHolder(TestMod.MODID + ":" + ItemCaleaLeaf.NAME)
    public static ItemCaleaLeaf itemCaleaLeaf;
    @GameRegistry.ObjectHolder(TestMod.MODID + ":" + ItemMortarAndPestle.NAME)
    public static ItemMortarAndPestle itemMortarAndPestle;

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        itemCaleaLeaf.initModel();
        itemMortarAndPestle.initModel();
    }
}
