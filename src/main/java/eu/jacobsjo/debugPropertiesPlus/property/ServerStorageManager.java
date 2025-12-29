package eu.jacobsjo.debugPropertiesPlus.property;

import eu.jacobsjo.debugPropertiesPlus.DebugPropertiesPlus;
import eu.jacobsjo.debugPropertiesPlus.networking.ClientboundDebugPropertyPayload;
import eu.jacobsjo.debugPropertiesPlus.networking.DebugPropertyUpdatePayload;
import eu.jacobsjo.debugPropertiesPlus.property.storage.ConfigStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyValueMap;
import eu.jacobsjo.debugPropertiesPlus.property.storage.WorldStorage;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.permissions.Permissions;

public class ServerStorageManager {
    private final MinecraftServer server;

    public ServerStorageManager(MinecraftServer server){
        this.server = server;
        WorldStorage.getStorage(server).updateLocalDebugProperties();
    }

    public <T> T get(DebugProperty<T> property) {
        if (property.config.perWorld()) {
            return WorldStorage.getStorage(server).get(property);
        }
        return ConfigStorage.getInstance().get(property);
    }

    public <T> void set(DebugProperty<T> property, T value) {
        if (!this.server.isDedicatedServer() && !property.config.perWorld()){
            throw new IllegalStateException("Trying to set not-per-world debug parameter on integrated server");
        }

        if (property.config.perWorld()) {
            WorldStorage.getStorage(server).set(property, value);
        }

        if (this.server.isDedicatedServer()){
            // only set non-per-world parameters on dedicated server; but then also save per-world parameters in config
            // storage to allow resetting worlds
            ConfigStorage.getInstance().set(property, value);
        }

        // update local property
        property.set(value);

        // send update payload
        DebugPropertyUpdatePayload<T> payload = new DebugPropertyUpdatePayload<>(property, value);
        PlayerLookup.all(this.server).stream()
                .filter(player -> !player.isLocalPlayer())
                .forEach(player -> ServerPlayNetworking.send(player, payload) );
    }

    public ClientboundDebugPropertyPayload createClientboundPayload(){
        DebugPropertyValueMap valueMap = new DebugPropertyValueMap(p -> p.config.onDedicatedServer());
        DebugProperty.PROPERTIES.values().stream().filter(p -> p.config.onDedicatedServer()).forEach(p -> this.setValueMapFromManager(valueMap, p));
        return new ClientboundDebugPropertyPayload(valueMap);
    }

    private <T> void setValueMapFromManager(DebugPropertyValueMap valueMap, DebugProperty<T> debugProperty){
        valueMap.set(debugProperty, get(debugProperty));
    }

    public <T> void handleUpdatePayload(DebugPropertyUpdatePayload<T> payload, ServerPlayNetworking.Context context){
        if (!context.server().isDedicatedServer() && !payload.property().config.perWorld()){
            DebugPropertiesPlus.LOGGER.warn("Received non-per-world debug properties update payload on integrated server; ignoring. Source: {}", context.player().nameAndId());
            context.responseSender().disconnect(Component.literal("Illagal payload: Trying to set non-per-world property on LAN server"));
            return;
        }
        if (!context.player().permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)){
            DebugPropertiesPlus.LOGGER.warn("Received debug properties update from non-op player {}; ignoring.", context.player().nameAndId());
            context.responseSender().disconnect(Component.literal("Illagal payload: Trying to set property with insufficient permissions."));
            return;
        }
        set(payload.property(), payload.newValue());
    }
}
