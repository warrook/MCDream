package syntal.testmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import syntal.testmod.TestMod;

public class BlockAshLine extends Block
{
    public static final String NAME = "ash_line";
    public static final ResourceLocation R_NAME = new ResourceLocation(TestMod.MODID, NAME);

    public BlockAshLine() {
        super(Material.CIRCUITS);
        setRegistryName(R_NAME);
        setUnlocalizedName(TestMod.MODID + "." + NAME);
        setCreativeTab(TestMod.creativeTab);
    }
}
