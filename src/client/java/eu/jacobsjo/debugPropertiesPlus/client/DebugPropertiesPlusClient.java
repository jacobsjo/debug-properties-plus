package eu.jacobsjo.debugPropertiesPlus.client;

import com.mojang.blaze3d.platform.InputConstants;
import eu.jacobsjo.debugPropertiesPlus.property.storage.NewWorldStorage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.client.KeyMapping;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class DebugPropertiesPlusClient implements ClientModInitializer {
    public static @Nullable KeyMapping screenOpenKey;

    @Override
    public void onInitializeClient() {
        screenOpenKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "eu.jacobsjo.debugPropertiesPlus.key.openScreen",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                KeyMapping.Category.DEBUG
        ));

        ServerWorldEvents.LOAD.register((server, level) -> NewWorldStorage.onWorldLoad(server));

    }
}
