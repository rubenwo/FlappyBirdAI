package Game.UI;


import Game.Models.Bird;
import Game.Models.IDrawable;
import Game.Models.Pipe;
import Game.Models.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class GameWindow extends JPanel implements KeyListener {
    private int width, height;
    private Timer drawLoop;
    private Timer gameLoop;

    private Vector2D gravity;

    private Bird bird;
    private ArrayList<Pipe> pipes;


    private long deltaTime, startTime, endTime;

    private int score, counter, gap;


    public GameWindow() {
        width = 1280;
        height = 720;
        bird = new Bird(null);

        generateFrame();
        pipes = new ArrayList<>();
        gap = 200;
        gravity = new Vector2D(0f, 0.2f);

        drawLoop = new Timer(1000 / GameConstants.TARGET_FPS, event -> {
            repaint();
        });
        gameLoop = new Timer(1000 / (GameConstants.TARGET_FPS), event -> {
            score++;
            counter++;
            CompletableFuture.runAsync(() -> {
                bird.applyForce(gravity);
                bird.update();
                getPipes().forEach(IDrawable::update);
            });


            if (counter > 60) {
                int topSize = (int) (Math.random() * (height / 2));
                int bottomSize = height - topSize - gap;
                pipes.add(new Pipe(true, width, height, topSize));
                pipes.add(new Pipe(false, width, height, bottomSize));
                counter = 0;
            }
            CompletableFuture.runAsync(() -> {
                if (bird.getPosition().getY() < 0 || bird.getPosition().getY() > height - 50) {
                    onGameEnded();
                }
                for (Pipe pipe : pipes) {
                    if (bird.hasCollision(pipe)) {
                        onGameEnded();
                        break;
                    }
                }
            });

            getPipes().removeIf(pipe -> pipe.getPosition().getX() < -(width / 10));
        });
        drawLoop.start();
        gameLoop.start();
    }

    private synchronized ArrayList<Pipe> getPipes() {
        return this.pipes;
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

    private void onGameEnded() {
        drawLoop.stop();
        gameLoop.stop();
        System.out.println("You're dead. Score: " + score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

        bird.draw(g2d);
        getPipes().forEach(p -> p.draw(g2d));

        drawFPS(g2d);
        drawScore(g2d);
    }

    private void drawFPS(Graphics2D g2d) {
        endTime = System.currentTimeMillis();
        deltaTime = endTime - startTime;
        startTime = System.currentTimeMillis();

        String fps = "FPS: ";
        if (deltaTime > 0)
            fps += Double.toString(1000 / deltaTime);
        //lastFPS = fps;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, 20));
        g2d.drawString(fps, 0, height - 50);
    }

    private void drawScore(Graphics2D g2d) {
        String score = "Score: " + this.score;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, 20));
        g2d.drawString(score, 0, 20);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.up();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
