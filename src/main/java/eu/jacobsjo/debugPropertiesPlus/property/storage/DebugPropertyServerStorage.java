package eu.jacobsjo.debugPropertiesPlus.property.storage;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.server.MinecraftServer;

public class DebugPropertyServerStorage {
    private final MinecraftServer server;

    public DebugPropertyServerStorage(MinecraftServer server){
        this.server = server;
        DebugPropertyWorldStorage.getStorage(server);
    }

    public <T> T get(DebugProperty<T> property) {
        if (property.config.perWorld()) {
            return DebugPropertyWorldStorage.getStorage(server).get(property);
        }
        return DebugPropertyConfigStorage.getInstance().get(property);
    }

    public <T> void set(DebugProperty<T> property, T value) {
        if (!this.server.isDedicatedServer() && !property.config.perWorld()){
            throw new IllegalStateException("Trying to set not-per-world debug parameter on integrated server");
        }

        if (property.config.perWorld()) {
            DebugPropertyWorldStorage.getStorage(server).set(property, value);
        }

        if (this.server.isDedicatedServer()){
            // only set non-per-world parameters on dedicated server; but then also save per-world parameters in config
            // storage to allow resetting worlds
            DebugPropertyConfigStorage.getInstance().set(property, value);
        }
    }
}
