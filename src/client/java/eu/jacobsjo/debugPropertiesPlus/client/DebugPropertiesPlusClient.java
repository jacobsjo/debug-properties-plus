package eu.jacobsjo.debugPropertiesPlus.client;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class DebugPropertiesPlusClient implements ClientModInitializer {

    Minecraft minecraft = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        DebugProperty.onChange(debugProperty -> {
            if (debugProperty.config.updateDebugRenderer()){
                minecraft.debugEntries.rebuildCurrentList();
            }
        });
    }
}
