package com.kalle.crazypotioneffects;

import com.kalle.crazypotioneffects.Events.PlayerDeath;
import com.kalle.crazypotioneffects.Events.PlayerJoin;
import com.kalle.crazypotioneffects.Events.PlayerQuit;
import com.kalle.crazypotioneffects.Items.Cure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Main class that holds the startup and shutdown logic of the plugin.
 */
public final class Main extends JavaPlugin {

    private Config config = new Config(this); //create config variable

    @Override
    public void onEnable() {
        // Plugin startup logic
        config.setup(); //create and load files
        EffectController.initialize(config.getEffects()); //initialize to be able to distinguish bad and good effects
        //register Events:
        getServer().getPluginManager().registerEvents(new PlayerDeath(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this,config),this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(),this);
        getServer().getPluginManager().registerEvents(new Cure(this,config),this); //also loads custom item and its event

        for (Player i : Bukkit.getOnlinePlayers()) { //in case of a server reload
            MyEffect myEffect = new MyEffect(this, config, i); //load each effect of every player that is still on the server
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "CrazyPotionEffects has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player i : Bukkit.getOnlinePlayers()) { //when server gets shutdown changes must be saved
            MyEffect myEffect = MyEffect.getMyEffect(i);
            myEffect.playerLogOut();
        }
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "CrazyPotionEffects has been disabled!");
    }
}
