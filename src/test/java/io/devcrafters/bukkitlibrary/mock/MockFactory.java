package io.devcrafters.bukkitlibrary.mock;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

public class MockFactory {

    private MockFactory() {
    }

    public static MockPlugin createPlugin() {
        MockPlugin plugin = new MockPlugin();
        try {
            Field server = Bukkit.class.getDeclaredField("server");
            server.setAccessible(true);
            server.set(null, plugin.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return plugin;
    }

    public static MockPlayer createPlayer(String name, UUID uuid) {
        return new MockPlayer(name, uuid);
    }

    public static MockPlayer createPlayer() {
        return new MockPlayer();
    }

    public static void addPlayer(Server server, Player player) {
        if(!(server instanceof MockServer)) {
            throw new IllegalArgumentException("Server must be mocked-server.");
        }
        ((MockServer) server).addPlayer(player);
    }

    public static void removePlayer(Server server, Player player) {
        if(!(server instanceof MockServer)) {
            throw new IllegalArgumentException("Server must be mocked-server.");
        }
        ((MockServer) server).removePlayer(player);
    }

}
