package com.kalle.crazypotioneffects.Events;

import com.kalle.crazypotioneffects.MyEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A class, which implements the listener that detects the event when a player quits.
 */
public class PlayerQuit implements Listener {

    /**
     * The method that implements what happens when the event gets triggered.
     * @param event event that gets triggered
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer(); //get the player that triggered the event
        MyEffect myEffect = MyEffect.getMyEffect(player); //get the MyEffect object of the player, so we can call method on the object
        myEffect.playerLogOut(); //player logs out so his MyEffect object is no longer needed
    }

}
