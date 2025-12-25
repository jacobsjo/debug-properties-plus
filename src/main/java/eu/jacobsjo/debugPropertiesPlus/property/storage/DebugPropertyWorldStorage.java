package eu.jacobsjo.debugPropertiesPlus.property.storage;

import com.mojang.serialization.Codec;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.Objects;

public class DebugPropertyWorldStorage extends SavedData implements DebugPropertyStorage {
    private static final Codec<DebugPropertyWorldStorage> CODEC = DebugPropertyValueMap.codec(p -> p.config.perWorld())
            .xmap(DebugPropertyWorldStorage::new, DebugPropertyWorldStorage::valueMap);

    private static final SavedDataType<DebugPropertyWorldStorage> TYPE = new SavedDataType<>(
            "debug-properties-plus", DebugPropertyWorldStorage::new, CODEC, null
    );

    public static DebugPropertyWorldStorage getStorage(MinecraftServer server){
        return Objects.requireNonNull(server.getLevel(Level.OVERWORLD)).getDataStorage().computeIfAbsent(TYPE);
    }

    public static void setStorage(MinecraftServer server, DebugPropertyWorldStorage storage){
        Objects.requireNonNull(server.getLevel(Level.OVERWORLD)).getDataStorage().set(TYPE, storage);
    }

    private final DebugPropertyValueMap valueMap;

    public DebugPropertyWorldStorage() {
        this.valueMap = new DebugPropertyValueMap(p -> p.config.perWorld());
        this.setDirty();
    }

    public DebugPropertyWorldStorage(DebugPropertyStorage defaults) {
        this.valueMap = new DebugPropertyValueMap(defaults, p -> p.config.perWorld());
    }

    private DebugPropertyWorldStorage(DebugPropertyValueMap map) {
        this.valueMap = map;
        this.setDirty();
    }

    private DebugPropertyValueMap valueMap(){
        return valueMap;
    }

    public <T> T get(DebugProperty<T> property) {
        return this.valueMap.get(property);
    }

    public <T> void set(DebugProperty<T> property, T value) {
        this.valueMap.set(property, value);
        this.setDirty();
    }

}
