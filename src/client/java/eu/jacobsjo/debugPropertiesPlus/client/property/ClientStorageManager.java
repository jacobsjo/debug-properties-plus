package eu.jacobsjo.debugPropertiesPlus.client.property;

import eu.jacobsjo.debugPropertiesPlus.client.property.storage.RemoteServerStorage;
import eu.jacobsjo.debugPropertiesPlus.networking.ClientboundDebugPropertyPayload;
import eu.jacobsjo.debugPropertiesPlus.networking.DebugPropertyUpdatePayload;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import eu.jacobsjo.debugPropertiesPlus.property.storage.ConfigStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.WorldStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.NewWorldStorage;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

public class ClientStorageManager {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static @Nullable RemoteServerStorage remoteServerStorage;

    private static DebugPropertyStorage getStorage(DebugProperty<?> property){
        if (property.config.onDedicatedServer() && minecraft.player != null && !minecraft.isLocalServer() && remoteServerStorage != null){
            return remoteServerStorage;
        }

        if (property.config.perWorld()) {
            MinecraftServer server = minecraft.getSingleplayerServer();
            if (server != null){
                return WorldStorage.getStorage(server);
            } else if (NewWorldStorage.getNewWorldStorage() != null){
                return NewWorldStorage.getNewWorldStorage();
            }
        }
        return ConfigStorage.getInstance();
    }

    public static <T> T get(DebugProperty<T> property) {
        DebugPropertyStorage storage = getStorage(property);
        return storage.get(property);
    }

    public static <T> void set(DebugProperty<T> property, T value) {
        DebugPropertyStorage storage = getStorage(property);
        if (storage.get(property) != value) {
            storage.set(property, value);
        }

        // if not setting on a remove server, update local property
        if (storage != remoteServerStorage){
            property.set(value);
        }

        // update debug renderers if necessary
        if (property.config.updateDebugRenderer()){
            minecraft.debugEntries.rebuildCurrentList();
        }

        // on lan, send update to everyone else
        MinecraftServer server = minecraft.getSingleplayerServer();
        if (server != null && !server.isSingleplayer()){
            DebugPropertyUpdatePayload<T> payload = new DebugPropertyUpdatePayload<>(property, value);

            PlayerLookup.all(server).stream()
                    .filter(player -> !player.isLocalPlayer())
                    .forEach(player -> ServerPlayNetworking.send(player, payload) );
        }
    }

    public static void handlePayload(ClientboundDebugPropertyPayload payload, @SuppressWarnings("unused") ClientConfigurationNetworking.Context context){
        remoteServerStorage = new RemoteServerStorage(payload);
    }

    public static <T> void handlePayload(DebugPropertyUpdatePayload<T> payload, @SuppressWarnings("unused") ClientPlayNetworking.Context context){
        if (remoteServerStorage == null){
            throw new IllegalStateException("Trying to update update property before setting");
        }
        remoteServerStorage.handlePayload(payload);
    }
}
