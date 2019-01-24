import AI.Brain;
import Game.UI.GameWindow;
import Net.Connection;

public class Main {
    public static void main(String[] args) {
        GameWindow ui = new GameWindow();
        Brain brain = new Brain();
        Connection connection = new Connection();
        connection.updateHighScore("Ruben", 1230123);
    }
}
