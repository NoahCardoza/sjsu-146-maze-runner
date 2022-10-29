/**
 * Interface defined for modular design of the
 * maze display method.
 */
public interface MazeGetter {
    /**
     * A method to return the character to display in each tile
     * of the maze based on the row/column.
     *
     * @param row the row to lookup
     * @param col the column to lookup
     *
     * @return a single character string to represent the tile at that row/col
     */
    String get(int row, int col);
}
