package eu.jacobsjo.debugPropertiesPlus.client;

import eu.jacobsjo.debugPropertiesPlus.client.property.storage.DebugPropertyClientStorage;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class DebugPropertiesPlusClient implements ClientModInitializer {

    Minecraft minecraft = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        DebugPropertyClientStorage.bootstrap();
    }
}
