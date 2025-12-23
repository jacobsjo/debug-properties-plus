package eu.jacobsjo.debugPropertiesPlus.client;

import com.mojang.blaze3d.platform.InputConstants;
import eu.jacobsjo.debugPropertiesPlus.client.property.storage.DebugPropertyClientStorage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class DebugPropertiesPlusClient implements ClientModInitializer {
    Minecraft minecraft = Minecraft.getInstance();

    public static KeyMapping screenOpenKey;

    @Override
    public void onInitializeClient() {
        DebugPropertyClientStorage.bootstrap();

        screenOpenKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "eu.jacobsjo.debugPropertiesPlus.key.openScreen",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                KeyMapping.Category.DEBUG
        ));
    }
}
