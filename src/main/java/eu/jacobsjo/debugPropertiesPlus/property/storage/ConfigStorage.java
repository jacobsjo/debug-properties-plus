package eu.jacobsjo.debugPropertiesPlus.property.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import eu.jacobsjo.debugPropertiesPlus.DebugPropertiesPlus;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigStorage implements DebugPropertyStorage{
    private static @Nullable ConfigStorage INSTANCE = null;

    private static final Codec<DebugPropertyValueMap> VALUE_MAP_CODEC = DebugPropertyValueMap.codec(p -> true);

    private final DebugPropertyValueMap valueMap;
    private final @Nullable File file;

    private ConfigStorage(@Nullable File file) {
        this.valueMap = new DebugPropertyValueMap(p -> true);
        this.file = file;
    }

    private ConfigStorage(DebugPropertyValueMap map, @Nullable File file) {
        this.valueMap = map;
        this.file = file;
        this.updateFile();
    }

    public static ConfigStorage getInstance() {
        if (INSTANCE == null) {
            File file = new File("debug-properties-plus.json");
            try (FileReader fileReader = new FileReader(file)) {
                JsonElement json = JsonParser.parseReader(fileReader);
                DebugPropertyValueMap valueMap = VALUE_MAP_CODEC.parse(JsonOps.INSTANCE, json).getPartialOrThrow();
                INSTANCE = new ConfigStorage(valueMap, file);
            } catch (IOException e) {
                DebugPropertiesPlus.LOGGER.info("Could not read config file, starting from stretch: ", e);
                INSTANCE = new ConfigStorage(file);
            } catch (IllegalStateException e) {
                DebugPropertiesPlus.LOGGER.warn("Could not parse config file, starting from stretch: ", e);
                INSTANCE = new ConfigStorage(file);
            }
        }
        return INSTANCE;
    }

    private void updateFile(){
        if (file == null) return;

        try (FileWriter fileWriter = new FileWriter(file)) {
            JsonElement json = VALUE_MAP_CODEC.encodeStart(JsonOps.INSTANCE, valueMap).getOrThrow();
            JsonWriter writer = new JsonWriter(fileWriter);
            writer.setIndent("  ");
            GsonHelper.writeValue(writer, json, null);
        } catch (IOException e) {
            DebugPropertiesPlus.LOGGER.error("Could not save config file:", e);
        } catch (IllegalStateException e){
            DebugPropertiesPlus.LOGGER.error("Could not encode config file: ", e);
        }
    }

    public <T> T get(DebugProperty<T> property) {
        return this.valueMap.get(property);
    }

    public <T> void set(DebugProperty<T> property, T value) {
        this.valueMap.set(property, value);
        this.updateFile();
    }

    public void updateLocalDebugProperties() {
        this.valueMap.updateDebugPropertiesFromMap();
    }

}
