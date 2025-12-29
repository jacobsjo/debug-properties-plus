package eu.jacobsjo.debugPropertiesPlus.property.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.function.Predicate;

public class DebugPropertyValueMap {
    public static Codec<DebugPropertyValueMap> codec(Predicate<DebugProperty<?>> predicate){
        return Codec.<DebugProperty<?>, Object>dispatchedMap(
                DebugProperty.BY_NAME_CODEC.validate(p -> predicate.test(p) ? DataResult.success(p) : DataResult.error(() -> "Debug property " + p + " not allowed here.")),
                p -> p.valueCodec
        ).xmap(map -> new DebugPropertyValueMap(map, predicate), ps -> ps.values);
    }

    public static StreamCodec<ByteBuf, DebugPropertyValueMap> STREAM_CODEC = ByteBufCodecs.fromCodec(codec(p -> true));

    private final Predicate<DebugProperty<?>> predicate;
    private final Map<DebugProperty<?>, Object> values;

    public DebugPropertyValueMap(Map<DebugProperty<?>, Object> values, Predicate<DebugProperty<?>> predicate){
        this.values = new Reference2ObjectOpenHashMap<>(values);
        this.predicate = predicate;
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
    }

    public DebugPropertyValueMap(Predicate<DebugProperty<?>> predicate){
        this.values = new Reference2ObjectOpenHashMap<>();
        this.predicate = predicate;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(DebugProperty<T> property) {
        return (T) values.getOrDefault(property, property.defaultValue);
    }

    public <T> void set(DebugProperty<T> property, T value) {
        if (value.equals(property.defaultValue)){
            values.remove(property);
        } else {
            values.put(property, value);
        }
    }

    public void updateDebugPropertiesFromMap(){
        DebugProperty.PROPERTIES.values().stream().filter(this.predicate).forEach(this::updateDebugPropertyFromMap);
    }

    private void updateDebugPropertyFromMap(DebugProperty<?> p) {
        p.set(get(p));
    }
}
