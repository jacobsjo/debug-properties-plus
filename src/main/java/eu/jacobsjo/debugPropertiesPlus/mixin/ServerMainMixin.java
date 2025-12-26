package eu.jacobsjo.debugPropertiesPlus.mixin;

import eu.jacobsjo.debugPropertiesPlus.property.storage.NewWorldStorage;
import net.minecraft.core.Registry;
import net.minecraft.server.Main;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Main.class)
public class ServerMainMixin {
    @Inject(method = "createNewWorldData", at = @At("HEAD"))
    private static void createNewWorldData(DedicatedServerSettings dedicatedServerSettings, WorldLoader.DataLoadContext dataLoadContext, Registry<LevelStem> registry, boolean bl, boolean bl2, CallbackInfoReturnable<WorldLoader.DataLoadOutput<WorldData>> cir){
        NewWorldStorage.startCreateNewWorld();
    }
}
