import Game.UI.GameWindow;
import NN.NeuralNetwork;
import Net.Connection;

public class Main {
    public static void main(String[] args) {
        GameWindow ui = new GameWindow();
        Connection connection = new Connection();
        connection.updateHighScore("Ruben", 1230123);
    }
}
