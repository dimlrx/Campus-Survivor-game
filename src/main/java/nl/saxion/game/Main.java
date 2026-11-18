package nl.saxion.game;
import nl.saxion.game.campussurvivor.CampusMenuScreen;
import nl.saxion.game.campussurvivor.CampusGameScreen;
import nl.saxion.gameapp.GameApp;
public class Main {
    public static void main(String[] args) {
        GameApp.addScreen("menu", new CampusMenuScreen());
        GameApp.addScreen("game", new CampusGameScreen());
        GameApp.start("Campus Survivor", 1000, 650, 60, false, "menu");
    }
}
