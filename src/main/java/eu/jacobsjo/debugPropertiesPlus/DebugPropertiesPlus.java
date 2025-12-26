package eu.jacobsjo.debugPropertiesPlus;

import eu.jacobsjo.debugPropertiesPlus.command.DebugPropertyCommand;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyConfigStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyServerStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyWorldStorage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.SharedConstants;
import org.jspecify.annotations.Nullable;

public class DebugPropertiesPlus implements ModInitializer {
    public static @Nullable DebugPropertyServerStorage serverStorage;

    @Override
    public void onInitialize() {
        SharedConstants.DEBUG_ENABLED = true;
        DebugProperty.bootstrap();
        DebugPropertyConfigStorage.getInstance();

        // initalize per world storage, so it sets the debug properties
        ServerWorldEvents.LOAD.register((server, level) -> {
            if (server.isDedicatedServer()) {
                DebugPropertyWorldStorage.getStorage(server);
            }

            serverStorage = new DebugPropertyServerStorage(server);
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            DebugPropertyCommand.register(dispatcher, environment);
        });
    }
}
