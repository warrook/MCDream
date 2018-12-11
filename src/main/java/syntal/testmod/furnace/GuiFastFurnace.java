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
    private TileFastFurnace furnace;

    public GuiFastFurnace(TileFastFurnace tileEntity, ContainerFastFurnace container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        furnace = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0,0, xSize, ySize);

        if (furnace.getClientProgress() > 0)
        {
            int percentage = 100 - furnace.getClientProgress() * 100 / TileFastFurnace.MAX_PROGRESS;
            drawString(mc.fontRenderer, "Progress: " + percentage + "%", guiLeft + 10, guiTop + 50, 0xffffff);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
