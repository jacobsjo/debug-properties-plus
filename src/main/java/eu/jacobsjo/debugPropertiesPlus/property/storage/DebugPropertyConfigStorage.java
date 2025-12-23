package eu.jacobsjo.debugPropertiesPlus.property.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.util.GsonHelper;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DebugPropertyConfigStorage implements DebugPropertyStorage{
    private static final Codec<DebugPropertyValueMap> VALUE_MAP_CODEC = DebugPropertyValueMap.codec(p -> !p.config.perWorld());

    private final DebugPropertyValueMap valueMap;
    private final @Nullable File file;

    public DebugPropertyConfigStorage() {
        this(null);
    }

    private DebugPropertyConfigStorage(@Nullable File file) {
        this.valueMap = new DebugPropertyValueMap(p -> !p.config.perWorld());
        this.file = file;
    }

    private DebugPropertyConfigStorage(DebugPropertyValueMap map, @Nullable File file) {
        this.valueMap = map;
        this.file = file;
        this.updateFile();
    }

    public static DebugPropertyConfigStorage getStorage(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            JsonElement json = JsonParser.parseReader(fileReader);
            DebugPropertyValueMap valueMap = VALUE_MAP_CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
            return new DebugPropertyConfigStorage(valueMap, file);
        } catch (IOException e){
            // TODO logging: file reading error
            return new DebugPropertyConfigStorage(file);
        } catch (IllegalStateException e){
            // TODO logging: decoding error
            return new DebugPropertyConfigStorage(file);
        }
    }

    private void updateFile(){
        if (file == null) return;

        try (FileWriter fileWriter = new FileWriter(file)) {
            JsonElement json = VALUE_MAP_CODEC.encodeStart(JsonOps.INSTANCE, valueMap).getOrThrow();
            JsonWriter writer = new JsonWriter(fileWriter);
            GsonHelper.writeValue(writer, json, null);
        } catch (IOException e) {
            //TODO logging: file writing error
        } catch (IllegalStateException e){
            // TODO logging: encoding error
        }
    }

    public <T> T get(DebugProperty<T> property) {
        return this.valueMap.get(property);
    }

    public <T> void set(DebugProperty<T> property, T value) {
        this.valueMap.set(property, value);
        this.updateFile();
    }

}
