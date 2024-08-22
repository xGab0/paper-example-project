package com.example.exampleplugin.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.regex.Pattern;

public class ExamplePluginListener implements Listener {

    private static final Pattern slashPattern = Pattern.compile("\\B/\\w+");
    private static final Pattern atPattern = Pattern.compile("\\B@\\w+");
    private static final Pattern hashtagPattern = Pattern.compile("\\B#\\w+");

    private static final TextReplacementConfig slashReplaceConfig = TextReplacementConfig.builder()
            .match(slashPattern)
            .replacement(builder -> builder.color(NamedTextColor.LIGHT_PURPLE))
            .build();

    private static final TextReplacementConfig atReplaceConfig = TextReplacementConfig.builder()
            .match(atPattern)
            .replacement(builder -> builder.color(NamedTextColor.GREEN))
            .build();

    private static final TextReplacementConfig hashtagReplaceConfig = TextReplacementConfig.builder()
            .match(hashtagPattern)
            .replacement(builder -> builder.color(NamedTextColor.RED))
            .build();

    @EventHandler
    public void cmdHighlights(AsyncChatEvent event) {
        var message = event.message();
        var modifiedMessage = message
                .replaceText(slashReplaceConfig)
                .replaceText(atReplaceConfig)
                .replaceText(hashtagReplaceConfig);

        event.message(modifiedMessage);
    }

}