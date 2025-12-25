package eu.jacobsjo.debugPropertiesPlus.client.property.storage;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyConfigStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyWorldStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

public class DebugPropertyClientStorage {
    private static @Nullable DebugPropertyWorldStorage createNewWorldStorage;
    private static final Minecraft minecraft = Minecraft.getInstance();

    private static DebugPropertyStorage getStorage(DebugProperty<?> property){

        if (property.config.perWorld()) {
            MinecraftServer server = minecraft.getSingleplayerServer();
            if (server != null){
                return DebugPropertyWorldStorage.getStorage(server);
            } else if (createNewWorldStorage != null){
                return createNewWorldStorage;
            }
        }
        return DebugPropertyConfigStorage.getInstance();
    }

    public static void startCreateNewWorld(){
        createNewWorldStorage = new DebugPropertyWorldStorage(DebugPropertyConfigStorage.getInstance());
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
