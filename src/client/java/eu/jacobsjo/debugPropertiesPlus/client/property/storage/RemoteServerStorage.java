package eu.jacobsjo.debugPropertiesPlus.client.property.storage;

import eu.jacobsjo.debugPropertiesPlus.networking.ClientboundDebugPropertyPayload;
import eu.jacobsjo.debugPropertiesPlus.networking.DebugPropertyUpdatePayload;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyStorage;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyValueMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.server.permissions.Permissions;

public class RemoteServerStorage implements DebugPropertyStorage {
    private final DebugPropertyValueMap valueMap;

    public RemoteServerStorage(ClientboundDebugPropertyPayload payload) {
        this.valueMap = payload.valueMap();
    }

    @Override
    public <T> T get(DebugProperty<T> property) {
        return valueMap.get(property);
    }

    @Override
    public <T> void set(DebugProperty<T> property, T value) {
        // This is clientside validation; duplicated on the server
        if (Minecraft.getInstance().player == null || !Minecraft.getInstance().player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) {
            throw new IllegalStateException("Trying to set remote property without sufficient permissions");
        }

        valueMap.set(property, value);
        ClientPlayNetworking.send(new DebugPropertyUpdatePayload<>(property, value));
    }

    public <T> void handlePayload(DebugPropertyUpdatePayload<T> payload){
        valueMap.set(payload.property(), payload.newValue());
    }
}
