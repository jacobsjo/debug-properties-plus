package eu.jacobsjo.debugPropertiesPlus.client.property.storage;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyConfigStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyWorldStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.NewWorldStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class DebugPropertyClientStorage {
    private static final Minecraft minecraft = Minecraft.getInstance();

    private static DebugPropertyStorage getStorage(DebugProperty<?> property){

        if (property.config.perWorld()) {
            MinecraftServer server = minecraft.getSingleplayerServer();
            if (server != null){
                return DebugPropertyWorldStorage.getStorage(server);
            } else if (NewWorldStorage.getNewWorldStorage() != null){
                return NewWorldStorage.getNewWorldStorage();
            }
        }
        return DebugPropertyConfigStorage.getInstance();
    }

    public static <T> T get(DebugProperty<T> property) {
        DebugPropertyStorage storage = getStorage(property);
        return storage.get(property);
    }

    public static <T> void set(DebugProperty<T> property, T value) {
        DebugPropertyStorage storage = getStorage(property);
        storage.set(property, value);

        if (property.config.updateDebugRenderer()){
            minecraft.debugEntries.rebuildCurrentList();
        }
    }
}
