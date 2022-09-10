package com.kalle.crazypotioneffects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A class that holds all effects of a player and also implements a task,
 * so that the effects last forever.
 */
public class MyEffect {

    private Main plugin;
    private Config config; //config variable to operate on the .yml files

    private static ArrayList<MyEffect> itemList = new ArrayList<>(); //list of all MyEffects so we can call them by passing a player

    private Player player; //player that is owner of the effects
    private ArrayList<PotionEffectType> badEffects = new ArrayList<>(); //his bad effects
    private ArrayList<PotionEffectType> goodEffects = new ArrayList<>(); //his good effects
    private int taskID; //task ID to be able to cancel his Thread when he goes offline

    /**
     * The class constructor to pas the config so we can make changes to the .yml files.
     * Also starts the Thread to keep the effects of the player running.
     * @param plugin main object to be able to schedule the task
     * @param config to be able to operate on the .yml files
     * @param player to set the ownership of this object
     */
    public MyEffect(Main plugin, Config config, Player player) {
        this.plugin = plugin;
        this.config = config;
        this.player = player;
        itemList.add(this); //add to the list, so we can call it by their player
        config.loadPlayer(this); //load effects from the config.yml into goodEffects and badEffects
        startEffects(); //starts thread to make effects last infinite
    }

    /**
     * The getter method of the players good effects.
     * @return good effects of the player
     */
    public ArrayList<PotionEffectType> getGoodEffects() {
        return goodEffects;
    }

    /**
     * The getter method of the players bad effects.
     * @return bad effects of the player
     */
    public ArrayList<PotionEffectType> getBadEffects() {
        return badEffects;
    }

    /**
     * The setter method of the players good effects.
     * @param effect the effect that needs to be added
     */
    public void addGoodEffect(PotionEffectType effect) {
        goodEffects.add(effect);
    }

    /**
     * The setter method of the players bad effects.
     * @param effect the effect that needs to be added
     */
    public void addBadEffect(PotionEffectType effect) {
        badEffects.add(effect);
    }

    /**
     * The setter method of the players good effects.
     * @param effect the effect that needs to be removed
     */
    public void removeGoodEffect(PotionEffectType effect) {
        goodEffects.remove(effect);
    }

    /**
     * The setter method of the players bad effects.
     * @param effect the effect that needs to be removed
     */
    public void removeBadEffect(PotionEffectType effect) {
        badEffects.remove(effect);
    }

    /**
     * The method that creates the task and starts the Thread
     * to keep the effects of the player running.
     */
    public void startEffects() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (PotionEffectType i : badEffects) {
                    player.addPotionEffect(new PotionEffect(i,140,0));
                }
                for (PotionEffectType i : goodEffects) {
                    player.addPotionEffect(new PotionEffect(i,140,0));
                }
            }
        }, 0L, 20L);
    }

    /**
     * The method to cancel the Thread because the player
     * went offline and the effects no longer need to be
     * applied.
     */
    public void stopEffects() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    /**
     * The getter method to get the owner of the effects.
     * @return player that is the owner of the effects
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * A static method to call a MyEffect object by its owner.
     * @param player the owner of the object that is needed
     * @return the MyEffect object of the passed player
     * @throws NoSuchElementException player has no object assigned and the plugin is getting an error
     */
    public static MyEffect getMyEffect(Player player) throws NoSuchElementException {
        for (MyEffect i : itemList) {
            if (i.getPlayer() == player) return i; //we got the object of the right player
        }
        throw new NoSuchElementException("The player: '" + player.getUniqueId() + "' has no object assigned.");
    }

    /**
     * A method to call when the player logs out or the server gets reloaded.
     * Thread gets stopped. Object gets removed from the list because it does
     * not need to be called anymore. And changes getting saved to the
     * playerdata.yml file.
     */
    public void playerLogOut() {
        config.storePlayer(this); //save changes of this object
        itemList.remove(this); //remove this object
        stopEffects(); //stop the task
    }

}
