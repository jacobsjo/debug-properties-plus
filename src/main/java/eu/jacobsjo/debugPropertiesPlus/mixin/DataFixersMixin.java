package eu.jacobsjo.debugPropertiesPlus.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.schemas.Schema;
import eu.jacobsjo.debugPropertiesPlus.fileFix.WorldStorageFileFix;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.filefix.FileFixerUpper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DataFixers.class)
public abstract class DataFixersMixin {

    @Definition(id = "addSchema", method = "Lnet/minecraft/util/filefix/FileFixerUpper$Builder;addSchema(Lcom/mojang/datafixers/DataFixerBuilder;ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;")
    @Expression("?.addSchema(?, 4772, ?)")
    @ModifyExpressionValue(method = "addFixers", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private static Schema addWorldStorageFileFix(Schema original, @Local(argsOnly = true) FileFixerUpper.Builder fileFixerUpper) {
        fileFixerUpper.addFixer(new WorldStorageFileFix(original));
        return original;
    }
}