package eu.jacobsjo.debugPropertiesPlus.property.storage;

import com.mojang.serialization.Codec;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.Objects;

public class WorldStorage extends SavedData implements DebugPropertyStorage {
    private static final Codec<WorldStorage> CODEC = DebugPropertyValueMap.codec(p -> p.config.perWorld())
            .xmap(WorldStorage::new, WorldStorage::valueMap);

    private static final SavedDataType<WorldStorage> TYPE = new SavedDataType<>(
            "debug-properties-plus", WorldStorage::new, CODEC, null
    );

    public static WorldStorage getStorage(MinecraftServer server){
        return Objects.requireNonNull(server.getLevel(Level.OVERWORLD)).getDataStorage().computeIfAbsent(TYPE);
    }

    public static void setStorage(MinecraftServer server, WorldStorage storage){
        Objects.requireNonNull(server.getLevel(Level.OVERWORLD)).getDataStorage().set(TYPE, storage);
    }

    private final DebugPropertyValueMap valueMap;

    public WorldStorage() {
        this.valueMap = new DebugPropertyValueMap(p -> p.config.perWorld());
        this.setDirty();
    }

    public WorldStorage(DebugPropertyStorage defaults) {
        this.valueMap = new DebugPropertyValueMap(defaults, p -> p.config.perWorld());
    }

    private WorldStorage(DebugPropertyValueMap map) {
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
