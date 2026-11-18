package nl.saxion.game.campussurvivor.gameplay;
import nl.saxion.game.campussurvivor.systems.StudentStats;
import nl.saxion.game.campussurvivor.systems.GameClock;
/** Eighteen one-time location missions; rewards and requirements affect student life. */
public final class MissionSystem {
 private final StudentStats stats; private final GameClock clock;
 private final boolean[][] done=new boolean[7][3];
 private final String[][] names={
  {"Finish the group assignment","Prepare exam flashcards","Help a lost first-year"},
  {"Clean your apartment","Cook a healthy meal","Repair your study corner"},
  {"Attend a tech workshop","Build a prototype","Pitch your project"},
  {"Explore local history","Find a student discount","Volunteer at an event"},
  {"Meet your study friends","Organize a quiz night","Help the cafe staff"},
  {"Restock the shelves","Serve the lunch rush","Train a new colleague"},
  {"Find the correct platform","Help a traveller at the gates","Plan a student train trip"}
 };
 private final int[][] energy={{18,12,8},{8,5,10},{12,20,15},{7,5,15},{10,14,16},{12,18,14},{6,9,11}};
 private final int[][] cash={{0,0,0},{0,12,15},{0,10,0},{0,8,0},{10,20,25},{0,0,0},{0,0,5}};
 public MissionSystem(StudentStats stats,GameClock clock){this.stats=stats;this.clock=clock;}
 public String label(int room,int choice){int i=choice-1;return "["+choice+"] "+names[room][i]+(done[room][i]?"  (DONE)":"  (energy "+energy[room][i]+")");}
 public String perform(int room,int choice){int i=choice-1;if(room<0||room>=7||i<0||i>=3)return "Invalid mission";
  if(done[room][i])return "Mission already completed. Try another task.";
  if(stats.energy<energy[room][i]+5)return "Not enough energy. Sleep at your flat first.";
  if(stats.money<cash[room][i])return "Not enough money. Work at Jumbo first.";
  stats.energy-=energy[room][i];stats.money-=cash[room][i];clock.advanceMinutes(60+30*i);
  if(room==0){stats.knowledge+=10+4*i;stats.stress+=3;}
  if(room==1){stats.stress-=10+3*i;stats.energy+=6;}
  if(room==2){stats.knowledge+=8+3*i;stats.popularity+=3;}
  if(room==3){stats.popularity+=7+2*i;stats.stress-=3;}
  if(room==4){stats.popularity+=9+3*i;stats.alcohol+=i==0?0:5;}
  if(room==5){stats.money+=25+15*i;stats.stress+=5;}
  if(room==6){stats.knowledge+=5+2*i;stats.popularity+=4+i;stats.stress-=2;}
  stats.normalize();done[room][i]=true;
  return "Completed: "+names[room][i]+"! Check your updated stats.";
 }
 public int completed(){int n=0;for(boolean[] a:done)for(boolean b:a)if(b)n++;return n;}
 public void npcReward(){stats.popularity+=4;stats.knowledge+=3;stats.normalize();}
}
