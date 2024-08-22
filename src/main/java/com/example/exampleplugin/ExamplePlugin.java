package com.example.exampleplugin;

import com.example.exampleplugin.listener.ExamplePluginListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ExamplePluginListener(), this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onLoad() {

    }

}