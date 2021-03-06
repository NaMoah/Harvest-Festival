package joshie.harvest.npc.gui;

import joshie.harvest.HarvestFestival;
import joshie.harvest.api.HFApi;
import joshie.harvest.api.npc.gift.IGiftHandler.Quality;
import joshie.harvest.core.HFTrackers;
import joshie.harvest.core.helpers.TextHelper;
import joshie.harvest.npc.HFNPCs;
import joshie.harvest.npc.NPCRegistry;
import joshie.harvest.npc.entity.EntityNPC;
import joshie.harvest.tools.ToolHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.util.Locale;

public class GuiNPCGift extends GuiNPCChat {
    private final ItemStack gift;
    private final Quality value;

    public GuiNPCGift(EntityPlayer player, EntityNPC npc, EnumHand hand) {
        super(player, npc, hand, -1, false);
        gift = player.getHeldItem(hand).copy();
        value = npc.getNPC().getGiftValue(gift);
    }

    @Override
    public String getScript() {
        if (NPCRegistry.INSTANCE.getGifts().isBlacklisted(gift)) return TextHelper.getSpeech(npc, "gift.no");
        if (ToolHelper.isBlueFeather(gift)) {
            int relationship = HFApi.player.getRelationsForPlayer(player).getRelationship(npc.getNPC().getUUID());
            if (relationship >= HFNPCs.MARRIAGE_REQUIREMENT && npc.getNPC().isMarriageCandidate()) {
                return TextHelper.getSpeech(npc, "marriage.accept");
            } else return TextHelper.getSpeech(npc, "marriage.reject");
        } else if (HFTrackers.getClientPlayerTracker().getRelationships().gift(player, npc.getNPC().getUUID(), value.getRelationPoints())) {
            return TextHelper.getSpeech(npc, "gift." + value.name().toLowerCase(Locale.ENGLISH));
        } else return TextHelper.getSpeech(npc, "gift.reject");
    }

    @Override
    public void endChat() {
        player.closeScreen();

        if (nextGui != -1) {
            player.openGui(HarvestFestival.instance, nextGui, player.worldObj, npc.getEntityId(), 0, -1);
        }
    }
}