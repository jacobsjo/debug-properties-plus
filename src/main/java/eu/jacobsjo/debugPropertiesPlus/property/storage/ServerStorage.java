package eu.jacobsjo.debugPropertiesPlus.property.storage;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.server.MinecraftServer;

public class ServerStorage {
    private final MinecraftServer server;

    public ServerStorage(MinecraftServer server){
        this.server = server;
        WorldStorage.getStorage(server);
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
    }
}
