package Game.Models;

public class Vector2D {
    private float x, y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D vector) {
        x += vector.x;
        y += vector.y;
    }

    public void sub(Vector2D vector) {
        x -= vector.x;
        y -= vector.y;
    }

    public void mult(float scalar) {
        x *= scalar;
        y *= scalar;
    }

    public void div(float scalar) {
        x /= scalar;
        y /= scalar;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
