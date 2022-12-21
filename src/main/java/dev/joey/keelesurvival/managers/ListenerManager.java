package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.admin.clearlag.ClearLag;
import dev.joey.keelesurvival.server.entityclearing.MinecartRemoval;
import dev.joey.keelesurvival.server.headdrop.HeadEvent;

public class ListenerManager {

    public ListenerManager(KeeleSurvival keeleSurvival) {

        new ClearLag(keeleSurvival);
        new MinecartRemoval(keeleSurvival);
        new HeadEvent(keeleSurvival);

    }
}
