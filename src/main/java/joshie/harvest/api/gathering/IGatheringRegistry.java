package joshie.harvest.api.gathering;

import joshie.harvest.api.calendar.Season;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** This is for registering blocks to be generated by the gathering system **/
public interface IGatheringRegistry {
    /** Register a block state to be generated in a specific season
     * @param season the season this should generate in
     * @param state the block state to generate
     * @param weight weight for this to generate **/
    void registerGathering(Season season, IBlockState state, double weight);

    /** Register a block state to be generated in any season
     * @param state the block state to generate
     * @param weight weight for this to generate**/
    void registerGathering(IBlockState state, double weight);

    /** Returns a random block state for this season
     * @param world the world object
     * @param season the current season
     * @return a block state */
    IBlockState getRandomStateForSeason(World world, Season season);
}