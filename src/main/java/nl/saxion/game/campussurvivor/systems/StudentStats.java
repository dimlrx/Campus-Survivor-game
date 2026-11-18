package nl.saxion.game.campussurvivor.systems;

/** Six student-life attributes. Values except money are limited to 0..100. */
public final class StudentStats {
    public float energy = 85, money = 75, stress = 15, knowledge = 10, alcohol = 0, popularity = 25;
    private static float clamp(float v){return Math.max(0, Math.min(100, v));}
    /** Passive changes per in-game hour, before action systems arrive in Sprint 2. */
    public void advanceHours(float hours){
        energy=clamp(energy-1.3f*hours);
        stress=clamp(stress+0.45f*hours);
        alcohol=clamp(alcohol-5f*hours);
    }
    public void normalize(){
        energy=clamp(energy);stress=clamp(stress);knowledge=clamp(knowledge);
        alcohol=clamp(alcohol);popularity=clamp(popularity);money=Math.max(0,money);
    }
    public String reputation(){
        if(alcohol>=60)return "Drunk / bad impression";
        if(popularity>=65)return "Popular";
        if(popularity<=15)return "Unknown";
        return "Regular student";
    }
    public String summary(){return "Energy " + Math.round(energy) + "  EUR " + Math.round(money) +
            "  Stress " + Math.round(stress) + "  Knowledge " + Math.round(knowledge) +
            "  Alcohol " + Math.round(alcohol) + "  Popularity " + Math.round(popularity);}
}
