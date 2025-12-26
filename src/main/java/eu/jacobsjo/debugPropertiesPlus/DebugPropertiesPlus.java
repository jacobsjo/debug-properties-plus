package eu.jacobsjo.debugPropertiesPlus;

import eu.jacobsjo.debugPropertiesPlus.command.DebugPropertyCommand;
import eu.jacobsjo.debugPropertiesPlus.networking.ClientboundDebugPropertyPayload;
import eu.jacobsjo.debugPropertiesPlus.networking.DebugPropertyUpdatePayload;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import eu.jacobsjo.debugPropertiesPlus.property.storage.ConfigStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.ServerStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.WorldStorage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.SharedConstants;
import org.jspecify.annotations.Nullable;

public class DebugPropertiesPlus implements ModInitializer {
    public static @Nullable ServerStorage serverStorage;
    public static String MOD_ID = "debug-properties-plus";

    @Override
    public void onInitialize() {
        SharedConstants.DEBUG_ENABLED = true;
        DebugProperty.bootstrap();
        ConfigStorage.getInstance();

        // initalize per world storage, so it sets the debug properties
        ServerWorldEvents.LOAD.register((server, level) -> {
            if (server.isDedicatedServer()) {
                WorldStorage.getStorage(server);
            }

            serverStorage = new ServerStorage(server);
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> DebugPropertyCommand.register(dispatcher, environment));

        PayloadTypeRegistry.configurationS2C().register(ClientboundDebugPropertyPayload.ID, ClientboundDebugPropertyPayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(DebugPropertyUpdatePayload.ID, DebugPropertyUpdatePayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(DebugPropertyUpdatePayload.ID, DebugPropertyUpdatePayload.STREAM_CODEC);

        ServerConfigurationConnectionEvents.CONFIGURE.register((listener, server) -> {
            if (listener.getOwner() == server.getSingleplayerProfile()) return;
            assert serverStorage != null;
            ServerConfigurationNetworking.send(listener, serverStorage.getPayload());
        });
    }


}
