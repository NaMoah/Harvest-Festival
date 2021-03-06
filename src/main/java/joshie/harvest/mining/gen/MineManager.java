package joshie.harvest.mining.gen;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import joshie.harvest.core.HFTrackers;
import joshie.harvest.core.helpers.NBTHelper;
import joshie.harvest.core.util.annotations.HFEvents;
import joshie.harvest.mining.MiningHelper;
import joshie.harvest.npc.HFNPCs;
import joshie.harvest.npc.NPC;
import joshie.harvest.npc.NPCHelper;
import joshie.harvest.npc.entity.EntityNPC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;

@HFEvents
public class MineManager extends WorldSavedData {
    public static final int CHUNK_BOUNDARY = 10;
    private static final TIntObjectMap<TIntObjectMap<IBlockState[][]>> generation = new TIntObjectHashMap<>();
    private static final TIntObjectMap<int[]> coordinates = new TIntObjectHashMap<>();
    private TIntObjectMap<TIntObjectMap<BlockPos>> portalCoordinates = new TIntObjectHashMap<>();
    private TIntSet generated = new TIntHashSet();

    public MineManager(String string) {
        super(string);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        portalCoordinates = NBTHelper.readPositionCollection(tag.getTagList("PortalCoordinates", 10));
        if (tag.hasKey("GeneratedMiner")) {
            generated = new TIntHashSet(tag.getIntArray("GeneratedMiner"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setTag("PortalCoordinates", NBTHelper.writePositionCollection(portalCoordinates));
        tag.setIntArray("GeneratedMiner", generated.toArray());
        return tag;
    }

    public void onTeleportToMine(World world, int mineID) {
        if (!generated.contains(mineID)) {
            BlockPos pos = getSpawnCoordinateForMine(mineID, 1);
            EntityNPC entity = NPCHelper.getEntityForNPC(world, (NPC) HFNPCs.MINER);
            pos = MiningHelper.modifySpawnAndPlayerRotation((WorldServer)world, pos, entity);
            entity.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            entity.resetSpawnHome();
            world.spawnEntityInWorld(entity);
            generated.add(mineID);
        }

        markDirty();
    }

    static boolean areCoordinatesGenerated(World world, int mineID, int floor) {
        return HFTrackers.getMineManager(world).getCoordinateMap(mineID).containsKey(floor);
    }

    BlockPos getSpawnCoordinateForMine(int mineID, int floor) {
        BlockPos ret = getCoordinateMap(mineID).get(floor);
        if (ret == null) {
            return new BlockPos(0, 254, mineID * CHUNK_BOUNDARY * 16);
        }

        return ret;
    }

    private TIntObjectMap<BlockPos> getCoordinateMap(int mineID) {
        TIntObjectMap<BlockPos> map = portalCoordinates.get(mineID);
        if (map == null) {
            map = new TIntObjectHashMap<>();
            portalCoordinates.put(mineID, map);
        }

        return map;
    }

    void setSpawnForMine(int mineID, int floor, int x, int y, int z) {
        getCoordinateMap(mineID).putIfAbsent(floor, new BlockPos(x, y, z));
        markDirty();
    }

    static TIntObjectMap<IBlockState[][]> getStateMap(int mapIndex) {
        return MineManager.generation.get(mapIndex);
    }

     static void putStateMap(int mapIndex, TIntObjectMap<IBlockState[][]>  map) {
         MineManager.generation.put(mapIndex, map);
    }

    static boolean containsStateKey(int mapIndex) {
        return MineManager.generation.containsKey(mapIndex);
    }

    static boolean containsCoordinatesKey(int mapIndex) {
        return MineManager.coordinates.containsKey(mapIndex);
    }

    static void putCoordinates(int mapIndex, int[] coordinates) {
        MineManager.coordinates.put(mapIndex, coordinates);
    }

    static int getCoordinates(int mapIndex, int position) {
        return MineManager.coordinates.get(mapIndex)[position];
    }
}
