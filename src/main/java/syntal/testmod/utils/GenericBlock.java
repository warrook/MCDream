package syntal.testmod.utils;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import syntal.testmod.TestMod;

public class GenericBlock extends Block
{
    public GenericBlock(Material materialIn, String name, ResourceLocation r_name) {
        super(materialIn);
        setRegistryName(r_name);
        setUnlocalizedName(TestMod.MODID + "." + name);
        setCreativeTab(TestMod.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel()
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
