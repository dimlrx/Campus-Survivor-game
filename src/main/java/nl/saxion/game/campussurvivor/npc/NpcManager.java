package nl.saxion.game.campussurvivor.npc;

import java.util.ArrayList;
import java.util.List;
import nl.saxion.game.campussurvivor.world.WorldMap;
import nl.saxion.game.campussurvivor.systems.GameClock;

public final class NpcManager {
    private final List<CampusNpc> npcs=new ArrayList<>();
    public NpcManager(){
        // All points are on walkable street tiles. Day positions are near Saxion.
        npcs.add(new CampusNpc("Tim","See you at the group meeting!",10.5f*64,10.5f*64,17.5f*64,10.5f*64,0));
        npcs.add(new CampusNpc("Noor","I still need to finish my assignment.",28.5f*64,18.5f*64,19.5f*64,18.5f*64,2));
        npcs.add(new CampusNpc("Sam","Coffee is my study strategy.",9.5f*64,21.5f*64,10.5f*64,16.5f*64,4));
        npcs.add(new CampusNpc("Alex","Have you seen my laptop charger?",29.5f*64,10.5f*64,19.5f*64,10.5f*64,6));
        // Additional students and locals with their own routines and conversations.
        npcs.add(new CampusNpc("Mila","My group project is due soon.",12.5f*64,18.5f*64,16.5f*64,10.5f*64,1));
        npcs.add(new CampusNpc("Jesse","The Jumbo shift starts early tomorrow.",33.5f*64,10.5f*64,32.5f*64,10.5f*64,3));
        npcs.add(new CampusNpc("Lotte","I am saving up for a new bike.",25.5f*64,18.5f*64,22.5f*64,18.5f*64,5));
        npcs.add(new CampusNpc("Daan","Are you ready for the programming exam?",18.5f*64,21.5f*64,18.5f*64,10.5f*64,7));
        npcs.add(new CampusNpc("Yara","The Oude Markt gets busy after sunset.",29.5f*64,21.5f*64,28.5f*64,18.5f*64,9));
        npcs.add(new CampusNpc("Bram","I'm working on a prototype at Kennispark.",9.5f*64,22.5f*64,10.5f*64,22.5f*64,11));
        npcs.add(new CampusNpc("Sophie","I need a quiet place to study.",5.5f*64,5.5f*64,17.5f*64,10.5f*64,13));
        npcs.add(new CampusNpc("Rayan","I just moved into the student flat.",4.5f*64,10.5f*64,10.5f*64,10.5f*64,15));
    }
    public void update(float dt,GameClock clock,WorldMap world){for(CampusNpc npc:npcs)npc.update(dt,clock,world);}
    public List<CampusNpc> all(){return npcs;}
    public CampusNpc closest(float x,float y){
        CampusNpc best=null;float dist=Float.MAX_VALUE;
        for(CampusNpc npc:npcs){float dx=npc.x-x,dy=npc.y-y,d=dx*dx+dy*dy;
            if(d<dist&&npc.near(x,y)){best=npc;dist=d;}}
        return best;
    }
}
