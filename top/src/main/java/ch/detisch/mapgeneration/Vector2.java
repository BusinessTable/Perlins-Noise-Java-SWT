package ch.detisch.mapgeneration;

public class Vector2 {
    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void add(Vector2 vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }

    public void sub(Vector2 vector) {
        this.x -= vector.getX();
        this.y -= vector.getY();
    }
}
