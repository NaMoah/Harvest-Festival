package joshie.harvest.api.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/** This is for creating special purchasing rules for players,
 *  for example not being able to buy something until you've married someone */
public interface ISpecialPurchaseRules {
    /** Whether this can currently be bought or not
     *  @param world    the world object
     *  @param player   the player object
     *  @param amount   the amount of items attempting to be bought **/
    boolean canBuy(World world, EntityPlayer player, int amount);
}
