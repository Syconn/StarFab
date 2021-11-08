package mod.syconn.stf.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.syconn.stf.StarMain;
import mod.syconn.stf.client.screens.handler.WorkstationScreenHandler;
import mod.syconn.stf.items.Hilts;
import mod.syconn.stf.items.KyberCrystal;
import mod.syconn.stf.items.lightsaber.Lightsaber;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WorkstationScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(StarMain.ID, "textures/gui/container/workstation.png");
    private static final Identifier ICONS = new Identifier(StarMain.ID, "textures/gui/container/icons.png");
    private final PlayerEntity player;
    private int xSize = 176, ySize = 166;

    public WorkstationScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.player = inventory.player;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        x = (x + (backgroundWidth / 2)) - 9;
        y = (y + (backgroundWidth / 2)) - 54;

        if (handler instanceof WorkstationScreenHandler) {
            WorkstationScreenHandler handler = (WorkstationScreenHandler) this.handler;

            if (!(handler.getSlot(0).getStack().copy().getItem() instanceof Hilts)) {
                renderIcon(matrices, x - 52, y + 1, 1, 0);
            }
            if (!(handler.getSlot(1).getStack().copy().getItem() instanceof Lightsaber)) {
                renderIcon(matrices, x, y, 0, 0);
            }
            if (!(handler.getSlot(2).getStack().copy().getItem() instanceof KyberCrystal)) {
                renderIcon(matrices, x + 54, y + 1, 2, 0);
            }
        }
    }

    private void renderIcon(MatrixStack matrices, int x, int y, int xFactor, int yFactor){
        RenderSystem.setShaderTexture(0, ICONS);
        drawTexture(matrices, x, y, (16 * xFactor) + xFactor, (16 * yFactor) + yFactor, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
