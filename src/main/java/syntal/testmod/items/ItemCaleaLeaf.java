package syntal.testmod.items;

import net.minecraft.util.ResourceLocation;
import syntal.testmod.TestMod;
import syntal.testmod.utils.GenericItem;

public class ItemCaleaLeaf extends GenericItem
{
    public static final String NAME = "calea_leaf";
    public static final ResourceLocation R_NAME = new ResourceLocation(TestMod.MODID, NAME);

    public ItemCaleaLeaf() {
        super(NAME, R_NAME);
    }
}
