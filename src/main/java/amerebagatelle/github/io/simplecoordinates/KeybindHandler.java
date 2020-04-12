package amerebagatelle.github.io.simplecoordinates;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.ArrayList;

public class KeybindHandler {
    private static FabricKeyBinding coordinatesMenu;
    private static boolean coordinatesMenuWasPressed;

    public static void initKeybinds() {

        KeyBindingRegistry.INSTANCE.addCategory("simplecoordinates");

        coordinatesMenu = FabricKeyBinding.Builder.create(
                new Identifier("coordinatesmenukeybind"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "simplecoordinates"
        ).build();
        KeyBindingRegistry.INSTANCE.register(coordinatesMenu);
    }

    public static void registerKeybindActions() {
        ClientTickCallback.EVENT.register(keypress -> {
            if(coordinatesMenu.isPressed() && !coordinatesMenuWasPressed) {
                try {
                    ArrayList<ArrayList<String>> coordinates = CoordinatesManager.loadCoordinates();
                    if(!coordinates.isEmpty()) {
                        coordinates.forEach(coordinate -> keypress.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(coordinate.toString())));
                    } else {
                        keypress.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("No coordinates to display."));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            coordinatesMenuWasPressed = coordinatesMenu.isPressed();
        });
    }
}
