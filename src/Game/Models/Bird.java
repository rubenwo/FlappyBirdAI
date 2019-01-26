package Game.Models;

import Game.UI.GameConstants;
import NN.NeuralNetwork;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Bird implements IDrawable {
    private NeuralNetwork brain;
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private int size, neuron_inputs;
    private float score, fitness;

    public Bird(NeuralNetwork brain) {
        this.position = new Vector2D(GameConstants.WIDTH / 4f, GameConstants.HEIGHT / 2f);
        this.neuron_inputs = 4;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.size = 50;
        if (brain != null) {
            this.brain = brain;
            this.brain.mutate(((e, i, j) -> {
                if (Math.random() < 0.2) {
                    float offset = (float) (Math.random() * .5);
                    return e + offset;
                } else
                    return e;
            }));
        } else {
            this.brain = new NeuralNetwork(neuron_inputs, 8, 2);
        }
        this.score = 0;
        this.fitness = 0;
    }

    public Bird copy() {
        return new Bird(this.brain);
    }

    public void applyForce(Vector2D force) {
        acceleration.add(force);
    }

    @Override
    public void update() {
        position.add(velocity);
        velocity.add(acceleration);
        acceleration.mult(0);
        score++;
    }

    public void up() {
        this.applyForce(new Vector2D(0, -3));
    }

    public boolean hasCollision(Pipe pipe) {
        boolean collision = false;
        if (pipe.isTop()) {
            if (pipe.getPosition().getX() == this.position.getX() && (pipe.getPosition().getY() + pipe.getSize()) > this.position.getY()) {
                System.out.println("Hitting top with bird Y-position: " + this.position.getY());
                System.out.println("Hitting top with pipe Y-position: " + (pipe.getPosition().getY() - pipe.getSize() + size));
                collision = true;
            }
        } else if (this.getPosition().getY() < 0 || this.getPosition().getY() > GameConstants.HEIGHT - 50) {
            collision = true;
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

    public void think(ArrayList<Pipe> pipes) {
        Pipe closest = null;
        float record = 10000000000000f;
        for (int i = 0; i < pipes.size(); i++) {
            float diff = pipes.get(i).getPosition().getX() - this.position.getX();
            if (diff > 0 && diff < record) {
                record = diff;
                closest = pipes.get(i);
            }
        }

        if (closest != null) {
            float[] inputs = new float[neuron_inputs];
            if (closest.isTop()) {
                inputs[0] = closest.getPosition().getX() / GameConstants.WIDTH;
                inputs[1] = closest.getPosition().getY() / GameConstants.HEIGHT;
                inputs[2] = (closest.getPosition().getY() - 200) / GameConstants.HEIGHT;
                inputs[3] = this.position.getY() / GameConstants.HEIGHT;
            } else {
                inputs[0] = closest.getPosition().getX() / GameConstants.WIDTH;
                inputs[1] = (closest.getPosition().getY() + 200) / GameConstants.HEIGHT;
                inputs[2] = closest.getPosition().getY() / GameConstants.HEIGHT;
                inputs[3] = this.position.getY() / GameConstants.HEIGHT;
            }

            float[] action = this.brain.predict(inputs);
            if (action[1] > action[0])
                this.up();
            //   System.out.println("Action: " + action[0] + " | " + action[1]);

        }


    }

    public float getScore() {
        return score;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public NeuralNetwork getBrain() {
        return brain;
    }
}
