package math;

public class Pair {
    
    private final int x;
    private final int y;

    public Pair(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Pair)) return false;
        Pair po = (Pair) o;
        return (this.x == po.x && this.y == po.y);
    }
}
