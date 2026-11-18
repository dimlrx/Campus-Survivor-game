package nl.saxion.game.campussurvivor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;
import nl.saxion.game.campussurvivor.player.Student;
import nl.saxion.game.campussurvivor.world.WorldMap;
import nl.saxion.game.campussurvivor.world.InteriorScene;
import nl.saxion.game.campussurvivor.systems.GameClock;
import nl.saxion.game.campussurvivor.systems.StudentStats;
import nl.saxion.game.campussurvivor.npc.CampusNpc;
import nl.saxion.game.campussurvivor.npc.NpcManager;
import nl.saxion.game.campussurvivor.gameplay.ActivitySystem;
import nl.saxion.game.campussurvivor.gameplay.SemesterSystem;
import nl.saxion.game.campussurvivor.gameplay.MissionSystem;
import nl.saxion.game.campussurvivor.npc.DialogueSystem;

/** Sprint 1: persistent clock, six stats, moving NPCs and E-key conversations. */
public final class CampusGameScreen extends ScalableGameScreen {
    private static final int WIDTH=1000,HEIGHT=650;
    private WorldMap world;
    private Student player;
    private GameClock clock;
    private StudentStats stats;
    private NpcManager npcs;
    private ActivitySystem activities;
    private SemesterSystem semester;
    private MissionSystem missions;
    private DialogueSystem conversations;
    private float cameraX,cameraY,dialogueTimer;
    private String dialogue="";
    private boolean paused;
    private boolean mapOverview;
    private int indoor=-1;
    private final Texture[] interiorPhotos = new Texture[7];
    private SpriteBatch photoBatch;
    private Texture cityMapTexture;
    private final Texture[] npcSprites = new Texture[12];
    private Texture playerSprite,entranceSprite;
    private OrthographicCamera photoCamera;
    private FitViewport photoViewport;
    public CampusGameScreen(){super(WIDTH,HEIGHT);}
    @Override public void show(){
        world=new WorldMap();player=new Student();
        float[] spawn=world.nearestPath(player.x,player.y);player.x=spawn[0];player.y=spawn[1];
        clock=new GameClock();stats=new StudentStats();npcs=new NpcManager();activities=new ActivitySystem(clock,stats);semester=new SemesterSystem(clock,stats);missions=new MissionSystem(stats,clock);conversations=new DialogueSystem(stats,missions);
        cameraX=0;cameraY=0;dialogue="";dialogueTimer=0;paused=false;indoor=-1;mapOverview=false;
        photoBatch = new SpriteBatch();
        cityMapTexture = new Texture(Gdx.files.internal("maps/enschede_centrum_2d.jpg"));
        for(int i=0;i<npcSprites.length;i++){
            npcSprites[i]=new Texture(Gdx.files.internal(String.format("npcs/npc_%02d.png",i)));
            npcSprites[i].setFilter(Texture.TextureFilter.Nearest,Texture.TextureFilter.Nearest);
        }
        playerSprite = new Texture(Gdx.files.internal("npcs/player.png"));
        playerSprite.setFilter(Texture.TextureFilter.Nearest,Texture.TextureFilter.Nearest);
        entranceSprite = new Texture(Gdx.files.internal("maps/entrance.png"));
        photoCamera = new OrthographicCamera();
        photoViewport = new FitViewport(WIDTH, HEIGHT, photoCamera);
        photoViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        for(int i : new int[]{0,1,2,3,4,5,6}) interiorPhotos[i] = new Texture(Gdx.files.internal("interiors/location_"+i+".jpg"));
        GameApp.addFont("hud","fonts/basic.ttf",13);
        GameApp.addFont("map-label","fonts/basic.ttf",12);
    }
    @Override public void render(float delta){
        super.render(delta);
        if(GameApp.isKeyJustPressed(Input.Keys.ESCAPE)){GameApp.switchScreen("menu");return;}
        if(semester.finished() && GameApp.isKeyJustPressed(Input.Keys.N)){GameApp.switchScreen("menu");return;}
        if(GameApp.isKeyJustPressed(Input.Keys.P))paused=!paused;
        if(GameApp.isKeyJustPressed(Input.Keys.TAB))mapOverview=!mapOverview;
        if(GameApp.isKeyJustPressed(Input.Keys.Q) && indoor>=0){indoor=-1;dialogue="Exited building";dialogueTimer=2;}
        if(!mapOverview && GameApp.isKeyJustPressed(Input.Keys.E) && indoor<0 && !conversations.active()){
            int entrance=entranceRoom();
            if(entrance>=0){indoor=entrance;dialogue="";dialogueTimer=0;}
        }
        float dt=Math.min(delta,.05f);
        if(!paused && !mapOverview && !semester.finished()){
            if(conversations.active()){
                for(int k=1;k<=3;k++){int key=k==1?Input.Keys.NUM_1:k==2?Input.Keys.NUM_2:Input.Keys.NUM_3;
                    if(GameApp.isKeyJustPressed(key))conversations.choose(k);
                }
                if(GameApp.isKeyJustPressed(Input.Keys.Q))conversations.close();
            }
            if(indoor<0 && !conversations.active())player.update(dt,world);
            stats.advanceHours(clock.advance(dt));
            npcs.update(dt,clock,world);
            dialogueTimer=Math.max(0,dialogueTimer-dt);
            for(int key=1;key<=4 && !conversations.active();key++){
                int input=key==1?Input.Keys.NUM_1:key==2?Input.Keys.NUM_2:key==3?Input.Keys.NUM_3:Input.Keys.NUM_4;
                if(GameApp.isKeyJustPressed(input) && indoor<0){
                    activities.perform(key,player.x,player.y);
                    dialogue=activities.message();dialogueTimer=5;
                }
            }
            if(!mapOverview && GameApp.isKeyJustPressed(Input.Keys.E) && indoor<0 && entranceRoom()<0 && !conversations.active()){
                CampusNpc nearest=npcs.closest(player.x,player.y);
                if(nearest!=null){conversations.start(nearest.name,clock);dialogueTimer=0;}
            }
        }
        if(!paused && !mapOverview && !semester.finished() && indoor>=0 && !conversations.active()){
            // Inside buildings, mission keys work directly without holding M.
            for(int k=1;k<=3;k++){
                int key=k==1?Input.Keys.NUM_1:k==2?Input.Keys.NUM_2:Input.Keys.NUM_3;
                if(GameApp.isKeyJustPressed(key)){
                    dialogue=missions.perform(indoor,k);dialogueTimer=6;
                }
            }
            // Fourth option is a repeatable activity, only shown inside its building.
            if(GameApp.isKeyJustPressed(Input.Keys.NUM_4) && indoor==1){
                activities.performInside(2,indoor);
                dialogue=activities.message();dialogueTimer=6;
            }
        }
        if(!semester.finished() && semester.update()){dialogue=semester.announcement();dialogueTimer=8;}
        cameraX=Math.max(0,Math.min(world.width()-WIDTH,player.x-WIDTH/2f));
        cameraY=Math.max(0,Math.min(world.height()-HEIGHT,player.y-HEIGHT/2f));
        GameApp.clearScreen("emerald-800");
        // A full-resolution street map replaces the old rectangular grass/road tile artwork.
        // The original walkable tiles and collision system remain unchanged.
        if(indoor<0 && cityMapTexture!=null){
            photoViewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
            photoViewport.apply();
            photoBatch.setProjectionMatrix(photoCamera.combined);
            photoBatch.begin();
            if(mapOverview){
                photoBatch.draw(cityMapTexture,0,0,WIDTH,HEIGHT);
            }else{
                photoBatch.draw(cityMapTexture,-cameraX,-cameraY,world.width(),world.height());
            }
            photoBatch.end();
        }
        GameApp.startShapeRenderingFilled();
        if(indoor>=0){
            // All seven locations now use supplied photographic interiors.
            if(interiorPhotos[indoor]==null) InteriorScene.draw(indoor);
        }else if(!mapOverview){
            // Character sprites are rendered in a separate sprite batch below.
            // Main character is rendered from the supplied pixel-art sheet below.
        }
        // The TAB overview shows only the clean city map, without any UI panels.
        if(!mapOverview){
            GameApp.drawRect(0,HEIGHT-90,WIDTH,90,"slate-900");
            GameApp.drawRect(0,0,WIDTH,76,"slate-900");
            if(dialogueTimer>0)GameApp.drawRect(85,90,830,66,"slate-900");
            if(conversations.active())GameApp.drawRect(75,95,850,220,"slate-900");
        }
        GameApp.endShapeRendering();
        // Draw the user's supplied pixel-art characters as actual moving NPC sprites.
        if(indoor<0 && !mapOverview){
            photoViewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
            photoViewport.apply();
            photoBatch.setProjectionMatrix(photoCamera.combined);
            photoBatch.begin();
            int spriteIndex=0;
            for(CampusNpc npc:npcs.all()){
                Texture sprite=npcSprites[spriteIndex % npcSprites.length];
                float x=npc.x-cameraX,y=npc.y-cameraY;
                if(sprite!=null && x>-60 && x<WIDTH+60 && y>-60 && y<HEIGHT+80)
                    photoBatch.draw(sprite,x-21,y-17,42,58);
                spriteIndex++;
            }
            if(playerSprite!=null){
                photoBatch.draw(playerSprite,player.x-cameraX-21,player.y-cameraY-17,42,58);
            }
            for(int i=0;i<WorldMap.ENTRY_X.length;i++){
                float ex=WorldMap.ENTRY_X[i]-cameraX,ey=WorldMap.ENTRY_Y[i]-cameraY;
                if(ex>-80&&ex<WIDTH+80&&ey>-80&&ey<HEIGHT+80){
                    float dx=player.x-WorldMap.ENTRY_X[i];
                    float dy=player.y-WorldMap.ENTRY_Y[i];
                    boolean near=dx*dx+dy*dy<80f*80f;
                    if(near){
                        // Bright pulsing halo when the player is close enough to enter.
                        float pulse=(float)(0.5+0.5*Math.sin(System.currentTimeMillis()/170.0));
                        photoBatch.setColor(0.35f+0.25f*pulse,1f,0.40f+0.25f*pulse,0.25f+0.25f*pulse);
                        photoBatch.draw(entranceSprite,ex-43,ey-5,86,79);
                        photoBatch.setColor(1f,1f,1f,1f);
                        photoBatch.draw(entranceSprite,ex-37,ey,74,67);
                    }else{
                        photoBatch.setColor(1f,1f,1f,1f);
                        photoBatch.draw(entranceSprite,ex-34,ey+3,68,61);
                    }
                }
            }
            photoBatch.end();
        }
        // Entrances are shown as green doorway sprites, matching the reference.
        // Render the user-supplied photos between the world geometry and the HUD.
        // Centre-crop rather than stretching the photographs.
        if(indoor>=0 && interiorPhotos[indoor]!=null){
            photoViewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
            photoViewport.apply();
            photoBatch.setProjectionMatrix(photoCamera.combined);
            Texture photo=interiorPhotos[indoor];
            float scale=Math.max((float)WIDTH/photo.getWidth(),(float)HEIGHT/photo.getHeight());
            float w=photo.getWidth()*scale,h=photo.getHeight()*scale;
            photoBatch.begin();
            photoBatch.draw(photo,(WIDTH-w)/2f,(HEIGHT-h)/2f,w,h);
            photoBatch.end();
        }
        // Mission panel is always readable on top of the location photograph.
        if(indoor>=0 && !conversations.active()){
            GameApp.startShapeRenderingFilled();
            GameApp.drawRect(130,indoor==1?230:255,740,indoor==1?195:170,"slate-900");
            GameApp.endShapeRendering();
        }
        GameApp.startSpriteRendering();
        if(!mapOverview){
        GameApp.drawTextCentered("hud","CAMPUS SURVIVOR  |  "+clock.display()+"  |  "+(indoor>=0?InteriorScene.NAMES[indoor]:world.location(player.x,player.y)),WIDTH/2f,HEIGHT-29,"white");
        GameApp.drawTextCentered("hud", "Energy " + Math.round(stats.energy) + "   EUR " + Math.round(stats.money) + "   Stress " + Math.round(stats.stress) + "   Knowledge " + Math.round(stats.knowledge), WIDTH/2f,HEIGHT-53,"white");
        GameApp.drawTextCentered("hud", "Alcohol " + Math.round(stats.alcohol) + "   Popularity " + Math.round(stats.popularity) + "   Exams " + semester.examsPassed() + "/" + semester.examsTaken() + "   " + semester.daysRemaining() + " days left",WIDTH/2f,HEIGHT-76,"amber-400");
        CampusNpc near=npcs.closest(player.x,player.y);
        GameApp.drawTextCentered("hud",semester.finished()?"SEMESTER COMPLETE - N menu / ESC menu":paused?"PAUSED - P to resume":indoor>=0?(indoor==1?"1/2/3 MISSIONS  |  4 SLEEP (8H)  |  Q EXIT":"1/2/3 MISSIONS  |  Q EXIT"):entranceRoom()>=0?"E ENTER BUILDING  |  "+activities.hint(player.x,player.y):activities.hint(player.x,player.y),WIDTH/2f,43,"white");
        GameApp.drawTextCentered("hud",indoor>=0?"Mission progress: "+missions.completed()+"/21  |  Q exit":near!=null?"E talk to "+near.name+"  |  "+stats.reputation():"WASD move  |  TAB map  |  E enter/talk  |  P pause  |  ESC menu",WIDTH/2f,12,"white");
        if(dialogueTimer>0)GameApp.drawTextCentered("hud",dialogue,WIDTH/2f,112,"white");
        if(indoor>=0 && !conversations.active()){
            GameApp.drawTextCentered("hud","MISSIONS - "+InteriorScene.NAMES[indoor],WIDTH/2f,395,"amber-400");
            for(int k=1;k<=3;k++)GameApp.drawTextCentered("hud",missions.label(indoor,k),WIDTH/2f,363-(k-1)*29,"white");
            if(indoor==1)GameApp.drawTextCentered("hud","[4] Sleep in your bed (8h) - restore energy and reduce stress",WIDTH/2f,268,"amber-400");
        }
        if(conversations.active()){
            String[] lines=conversations.lines();
            for(int k=0;k<lines.length;k++)GameApp.drawTextCentered("hud",lines[k],WIDTH/2f,285-k*27,k==0?"amber-400":"white");
        }
        if(semester.finished()) {
            GameApp.drawTextCentered("hud", "SEMESTER RESULT", WIDTH/2f, 350, "amber-400");
            GameApp.drawTextCentered("hud", semester.ending(), WIDTH/2f, 310, "white");
            GameApp.drawTextCentered("hud", "Exams passed: " + semester.examsPassed() + "/3  |  Average: " + semester.averageScore(), WIDTH/2f, 275, "white");
            GameApp.drawTextCentered("hud", "Press N or ESC to return to menu", WIDTH/2f, 238, "white");
        } else {
            // Exam progress is displayed in the top HUD, not over the world.
        }
        }
        GameApp.endSpriteRendering();
    }
    private int activityRoom(int key){return key==1?0:key==2?1:key==3?5:key==4?4:-1;}
    /** Entrance positions correspond to the walkable door tiles in WorldMap. */
    private int entranceRoom(){
        int[] xs=WorldMap.DOOR_X,ys=WorldMap.DOOR_Y;
        for(int i=0;i<7;i++){
            float dx=player.x-WorldMap.ENTRY_X[i],dy=player.y-WorldMap.ENTRY_Y[i];
            if(dx*dx+dy*dy<80*80)return i;
        }
        return -1;
    }
    @Override public void hide(){
        GameApp.disposeFont("hud");GameApp.disposeFont("map-label");
        for(int i=0;i<interiorPhotos.length;i++)if(interiorPhotos[i]!=null){interiorPhotos[i].dispose();interiorPhotos[i]=null;}
        for(int i=0;i<npcSprites.length;i++)if(npcSprites[i]!=null){npcSprites[i].dispose();npcSprites[i]=null;}
        if(playerSprite!=null){playerSprite.dispose();playerSprite=null;}
        if(entranceSprite!=null){entranceSprite.dispose();entranceSprite=null;}
        if(cityMapTexture!=null){cityMapTexture.dispose();cityMapTexture=null;}
        if(photoBatch!=null){photoBatch.dispose();photoBatch=null;}
    }
}
