package eu.jacobsjo.debugPropertiesPlus.client.mixin;

import eu.jacobsjo.debugPropertiesPlus.client.ToggleableCheckbox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.input.InputWithModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Checkbox.class)
public class CheckboxMixin implements ToggleableCheckbox {
    @Shadow
    private boolean selected;
    @Unique
    private boolean enabled = true;

    @Override
    public void debug_properties_plus$setValue(boolean value) {
        this.selected = value;
    }

    @Override
    public void debug_properties_plus$setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    public void onPress(InputWithModifiers inputWithModifiers, CallbackInfo ci){
        if (!this.enabled){
            ci.cancel();
        }
    }

}
