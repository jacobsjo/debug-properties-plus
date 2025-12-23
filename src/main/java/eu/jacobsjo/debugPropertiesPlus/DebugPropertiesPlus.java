package eu.jacobsjo.debugPropertiesPlus;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyWorldStorage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.SharedConstants;

public class DebugPropertiesPlus implements ModInitializer {

    @Override
    public void onInitialize() {
        SharedConstants.DEBUG_ENABLED = true;
        DebugProperty.bootstrap();

        // initalize per world storage, so it sets the debug properties
        ServerWorldEvents.LOAD.register((server, level) -> DebugPropertyWorldStorage.getStorage(server));
    }
}
