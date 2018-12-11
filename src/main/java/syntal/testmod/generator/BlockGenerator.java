package syntal.testmod.generator;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import syntal.testmod.TestMod;

import javax.annotation.Nullable;

public class BlockGenerator extends Block implements ITileEntityProvider
{
    public static final String NAME = "generator";
    public static final ResourceLocation GENERATOR = new ResourceLocation(TestMod.MODID, NAME);

    public BlockGenerator() {
        super(Material.GLASS);
        setRegistryName(GENERATOR);
        setUnlocalizedName(TestMod.MODID + "." + NAME);
        //setHarvestLevel("pickaxe", 0); //iron
        setCreativeTab(TestMod.creativeTab);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileGenerator();
    }

    @SideOnly(Side.CLIENT)
    public void initModel()
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
