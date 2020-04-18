package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinatesWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

import java.io.IOException;
import java.util.ArrayList;

public class CoordinatesScreen extends Screen {
    private MinecraftClient client;
    public CoordinatesWidget coordinatesWidget;
    public ArrayList<String> selectedCoordinates;
    private TextRenderer textRenderer;
    private final int textColor = 16777215;

    public CoordinatesScreen(MinecraftClient client) {
        super(new TranslatableText("coordinates.title"));
        this.client = client;
        this.textRenderer = client.textRenderer;
    }

    @Override
    public void init() {
        super.init();
        ArrayList<ArrayList<String>> coordinatesList;
        try {
            coordinatesList = CoordinatesManager.loadCoordinates();
            coordinatesWidget = new CoordinatesWidget(this, client, coordinatesList);
        } catch (IOException e) {
            SimpleCoordinates.logger.error(I18n.translate("return.simplecoordinates.coordinateloadfail"));
        }
        this.children.add(coordinatesWidget);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        if(coordinatesWidget != null) {
            this.renderBackground();
            this.coordinatesWidget.render(mouseX, mouseY, delta);
            this.drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 20, 16777215);
            if (selectedCoordinates != null) {
                int coordinatesDrawY = this.height / 7 * 6;
                int coordinatesDrawX = 100;
                String x = selectedCoordinates.get(1);
                String y = selectedCoordinates.get(2);
                String z = selectedCoordinates.get(3);
                this.drawString(textRenderer, selectedCoordinates.get(0), coordinatesDrawX, coordinatesDrawY - 20, textColor);
                this.drawString(textRenderer, x, coordinatesDrawX, coordinatesDrawY, textColor);
                this.drawString(textRenderer, y, coordinatesDrawX + textRenderer.getStringWidth(x) + 20, coordinatesDrawY, textColor);
                this.drawString(textRenderer, z, coordinatesDrawX + textRenderer.getStringWidth(x) + textRenderer.getStringWidth(y) + 40, coordinatesDrawY, textColor);
                this.drawString(textRenderer, selectedCoordinates.get(4), coordinatesDrawX, coordinatesDrawY + 20, textColor);
            }
            super.render(mouseX, mouseY, delta);
        } else {
            client.openScreen(null);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void select(CoordinatesWidget.Entry entry, ArrayList<String> selectedCoordinates) {
        this.coordinatesWidget.setSelected(entry);
        this.selectedCoordinates = selectedCoordinates;
    }
}
