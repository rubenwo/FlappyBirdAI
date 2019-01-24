package Game.Models;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Bird implements IDrawable {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;

    public Bird(Vector2D position) {
        this.position = position;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
    }

    public void applyForce(Vector2D force) {
        acceleration.add(force);
    }

    @Override
    public void update() {
        position.add(velocity);
        velocity.add(acceleration);
        acceleration.mult(0);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fill(new Ellipse2D.Float(position.getX(), position.getY(), 50, 50));
    }
}
