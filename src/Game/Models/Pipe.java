package Game.Models;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Pipe implements IDrawable {
    private Vector2D position;
    private Vector2D velocity;

    public Pipe() {
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.draw(new Rectangle2D.Float(position.getX(), position.getY(), 10, 10));
    }
}
