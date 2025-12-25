package eu.jacobsjo.debugPropertiesPlus.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import eu.jacobsjo.debugPropertiesPlus.DebugPropertiesPlus;
import eu.jacobsjo.debugPropertiesPlus.property.DebugProperty;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class DebugPropertyCommand {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, Commands.CommandSelection environment){
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("debugproperty")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));

        DebugProperty.PROPERTIES.forEach((key, property) -> {
            if (property.config.perWorld() || (environment.includeDedicated && property.config.onDedicatedServer())) {
                builder.then(buildPropertyArguments(property, Commands.literal(key)));
            }
        });

        commandDispatcher.register(builder);
    }

    static <T> LiteralArgumentBuilder<CommandSourceStack> buildPropertyArguments(
            DebugProperty<T> property, LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder
    ) {
        return literalArgumentBuilder
            .executes(context -> getDebugProperty(property, context))
            .then(Commands.argument("value", property.argument)
                .executes(context -> setDebugProperty(property, context))
            );
    }

    private static <T> int getDebugProperty(DebugProperty<T> property, CommandContext<CommandSourceStack> context) {
        assert DebugPropertiesPlus.serverStorage != null;
        T result = DebugPropertiesPlus.serverStorage.get(property);

        context.getSource().sendSuccess(() -> Component.translatable("debug-properties-plus.command.get", property.name, result.toString()), true);
        return result instanceof Boolean ? (Boolean) result ? 1 : 0 : (Integer) result;
    }

    private static <T> int setDebugProperty(DebugProperty<T> property, CommandContext<CommandSourceStack> context) {
        assert DebugPropertiesPlus.serverStorage != null;
        T argument = (T) context.getArgument("value", property.defaultValue.getClass());
        DebugPropertiesPlus.serverStorage.set(property, argument);

        context.getSource().sendSuccess(() -> Component.translatable("debug-properties-plus.command.set", property.name, argument.toString()), true);
        return 0;
    }

}
