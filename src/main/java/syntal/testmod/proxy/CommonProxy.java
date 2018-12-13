package syntal.testmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
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
import syntal.testmod.blocks.BlockCalea;
import syntal.testmod.items.ItemCaleaLeaf;
import syntal.testmod.blocks.BlockFastFurnace;
import syntal.testmod.items.ItemMortarAndPestle;
import syntal.testmod.network.Messages;
import syntal.testmod.tileentities.TileFastFurnace;
import syntal.testmod.blocks.BlockGenerator;
import syntal.testmod.tileentities.TileGenerator;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e) {
        Messages.registerMessages(TestMod.MODID);
    }
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(TestMod.instance, new GuiHandler());
    }
    public void postInit(FMLPostInitializationEvent e) {}

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e)
    {
        e.getRegistry().register(new BlockFastFurnace());
        e.getRegistry().register(new BlockGenerator());

        GameRegistry.registerTileEntity(TileFastFurnace.class, BlockFastFurnace.R_NAME);
        GameRegistry.registerTileEntity(TileGenerator.class, BlockGenerator.R_NAME);

        e.getRegistry().register(new BlockCalea());
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e)
    {
        e.getRegistry().register(new ItemBlock(ModBlocks.blockFastFurnace).setRegistryName(BlockFastFurnace.R_NAME));
        e.getRegistry().register(new ItemBlock(ModBlocks.blockGenerator).setRegistryName(BlockGenerator.R_NAME));

        e.getRegistry().register(new ItemBlock(ModBlocks.blockCalea).setRegistryName(BlockCalea.R_NAME));

        e.getRegistry().register(new ItemCaleaLeaf());
        e.getRegistry().register(new ItemMortarAndPestle());
    }

    // ------------------------------------------------------------------------------------------------

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from the client side");
    }

    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from the client side");
    }
}
