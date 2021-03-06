package joshie.harvest.player;

import joshie.harvest.api.calendar.CalendarDate;
import joshie.harvest.api.calendar.Season;
import joshie.harvest.api.quests.QuestType;
import joshie.harvest.calendar.CalendarHelper;
import joshie.harvest.core.achievements.HFAchievements;
import joshie.harvest.core.helpers.EntityHelper;
import joshie.harvest.core.network.PacketHandler;
import joshie.harvest.core.util.interfaces.IQuestMaster;
import joshie.harvest.player.relationships.RelationshipDataServer;
import joshie.harvest.player.stats.StatsServer;
import joshie.harvest.player.tracking.TrackingServer;
import joshie.harvest.quests.data.QuestDataServer;
import joshie.harvest.quests.packet.PacketQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerTrackerServer extends PlayerTracker implements IQuestMaster {
    private final QuestDataServer quests;
    private final RelationshipDataServer relationships;
    private final StatsServer stats;
    protected final TrackingServer tracking;

    //References to the player and uuid this refers to
    private EntityPlayerMP player; //No Direct calling, it's a cache value
    private final UUID uuid; //SHOULD NOT BE CALLED, EXCEPT BY GET AND CREATE PLAYER

    public PlayerTrackerServer(@Nullable EntityPlayerMP player, UUID uuid) {
        this.uuid = uuid;
        this.player = player != null ? player: getAndCreatePlayer();
        quests = new QuestDataServer(this);
        relationships = new RelationshipDataServer(this);
        stats = new StatsServer(this);
        tracking = new TrackingServer(this);
    }

    //Pass the world that this player is currently in
    @Override
    public EntityPlayerMP getAndCreatePlayer() {
        if (player == null) {
            player = EntityHelper.getPlayerFromUUID(uuid);
        }

        return player;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public RelationshipDataServer getRelationships() {
        return relationships;
    }

    @Override
    public QuestDataServer getQuests() {
        return quests;
    }

    @Override
    public StatsServer getStats() {
        return stats;
    }

    @Override
    public QuestType getQuestType() {
        return QuestType.PLAYER;
    }

    @Override
    public void sync(EntityPlayer other, PacketQuest packet) {
        if (other != null) PacketHandler.sendToClient(packet, other);
        else {
            EntityPlayerMP player = getAndCreatePlayer();
            if (player != null && player.connection != null && player.connection.netManager != null) {
                PacketHandler.sendToClient(packet, getAndCreatePlayer());
            }
        }
    }

    public TrackingServer getTracking() {
        return tracking;
    }

    public void newDay(CalendarDate yesterday, CalendarDate today) {
        //Add their gold from selling items
        relationships.newDay(yesterday, today);
        EntityPlayerMP player = getAndCreatePlayer();
        if (player != null) {
            long gold = tracking.newDay();
            if (gold > 0) player.addStat(HFAchievements.firstShipping);
            stats.addGold(null, gold);
            if (stats.getGold() >= 1000000) player.addStat(HFAchievements.millionaire);
            relationships.resetStatus(yesterday, player); //Reset the relationship status
            stats.syncGold(player); //Resync the players gold
            if (today.getSeason() == Season.WINTER && today.getDay() == 25) player.addStat(HFAchievements.firstChristmas);
            if (CalendarHelper.getYearsPassed(stats.getBirthday(), today) >= 1) {
                player.addStat(HFAchievements.birthday);
            }
        }
    }

    public void syncPlayerStats(EntityPlayerMP player) {
        //Only sync the stats if the player is online
        if (player.connection != null && player.connection.netManager != null) {
            quests.sync(player);
            relationships.sync(player);
            stats.sync(player);
            tracking.sync(player);
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        quests.readFromNBT(nbt.getCompoundTag("Quests"));
        relationships.readFromNBT(nbt.getCompoundTag("Relationships"));
        stats.readFromNBT(nbt.getCompoundTag("Stats"));
        tracking.readFromNBT(nbt.getCompoundTag("Tracking"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("Quests", quests.writeToNBT(new NBTTagCompound()));
        nbt.setTag("Relationships", relationships.writeToNBT(new NBTTagCompound()));
        nbt.setTag("Stats", stats.writeToNBT(new NBTTagCompound()));
        nbt.setTag("Tracking", tracking.writeToNBT(new NBTTagCompound()));
        return nbt;
    }
}
