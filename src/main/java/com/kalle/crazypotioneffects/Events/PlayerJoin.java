package com.kalle.crazypotioneffects.Events;

import com.kalle.crazypotioneffects.Config;
import com.kalle.crazypotioneffects.Main;
import com.kalle.crazypotioneffects.MyEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * A class, which implements the listener that detects the event when a player joins.
 */
public class PlayerJoin implements Listener {

    private Main plugin;
    private Config config; //config variable to be able to operate on the .yml files

    /**
     * A class constructor in order to pass the config, so
     * we can operate on the .yml files.
     * @param plugin main object to be able to pass it to next constructor
     * @param config config so we can operate on the .yml files
     */
    public PlayerJoin(Main plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
    }

    /**
     * The method that implements what happens when the event gets triggered.
     * @param event event that gets triggered
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer(); //get the player that triggered the event
        MyEffect myEffect = new MyEffect(plugin, config, player); //create MyEffect object for the player
    }

}
