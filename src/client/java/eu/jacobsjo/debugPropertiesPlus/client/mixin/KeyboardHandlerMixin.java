package eu.jacobsjo.debugPropertiesPlus.client.mixin;

import eu.jacobsjo.debugPropertiesPlus.client.screen.DebugPropertyScreen;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "handleDebugKeys", at= @At("TAIL"), cancellable = true)
    public void handleDebugKeys(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir){
        if (keyEvent.key() == java.awt.event.KeyEvent.VK_P){
            if (this.minecraft.screen instanceof DebugPropertyScreen) {
                this.minecraft.screen.onClose();
            } else if (this.minecraft.canInterruptScreen()) {
                if (this.minecraft.screen != null) {
                    this.minecraft.screen.onClose();
                }

                this.minecraft.setScreen(new DebugPropertyScreen());
            }

            cir.setReturnValue(true);
        }
    }
}
