package nl.saxion.game.campussurvivor.player;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.game.campussurvivor.world.WorldMap;

/** Player moves on separate axes, allowing sliding along building walls. */
public final class Student {
    public float x=520f,y=1000f;
    private static final float SPEED=245f,RADIUS=15f;
    public void update(float dt,WorldMap world){
        float dx=0,dy=0;
        if(GameApp.isKeyPressed(Input.Keys.A)||GameApp.isKeyPressed(Input.Keys.LEFT))dx--;
        if(GameApp.isKeyPressed(Input.Keys.D)||GameApp.isKeyPressed(Input.Keys.RIGHT))dx++;
        if(GameApp.isKeyPressed(Input.Keys.W)||GameApp.isKeyPressed(Input.Keys.UP))dy++;
        if(GameApp.isKeyPressed(Input.Keys.S)||GameApp.isKeyPressed(Input.Keys.DOWN))dy--;
        if(dx==0&&dy==0)return;
        float length=(float)Math.sqrt(dx*dx+dy*dy);dx=dx/length*SPEED*dt;dy=dy/length*SPEED*dt;
        if(world.canStand(x+dx,y,RADIUS))x+=dx;
        if(world.canStand(x,y+dy,RADIUS))y+=dy;
    }
    public void draw(float cameraX,float cameraY){
        float sx=x-cameraX,sy=y-cameraY;
        GameApp.drawRect(sx-12,sy-16,24,30,"sky-500");
        GameApp.drawRect(sx-10,sy+14,20,15,"amber-200");
        GameApp.drawRect(sx+10,sy-8,9,18,"violet-500");
    }
    public void drawOblique(float cameraX,float cameraY){
        float sx=WorldMap.projectX(x,y)-cameraX;
        float sy=WorldMap.projectY(x,y)-cameraY;
        GameApp.drawRect(sx-13,sy-5,26,9,"slate-900");
        GameApp.drawRect(sx-10,sy+2,20,32,"sky-500");
        GameApp.drawRect(sx-8,sy+33,16,14,"amber-200");
        GameApp.drawRect(sx-10,sy-3,8,11,"slate-900");
        GameApp.drawRect(sx+3,sy-3,8,11,"slate-900");
    }
}
