package mod.syconn.stf.client.screens.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.syconn.stf.StarMain;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TabButton extends ButtonWidget {

    private static final Identifier TEXTURE = new Identifier(StarMain.ID, "textures/gui/tabs.png");

    public TabButton(int x, int y, Text message, PressAction onPress) {
        super(x, y, 20, 20, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //if ()
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x, y, 0, 0, 28, 30);
    }
}
