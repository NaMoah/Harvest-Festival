package joshie.harvest.town;

import joshie.harvest.api.buildings.BuildingLocation;

import static joshie.harvest.buildings.HFBuildings.*;

@SuppressWarnings("all")
public class BuildingLocations {
    public static final BuildingLocation FISHINGHUTUPSTAIRS = new BuildingLocation(FISHING_HUT, "fisher.upstairs").withDistance(8D);
    public static final BuildingLocation FISHINGHUTDOWNSTAIRS = new BuildingLocation(FISHING_HUT, "jacob").withDistance(8D);
    public static final BuildingLocation POND = new BuildingLocation(FISHING_HOLE, "pond.pier").withDistance(16D).withTime(1500L);
    public static final BuildingLocation PONDBACK = new BuildingLocation(FISHING_HOLE, "pond.back").withDistance(20D).withTime(1500L);
    public static final BuildingLocation PONDLEFT = new BuildingLocation(FISHING_HOLE, "pond.left").withDistance(20D).withTime(1500L);
    public static final BuildingLocation PONDRIGHT = new BuildingLocation(FISHING_HOLE, "pond.right").withDistance(20D).withTime(1500L);
    public static final BuildingLocation GODDESSBACKRIGHT = new BuildingLocation(GODDESS_POND, "goddess.right.back").withDistance(24D).withTime(1500L);
    public static final BuildingLocation GODDESSFRONT = new BuildingLocation(GODDESS_POND, "goddess.front").withDistance(24D).withTime(1500L);
    public static final BuildingLocation GODDESSFRONTLEFT = new BuildingLocation(GODDESS_POND, "goddess.left").withDistance(24D).withTime(1500L);
    public static final BuildingLocation GODDESSFRONTRIGHT = new BuildingLocation(GODDESS_POND, "goddess.right").withDistance(24D).withTime(1500L);
    public static final BuildingLocation BARNBUILDING = new BuildingLocation(BARN, "jim").withDistance(8D);
    public static final BuildingLocation BARNLEFT = new BuildingLocation(BARN, "barn.left").withDistance(8D);
    public static final BuildingLocation BARNDOOR = new BuildingLocation(BARN, "barn.door").withDistance(20D);
    public static final BuildingLocation BARNRIGHT = new BuildingLocation(BARN, "barn.right").withDistance(20D);
    public static final BuildingLocation POULTRYBUILDING = new BuildingLocation(POULTRY_FARM, "ashlee").withDistance(8D);
    public static final BuildingLocation POULTRYDOOR = new BuildingLocation(POULTRY_FARM, "poultry.door").withDistance(20D);
    public static final BuildingLocation TOWNHALLLEFT = new BuildingLocation(TOWNHALL, "townhall.wing.left").withDistance(10D);
    public static final BuildingLocation TOWNHALLRIGHT = new BuildingLocation(TOWNHALL, "townhall.wing.right").withDistance(10D);
    public static final BuildingLocation TOWNHALLENTRANCE = new BuildingLocation(TOWNHALL, "townhall.door").withDistance(10D);
    public static final BuildingLocation TOWNAHLLCENTRE = new BuildingLocation(TOWNHALL, "townhall.entrance").withDistance(10D);
    public static final BuildingLocation TOWNHALLSTAGE = new BuildingLocation(TOWNHALL, "jamie");
    public static final BuildingLocation TOWNHALLTEEN = new BuildingLocation(TOWNHALL, "townhall.bedroom.teen");
    public static final BuildingLocation TOWNHALLTEENBED = new BuildingLocation(TOWNHALL, "cloe");
    public static final BuildingLocation TOWNAHLLADULT = new BuildingLocation(TOWNHALL, "townhall.bedroom.adult");
    public static final BuildingLocation TOWNHALLCHILDBED = new BuildingLocation(TOWNHALL, "abi");
    public static final BuildingLocation CHURCHINSIDE = new BuildingLocation(CHURCH, "thomas").withDistance(10D).withTime(1000L);
    public static final BuildingLocation CHURCHPEWFRONTLEFT = new BuildingLocation(CHURCH, "church.pew.left.front").withDistance(3D).withTime(1000L);
    public static final BuildingLocation CHURCHPEWFRONTRIGHT = new BuildingLocation(CHURCH, "church.pew.right.front").withDistance(3D).withTime(1000L);
    public static final BuildingLocation CHURCHPEWCENTRE = new BuildingLocation(CHURCH, "church.pew.centre").withDistance(3D).withTime(1000L);
    public static final BuildingLocation CHURCHPEWBACKLEFT = new BuildingLocation(CHURCH, "church.pew.left.back").withDistance(3D).withTime(1000L);
    public static final BuildingLocation CHURCHPEWBACKTRIGHT = new BuildingLocation(CHURCH, "church.pew.right.back").withDistance(3D).withTime(1000L);
    public static final BuildingLocation CHURCH_RIGHT = new BuildingLocation(CHURCH, "church.right").withDistance(20D).withTime(1000L);
    public static final BuildingLocation GENERALFRONT = new BuildingLocation(SUPERMARKET, "market.customer").withDistance(16D).withTime(1500L);
    public static final BuildingLocation GENERALTABLES = new BuildingLocation(SUPERMARKET, "market.tables").withDistance(16D).withTime(1500L);
    public static final BuildingLocation GENERALGARDEN = new BuildingLocation(SUPERMARKET, "market.garden").withDistance(16D).withTime(1500L);
    public static final BuildingLocation GENERALBACKYARD = new BuildingLocation(SUPERMARKET, "market.backyard").withDistance(24D).withTime(1500L);
    public static final BuildingLocation GENERALBASEMENT = new BuildingLocation(SUPERMARKET, "market.basement").withDistance(16D).withTime(1500L);
    public static final BuildingLocation GENERAL_STAIRS_TOP = new BuildingLocation(SUPERMARKET, "market.stairs").withDistance(16D).withTime(1500L);
    public static final BuildingLocation GENERALBASEMENTBACK = new BuildingLocation(SUPERMARKET, "jenni").withDistance(8D);
    public static final BuildingLocation GENERALENTRANCE = new BuildingLocation(SUPERMARKET, "girafi").withDistance(8D);
    public static final BuildingLocation GENERALBEDROOM = new BuildingLocation(SUPERMARKET, "candice").withDistance(8D);
    public static final BuildingLocation GENERALTILL = new BuildingLocation(SUPERMARKET, "market.till").withDistance(4D);
    public static final BuildingLocation CARPENTERFRONT = new BuildingLocation(CARPENTER, "carpenter.door").withDistance(24D).withTime(1500L);
    public static final BuildingLocation CARPENTERDOWN = new BuildingLocation(CARPENTER, "carpenter.downstairs").withDistance(3D);
    public static final BuildingLocation CARPENTERUP = new BuildingLocation(CARPENTER, "jade").withDistance(3D);
    public static final BuildingLocation CHURCHFRONT = new BuildingLocation(CHURCH, "church.door").withDistance(24D).withTime(1500L);
    public static final BuildingLocation MINEENTRANCE = new BuildingLocation(MINING_HILL, "mine.front").withDistance(24D).withTime(1500L);
    //public static final BuildingLocation MINEHUTENTRANCE = new BuildingLocation(MINING_HUT, "miner.door").withDistance(16D).withTime(1500L);
    //public static final BuildingLocation MINEHUTSIDE = new BuildingLocation(MINING_HUT, "brandon").withDistance(4D);
    //public static final BuildingLocation MINEHUTGRAVEL = new BuildingLocation(MINING_HUT, "miner.gravel").withDistance(4D);
    public static final BuildingLocation CAFETILL = new BuildingLocation(CAFE, "cafe.till").withDistance(4D);
    public static final BuildingLocation CAFEFRONT = new BuildingLocation(CAFE, "cafe.entrance").withDistance(16D).withTime(1500L);
    public static final BuildingLocation CAFECUSTOMER = new BuildingLocation(CAFE, "cafe.customer").withDistance(16D).withTime(1500L);
    public static final BuildingLocation CAFEBALCONY = new BuildingLocation(CAFE, "liara").withDistance(16D).withTime(1500L);
    public static final BuildingLocation CAFEKITCHEN = new BuildingLocation(CAFE, "katlin").withDistance(16D).withTime(1500L);
    public static final BuildingLocation BLACKSMITHFRONT = new BuildingLocation(BLACKSMITH, "blacksmith.entrance").withDistance(16D).withTime(1500L);
    public static final BuildingLocation BLACKSMITHDOOR = new BuildingLocation(BLACKSMITH, "blacksmith.door").withDistance(20D).withTime(1500L);
    public static final BuildingLocation BLACKSMITHFURNACE = new BuildingLocation(BLACKSMITH, "daniel");
    public static final BuildingLocation CLOCKMAKER_DOOR = new BuildingLocation(CLOCKMAKER, "clockworker.door");
    public static final BuildingLocation CLOCKMAKERUPSTAIRS = new BuildingLocation(CLOCKMAKER, "fenn");
    public static final BuildingLocation CLOCKMAKERDOWNSTAIRS = new BuildingLocation(CLOCKMAKER, "tiberius");

    //TODO: Convert to building locations vvv
    public static final String MARKET_DOOR = "market.entrance";
    public static final String TOWNHALL_CHILD_BEDROOM = "townhall.bedroom.child";
    public static final String TOWNHALL_FRONT_OF_STAGE = "townhall.stage.front";
    public static final String TOWNHALL_LEFT_OF_STAGE = "townhall.stage.left";
    public static final String TOWNHALL_RIGHT_OF_STAGE = "townhall.stage.right";
    public static final String CAFE_DOOR = "cafe.door";
    public static final String CAFE_STAIRS = "cafe.stairs";
    public static final String MINE_STAIRS = "mine.stairs";
    public static final String MINE_RIGHT = "mine.right";
    public static final String MINE_BACK = "mine.back";
    public static final String MINER_HALL = "miner.hall";
    public static final String POULTRY_FRONT = "poultry.front";
    public static final String FISHER_RIGHT = "fisher.right";
    public static final String FISHER_LEFT = "fisher.left";
    public static final String FISHER_DOOR = "fisher.door";
    public static final String CHURCH_LEFT = "church.left";
    public static final String GODDESS_BACK = "goddess.back";
    public static final String GODDESS_BACK_LEFT = "goddess.left.back";
    public static final String GODDESS_MIDDLE_RIGHT = "goddess.right.middle";
    public static final String GODDESS_MIDDLE_LEFT = "goddess.left.middle";
    public static final String GODDESS_WATER = "goddess.water";
}
