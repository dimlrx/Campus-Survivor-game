package nl.saxion.game.campussurvivor.systems;

/** One real second equals one in-game minute. Starts Monday 08:00. */
public final class GameClock {
    private float minutes = 8 * 60;
    private static final String[] DAYS={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    public float advance(float seconds){float elapsed=Math.max(0,Math.min(seconds,.05f));minutes+=elapsed;return elapsed/60f;}
    public void advanceMinutes(float amount){minutes+=Math.max(0,amount);}
    public int day(){return (int)(minutes/1440);}
    public int hour(){return (int)(minutes/60)%24;}
    public int minute(){return (int)minutes%60;}
    public String display(){return DAYS[day()%7]+" " + String.format(java.util.Locale.ROOT,"%02d:%02d",hour(),minute());}
}
