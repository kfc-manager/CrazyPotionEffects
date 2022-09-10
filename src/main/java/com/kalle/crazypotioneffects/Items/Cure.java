package com.kalle.crazypotioneffects.Items;

import com.kalle.crazypotioneffects.Config;
import com.kalle.crazypotioneffects.EffectController;
import com.kalle.crazypotioneffects.Main;
import com.kalle.crazypotioneffects.MyEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

/**
 * A class to implement te custom item. The item is supposed
 * to possibly cure a negative effect.
 */
public class Cure implements Listener {

    private ItemStack item = new ItemStack(Material.POTION,1); //set materila of the item
    private Main plugin;
    private Config config; //config variable to operate on the .yml files

    /**
     * The class constructor to set the item metadata and pass the
     * config variable to operate on the .yml files.
     * @param plugin to register the item and its recipe
     * @param config to operate on the .yml files
     */
    public Cure(Main plugin, Config config) {
        this.plugin = plugin;
        ItemMeta meta = item.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        pmeta.setBasePotionData(new PotionData(PotionType.WATER)); //set item to water potion
        meta.setDisplayName(ChatColor.WHITE + "Cure"); //set name
        //create lore for item
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "The cure has a 50% chance to"); //line 1
        lore.add(ChatColor.GRAY + "cure a negative effect."); //line 2
        lore.add(ChatColor.GRAY + "But be careful! If it does not"); //line 3
        lore.add(ChatColor.GRAY + "cure an effect, it adds one!"); //line 4
        meta.setLore(lore);

        item.setItemMeta(meta); //set changes to item

        //create recipe for item
        NamespacedKey key = new NamespacedKey(plugin,"cure");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("123", "456", "789");
        Material[] ingredients = config.getRecipe();
        recipe.setIngredient('1',ingredients[0]);
        recipe.setIngredient('2',ingredients[1]);
        recipe.setIngredient('3',ingredients[2]);
        recipe.setIngredient('4',ingredients[3]);
        recipe.setIngredient('5',ingredients[4]);
        recipe.setIngredient('6',ingredients[5]);
        recipe.setIngredient('7',ingredients[6]);
        recipe.setIngredient('8',ingredients[7]);
        recipe.setIngredient('9',ingredients[8]);
        Bukkit.addRecipe(recipe); //register recipe
    }

    /**
     * The method that implements what happens when the item gets used.
     * @param event event that gets triggered by the item usage
     */
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Cure")) {
            Player player = event.getPlayer();
            MyEffect myEffect = MyEffect.getMyEffect(player);
            EffectController.cureEffect(myEffect); //possibly cure an negative effect
        }
    }

}
