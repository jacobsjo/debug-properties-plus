package eu.jacobsjo.debugPropertiesPlus.client.mixin;

import eu.jacobsjo.debugPropertiesPlus.property.storage.NewWorldStorage;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectWorldScreen.class)
public class SelectWorldScreenMixin {

    @Inject(method = "lambda$createDebugWorldRecreateButton$0", at=@At("HEAD"))
    public void createDebugWorldRecreateButtonPress(Button button, CallbackInfo ci){
        NewWorldStorage.startCreateNewWorld();
    }
}
