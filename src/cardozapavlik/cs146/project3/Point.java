package cardozapavlik.cs146.project3;

/**
 * Represents a node in the graph and used to store
 * information about a point in the graph.
 */
public class Point {
    private final int row;
    private final int col;
    private int state;
    private int seenAt;
    public static final int UNVISITED = 0;
    public static final int VISITED = 1;
    public static final int CHOSEN = 2;
    private Point parent;
    private int distance;

    /**
     * Shortcut to construct a point from an int array
     * when loading a list of points from a serialized
     * unit test file.
     *
     * @param coords and array containing [col, row]
     */
    public Point(int[] coords){
        this.row = coords[1];
        this.col = coords[0];
        this.state = UNVISITED;
        this.parent = null;
        this.distance = -1;
    }

    /**
     * Constructs a point from a row, column pair.
     *
     * @param row the row the point is on
     * @param col the column the row is on
     */
    public Point(int row, int col){
        this.row = row;
        this.col = col;
        this.state = UNVISITED;
        this.parent = null;
        this.distance = -1;
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

    public void setParent(Point parent){
        this.parent = parent;
    }

    public Point getParent(){
        return this.parent;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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

