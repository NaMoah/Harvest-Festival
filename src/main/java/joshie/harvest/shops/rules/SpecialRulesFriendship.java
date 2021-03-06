package joshie.harvest.shops.rules;

import joshie.harvest.api.HFApi;
import joshie.harvest.api.core.ISpecialPurchaseRules;
import joshie.harvest.api.npc.INPC;
import joshie.harvest.npc.NPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SpecialRulesFriendship implements ISpecialPurchaseRules {
    private final NPC npc;
    private final int relationship;

    public SpecialRulesFriendship(INPC npc, int relationship) {
        this.npc = (NPC) npc;
        this.relationship = relationship;
    }

    @Override
    public boolean canBuy(World world, EntityPlayer player, int amount) {
        return HFApi.player.getRelationsForPlayer(player).getRelationship(npc.getUUID()) >= relationship;
    }
}
