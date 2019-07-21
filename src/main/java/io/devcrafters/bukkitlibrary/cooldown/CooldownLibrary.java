package io.devcrafters.bukkitlibrary.cooldown;

import io.devcrafters.bukkitlibrary.Library;
import io.devcrafters.bukkitlibrary.repeater.RepeatEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CooldownLibrary extends Library {

    private Map<String, HashMap<String, CooldownData>> cooldownData = new HashMap<>();

    public CooldownLibrary(Plugin plugin) {
        super("Cooldown Library", plugin);
    }

    public HashMap<String, CooldownData> getCooldownData(Player player) {
        return cooldownData.get(player.getName());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        cooldownData.remove(event.getPlayer());
    }

    public boolean usable(Player player, String abilityName) {
        if (!cooldownData.containsKey(player.getName()))
            return true;

        Map<String, CooldownData> cooldown = getCooldownData(player);
        if (!cooldown.containsKey(abilityName))
            return true;

        CooldownData data = cooldown.get(abilityName);
        return System.currentTimeMillis() - data.lastCooldown > data.recharge;
    }

    public boolean use(Player player, String abilityName, long cooldown) {
        if (cooldownData.containsKey(player.getName()) && getCooldownData(player).containsKey(abilityName))
            return false;

        CooldownData data = new CooldownData();
        data.recharge = cooldown;
        data.lastCooldown = System.currentTimeMillis();

        if (!cooldownData.containsKey(player.getName()))
            cooldownData.put(player.getName(), new HashMap<>());

        cooldownData.get(player.getName()).put(abilityName, data);
        return true;
    }

    @EventHandler
    public void onTick(RepeatEvent event) {
        Iterator<String> playerIterator = cooldownData.keySet().iterator();
        while (playerIterator.hasNext()) {
            String playerName = playerIterator.next();

            Iterator<String> abilityIterator = cooldownData.get(playerName).keySet().iterator();
            while (abilityIterator.hasNext()) {
                String ability = abilityIterator.next();
                CooldownData data = cooldownData.get(playerName).get(ability);

                if (System.currentTimeMillis() - data.lastCooldown > data.recharge) {
                    abilityIterator.remove();

                    Player player = Bukkit.getPlayerExact(playerName);
                    if (player == null)
                        continue;

                    player.sendMessage(ChatColor.WHITE + "You can use " + ChatColor.YELLOW + ability + ChatColor.WHITE + ".");
                }
            }
            if (cooldownData.get(playerName).isEmpty())
                playerIterator.remove();
        }
    }

}
