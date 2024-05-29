package com.example.exampleplugin.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

// https://docs.papermc.io/paper/dev/commands#basiccommand
public class BasicCommandExample implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            stack.getSender().sendRichMessage("<rainbow>Fun activated!");
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        return List.of();
    }

}