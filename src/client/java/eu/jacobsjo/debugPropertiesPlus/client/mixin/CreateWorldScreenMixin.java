package eu.jacobsjo.debugPropertiesPlus.client.mixin;

import eu.jacobsjo.debugPropertiesPlus.client.property.storage.DebugPropertyClientStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldCallback;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.OptionalLong;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private static void init(Minecraft minecraft, Runnable runnable, WorldCreationContext worldCreationContext, Optional optional, OptionalLong optionalLong, CreateWorldCallback createWorldCallback, CallbackInfo ci){
        DebugPropertyClientStorage.startCreateNewWorld();
    }

    @Inject(method = "popScreen", at = @At("TAIL"))
    public void popScreen(CallbackInfo ci){
        DebugPropertyClientStorage.cancelCreateNewWorld();
    }
}
