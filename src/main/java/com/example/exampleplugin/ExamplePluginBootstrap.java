package com.example.exampleplugin;

import com.example.exampleplugin.command.BasicCommandExample;
import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// https://docs.papermc.io/paper/dev/getting-started/paper-plugins#bootstrapper
public class ExamplePluginBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("basic-example", "some help description string", new BasicCommandExample());
        });

        // https://docs.papermc.io/paper/dev/commands
        //
        // Registered brigadier commands in bootstrap context
        // will be available in datapacks and mcfunctions
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("example-brigadier")
                            .executes(ctx -> {
                                ctx.getSource().getSender().sendPlainMessage("some message");
                                return Command.SINGLE_SUCCESS;
                            })
                            .build(),
                    "some bukkit help description string",
                    List.of("an-alias")
            );
        });
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        return new ExamplePlugin();
    }

}