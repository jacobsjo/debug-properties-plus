package eu.jacobsjo.debugPropertiesPlus.client;

import com.mojang.blaze3d.platform.InputConstants;
import eu.jacobsjo.debugPropertiesPlus.client.property.ClientStorageManager;
import eu.jacobsjo.debugPropertiesPlus.networking.ClientboundDebugPropertyPayload;
import eu.jacobsjo.debugPropertiesPlus.networking.DebugPropertyUpdatePayload;
import eu.jacobsjo.debugPropertiesPlus.property.storage.NewWorldStorage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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

        ClientPlayNetworking.registerGlobalReceiver(DebugPropertyUpdatePayload.ID, ClientStorageManager::handlePayload);
        ClientConfigurationNetworking.registerGlobalReceiver(ClientboundDebugPropertyPayload.ID, ClientStorageManager::handlePayload);
    }
}
