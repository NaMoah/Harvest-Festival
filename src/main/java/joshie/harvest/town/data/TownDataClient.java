package joshie.harvest.town.data;

import joshie.harvest.buildings.BuildingRegistry;
import joshie.harvest.buildings.BuildingStage;
import joshie.harvest.quests.data.QuestDataClient;

import java.util.LinkedList;

public class TownDataClient extends TownData<QuestDataClient> {
    private final QuestDataClient quest = new QuestDataClient();

    @Override
    public QuestDataClient getQuests() {
        return quest;
    }

    public void addBuilding(TownBuilding building) {
        buildings.put(BuildingRegistry.REGISTRY.getKey(building.building), building);
        inhabitants.addAll(building.building.getInhabitants());
    }

    public void setBuilding(LinkedList<BuildingStage> building) {
        this.building = building;
    }
}