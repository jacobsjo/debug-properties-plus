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
    @Unique private static final Component DEBUG_PROPERTIES_LABEL = Component.translatable("debug-properties-plus.button.open-screen");
    @Unique private static final Minecraft minecraft = Minecraft.getInstance();

    @Shadow @Final CreateWorldScreen this$0;

    @Inject(method = "<init>", at=@At("TAIL"))
    public void init(CreateWorldScreen createWorldScreen, CallbackInfo ci, @Local GridLayout.RowHelper rowHelper){
        rowHelper.addChild(
            Button.builder(
                    DEBUG_PROPERTIES_LABEL,
                    button -> minecraft.setScreen(
                            new DebugPropertyScreen(
                                    true,
                                    () -> minecraft.setScreen(this$0)
                            )
                    )
            ).width(210).build()
        );
    }
}
