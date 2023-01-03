package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.server.advancedsurvival.bounties.BountyListener;
import dev.joey.keelesurvival.server.chestprotection.ChestListener;
import dev.joey.keelesurvival.server.economy.listeners.PlayerEconomy;
import dev.joey.keelesurvival.server.entityclearing.MinecartRemoval;
import dev.joey.keelesurvival.server.headdrop.HeadEvent;
import dev.joey.keelesurvival.util.UtilClass;

public class ListenerManager {

    public ListenerManager() {

        new MinecartRemoval();
        new HeadEvent();
        new PlayerEconomy();
        new ChestListener();

        if (UtilClass.isAdvancedSurvivalEnabled()) {
            new BountyListener();
        }

    }
}
