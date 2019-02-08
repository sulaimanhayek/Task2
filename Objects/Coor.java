package Objects;

public class Coor {
    public int x;
    public int y;

    public Coor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int distanceTo(Coor r) {
        return Math.abs(x - r.x) + Math.abs(y - r.y);
    }
}