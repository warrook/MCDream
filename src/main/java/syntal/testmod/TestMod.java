package syntal.testmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import syntal.testmod.proxy.CommonProxy;

@Mod(modid = TestMod.MODID, name = TestMod.MODNAME, version = TestMod.MODVERSION, dependencies = "required-after:forge@[14.23.5.2768,)", useMetadata = true)
public class TestMod
{
    public static final String MODID = "testmod";
    public static final String MODNAME = "Test Mod";
    public static final String MODVERSION = "0.0.1";

    @SidedProxy(clientSide = "syntal.testmod.proxy.ClientProxy", serverSide = "syntal.testmod.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static CreativeTabs creativeTab = new CreativeTabs(TestMod.MODID)
    {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.blockFastFurnace);
        }
    };

    @Mod.Instance
    public static TestMod instance;

    public static Logger LOGGER;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}
