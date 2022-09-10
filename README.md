# CrazyPotionEffects

## About

The reddit user u/B3n5__ made a post on the subreddit r/MinecraftPlugins in which he described a plugin like this. The plugin adds permanent potion effects, which are obtained by either dying or killing a player. It also adds an item, which can cure the negative effects. The plugin can be configured by the effects.yml (which effects are getting added) and recipe.yml (recipe of the added item).

## Features

- when a player logs on for the first time, he either gets a random positive or random negative effect

- when a player dies and he has at least one positive effect, one random good effect gets removed

- when a player dies and he as no positive effect, a random negative effect gets added

- when a player kills a player and he has at least one negative effect, one random negative effect gets removed

- when a player kills a player and he has no negative effect, a random positive effects gets added

- when a plyer kills a player he has a 15% chance to swap one of his negative effects with a good effect of his victim (can also swap good for good if the killer has no negative effect)

- an item is added, which can be crafted and cure with a 50% chance one random negative effect, but if does not cure it adds one random negative effect

## Installation

In the project folder "CrazyPotionEffects/target/" is a compiled .jar file, which you can simply drag in your plugins folder of your server. You can also directly download the .jar with this link: https://github.com/kfc-manager/CrazyPotionEffects/raw/main/target/CrazyPotionEffects-1.0.jar . Reload the server and the console should say: "CrasyPotionEffects has been enabled!". Also in your plugins folder should be a "CrazyPotionEffects" folder generated, which holds three .yml files. It is recommended that you do not make changes to the playerdata.yml file. However effects.yml and recipe.yml serve a configuration purpose for the plugin (but they do not need to be configured, they have default settings). Follow the **Configuration** instructions below, if you want to make changes to the plugin.

## Configuration

NOTE: Make sure to reload the server after making changes to the configuration files.

**effects.yml**

In this file you can decide which of the effects are getting added to the plugin and if they are either a positive or a negative effect. Make sure that you only change the "Usage" of the effect entry. Do not add or delete entrys. Also do not change the type.
- to not add an effect to the plugin, set "Usage" to "none"
- to add an effect as positive effect to the plugin, set "Usage" to "good"
- to add an effect as negative effect to the plugin, set "Usage" to "bad"

If an key word could not be read properly the effect will not be added to the plugin (make sure that you do not have any typos in the key words).

**recipe.yml**

In this file you can set the recipe of the item (cure described above). If a mistake was made in this file and the ingredients could not be properly read, the file is getting restored to default. Only change the ingredient entrys, do not delete them! Make sure to use the right material name. You can find all possible materials on here: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html .
