package eu.jacobsjo.debugPropertiesPlus.property.storage;

import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;

public class DefaultStorage implements DebugPropertyStorage{
    @Override
    public <T> T get(DebugProperty<T> property) {
        return property.defaultValue;
    }

    @Override
    public <T> void set(DebugProperty<T> property, T value) {
        throw new IllegalStateException("Can't set valude of default storage");
    }
}
