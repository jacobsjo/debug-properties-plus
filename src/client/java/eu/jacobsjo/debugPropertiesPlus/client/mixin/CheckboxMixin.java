package eu.jacobsjo.debugPropertiesPlus.client.mixin;

import eu.jacobsjo.debugPropertiesPlus.client.ToggleableCheckbox;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Checkbox.class)
public abstract class CheckboxMixin extends AbstractWidget implements ToggleableCheckbox {
    @Shadow
    private boolean selected;

    public CheckboxMixin(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    @Override
    public void debug_properties_plus$setValue(boolean value) {
        this.selected = value;
    }

    @Override
    public void debug_properties_plus$setEnabled(boolean enabled) {
        this.active = enabled;
    }

    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    public void onPress(InputWithModifiers inputWithModifiers, CallbackInfo ci){
        if (!this.active){
            ci.cancel();
        }
    }

}
