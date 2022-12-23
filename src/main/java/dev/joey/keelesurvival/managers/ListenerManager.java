package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.admin.clearlag.ClearLag;
import dev.joey.keelesurvival.server.economy.listeners.PlayerEconomy;
import dev.joey.keelesurvival.server.entityclearing.MinecartRemoval;
import dev.joey.keelesurvival.server.headdrop.HeadEvent;

public class ListenerManager {

    public ListenerManager() {

        new ClearLag();
        new MinecartRemoval();
        new HeadEvent();
        new PlayerEconomy();

    }
}
