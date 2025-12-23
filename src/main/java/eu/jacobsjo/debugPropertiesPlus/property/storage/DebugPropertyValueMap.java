package eu.jacobsjo.debugPropertiesPlus.property.storage;

import com.mojang.serialization.Codec;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;

import java.util.Map;
import java.util.function.Predicate;

public class DebugPropertyValueMap {
    public static Codec<DebugPropertyValueMap> codec(Predicate<DebugProperty<?>> predicate){
        return Codec.<DebugProperty<?>, Object>dispatchedMap(
                DebugProperty.BY_NAME_CODEC,
                p -> p.valueCodec
        ).xmap(map -> new DebugPropertyValueMap(map, predicate), ps -> ps.values);
    }

    private final Map<DebugProperty<?>, Object> values;

    public DebugPropertyValueMap(Map<DebugProperty<?>, Object> values, Predicate<DebugProperty<?>> predicate){
        this.values = new Reference2ObjectOpenHashMap<>(values);
        this.setStoreFromDebugProperties(predicate);
        this.setDebugPropertiesFromStore();
    }

    DebugPropertyValueMap(Predicate<DebugProperty<?>> predicate){
        this.values = new Reference2ObjectOpenHashMap<>();
        this.setStoreFromDebugProperties(predicate);
    }

    private void setStoreFromDebugProperties(Predicate<DebugProperty<?>> predicate){
        DebugProperty.PROPERTIES.values().stream().filter(predicate).forEach(p -> {
            String propertyString = System.getProperty("MC_DEBUG_" + p.name);
            if (propertyString == null) return;
            if (p.type == Boolean.class){
                if (propertyString.isEmpty() || Boolean.parseBoolean(propertyString)){
                    values.put(p, true);
                }
            } else if (p.type == Integer.class){
                try {
                    int intValue = Integer.parseInt(propertyString);
                    if (intValue != 0){
                        values.put(p, true);
                    }
                } catch (NumberFormatException e) {
                    // TODO logging
                }
            } else {
                throw new IllegalStateException("Can't handle property type " + p.type.getName());
            }
        });
    }

    private void setDebugPropertiesFromStore(){
        values.forEach((k, v) -> k.set(v));
    }

    public <T> T get(DebugProperty<T> property) {
        return (T) values.getOrDefault(property, property.defaultValue);
    }

    public <T> void set(DebugProperty<T> property, T value) {
        values.put(property, value);
        property.set(value);
    }
}
