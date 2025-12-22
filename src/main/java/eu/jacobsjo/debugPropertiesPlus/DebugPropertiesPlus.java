package eu.jacobsjo.debugPropertiesPlus;

import net.fabricmc.api.ModInitializer;
import net.minecraft.SharedConstants;

public class DebugPropertiesPlus implements ModInitializer {

    @Override
    public void onInitialize() {
        SharedConstants.DEBUG_ENABLED = true;
    }
}
