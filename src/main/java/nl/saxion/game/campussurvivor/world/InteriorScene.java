package nl.saxion.game.campussurvivor.world;

import nl.saxion.gameapp.GameApp;

/** Hand-built 2.5D interior sets. Geometry is deliberately original, not claimed as surveyed buildings. */
public final class InteriorScene {
    public static final String[] NAMES={"SAXION | STUDY LOUNGE","STUDENTENFLAT | APARTMENT","KENNISPARK | TECH HUB","CENTRUM | SHOPPING STREET","OUDE MARKT | CAFE","JUMBO | WORKPLACE","ENSCHEDE CS | STATION"};
    public static final String[] DETAILS={"Library desks, computers and campus windows","Bed, kitchen and personal study desk","Glass offices, plants and workstations","Shop displays and an indoor arcade","Bar, lounge tables and warm lighting","Jumbo supermarket checkout counters and shelves","NS gates, platforms and train information"};
    public static final String[] ACTIONS={"[1] STUDY FOR 2 HOURS","[2] SLEEP FOR 8 HOURS","EXPLORE THE TECH HUB","EXPLORE THE CITY CENTRE","[4] GO OUT FOR 3 HOURS","[3] WORK FOR 4 HOURS","TRAVEL FROM ENSCHEDE CS"};
    private InteriorScene(){}
    private static void r(float x,float y,float w,float h,String c){GameApp.drawRect(x,y,w,h,c);}
    public static void draw(int room){
        String wall=room==4?"rose-800":room==1?"violet-800":room==0?"sky-800":"slate-700";
        r(0,0,1000,650,"slate-900");
        // Perspective room: back wall, slanted side walls and tiled floor.
        r(65,155,870,425,wall);r(85,170,830,345,"slate-800");
        r(65,155,36,425,"slate-700");r(899,155,36,425,"slate-700");
        r(85,100,830,100,"slate-600");
        for(int x=100;x<920;x+=90)r(x,101,3,97,"slate-500");
        for(int y=115;y<199;y+=27)r(85,y,830,2,"slate-500");
        // Three tall glazed windows with mullions and sky beyond.
        for(int x=145;x<=710;x+=280){
            r(x,345,170,138,"slate-900");r(x+9,354,152,120,"sky-300");
            r(x+80,354,5,120,"slate-700");r(x+9,411,152,5,"slate-700");
            r(x+20,354,34,47,"sky-500");r(x+110,354,32,65,"sky-500");
        }
        // Furniture gives each interior a recognizable purpose.
        if(room==0||room==2){
            for(int x=150;x<=690;x+=270){
                r(x+18,208,150,16,"amber-800");r(x+28,160,12,48,"slate-900");r(x+146,160,12,48,"slate-900");
                r(x+60,226,72,57,"slate-900");r(x+66,232,60,43,"sky-300");r(x+89,211,12,16,"slate-400");
                r(x+52,138,65,17,"slate-700");r(x+65,154,12,47,"slate-700");
            }
            r(425,300,150,19,"amber-800");r(450,319,96,46,"slate-900");r(459,328,78,29,"sky-300");
        }else if(room==1){
            r(145,195,285,74,"slate-900");r(154,214,267,45,"violet-500");r(160,232,84,23,"slate-300");
            r(540,198,220,18,"amber-800");r(558,150,12,48,"slate-900");r(740,150,12,48,"slate-900");
            r(605,218,75,58,"slate-900");r(614,226,57,40,"sky-300");
            r(480,305,325,50,"slate-500");r(500,315,48,34,"slate-300");r(565,315,48,34,"slate-300");
        }else if(room==4){
            r(150,260,700,63,"amber-800");r(150,220,700,40,"slate-900");
            for(int x=200;x<=740;x+=135){r(x,325,65,70,"slate-900");r(x+7,332,51,54,"amber-200");}
            for(int x=235;x<=765;x+=265){r(x,143,110,17,"amber-800");r(x+15,160,12,55,"slate-900");r(x+80,160,12,55,"slate-900");}
            r(480,460,40,60,"amber-200");
        }else if(room==3||room==5){
            for(int x=145;x<=715;x+=190){
                r(x,245,135,165,"slate-900");r(x+10,258,115,135,"amber-800");
                for(int y=280;y<=370;y+=42){r(x+15,y,105,8,"slate-300");r(x+30,y+10,25,24,"sky-300");r(x+70,y+10,25,24,"violet-500");}
            }
            r(350,154,300,56,"slate-900");r(365,175,270,27,"amber-500");
        }
        // Additional themed set dressing; original stylized approximations, not surveyed interiors.
        if(room==0){ // Saxion: reception, lecture screen, campus noticeboard
            r(350,445,300,62,"slate-900");r(361,455,278,43,"sky-300");
            r(400,265,200,26,"amber-800");r(420,246,160,19,"slate-700");
            for(int x=115;x<310;x+=55){r(x,295,43,55,"slate-900");r(x+5,302,33,45,"amber-200");}
        }else if(room==1){ // Flat: kitchenette, rug, books and storage
            r(115,365,260,95,"slate-500");r(130,376,85,73,"slate-300");r(230,376,125,73,"amber-800");
            r(270,386,35,9,"slate-900");r(275,402,22,22,"sky-300");
            r(420,110,200,37,"violet-500");r(450,120,140,14,"amber-200");
        }else if(room==2){ // Kennispark: innovation lab, electronics and server rack
            r(115,295,85,190,"slate-900");
            for(int y=320;y<470;y+=35){r(128,y,59,24,"slate-600");r(138,y+8,7,7,"emerald-500");}
            r(690,280,165,90,"slate-900");r(700,290,145,70,"cyan-600");
        }else if(room==3){ // City centre: boutique arcade and cafe corner
            r(120,435,180,70,"slate-900");r(130,445,160,50,"rose-500");
            r(705,145,150,75,"amber-800");r(720,155,120,52,"amber-200");
        }else if(room==4){ // Oude Markt: cafe tables, menu and pendant lamps
            r(400,435,200,74,"slate-900");r(412,448,176,48,"amber-800");
            for(int x=245;x<800;x+=265){r(x+40,500,12,55,"amber-800");r(x+12,470,68,25,"amber-200");}
        }else if(room==5){ // Shop: till, conveyor belt and warehouse crates
            r(335,235,330,36,"slate-900");r(355,246,160,18,"slate-500");
            r(550,272,75,52,"slate-900");r(560,282,55,30,"sky-300");
            for(int x=700;x<875;x+=70){r(x,130,60,58,"amber-800");r(x+5,145,50,6,"amber-200");}
        }
        // Wall sconces and highlights.
        for(int x:new int[]{108,878}){r(x,300,12,55,"amber-200");r(x-9,344,30,10,"amber-500");}
    }
}
