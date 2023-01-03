package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.server.advancedsurvival.bounties.Bounty;
import dev.joey.keelesurvival.server.advancedsurvival.difficulty.SurvivalDifficulty;
import dev.joey.keelesurvival.server.economy.Storage;

public class DataManager {

    public DataManager() {

        Storage.loadBalanceData();
        Bounty.loadBountyData();
        new SurvivalDifficulty();

    }
}
