package eu.jacobsjo.debugPropertiesPlus.networking;

import eu.jacobsjo.debugPropertiesPlus.DebugPropertiesPlus;
import eu.jacobsjo.debugPropertiesPlus.property.storage.DebugPropertyValueMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Used to send all server debug properties to the client in configuration phases. The aim is *not* to _set_ those properties
 * on the client but to _show_ them in the settings screen.
 */
public record ClientboundDebugPropertyPayload(DebugPropertyValueMap valueMap) implements CustomPacketPayload {
    public static final ResourceLocation CLIENTBOUND_DEBUG_PROPERTIES_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(DebugPropertiesPlus.MOD_ID, "debug_properties");
    public static final CustomPacketPayload.Type<ClientboundDebugPropertyPayload> ID = new CustomPacketPayload.Type<>(CLIENTBOUND_DEBUG_PROPERTIES_PAYLOAD_ID);
    public static final StreamCodec<FriendlyByteBuf, ClientboundDebugPropertyPayload> STREAM_CODEC = StreamCodec.composite(
            DebugPropertyValueMap.STREAM_CODEC,
            ClientboundDebugPropertyPayload::valueMap,
            ClientboundDebugPropertyPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
