package com.kalle.crazypotioneffects.Events;

import com.kalle.crazypotioneffects.EffectController;
import com.kalle.crazypotioneffects.MyEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * A class, which implements the listener that detects the event when a player dies.
 */
public class PlayerDeath implements Listener {

    /**
     * The method that implements what happens when the event gets triggered.
     * @param event event that gets triggered
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player deadPlayer = (Player) event.getEntity(); //get the player that triggered the event
        MyEffect deadEffect = MyEffect.getMyEffect(deadPlayer); //get effects of the dead player
        EffectController.minusEffect(deadEffect); //subtract effect from the player because he died
        if (!(deadPlayer.getKiller() instanceof Player)) return; //check if killer is a player, if not we are done
        Player killer = (Player) deadPlayer.getKiller(); //get the player that killed
        MyEffect killerEffect = MyEffect.getMyEffect(killer); //get the effects of the killer
        EffectController.plusEffect(killerEffect); //add effect because he killed another player
        EffectController.swapEffect(killerEffect,deadEffect); //they now have 15% chance to swap effects
    }

}
