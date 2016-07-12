package joshie.harvest.buildings.items;

import joshie.harvest.api.core.ICreativeSorted;
import joshie.harvest.buildings.Building;
import joshie.harvest.buildings.BuildingRegistry;
import joshie.harvest.buildings.HFBuildings;
import joshie.harvest.core.helpers.TownHelper;
import joshie.harvest.core.util.Direction;
import joshie.harvest.core.HFTab;
import joshie.harvest.core.util.base.ItemHFFML;
import joshie.harvest.core.util.generic.Text;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBuilding extends ItemHFFML<ItemBuilding, Building> implements ICreativeSorted {
    public ItemBuilding() {
        super(BuildingRegistry.REGISTRY, HFTab.TOWN);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Building building = getObjectFromStack(stack);
        if (building != null && !TownHelper.getClosestTownToPlayer(player).hasBuilding(building.getRegistryName())) {
            Direction direction = Direction.values()[world.rand.nextInt(Direction.values().length)];
            return building.generate(world, pos, direction.getMirror(), direction.getRotation());
        }

        return EnumActionResult.PASS;
    }

    @Override
    public Building getNullValue() {
        return HFBuildings.null_building;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return Text.format("harvestfestival.structures.spawn", getObjectFromStack(stack).getLocalisedName());
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return 200;
    }
}