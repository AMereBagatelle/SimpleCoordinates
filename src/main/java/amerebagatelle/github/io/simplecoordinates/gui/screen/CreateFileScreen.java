package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinateFileListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.io.IOException;

public class CreateFileScreen extends Screen {
    private TextFieldWidget nameWidget;
    private final CoordinatesScreen parent;

    public CreateFileScreen(CoordinatesScreen parent) {
        super(new LiteralText("CoordinateFileScreen"));
        this.parent = parent;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        this.nameWidget = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 - 100, 200, 20, new LiteralText(""));
        this.nameWidget.setText("Untitled");
        this.children.add(nameWidget);
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 2, 200, 20, new LiteralText("Create"), onPress -> {
            try {
                SimpleCoordinates.coordinatesManager.initNewCoordinatesFile(CoordinateFileListWidget.workingDirectory.toString() + "/" + this.nameWidget.getText() + ".coordinates");
            } catch (IOException ignored) {
            } finally {
                this.client.openScreen(parent);
            }
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 2 + 30, 200, 20, new LiteralText("Cancel"), onPress -> {
            this.client.openScreen(parent);
        }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, delta);
        this.drawCenteredString(matrixStack, this.textRenderer, "File Creation", this.width / 2, 20, 16777215);
        nameWidget.render(matrixStack, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        client.openScreen(this.parent);
    }
}
