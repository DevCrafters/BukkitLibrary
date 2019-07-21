package io.devcrafters.bukkitlibrary;

import io.devcrafters.bukkitlibrary.cooldown.CooldownLibrary;
import io.devcrafters.bukkitlibrary.repeater.Repeater;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitLibrary extends JavaPlugin {

    private static BukkitLibrary instance;

    private CooldownLibrary cooldownLibrary;

    public BukkitLibrary() {
        instance = this;
    }

    public static CooldownLibrary getCooldownLibrary() {
        return instance.cooldownLibrary;
    }

    public static BukkitLibrary getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        cooldownLibrary = new CooldownLibrary(this);

        new Repeater(this);
    }

    @Override
    public void onDisable() {

    }

}
