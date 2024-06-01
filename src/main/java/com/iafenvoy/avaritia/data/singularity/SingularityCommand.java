package com.iafenvoy.avaritia.data.singularity;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SingularityCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher
                .register(CommandManager.literal("singularity")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("target", EntityArgumentType.entities())
                                .then(CommandManager.argument("id", StringArgumentType.string())
                                        .suggests((context, builder) -> {
                                            for (Singularity singularity : Singularity.MATERIALS.values())
                                                builder.suggest(singularity.getId());
                                            return builder.buildFuture();
                                        })
                                        .executes(SingularityCommand::executor)))));
    }

    private static int executor(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Singularity singularity = SingularityHelper.get(StringArgumentType.getString(context, "id"));
        if (singularity == Singularity.EMPTY) {
            context.getSource().sendError(Text.literal("Unknown singularity"));
            return 0;
        }
        EntityArgumentType.getEntities(context, "target").forEach(entity -> {
            if (entity instanceof PlayerEntity player)
                player.getInventory().insertStack(SingularityHelper.buildStack(singularity));
        });
        return 1;
    }
}
