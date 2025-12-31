package eu.jacobsjo.debugPropertiesPlus.property.storage;

import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

public class NewWorldStorage {
    private static @Nullable WorldStorage newWorldStorage;

    public static void startCreateNewWorld(){
        newWorldStorage = new WorldStorage(ConfigStorage.getInstance());
    }

    public static void cancelCreateNewWorld(){
        newWorldStorage = null;
    }

    public static void onWorldLoad(MinecraftServer server){
        if (newWorldStorage != null) {
            WorldStorage.setStorage(server, newWorldStorage);
            newWorldStorage = null;
        }
    }

    public static @Nullable WorldStorage getNewWorldStorage(){
        return newWorldStorage;
    }
}
