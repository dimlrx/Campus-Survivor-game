package nl.saxion.game.campussurvivor.gameplay;

import nl.saxion.game.campussurvivor.systems.GameClock;
import nl.saxion.game.campussurvivor.systems.StudentStats;
import nl.saxion.game.campussurvivor.world.WorldMap;

/** Sprint 2: location-based activities and meaningful consequences. */
public final class ActivitySystem {
    private final GameClock clock;
    private final StudentStats stats;
    private String message = "Visit Saxion, Jumbo, your flat or Oude Markt for activities.";

    public ActivitySystem(GameClock clock, StudentStats stats) { this.clock=clock; this.stats=stats; }
    public String message() { return message; }

    /** Returns an activity hint when the player stands near a building entrance. */
    public String hint(float x,float y) {
        int area=area(x,y);
        switch(area) {
            case 1: return "[1] Study at Saxion (2h)";
            case 2: return "[2] Sleep at student flat (8h)";
            case 3: return "[3] Work at Jumbo (4h)";
            case 4: return "[4] Go out at Oude Markt (3h)";
            default: return "Walk to a building entrance to perform an activity";
        }
    }
    private int area(float x,float y) {
        float tile=WorldMap.TILE;
        // Entrance tiles are walkable and immediately adjacent to the corresponding buildings.
        if(near(x,y,(WorldMap.DOOR_X[0]+.5f)*tile,(WorldMap.DOOR_Y[0]+.5f)*tile,85))return 1;
        if(near(x,y,(WorldMap.DOOR_X[1]+.5f)*tile,(WorldMap.DOOR_Y[1]+.5f)*tile,85))return 2;
        if(near(x,y,(WorldMap.DOOR_X[5]+.5f)*tile,(WorldMap.DOOR_Y[5]+.5f)*tile,85))return 3;
        if(near(x,y,(WorldMap.DOOR_X[4]+.5f)*tile,(WorldMap.DOOR_Y[4]+.5f)*tile,85))return 4;
        return 0;
    }
    private boolean near(float x,float y,float tx,float ty,float r) {
        float dx=x-tx,dy=y-ty;return dx*dx+dy*dy<=r*r;
    }
    public boolean perform(int key,float x,float y) {
        if(area(x,y)!=key) {message="Find the matching building entrance first!";return false;}
        return applyActivity(key);
    }
    /** Allow an activity inside its own building (e.g. sleeping inside the flat). */
    public boolean performInside(int key, int room) {
        int expected=key==1?0:key==2?1:key==3?5:key==4?4:-1;
        if(room!=expected) {message="This activity belongs to another building.";return false;}
        return applyActivity(key);
    }
    private boolean applyActivity(int key) {
        if(key==1) {
            if(stats.energy<15) {message="Too exhausted to study. Get some sleep first.";return false;}
            passHours(2);stats.energy-=19;stats.knowledge+=18;stats.stress+=9;
            if(stats.alcohol>=25){stats.knowledge-=8;message="Studied 2h, but alcohol reduced what you learned.";}
            else message="Studied for 2h: knowledge +18, energy -19, stress +9.";
        } else if(key==2) {
            passHours(8);stats.energy+=65;stats.stress-=22;stats.alcohol-=25;
            message="Slept for 8h: energy restored and stress reduced.";
        } else if(key==3) {
            if(stats.energy<20){message="Too tired to work. Sleep first.";return false;}
            passHours(4);stats.money+=55;stats.energy-=30;stats.stress+=11;
            message="Worked at Jumbo for 4h: earned EUR 55, but lost energy.";
        } else if(key==4) {
            if(stats.money<25){message="You need EUR 25 to go out. Work first!";return false;}
            passHours(3);stats.money-=25;stats.energy-=24;stats.alcohol+=34;stats.popularity+=16;stats.stress-=12;
            if(stats.alcohol>=60){stats.popularity-=12;stats.stress+=12;message="Wild night! Too much alcohol hurt your reputation.";}
            else message="Night out: popularity +16, alcohol +34, EUR -25.";
        } else return false;
        stats.normalize();return true;
    }
    private void passHours(float hours) {
        clock.advanceMinutes(hours*60);
        stats.advanceHours(hours);
    }
}
