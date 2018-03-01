package com.reynke.sloud.users.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.reynke.sloud.users.controller.UserController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class UserListener implements Listener {

    private UserController userController;

    @Inject
    public UserListener(UserController userController) {
        this.userController = userController;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        userController.joinAction(playerJoinEvent);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        userController.quitAction(playerQuitEvent);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerKick(PlayerKickEvent playerKickEvent) {
        userController.quitAction(playerKickEvent);
    }
}
