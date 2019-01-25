package Game.Models;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Pipe implements IDrawable {
    private Vector2D position;
    private Vector2D velocity;
    private int size;
    private int width;
    private boolean top;

    public Pipe(boolean top, int width, int height, int size) {
        this.size = size;
        this.top = top;
        this.width = width / 15;
        if (top) {
            position = new Vector2D(width, 0);
        } else {
            position = new Vector2D(width, height - size);
        }
        velocity = new Vector2D(-10, 0);
    }

    @Override
    public void update() {
        position.add(velocity);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.fill(new Rectangle2D.Float(position.getX(), position.getY(), width, size));
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public boolean isTop() {
        return top;
    }
}
