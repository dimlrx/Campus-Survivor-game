package nl.saxion.game.campussurvivor.npc;

import nl.saxion.gameapp.GameApp;
import nl.saxion.game.campussurvivor.world.WorldMap;
import nl.saxion.game.campussurvivor.systems.GameClock;

/** A named student with a simple daily routine and an interaction line. */
public final class CampusNpc {
    public final String name;
    private final String dialogue;
    private final float homeX,homeY,dayX,dayY;
    public float x,y;
    private float phase;
    private boolean placed=false;
    private float waypointX,waypointY;
    public CampusNpc(String name,String dialogue,float homeX,float homeY,float dayX,float dayY,float phase){
        this.name=name;this.dialogue=dialogue;this.homeX=homeX;this.homeY=homeY;
        this.dayX=dayX;this.dayY=dayY;this.phase=phase;x=homeX;y=homeY;
    }
    public void update(float dt,GameClock clock,WorldMap world){
        if(!placed){float[] p=world.nearestPath(x,y);x=p[0];y=p[1];waypointX=x;waypointY=y;placed=true;}
        // NPCs may only walk on the street/pavement mask, never through buildings.
        boolean school=clock.hour()>=9&&clock.hour()<17;
        float tx=school?dayX:homeX,ty=school?dayY:homeY;
        phase+=dt;
        // Small walking patrol around their daily destination.
        tx+= (float)Math.sin(phase*.65f)*34;
        ty+= (float)Math.cos(phase*.45f)*22;
        // Select a reachable local waypoint; don't move straight through roofs.
        if((float)Math.hypot(x-waypointX,y-waypointY)<18 || !world.canStand(waypointX,waypointY,9)){
            float best=Float.MAX_VALUE;
            float targetX=x,targetY=y;
            for(int k=0;k<16;k++){
                float angle=(float)(phase*.27+k*Math.PI/8);
                float xx=x+(float)Math.cos(angle)*64, yy=y+(float)Math.sin(angle)*64;
                if(world.canStand(xx,yy,9)){
                    float score=(xx-tx)*(xx-tx)+(yy-ty)*(yy-ty)+k*20;
                    if(score<best){best=score;targetX=xx;targetY=yy;}
                }
            }
            waypointX=targetX;waypointY=targetY;
        }
        float dx=waypointX-x,dy=waypointY-y,dist=(float)Math.sqrt(dx*dx+dy*dy);
        if(dist>2){float step=Math.min(dist,dt*75);float nx=x+dx/dist*step,ny=y+dy/dist*step;
            if(world.canStand(nx,y,12))x=nx;
            if(world.canStand(x,ny,12))y=ny;
        }
    }
    public boolean near(float px,float py){float dx=px-x,dy=py-y;return dx*dx+dy*dy<100*100;}
    public String talk(GameClock clock){return name+": "+(clock.hour()<9?"Morning! ":clock.hour()>=20?"Long day! ":"")+dialogue;}
    public void draw(float cx,float cy){
        float sx=x-cx,sy=y-cy;
        GameApp.drawRect(sx-11,sy-14,22,27,"rose-500");
        GameApp.drawRect(sx-9,sy+13,18,14,"amber-200");
    }
    public void drawOblique(float cameraX,float cameraY){
        float sx=WorldMap.projectX(x,y)-cameraX;
        float sy=WorldMap.projectY(x,y)-cameraY;
        GameApp.drawRect(sx-13,sy-5,26,9,"slate-900");
        GameApp.drawRect(sx-10,sy+2,20,32,"rose-500");
        GameApp.drawRect(sx-8,sy+33,16,14,"amber-200");
        GameApp.drawRect(sx-10,sy-3,8,11,"slate-900");
        GameApp.drawRect(sx+3,sy-3,8,11,"slate-900");
    }
}
