package com.kalle.crazypotioneffects;

import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A class to handle all operations on the MyEffect objects, also
 * important for distinguishing the bad from the good effects.
 */
public class EffectController {

    private static final PotionEffectType potionID[] = new PotionEffectType[27]; //array to assign identifiers to the potion types

    private static HashMap<PotionEffectType, Boolean> effects = new HashMap<>(); //hash map to initialize all the good and bad effects added to the plugin

    private static ArrayList<PotionEffectType> goodPool = new ArrayList<>(); //to store the pool of all possible good effects
    private static ArrayList<PotionEffectType> badPool = new ArrayList<>(); //to store the pool of all possible bad effects

    /**
     * A method to initialize all good and bad effects, also assigns the IDs to the potion types.
     * Uses the hash map to decide rather good or bad and if it is even supposed to be added.
     * @param viableEffects
     */
    public static void initialize(HashMap<PotionEffectType, Boolean> viableEffects) {

        effects = viableEffects;

        //assign IDs to the potion types
        potionID[0] = PotionEffectType.ABSORPTION;
        potionID[1] = PotionEffectType.BAD_OMEN;
        potionID[2] = PotionEffectType.BLINDNESS;
        potionID[3] = PotionEffectType.CONDUIT_POWER;
        potionID[4] = PotionEffectType.CONFUSION;
        potionID[5] = PotionEffectType.DAMAGE_RESISTANCE;
        potionID[6] = PotionEffectType.DARKNESS;
        potionID[7] = PotionEffectType.DOLPHINS_GRACE;
        potionID[8] = PotionEffectType.FAST_DIGGING;
        potionID[9] = PotionEffectType.FIRE_RESISTANCE;
        potionID[10] = PotionEffectType.GLOWING;
        potionID[11] = PotionEffectType.HEALTH_BOOST;
        potionID[12] = PotionEffectType.HERO_OF_THE_VILLAGE;
        potionID[13] = PotionEffectType.HUNGER;
        potionID[14] = PotionEffectType.INCREASE_DAMAGE;
        potionID[15] = PotionEffectType.INVISIBILITY;
        potionID[16] = PotionEffectType.JUMP;
        potionID[17] = PotionEffectType.LEVITATION;
        potionID[18] = PotionEffectType.NIGHT_VISION;
        potionID[19] = PotionEffectType.POISON;
        potionID[20] = PotionEffectType.REGENERATION;
        potionID[21] = PotionEffectType.SATURATION;
        potionID[22] = PotionEffectType.SLOW;
        potionID[23] = PotionEffectType.SLOW_DIGGING;
        potionID[24] = PotionEffectType.SPEED;
        potionID[25] = PotionEffectType.WATER_BREATHING;
        potionID[26] = PotionEffectType.WEAKNESS;

        //initialize effects to good or bad or not add it to the game
        for (PotionEffectType i : potionID) { //loop through all possible effects
            if (viableEffects.get(i) == null) continue; //effects that are not getting added
            if (viableEffects.get(i) == true) goodPool.add(i); //good effects
            if (viableEffects.get(i) == false) badPool.add(i); //bad effects
        }

    }

    /**
     * A method to get the effect mapped to the passed ID.
     * @param ID ID of the wanted potion type
     * @return potion type with passed ID
     */
    public static PotionEffectType getEffect(int ID) {
        return potionID[ID];
    }

    /**
     * A method to check if a potion type is a good
     * or a bad effect.
     * @param type potion type that needs to be checked
     * @return true if good effect and false if bad effect
     */
    public static boolean isGood(PotionEffectType type) {
        return effects.get(type);
    }

    /**
     * A method that gets all remaining good effects from the pool,
     * so that no effect gets doubled.
     * @param goodEffects good effects that the player already has
     * @return effects that the player can still obtain
     */
    public static ArrayList<PotionEffectType> makeGoodList(ArrayList<PotionEffectType> goodEffects) {
        ArrayList<PotionEffectType> result = new ArrayList<>();
        for (int i = 0; i < goodPool.size(); i++) { //loop through pool of good effects
            if (goodEffects.contains(goodPool.get(i))) continue; //check if player already has effect
            result.add(goodPool.get(i)); //add effect if player does not have the effect yet
        }
        return result;
    }

    /**
     * A method that gets all remaining bad effects from the pool,
     * so that no effect gets doubled.
     * @param badEffects bad effects that the player already has
     * @return effects that the player can still obtain
     */
    public static ArrayList<PotionEffectType> makeBadList(ArrayList<PotionEffectType> badEffects) {
        ArrayList<PotionEffectType> result = new ArrayList<>();
        for (int i = 0; i < badPool.size(); i++) { //loop through pool of bad effects
            if (badEffects.contains(badPool.get(i))) continue; //check if player already has effect
            result.add(badPool.get(i)); //add effect if player does not have the effect yet
        }
        return result;
    }

    /**
     * A method to choose randomly an effect from the passed
     * array list.
     * @param effects effects that can be chosen from
     * @return a random effect of the passed list
     * @throws IndexOutOfBoundsException if the list is empty and no effect can be returned
     */
    public static PotionEffectType randomEffect(ArrayList<PotionEffectType> effects) throws IndexOutOfBoundsException{
        if (effects.isEmpty()) throw new IndexOutOfBoundsException("The passed ArrayList is empty and no element can be returned."); //list is empty
        Random random = new Random();
        int index = random.nextInt(effects.size());
        return effects.get(index); //return random effect
    }

    /**
     * A method to get the difference of two sets.
     * @param first first set that gets subtracted from
     * @param second second set that gets subtracts from the other set
     * @return difference of the two passed sets
     */
    public static ArrayList<PotionEffectType> setMinus(ArrayList<PotionEffectType> first, ArrayList<PotionEffectType> second) {
        ArrayList<PotionEffectType> result = first;
        for(PotionEffectType i : second) {  //loop through second set
            if (result.contains(i)) result.remove(i); //remove if the element is also in the second set
        }
        return result;
    }

    /**
     * A method that adds one random effect when a player
     * joins the server for the first time.
     * @param myEffect effects of the player
     */
    public static void newPlayer(MyEffect myEffect) {
        Random random = new Random();
        int n = random.nextInt(2); //50-50 chance of adding good or bad effect
        if (n < 1) {
            PotionEffectType effect = randomEffect(makeGoodList(new ArrayList<PotionEffectType>())); //get random good effect
            myEffect.addGoodEffect(effect); //add the good effect
            return;
        }
        PotionEffectType effect = randomEffect(makeBadList(new ArrayList<PotionEffectType>())); //get random bad effect
        myEffect.addBadEffect(effect); //add the bad effect
    }

    /**
     * A method to possibly cure one negative effect of a player.
     * It has a 50% to not cure the effect, but instead add one
     * negative effect.
     * @param myEffect effects of the player
     */
    public static void cureEffect(MyEffect myEffect) {
        ArrayList<PotionEffectType> badEffects = myEffect.getBadEffects(); //get the bad effects of the player
        if (badEffects.isEmpty()) return; //player has no effects that can be possibly cured
        Random random = new Random();
        int n = random.nextInt(2);
        if (n < 1) { //50% chance of curing
            PotionEffectType effect = randomEffect(badEffects); //pick random bad effect
            myEffect.removeBadEffect(effect); //remove bad effect
            return;
        }
        myEffect.addBadEffect(randomEffect(makeBadList(badEffects))); //add bad effect (curing failed)
    }

    /**
     * A method to either remove a good effect if he has at least one,
     * or add a negative effect if he has no good effects.
     * @param myEffect effects of the player
     */
    public static void minusEffect(MyEffect myEffect) {
        try {
            ArrayList<PotionEffectType> goodEffects = myEffect.getGoodEffects();
            ArrayList<PotionEffectType> badEffects = myEffect.getBadEffects();
            if (goodEffects.isEmpty()) { //check if player has good effects left
                myEffect.addBadEffect(randomEffect(makeBadList(badEffects))); //add bad effect
                return;
            }
            myEffect.removeGoodEffect(randomEffect(goodEffects)); //player has good effect left and it gets removed
        } catch (IndexOutOfBoundsException e) { //case can occur normally if player already has all effects or has no effects
            return; //nothing happens
        }
    }

    /**
     * A method to either remove a bad effect if he has at least one,
     * or add a good effect if he has no negative effects.
     * @param myEffect effects of the player
     */
    public static void plusEffect(MyEffect myEffect) {
        try {
            ArrayList<PotionEffectType> goodEffects = myEffect.getGoodEffects();
            ArrayList<PotionEffectType> badEffects = myEffect.getBadEffects();
            if (badEffects.isEmpty()) { //check if player has bad effects left
                myEffect.addGoodEffect(randomEffect(makeGoodList(goodEffects))); //add good effect
                return;
            }
            myEffect.removeBadEffect(randomEffect(badEffects)); //player has bad effect left and it gets removed
        } catch (IndexOutOfBoundsException e) { //case can occur normally if player already has all effects or has no effects
            return; //nothing happens
        }
    }

    /**
     * A method to possibly swap two effects of two different players.
     * It has a 15% chance to swap the effects. Either swaps a good effect
     * with negative effect or good effect with good effect.
     * @param first effects of the player that killed
     * @param second effects of the player that died
     */
    public static void swapEffect(MyEffect first, MyEffect second) {
        Random random = new Random();
        int n = random.nextInt(100);
        if (n >= 15) return; //85% chance to do nothing
        ArrayList<PotionEffectType> firstTmp = setMinus(second.getGoodEffects(),first.getGoodEffects()); //get all good effects that second has, but first do not
        ArrayList<PotionEffectType> secondTmp = setMinus(first.getBadEffects(),second.getBadEffects()); //get all bad effects that first has, but second do not
        if (!secondTmp.isEmpty() && !firstTmp.isEmpty()) { //check if there are items that can be swapped
            PotionEffectType firstItem = randomEffect(firstTmp); //get random item
            PotionEffectType secondItem = randomEffect(secondTmp); //get random item
            //swap the items
            first.addGoodEffect(firstItem);
            second.removeGoodEffect(firstItem);
            first.removeBadEffect(secondItem);
            second.addBadEffect(secondItem);
            return; //swap is completed
        }
        secondTmp = setMinus(first.getGoodEffects(),second.getGoodEffects()); //get all good effects that first has, but second do not
        if (!firstTmp.isEmpty() && !secondTmp.isEmpty()) { //check if there are items that can be swapped
            PotionEffectType firstItem = randomEffect(firstTmp); //get random item
            PotionEffectType secondItem = randomEffect(secondTmp); //get random item
            //swap the items
            first.addGoodEffect(firstItem);
            second.removeGoodEffect(firstItem);
            first.removeGoodEffect(secondItem);
            second.addGoodEffect(secondItem);
            //swap is completed
        }
    }

}
