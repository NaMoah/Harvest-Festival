package joshie.harvest.animals;

import joshie.harvest.animals.item.ItemAnimalTool.Tool;
import joshie.harvest.animals.packet.PacketSyncAnimal;
import joshie.harvest.animals.tracker.AnimalTrackerServer;
import joshie.harvest.api.HFApi;
import joshie.harvest.api.animals.AnimalAction;
import joshie.harvest.api.animals.AnimalStats;
import joshie.harvest.core.HFTrackers;
import joshie.harvest.core.helpers.EntityHelper;
import joshie.harvest.core.helpers.InventoryHelper;
import joshie.harvest.core.network.PacketHandler;
import joshie.harvest.core.util.annotations.HFEvents;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static joshie.harvest.core.helpers.InventoryHelper.ITEM;
import static joshie.harvest.core.helpers.InventoryHelper.ITEM_STACK;

@HFEvents
public class AnimalEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @SuppressWarnings("ConstantConditions")
    public void onEntityLoaded(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        AnimalStats stats = EntityHelper.getStats(entity);
        if (stats != null && entity instanceof EntityAnimal) {
            if (!entity.worldObj.isRemote) {
                stats.setEntity((EntityAnimal)entity);
                AnimalTrackerServer.addToQueue(() -> HFTrackers.<AnimalTrackerServer>getAnimalTracker(event.getWorld()).onJoinWorld((EntityAnimal) entity, stats));
            } else PacketHandler.sendToServer(new PacketSyncAnimal(entity.getEntityId())); //Request spawn data from the server
        }
    }

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public void onEntityDeath(LivingDeathEvent event) {
        AnimalStats stats = EntityHelper.getStats(event.getEntityLiving());
        if (stats != null) {
            HFTrackers.getAnimalTracker(event.getEntityLiving().worldObj).onDeath(stats);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteract event) {
        AnimalStats stats = EntityHelper.getStats(event.getTarget());
        ItemStack stack = event.getItemStack();
        if (stats != null && stack != null) {
            if (HFApi.animals.canEat(stack, stats.getType().getFoodTypes()) && stats.performAction(event.getWorld(), event.getEntityPlayer(), stack, AnimalAction.FEED)) {
                stack.splitStack(1);
                event.setCanceled(true);
            }
        }
    }

    /* When right clicking poultry, will throw any poultry on your head **/
    @HFEvents
    public static class PickupPoultry {
        public static boolean register() { return HFAnimals.PICKUP_POULTRY; }

        public boolean blocksPickup(EntityPlayer player) {
            return  InventoryHelper.getHandItemIsIn(player, ITEM_STACK, HFAnimals.TOOLS.getStackFromEnum(Tool.CHICKEN_FEED)) != null ||
                    InventoryHelper.getHandItemIsIn(player, ITEM_STACK, HFAnimals.TOOLS.getStackFromEnum(Tool.MEDICINE)) != null ||
                    InventoryHelper.getHandItemIsIn(player, ITEM, HFAnimals.TREATS) != null;
        }

        @SubscribeEvent
        public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
            EntityPlayer player = event.getEntityPlayer();
            if (!player.isBeingRidden() && !blocksPickup(player)) {
                Entity entity = event.getTarget();
                AnimalStats stats = EntityHelper.getStats(entity);
                if (stats != null && stats.performAction(player.worldObj, player, null, AnimalAction.TEST_MOUNT)) {
                    entity.startRiding(player, true);
                }
            }
        }

        private boolean forbidsDrop(Block block) {
            return block instanceof BlockDoor || block instanceof BlockFenceGate || block instanceof BlockTrapDoor
                    || block instanceof BlockLever || block instanceof BlockButton;
        }

        @SubscribeEvent
        @SuppressWarnings("ConstantConditions")
        public void onRightClickGround(PlayerInteractEvent.RightClickBlock event) {
            EntityPlayer player = event.getEntityPlayer();
            if (!forbidsDrop(event.getWorld().getBlockState(event.getPos()).getBlock())) {
                for (Entity entity : player.getPassengers()) {
                    AnimalStats stats = EntityHelper.getStats(entity);
                    if (stats != null && stats.performAction(player.worldObj, player, null, AnimalAction.TEST_MOUNT)) {
                        entity.dismountRidingEntity();
                        entity.rotationPitch = player.rotationPitch;
                        entity.rotationYaw = player.rotationYaw;
                        entity.moveRelative(0F, 0.1F, 1.05F);
                        stats.performAction(player.worldObj, player, null, AnimalAction.DISMOUNT);
                    }
                }
            }
        }
    }
}