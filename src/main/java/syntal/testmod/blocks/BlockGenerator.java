package syntal.testmod.blocks;

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
import syntal.testmod.tileentities.TileGenerator;
import syntal.testmod.utils.GenericBlock;

import javax.annotation.Nullable;

public class BlockGenerator extends GenericBlock implements ITileEntityProvider
{
    public static final String NAME = "generator";
    public static final ResourceLocation R_NAME = new ResourceLocation(TestMod.MODID, NAME);

    public BlockGenerator() {
        super(Material.GLASS, NAME, R_NAME);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileGenerator();
    }
}
