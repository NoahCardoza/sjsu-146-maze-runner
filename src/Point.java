public class Point {
    private int row;
    private int col;
    private int state;

    public static int UNVISITED = 0;
    public static int VISITED = 1;
    public static int CHOSEN = 2;
    public static int DISCARDED = -1;

    public Point(int row, int col){
        this.row = col;
        this.row = col;
        this.state = 0;
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

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setState(int state) {
        this.state = state;
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

