package eu.jacobsjo.debugPropertiesPlus.property.storage;

import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

public class NewWorldStorage {
    private static @Nullable DebugPropertyWorldStorage newWorldStorage;

    public static void startCreateNewWorld(){
        newWorldStorage = new DebugPropertyWorldStorage(DebugPropertyConfigStorage.getInstance());
    }

    public static void cancelCreateNewWorld(){
        newWorldStorage = null;
    }

    public static void onWorldLoad(MinecraftServer server){
        if (newWorldStorage != null) {
            DebugPropertyWorldStorage.setStorage(server, newWorldStorage);
            newWorldStorage = null;
        } else {
            DebugPropertyWorldStorage.getStorage(server);
        }
    }

    public static @Nullable DebugPropertyWorldStorage getNewWorldStorage(){
        return newWorldStorage;
    }
}
