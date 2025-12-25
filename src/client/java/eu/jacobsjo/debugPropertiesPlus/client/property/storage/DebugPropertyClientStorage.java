package eu.jacobsjo.debugPropertiesPlus.client.property.storage;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyConfigStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyWorldStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.util.Optional;

public class DebugPropertyClientStorage {
    private static final File configFile = new File("debug-properties-plus.json");
    private static @Nullable DebugPropertyStorage configStorage;
    private static @Nullable DebugPropertyWorldStorage createNewWorldStorage;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void bootstrap() {
        configStorage = DebugPropertyConfigStorage.getStorage(configFile);
    }

    private static @Nullable DebugPropertyStorage getStorage(DebugProperty<?> property){

        if (property.config.perWorld()) {
            MinecraftServer server = minecraft.getSingleplayerServer();
            if (server != null){
                return DebugPropertyWorldStorage.getStorage(server);
            } else if (createNewWorldStorage != null){
                return createNewWorldStorage;
            }
        }
        return configStorage;
    }

    public static void startCreateNewWorld(){
        createNewWorldStorage = new DebugPropertyWorldStorage(configStorage);
    }

    public static void cancelCreateNewWorld(){
        createNewWorldStorage = null;
    }
    public static void onCreateNewWorld(MinecraftServer server){
        if (createNewWorldStorage != null) {
            DebugPropertyWorldStorage.setStorage(server, createNewWorldStorage);
            createNewWorldStorage = null;
        } else {
            DebugPropertyWorldStorage.getStorage(server);
        }
    }

    public static <T> Optional<T> get(DebugProperty<T> property) {
        DebugPropertyStorage storage = getStorage(property);
        if (storage == null) return Optional.empty();
        return Optional.of(storage.get(property));
    }

    public static <T> void set(DebugProperty<T> property, T value) {
        DebugPropertyStorage storage = getStorage(property);
        if (storage == null){
            throw new IllegalStateException("Can't set property " + property + " - No stoage found");
        }
        storage.set(property, value);

        if (property.config.updateDebugRenderer()){
            minecraft.debugEntries.rebuildCurrentList();
        }
    }
}
