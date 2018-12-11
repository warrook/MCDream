package syntal.testmod;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import syntal.testmod.furnace.BlockFastFurnace;
import syntal.testmod.generator.BlockGenerator;

/*
    Basic blocks require these pieces (make sure you do them or it won't work):
    - Class, where registry name and unlocalized names are declared
    - Blockstate .json, where model and variants and their textures are assigned
        - Textures, if custom; model too
    - Instance and model initialization, in this file
    - Block and ItemBlock registration in CommonProxy
    - Localization
 */


public class ModBlocks
{
    @GameRegistry.ObjectHolder(TestMod.MODID + ":" + BlockFastFurnace.NAME)
    public static BlockFastFurnace blockFastFurnace;
    @GameRegistry.ObjectHolder(TestMod.MODID + ":" + BlockGenerator.NAME)
    public static BlockGenerator blockGenerator;

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        blockFastFurnace.initModel();
        blockGenerator.initModel();
    }

}
