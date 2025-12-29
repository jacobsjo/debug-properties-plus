package eu.jacobsjo.debugPropertiesPlus.property.storage;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;

public interface DebugPropertyStorage {
    <T> T get(DebugProperty<T> property);
    <T> void set(DebugProperty<T> property, T value);
}
