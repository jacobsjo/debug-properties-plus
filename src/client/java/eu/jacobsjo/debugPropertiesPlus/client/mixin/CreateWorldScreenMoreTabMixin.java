package eu.jacobsjo.debugPropertiesPlus.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import eu.jacobsjo.debugPropertiesPlus.client.screen.DebugPropertyScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.screens.worldselection.CreateWorldScreen$MoreTab")
public class CreateWorldScreenMoreTabMixin {
    @Unique private static final Component DEBUG_PROPERTIES_LABEL = Component.translatable("eu.jacobsjo.TODO");
    @Unique private static final Minecraft minecraft = Minecraft.getInstance();

    // Synthtic field of outer class
    @Shadow @Final CreateWorldScreen field_42178;

    @Inject(method = "<init>", at=@At("TAIL"))
    public void init(CreateWorldScreen createWorldScreen, CallbackInfo ci, @Local GridLayout.RowHelper rowHelper){
        rowHelper.addChild(
            Button.builder(
                    DEBUG_PROPERTIES_LABEL,
                    button -> minecraft.setScreen(
                            new DebugPropertyScreen(
                                    true,
                                    () -> minecraft.setScreen(field_42178)
                            )
                    )
            ).width(210).build()
        );
    }
}
