package Game.UI;


import Game.Models.Bird;
import Game.Models.IDrawable;
import Game.Models.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GameWindow extends JPanel implements KeyListener {
    private int width, height;
    private ArrayList<IDrawable> drawables;
    private Timer drawLoop;
    private Timer gameLoop;

    private Vector2D gravity;

    private Bird bird;
    private long deltaTime, startTime, endTime;
    private String lastFPS = "FPS: ";

    public GameWindow() {
        width = 1280;
        height = 720;
        generateFrame();
        drawables = new ArrayList<>();
        gravity = new Vector2D(0f, 0.2f);
        bird = new Bird(new Vector2D(width / 4, height / 2));
        drawables.add(bird);

        drawLoop = new Timer(1000 / Constants.TARGET_FPS, event -> {
            repaint();
        });
        gameLoop = new Timer(1000 / Constants.TARGET_FPS, event -> {
            bird.applyForce(gravity);
            drawables.forEach(IDrawable::update);
            if (bird.getPosition().getY() < 0 || bird.getPosition().getY() > height - 50) {
                drawLoop.stop();
                gameLoop.stop();
            }
        });
        drawLoop.start();
        gameLoop.start();
    }

    private void generateFrame() {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(this);
        //frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

        drawables.forEach(d -> d.draw(g2d));

        drawFPS(g2d);
    }

    private void drawFPS(Graphics2D g2d) {
        endTime = System.currentTimeMillis();
        deltaTime = endTime - startTime;
        startTime = System.currentTimeMillis();

        String fps = "FPS: ";
        if (deltaTime > 0)
            fps += Double.toString(1000 / deltaTime);
        lastFPS = fps;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, 20));
        g2d.drawString(fps, 0, 20);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.applyForce(new Vector2D(0, -3));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
