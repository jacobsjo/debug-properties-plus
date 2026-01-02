package eu.jacobsjo.debugPropertiesPlus.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.jacobsjo.debugPropertiesPlus.client.screen.DebugPropertyScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class DebugPropertiesPlusModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (Screen lastScreen) -> new DebugPropertyScreen(
                false,
                () -> Minecraft.getInstance().setScreen(lastScreen)
        );
    }
}
