package eu.jacobsjo.debugPropertiesPlus.networking;

import eu.jacobsjo.debugPropertiesPlus.DebugPropertiesPlus;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Used to send an updated debug properties to the client. The aim is here *not* to _set_ those properties on the client
 * but to _show_ them in the settings screen. Also used for the client to send update requests to the server.
 */
public record DebugPropertyUpdatePayload<T>(DebugProperty<T> property, T newValue) implements CustomPacketPayload {
    public static final Identifier DEBUG_PROPERTY_UPDATE_PAYLOAD_ID = Identifier.fromNamespaceAndPath(DebugPropertiesPlus.MOD_ID, "debug_property_update");
    public static final Type<DebugPropertyUpdatePayload<?>> ID = new Type<>(DEBUG_PROPERTY_UPDATE_PAYLOAD_ID);
    public static final StreamCodec<FriendlyByteBuf, DebugPropertyUpdatePayload<?>> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public DebugPropertyUpdatePayload<?> decode(FriendlyByteBuf byteBuf) {
            DebugProperty<?> property = DebugProperty.BY_NAME_STREAM_CODEC.decode(byteBuf);
            return decodeValue(property, byteBuf);
        }

        private <U> DebugPropertyUpdatePayload<U> decodeValue(DebugProperty<U> property, FriendlyByteBuf byteBuf){
            U value = property.valueStreamCodec.decode(byteBuf);
            return new DebugPropertyUpdatePayload<>(property, value);
        }

        @Override
        public void encode(FriendlyByteBuf byteBuf, DebugPropertyUpdatePayload<?> payload) {
            DebugProperty.BY_NAME_STREAM_CODEC.encode(byteBuf, payload.property);
            encodeValue(payload, byteBuf);
        }

        private <U> void encodeValue(DebugPropertyUpdatePayload<U> payload, FriendlyByteBuf byteBuf){
            payload.property.valueStreamCodec.encode(byteBuf, payload.newValue);
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
