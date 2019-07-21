package io.devcrafters.bukkitlibrary.cooldown;

import io.devcrafters.bukkitlibrary.mock.MockFactory;
import io.devcrafters.bukkitlibrary.mock.MockPlayer;
import io.devcrafters.bukkitlibrary.mock.MockPlugin;
import io.devcrafters.bukkitlibrary.repeater.RepeatEvent;
import org.junit.Assert;
import org.junit.Test;

public class CooldownLibraryTest {

    private static final String COOLDOWN_NAME = "TEST_COOLDOWN";

    @Test
    public void useTest() throws InterruptedException {
        MockPlugin plugin = MockFactory.createPlugin();
        MockPlayer player = MockFactory.createPlayer();

        Assert.assertEquals(player.getName(), MockPlayer.PLAYER_TEST_NAME);

        MockFactory.addPlayer(plugin.getServer(), player);

        CooldownLibrary cooldownLibrary = new CooldownLibrary(plugin);

        Assert.assertTrue(cooldownLibrary.usable(player, COOLDOWN_NAME));

        Assert.assertTrue(cooldownLibrary.use(player, COOLDOWN_NAME, 1000L));
        Assert.assertFalse(cooldownLibrary.use(player, COOLDOWN_NAME, 1000L));

        Thread.sleep(2000L);

        cooldownLibrary.onTick(new RepeatEvent());

        Assert.assertTrue(cooldownLibrary.use(player, COOLDOWN_NAME, 1000L));
    }

}
