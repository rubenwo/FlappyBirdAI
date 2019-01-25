package Game.Models;

import NN.NeuralNetwork;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Bird implements IDrawable {
    private NeuralNetwork brain;
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private int size;

    public Bird(Vector2D position) {
        this.position = position;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.size = 50;
        this.brain = new NeuralNetwork(5, 8, 2);

    }

    public Bird(Vector2D position, NeuralNetwork brain) {
        this.position = position;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.size = 50;
        this.brain = brain.copy();
        this.brain.mutate((float e, int i, int j) -> {
            if (Math.random() < 0.1) {
                float offset = (float) (Math.random() * 0.5);
                return e + offset;
            } else {
                return e;
            }
        });
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

    public boolean hasCollision(Pipe pipe) {
        boolean collision = false;
        if (pipe.isTop()) {
            if (pipe.getPosition().getX() == this.position.getX() && (pipe.getPosition().getY() + pipe.getSize()) > this.position.getY()) {
                System.out.println("Hitting top with bird Y-position: " + this.position.getY());
                System.out.println("Hitting top with pipe Y-position: " + (pipe.getPosition().getY() - pipe.getSize() + size));
                collision = true;
            }
        } else {
            if (pipe.getPosition().getX() == this.position.getX() && (pipe.getPosition().getY() - size / 2) < this.position.getY()) {
                System.out.println("Hitting bottom with bird Y-position: " + this.position.getY());
                System.out.println("Hitting bottom with pipe Y-position: " + (pipe.getPosition().getY() - pipe.getSize() + size));
                collision = true;
            }
        }
        return collision;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fill(new Ellipse2D.Float(position.getX(), position.getY(), size, size));
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }
}
