package nl.saxion.game.campussurvivor.world;

import nl.saxion.gameapp.GameApp;

/** Enschede-inspired geographic layout. West is left; north is up.
 * Landmarks are arranged by approximate real-world direction, NOT imported OSM geometry.
 */
public final class WorldMap {
    public static final int TILE=64, COLS=40, ROWS=28;
    // Landmark order: Saxion, student flat, Kennispark, Centrum, Oude Markt, shops.
    public static final int[] DOOR_X={9,32,6,18,27,35,20};
    public static final int[] DOOR_Y={19,20,11,12,12,8,3};
    private static final String[] NAMES={"SAXION","STUDENTENFLAT","KENNISPARK","CENTRUM","OUDE MARKT","JUMBO / WERK","ENSCHEDE CS"};
    private static final char[] TYPES={'s','f','k','c','o','w','t'};
    // Buildings are positioned to keep a walkable street grid, while reflecting
    // Kennispark NW, Saxion SW, and Oude Markt NE of Saxion.
    public static final int[][] BUILDINGS={{7,11,6,4},{2,6,6,4},{5,23,7,4},{21,14,7,4},{24,20,7,4},{31,12,7,4},{20,6,7,4}};
    public static final int[] ENTRY_X={592,2096,432,1200,1744,2288,1328};
    public static final int[] ENTRY_Y={1232,1296,720,816,784,560,208};
    private static final String[] PATHS={
        "00000000111100000001110000000000000000000000000000000000000011100000000000000011",
        "00000000111100000001111000000000000000000000000000000000000011100000000000000011",
        "00000000111100000001111000000000000000000000000000000000000011100000000000000111",
        "00000000111100000000111000000000000000000000000000000000000011100000000000000111",
        "00000000111100000000111000000000000000000000000000000000000011100000000001111111",
        "11111111111111111111111111111111111111111111111111111111111111111111111111111111",
        "11111111111111111111111111111111111111111111111111111111111111111111111111111111",
        "00000000111100000000111111111111111111111111111111111111000011100000000000001111",
        "00000000111100000000111111111111111111111111111111111111000011100000000000001111",
        "00000000111100000000011100000000000000111100000000000000000011100000000000001110",
        "00000000111100000000011100000000000000111100000000000000000011100000000000001110",
        "00000000111100000000011100000000000000111100000000000000000011100000000000011110",
        "00000000111100000000011110000000000000111100000000000000000011100000011111111111",
        "00000000111100000000011110000000000000111100000000000111111111111111111111111111",
        "00000000111000000000011111111111111111111111111111111111111111111111111111111111",
        "11111111111111111111111111111111111111111111111111111111111111111111111111111110",
        "11111111111111111111111111111111111111111111111111111111111011111111111111111100",
        "11111111111111111111111111111111111111111111111111111111111111111111111111111100",
        "00000001111000000000011110111111111111111111111111111111111111111111111111111100",
        "11111111111111111111111111111111111111000011111111111111111111111111111111111100",
        "11111111111111111111111111111111111110000001111111111111111111111000000000111100",
        "11111111111111111111111111111111111110000001111111111111111111100000000000111100",
        "11100011111111111111111111111111111110000001111111111111111111100000000000111000",
        "00000011111111111111101111111111111111000011111111111111111111100000000000111000",
        "00000011111111111111101111111111111111111111111111111111111111100000000000111000",
        "00000111100000000000000111111111111111111111111111111111111111100000000000111000",
        "00000111100000000000000111111111111111111111111111111111111111000000000000111000",
        "00000111100000000000000111111111111111111111111111111111111111000000000000111000",
        "00000111100000000000000111000000000000000000000000000000000111000000000001111000",
        "00000111000000000000000111000000000000000000000000000000000111000000000001111000",
        "00000111000000000000000111000000000000000000000000000000001111000000000001111000",
        "00001111000000000000000111100000000000000000000000000000001110000000000001110000",
        "00001111000000000000000111100000000000000000000000000000011110000000000001110000",
        "00001111000000000000000111100000000000000000000000000000011100000000000001110000",
        "00001111000000000000000111100000000000000000000000000000111100000000000001110000",
        "00001111000000000000000111100000000000000000000000000001111000000000000011110000",
        "00001110000011111111111111111111111111100000000000000001111000000000000011110000",
        "11111111111111111111111111111111111111111111111111111111110000000000000011111111",
        "11111111111111111111111111111111111111111111111111111111111111111111111111111111",
        "11111111111111111111111111111111111111111111111111111111111111111111111111111111",
        "11111110000011111111111011100011111111111111111111111111111111111111111111111111",
        "00011110000000000000000011111111111111111111111111111111111111111111111111100000",
        "00011110000000000000000111111111111111111111111111001110000000000000000011100000",
        "00011110000000000000000111111100000000000000000000001110000000000000000011100000",
        "00011110000000000000000111100000000000000000000000011110000000000000000011100000",
        "00011110000000000000000111100000000000000000000000011100000000000000000011100000",
        "00011110000000000000000111100000000000000000000000011100000000000000000011100000",
        "00011110000000000000000111100000000000000000000000011100000000000000000011100000",
        "00011110000000000000000111100000000000000000000000011100000000000000000011100000",
        "00011110000000000000000111100000000000000000000000011100000000000000000011100000",
        "00011110000000000000000111100000000000000000000000111100000000000000000011100000",
        "00011100000000000000000111100000000000000000000000111000000000000000000011100000",
        "00011100000000000000000111100000000000000000000000111000000000000000000011100000",
        "00011100000000000000000111100000000000000000000000111000000000000000000011100000",
        "00011100000000000000000111100000000000000000000000111000000000000000000011100000",
        "00011100000000000000000111100000000000000000000000111000000000000000000011100000"
    };
    private final char[][] tiles=new char[ROWS][COLS];
    public WorldMap(){
        for(int y=0;y<ROWS;y++)for(int x=0;x<COLS;x++)tiles[y][x]='g';
        // Connected avenues: schematic corridors inspired by the west-to-centre journey.
        road(0,0,COLS,2);road(0,26,COLS,2);road(0,0,2,ROWS);road(38,0,2,ROWS);
        road(0,10,COLS,2);road(0,18,COLS,2);road(0,21,COLS,2);
        road(9,0,3,ROWS);road(17,0,3,ROWS);road(28,0,3,ROWS);
        road(0,4,COLS,2);road(0,16,COLS,2);
        for(int i=0;i<BUILDINGS.length;i++){
            // Background image owns the architecture; do not create invisible old building blockers.
            tiles[DOOR_Y[i]][DOOR_X[i]]='p';
        }
    }
    private void road(int x,int y,int w,int h){for(int j=y;j<y+h;j++)for(int i=x;i<x+w;i++)if(i>=0&&i<COLS&&j>=0&&j<ROWS)tiles[j][i]='p';}

    public int width(){return COLS*TILE;}
    public int height(){return ROWS*TILE;}
    // Free movement: only the outside edge of the game world is blocked.
    // The old path mask no longer restricts the player or NPCs.
    public boolean blocked(float x,float y){
        return x<0 || y<0 || x>=width() || y>=height();
    }
    public float[] nearestPath(float x,float y){
        return new float[]{Math.max(16,Math.min(width()-16,x)),
                           Math.max(16,Math.min(height()-16,y))};
    }
    public boolean canStand(float x,float y,float r){return !blocked(x-r,y-r)&&!blocked(x+r,y-r)&&!blocked(x-r,y+r)&&!blocked(x+r,y+r);}
    public void draw(float cx,float cy,int vw,int vh){
        int left=Math.max(0,(int)(cx/TILE)-1),right=Math.min(COLS-1,(int)((cx+vw)/TILE)+1);
        int bottom=Math.max(0,(int)(cy/TILE)-1),top=Math.min(ROWS-1,(int)((cy+vh)/TILE)+1);
        for(int y=bottom;y<=top;y++)for(int x=left;x<=right;x++){
            float sx=x*TILE-cx,sy=y*TILE-cy;char t=tiles[y][x];
            GameApp.drawRect(sx,sy,65,65,t=='p'?"slate-500":t=='g'?"emerald-700":"slate-600");
            if(t=='p'){
                GameApp.drawRect(sx+2,sy+2,60,60,(x+y)%3==0?"slate-400":"slate-500");
                if((x+y)%4==0)GameApp.drawRect(sx+22,sy+30,18,4,"slate-300");
            }else if(t=='g'){
                if((x*13+y*17)%13==0){GameApp.drawRect(sx+29,sy+7,7,19,"amber-800");GameApp.drawRect(sx+14,sy+24,37,30,"emerald-900");GameApp.drawRect(sx+19,sy+32,27,23,"emerald-500");}
                else if((x*7+y*11)%9==0)GameApp.drawRect(sx+16,sy+16,9,5,"emerald-800");
            }
        }
        // Road intersections and zebra crossings.
        for(int x=9;x<=29;x+=9)for(int y:new int[]{10,18,21})for(int k=0;k<4;k++)
            GameApp.drawRect(x*TILE-cx+6+k*14,y*TILE-cy+18,9,30,"slate-200");
        // Building artwork is rendered from transparent 2.5D sprites in CampusGameScreen.
        // Walkable entrance tiles and collision geometry are unchanged.

    }
    public void drawLabels(float cx,float cy){for(int i=0;i<NAMES.length;i++){int[] b=BUILDINGS[i];GameApp.drawTextCentered("map-label",NAMES[i],(b[0]+b[2]/2f)*TILE-cx,(b[1]+b[3]-.45f)*TILE-cy,"white");}}
    public static float projectX(float x,float y){return (x-y)*.74f;}
    public static float projectY(float x,float y){return (x+y)*.37f;}
    public static float cameraProjectX(float x,float y){return projectX(x,y)-500;}
    public static float cameraProjectY(float x,float y){return projectY(x,y)-325;}
    public void drawOblique(float cx,float cy){
        for(int sum=0;sum<COLS+ROWS-1;sum++)for(int x=0;x<COLS;x++){
            int y=sum-x;if(y<0||y>=ROWS)continue;
            float sx=projectX(x*TILE,y*TILE)-cx,sy=projectY(x*TILE,y*TILE)-cy;
            char t=tiles[y][x];String ground=t=='p'?"slate-500":t=='g'?"emerald-700":"slate-400";
            GameApp.drawRect(sx-36,sy-12,72,24,ground);GameApp.drawRect(sx-26,sy-19,52,38,ground);
            if(t=='p'&&(x+y)%4==0)GameApp.drawRect(sx-9,sy-2,18,4,"slate-300");
            if(t!='p'&&t!='g'){
                int h=t=='f'?85:t=='k'?70:55;
                GameApp.drawRect(sx-22,sy+3,44,h,"slate-700");GameApp.drawRect(sx-27,sy+h-2,54,12,t=='s'?"sky-500":t=='o'?"rose-500":"amber-500");
                for(int a=17;a<h-10;a+=23)GameApp.drawRect(sx-12,sy+a,23,12,"sky-300");
            }
        }
    }
    public void drawObliqueLabels(float cx,float cy){for(int i=0;i<NAMES.length;i++){int[] b=BUILDINGS[i];float x=(b[0]+b[2]/2f)*TILE,y=(b[1]+b[3]/2f)*TILE;GameApp.drawTextCentered("map-label",NAMES[i],projectX(x,y)-cx,projectY(x,y)-cy+80,"white");}}
    public String location(float x,float y){float best=Float.MAX_VALUE;int nearest=0;for(int i=0;i<BUILDINGS.length;i++){int[] b=BUILDINGS[i];float dx=x-(b[0]+b[2]/2f)*TILE,dy=y-(b[1]+b[3]/2f)*TILE;float d=dx*dx+dy*dy;if(d<best){best=d;nearest=i;}}return best<550*550?NAMES[nearest]:"Enschede";}
}
