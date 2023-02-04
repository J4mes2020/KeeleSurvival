package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.server.bounties.BountyListener;
import dev.joey.keelesurvival.server.chestprotection.ChestListener;
import dev.joey.keelesurvival.server.events.PlayerFirstTimeBook;
import dev.joey.keelesurvival.server.events.dynamictnt.DynamicTNT;
import dev.joey.keelesurvival.server.economy.listeners.PlayerEconomy;
import dev.joey.keelesurvival.server.events.enderdragon.PreviousKill;
import dev.joey.keelesurvival.server.events.headdrop.HeadEvent;
import dev.joey.keelesurvival.server.events.meteorite.SpawnMeteorite;
import dev.joey.keelesurvival.server.events.sleeping.SleepController;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class ListenerManager {

    public ListenerManager() {

        new PreviousKill();
        new HeadEvent();
        new PlayerEconomy();
        new ChestListener();
        new BountyListener();
        new DynamicTNT();
        new SpawnMeteorite();
        new SleepController();

    }
}
