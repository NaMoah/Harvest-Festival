package joshie.harvest.player.relationships;

import joshie.harvest.api.HFApi;
import joshie.harvest.api.relations.IRelationships;
import joshie.harvest.core.util.annotations.HFApiImplementation;
import joshie.harvest.npc.HFNPCs;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

@HFApiImplementation
public class RelationshipHelper implements IRelationships {
    public static final RelationshipHelper INSTANCE = new RelationshipHelper();

    @Override
    public void adjustRelationship(EntityPlayer player, UUID key, int amount) {
        HFApi.player.getRelationsForPlayer(player).affectRelationship(key, amount);
    }

    @Override
    public int getRelationship(EntityPlayer player, UUID key) {
        return HFApi.player.getRelationsForPlayer(player).getRelationship(key);
    }

    @Override
    public int getMaximumRelationshipValue() {
        return HFNPCs.MAX_FRIENDSHIP;
    }
}
