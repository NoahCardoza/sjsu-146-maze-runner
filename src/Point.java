public class Point {
    private final int row;
    private final int col;
    private int state;
    private int seenAt;
    public static final int UNVISITED = 0;
    public static final int VISITED = 1;
    public static final int CHOSEN = 2;

    public Point(int row, int col){
        this.row = row;
        this.col = col;
        this.state = UNVISITED;
    }

    public int getSeenAt() {
        return seenAt;
    }

    public void setSeenAt(int seenAt) {
        this.seenAt = seenAt;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Point{" +
                "row=" + row +
                ", col=" + col +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (row != point.row) return false;
        return col == point.col;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 69 * result + col;
        return result;
    }
}

