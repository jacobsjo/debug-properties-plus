package eu.jacobsjo.debugPropertiesPlus.property.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;

import java.util.Map;
import java.util.function.Predicate;

public class DebugPropertyValueMap {
    public static Codec<DebugPropertyValueMap> codec(Predicate<DebugProperty<?>> predicate){
        return Codec.<DebugProperty<?>, Object>dispatchedMap(
                DebugProperty.BY_NAME_CODEC.validate(p -> predicate.test(p) ? DataResult.success(p) : DataResult.error(() -> "Debug property " + p + " not allowed here.")),
                p -> p.valueCodec
        ).xmap(map -> new DebugPropertyValueMap(map, predicate), ps -> ps.values);
    }

    private final Predicate<DebugProperty<?>> predicate;
    private final Map<DebugProperty<?>, Object> values;

    public DebugPropertyValueMap(Map<DebugProperty<?>, Object> values, Predicate<DebugProperty<?>> predicate){
        this.values = new Reference2ObjectOpenHashMap<>(values);
        this.predicate = predicate;
        this.setStoreFromDebugProperties();
        this.setDebugPropertiesFromStore();
    }

    DebugPropertyValueMap(DebugPropertyStorage defaults, Predicate<DebugProperty<?>> predicate){
        this.values = new Reference2ObjectOpenHashMap<>();
        DebugProperty.PROPERTIES.values().stream().filter(predicate).forEach(p -> {
            Object value = defaults.get(p);
            if (!value.equals(p.defaultValue)) {
                this.values.put(p, defaults.get(p));
            }
        });
        this.predicate = predicate;
        this.setStoreFromDebugProperties();
        this.setDebugPropertiesFromStore();
    }

    DebugPropertyValueMap(Predicate<DebugProperty<?>> predicate){
        this.values = new Reference2ObjectOpenHashMap<>();
        this.predicate = predicate;
        this.setStoreFromDebugProperties();
        this.setDebugPropertiesFromStore();
    }

    private void setStoreFromDebugProperties(){
        DebugProperty.PROPERTIES.values().stream().filter(this.predicate).forEach(p -> {
            String propertyString = System.getProperty("MC_DEBUG_" + p.name);
            if (propertyString == null) return;
            if (p.type == Boolean.class){
                boolean value = propertyString.isEmpty() || Boolean.parseBoolean(propertyString);
                if (!p.defaultValue.equals(value)){
                    values.put(p, value);
                }
            } else if (p.type == Integer.class){
                try {
                    int intValue = Integer.parseInt(propertyString);
                    if (!p.defaultValue.equals(intValue)){
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
        DebugProperty.PROPERTIES.values().stream().filter(this.predicate).forEach(p -> {
            p.set(get(p));
        });
    }

    public <T> T get(DebugProperty<T> property) {
        return (T) values.getOrDefault(property, property.defaultValue);
    }

    public <T> void set(DebugProperty<T> property, T value) {
        if (value.equals(property.defaultValue)){
            values.remove(property);
        } else {
            values.put(property, value);
        }
        property.set(value);
    }
}
