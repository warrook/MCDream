package syntal.testmod.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import syntal.testmod.ModBlocks;
import syntal.testmod.TestMod;
import syntal.testmod.furnace.BlockFastFurnace;
import syntal.testmod.furnace.TileFastFurnace;
import syntal.testmod.generator.BlockGenerator;
import syntal.testmod.generator.TileGenerator;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e) {}
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(TestMod.instance, new GuiHandler());
    }
    public void postInit(FMLPostInitializationEvent e) {}

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e)
    {
        e.getRegistry().register(new BlockFastFurnace());
        e.getRegistry().register(new BlockGenerator());

        GameRegistry.registerTileEntity(TileFastFurnace.class, TestMod.MODID + ":" + BlockFastFurnace.NAME);
        GameRegistry.registerTileEntity(TileGenerator.class, TestMod.MODID + ":" + BlockGenerator.NAME);
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e)
    {
        e.getRegistry().register(new ItemBlock(ModBlocks.blockFastFurnace).setRegistryName(BlockFastFurnace.FAST_FURNACE));
        e.getRegistry().register(new ItemBlock(ModBlocks.blockGenerator).setRegistryName(BlockGenerator.GENERATOR));
    }
}
