package nl.saxion.game.campussurvivor.gameplay;

import java.util.Random;
import nl.saxion.game.campussurvivor.systems.GameClock;
import nl.saxion.game.campussurvivor.systems.StudentStats;

/** Sprint 3: three exam weeks, deterministic scheduling and random campus events. */
public final class SemesterSystem {
    private final Random random = new Random();
    private final StudentStats stats;
    private final GameClock clock;
    private int lastDay = 0;
    private int examsTaken = 0;
    private int examsPassed = 0;
    private int totalExamScore = 0;
    private String announcement = "Semester started. Exams on days 7, 14 and 21.";
    private boolean finished;

    public SemesterSystem(GameClock clock, StudentStats stats) {
        this.clock = clock;
        this.stats = stats;
    }

    /** Call after every clock update, including multi-hour activities. */
    public boolean update() {
        int current = Math.min(clock.day(), 21);
        boolean changed = false;
        while (lastDay < current && !finished) {
            lastDay++;
            if (lastDay % 7 == 0) {
                takeExam();
                changed = true;
                if (lastDay == 21) finished = true;
            } else if (lastDay % 2 == 0) {
                randomEvent();
                changed = true;
            }
        }
        return changed;
    }

    private void takeExam() {
        int score = Math.max(0, Math.min(100, Math.round(stats.knowledge * .8f + stats.energy * .15f
                - stats.stress * .25f - stats.alcohol * .2f + 12)));
        examsTaken++;
        totalExamScore += score;
        boolean passed = score >= 55;
        if (passed) examsPassed++;
        stats.stress += passed ? -12 : 18;
        stats.energy -= 12;
        stats.knowledge -= 12; // New course material to learn for the next exam.
        stats.normalize();
        announcement = "EXAM " + examsTaken + ": " + score + "/100 - " + (passed ? "PASSED!" : "FAILED. Study more!");
    }

    private void randomEvent() {
        int event = random.nextInt(5);
        switch (event) {
            case 0:
                stats.money -= 35;
                stats.stress += 12;
                announcement = "EVENT: Laptop broke! Repair costs EUR 35. Stress +12.";
                break;
            case 1:
                stats.energy += 18;
                stats.stress -= 8;
                announcement = "EVENT: Free campus pizza! Energy +18, stress -8.";
                break;
            case 2:
                stats.stress += 15;
                announcement = "EVENT: Groupmate did nothing. Stress +15.";
                break;
            case 3:
                stats.money += 25;
                announcement = "EVENT: Extra shift available! You earned EUR 25.";
                break;
            default:
                stats.popularity += 12;
                announcement = "EVENT: Your presentation went viral! Popularity +12.";
        }
        stats.normalize();
    }

    public String announcement() { return announcement; }
    public boolean finished() { return finished; }
    public int examsPassed() { return examsPassed; }
    public int examsTaken() { return examsTaken; }
    public int averageScore() { return examsTaken == 0 ? 0 : totalExamScore / examsTaken; }
    public int daysRemaining() { return Math.max(0, 21 - clock.day()); }
    public String ending() {
        if (examsPassed == 3 && stats.stress < 75) return "TOP STUDENT - You survived Enschede with flying colours!";
        if (examsPassed >= 2) return "SEMESTER SURVIVED - You made it through!";
        if (stats.popularity >= 65) return "CAMPUS LEGEND - Famous, but the grades need work.";
        if (stats.money < 10) return "BROKE STUDENT - Time to find a better balance.";
        return "SECOND CHANCE - Try balancing study, work and rest.";
    }
}
