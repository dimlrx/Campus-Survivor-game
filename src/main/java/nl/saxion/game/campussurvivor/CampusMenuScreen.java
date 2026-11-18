package nl.saxion.game.campussurvivor;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public final class CampusMenuScreen extends ScalableGameScreen {
    public CampusMenuScreen(){super(1000,650);}
    @Override public void show(){GameApp.addFont("campus","fonts/basic.ttf",25);
        GameApp.addFont("menu-small","fonts/basic.ttf",15);}
    @Override public void render(float delta){
        super.render(delta);
        if(GameApp.isKeyJustPressed(Input.Keys.ENTER)){GameApp.switchScreen("game");return;}
        GameApp.clearScreen("slate-900");
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0,0,1000,120,"emerald-900");
        for(int i=0;i<6;i++){GameApp.drawRect(50+i*160,160,110,290,"slate-700");GameApp.drawRect(80+i*160,260,50,60,"sky-300");}
        GameApp.endShapeRendering();
        GameApp.startSpriteRendering();
        GameApp.drawTextCentered("campus","CAMPUS SURVIVOR",500,510,"amber-400");
        GameApp.drawTextCentered("menu-small","ENSCHEDE EDITION - SPRINT 3",500,460,"white");
        GameApp.drawTextCentered("menu-small","ENTER - Start exploring",500,355,"white");
        GameApp.drawTextCentered("menu-small","WASD - Move  |  E - Talk  |  P - Pause",500,310,"white");
        GameApp.drawTextCentered("menu-small","21 days, 3 exams, random events and multiple endings",500,110,"white");
        GameApp.endSpriteRendering();
    }
    @Override public void hide(){GameApp.disposeFont("campus");GameApp.disposeFont("menu-small");}
}
