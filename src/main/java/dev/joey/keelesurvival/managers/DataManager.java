package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.server.bounties.Bounty;
import dev.joey.keelesurvival.server.SurvivalDifficulty;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelesurvival.server.events.enderdragon.ControlDragonSpawn;

public class DataManager {

    public DataManager() {

        Storage.loadBalanceData();
        Bounty.loadBountyData();
        new SurvivalDifficulty();
        new ControlDragonSpawn();

    }
}
