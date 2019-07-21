package io.devcrafters.bukkitlibrary;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class Library implements Listener {

    private String libraryName;
    private Plugin plugin;

    public Library(String name, Plugin plugin) {
        libraryName = name;

        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);

        log("Initialized " + libraryName);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void log(String message) {
        System.out.println("[" + libraryName + "] " + message);
    }

}
