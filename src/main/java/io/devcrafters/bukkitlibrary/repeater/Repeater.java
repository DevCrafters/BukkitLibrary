package io.devcrafters.bukkitlibrary.repeater;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Repeater implements Runnable {

    public Repeater(JavaPlugin plugin) {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 1L);
    }

    @Override
    public void run() {
        Bukkit.getPluginManager().callEvent(new RepeatEvent());
    }

}
