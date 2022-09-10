package com.kalle.crazypotioneffects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class to handle all operations on the .yml files.
 * The file playerdata.yml holds all stores necessary
 * information that would get lost with each server
 * restart. The recipe.yml and effects.yml are configurations
 * for the plugin.
 */
public class Config {

    private Main plugin;

    //file and configuration variable for operations on playerdata.yml
    private FileConfiguration playerData;
    private File playerDataFile;

    //file and configuration variable for operations on effects.yml
    private FileConfiguration effects;
    private File effectsFile;

    //file and configuration variable for operations on recipe.yml
    private FileConfiguration recipe;
    private File recipeFile;
    private Material[] ingredients = new Material[9];

    /**
     * The class constructor to pass the Main object in order to get
     * the data folder of the plugin.
     * @param plugin
     */
    public Config(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * A method to set up and load all 3 .yml files (recipe.yml and effects.yml are also getting initialized).
     */
    public void setup() {
        //create data folder where .yml files are stored
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir(); //create data folder
        }

        //load playerdata.yml and set configuration
        playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        if (!playerDataFile.exists()) { //check if file exist
            try {
                playerDataFile.createNewFile(); //create the file
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[CrazyPotionEffects]: playerdata.yml file has been created!");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CrazyPotionEffects] CONFIG ERROR: Could not create playerdata.yml file!");
            }
        }
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        //load effects.yml and set configuration
        effectsFile = new File(plugin.getDataFolder(), "effects.yml");
        if (!effectsFile.exists()) { //check if file exist
            try {
                effectsFile.createNewFile(); //create the file
                effects = YamlConfiguration.loadConfiguration(effectsFile);
                reloadEffects(); //initializes the file with necessary construct
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[CrazyPotionEffects]: effects.yml file has been created!");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CrazyPotionEffects] CONFIG ERROR: Could not create effects.yml file!");
            }
        }
        effects = YamlConfiguration.loadConfiguration(effectsFile);

        //load recipe.yml and set configuration
        recipeFile = new File(plugin.getDataFolder(), "recipe.yml");
        if (!recipeFile.exists()) { //check if file exist
            try {
                recipeFile.createNewFile(); //create the file
                recipe = YamlConfiguration.loadConfiguration(recipeFile);
                reloadRecipe(); //initializes the file with necessary construct
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[CrazyPotionEffects]: recipe.yml file has been created!");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CrazyPotionEffects] CONFIG ERROR: Could not create recipe.yml file!");
            }
        }
        recipe = YamlConfiguration.loadConfiguration(recipeFile);
    }

    /**
     * A method to store the effects of a player in the playerdata.yml file.
     * @param myEffect the effects that need to be stored
     */
    public void storePlayer(MyEffect myEffect) {
        Player player = myEffect.getPlayer();
        String uuid = player.getUniqueId().toString(); //get uuid as identifier for the playerdata.yml
        for (int i = 0; i < 27; i++) { //loop through all possible effects
            PotionEffectType type = EffectController.getEffect(i); //get the effect of the iteration we are currently in
            if (myEffect.getBadEffects().contains(type) || myEffect.getGoodEffects().contains(type)) { //checks if player has the effect
                playerData.set(uuid + ".Effect " + i, true); //player has the effect
                continue;
            }
            playerData.set(uuid + ".Effect " + i, false); //player does not have the effect
        }
        try {
            playerData.save(playerDataFile); //save changes
            playerData = YamlConfiguration.loadConfiguration(playerDataFile); //set configurator to new made changes
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CrazyPotionEffects] CONFIG ERROR: could not save playerdata.yml!");
        }
    }

    /**
     * A method to load the effects of the player from the playerdata.yml file.
     * @param myEffect his effect variable so we can store his effects
     */
    public void loadPlayer(MyEffect myEffect) {
        Player player = myEffect.getPlayer();
        String uuid = player.getUniqueId().toString(); //get uuid as identifier for the playerdata.yml
        if (playerData.get(uuid + ".Effect 0") == null) { //player must be new and get a random effect assigned
            EffectController.newPlayer(myEffect); //random effect assigned to his myEffect
            return; //all done since player can not have any other effects saved
        }
        for (int i = 0; i < 27; i++) { //loop through all possible effects
            PotionEffectType type = EffectController.getEffect(i);
            try {
                boolean assigned = (Boolean) playerData.get(uuid + ".Effect " + i);
                if (!assigned) continue; //player does not have the effect
                if (EffectController.isGood(type)) { //check if the effect is a good or bad effect
                    myEffect.addGoodEffect(type); //effect is good
                } else {
                    myEffect.addBadEffect(type); //effect is bad
                }
            } catch (NullPointerException e) { //effect is not in hash map and therefore not in usage for the game
                continue; //nothing needs to happen
            }
        }
    }

    /**
     * A method to reload the construct of the effects.yml.
     * With every call it initializes the file with the default settings.
     */
    public void reloadEffects() {
        effects.set("Effect 0.Type", "ABSORPTION");
        effects.set("Effect 0.Usage", "good");
        effects.set("Effect 1.Type", "BAD_OMEN");
        effects.set("Effect 1.Usage", "bad");
        effects.set("Effect 2.Type", "BLINDNESS");
        effects.set("Effect 2.Usage", "bad");
        effects.set("Effect 3.Type", "CONDUIT_POWER");
        effects.set("Effect 3.Usage", "good");
        effects.set("Effect 4.Type", "CONFUSION");
        effects.set("Effect 4.Usage", "bad");
        effects.set("Effect 5.Type", "DAMAGE_RESISTANCE");
        effects.set("Effect 5.Usage", "good");
        effects.set("Effect 6.Type", "DARKNESS");
        effects.set("Effect 6.Usage", "bad");
        effects.set("Effect 7.Type", "DOLPHINS_GRACE");
        effects.set("Effect 7.Usage", "good");
        effects.set("Effect 8.Type", "FAST_DIGGING");
        effects.set("Effect 8.Usage", "good");
        effects.set("Effect 9.Type", "FIRE_RESISTANCE");
        effects.set("Effect 9.Usage", "good");
        effects.set("Effect 10.Type", "GLOWING");
        effects.set("Effect 10.Usage", "bad");
        effects.set("Effect 11.Type", "HEALTH_BOOST");
        effects.set("Effect 11.Usage", "good");
        effects.set("Effect 12.Type", "HERO_OF_THE_VILLAGE");
        effects.set("Effect 12.Usage", "good");
        effects.set("Effect 13.Type", "HUNGER");
        effects.set("Effect 13.Usage", "bad");
        effects.set("Effect 14.Type", "INCREASE_DAMAGE");
        effects.set("Effect 14.Usage", "good");
        effects.set("Effect 15.Type", "INVISIBILITY");
        effects.set("Effect 15.Usage", "good");
        effects.set("Effect 16.Type", "JUMP");
        effects.set("Effect 16.Usage", "good");
        effects.set("Effect 17.Type", "LEVITATION");
        effects.set("Effect 17.Usage", "none");
        effects.set("Effect 18.Type", "NIGHT_VISION");
        effects.set("Effect 18.Usage", "good");
        effects.set("Effect 19.Type", "POISON");
        effects.set("Effect 19.Usage", "bad");
        effects.set("Effect 20.Type", "REGENERATION");
        effects.set("Effect 20.Usage", "good");
        effects.set("Effect 21.Type", "SATURATION");
        effects.set("Effect 21.Usage", "good");
        effects.set("Effect 22.Type", "SLOW");
        effects.set("Effect 22.Usage", "bad");
        effects.set("Effect 23.Type", "SLOW_DIGGING");
        effects.set("Effect 23.Usage", "bad");
        effects.set("Effect 24.Type", "SPEED");
        effects.set("Effect 24.Usage", "good");
        effects.set("Effect 25.Type", "WATER_BREATHING");
        effects.set("Effect 25.Usage", "good");
        effects.set("Effect 26.Type", "WEAKNESS");
        effects.set("Effect 26.Usage", "bad");
        try {
            effects.save(effectsFile); //save changes
            effects = YamlConfiguration.loadConfiguration(effectsFile); //set configurator to new made changes
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CrazyPotionEffects] CONFIG ERROR: could not reload effects.yml!");
        }
    }

    /**
     * A method to gather all effects that are supposed to be added to the plugin.
     * If the plugin is not able to properly read an entry, it is skipped.
     * @return a hash map that hold every effects that is added to the plugin, if false it is a bad effect
     * and if true it is a good effect
     */
    public HashMap<PotionEffectType, Boolean> getEffects() {
        HashMap<PotionEffectType, Boolean> viableEffects = new HashMap<>();
        for (int i = 0; i < 27; i++) {
            try {
                String name = effects.get("Effect " + i + ".Type").toString(); //get the name of the effect
                PotionEffectType type = PotionEffectType.getByName(name); //get the potion type by the name string
                String usage = effects.get("Effect " + i + ".Usage").toString(); //get the usage value to decide how it is added to the plugin
                if (usage.equalsIgnoreCase("none")) continue; //effect shall not be in the game
                if (usage.equalsIgnoreCase("good")) viableEffects.put(type,true); //effect is a good effect in the game
                if (usage.equalsIgnoreCase("bad")) viableEffects.put(type,false); //effect is a bad effect in the game
            } catch (NullPointerException e) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CrazyPotionEffects] CONFIG ERROR: could not load an effect from effects.yml! (Please check the effects.yml file and restart the server)");
                continue; //could not read the effect properly and we continue
            }
        }
        return viableEffects; //result hash map
    }

    /**
     * A method to reload the recipe.yml. It is also used to read the ingredients.
     * Gets also called if the file could not be properly read and restores the
     * default values.
     */
    public void reloadRecipe() {
        recipe.set("Ingredient 1", "AIR");
        ingredients[0] = Material.AIR;
        recipe.set("Ingredient 2", "AIR");
        ingredients[1] = Material.AIR;
        recipe.set("Ingredient 3", "AIR");
        ingredients[2] = Material.AIR;
        recipe.set("Ingredient 4", "DIAMOND");
        ingredients[3] = Material.DIAMOND;
        recipe.set("Ingredient 5", "AIR");
        ingredients[4] = Material.AIR;
        recipe.set("Ingredient 6", "DIAMOND");
        ingredients[5] = Material.DIAMOND;
        recipe.set("Ingredient 7", "AIR");
        ingredients[6] = Material.AIR;
        recipe.set("Ingredient 8", "DIAMOND");
        ingredients[7] = Material.DIAMOND;
        recipe.set("Ingredient 9", "AIR");
        ingredients[8] = Material.AIR;
        try {
            recipe.save(recipeFile); //save changes
            recipe = YamlConfiguration.loadConfiguration(recipeFile); //set configurator to new made changes
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CrazyPotionEffects] CONFIG ERROR: could not reload recipe.yml!");
        }

    }

    /**
     * A method to get the recipe that is getting set by the values in the
     * recipe.yml file.
     * @return
     */
    public Material[] getRecipe() {
        try {
            for (int i = 0; i < 9; i++) {
                String string = recipe.get("Ingredient " + (i+1)).toString(); //read value
                Material material = Material.valueOf(string);
                ingredients[i] = material; //add material to ingredient list
            }
        } catch (IllegalArgumentException | NullPointerException e) { //recipe could not be properly read
            reloadRecipe(); //restore default
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CrazyPotionEffects] CONFIG ERROR: could not load recipe.yml properly! (file restored to default)");
        }
        return ingredients;
    }

}
