package syntal.testmod.furnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import syntal.testmod.TestMod;

public class GuiFastFurnace extends GuiContainer
{
    public static final int WIDTH = 176;
    public static final int HEIGHT = 155;

    private static final ResourceLocation background = new ResourceLocation(TestMod.MODID, "textures/gui/fast_furnace.png");

    public GuiFastFurnace(TileFastFurnace tileEntity, ContainerFastFurnace container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0,0, xSize, ySize);
    }
}
