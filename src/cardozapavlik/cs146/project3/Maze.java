package cardozapavlik.cs146.project3;

import java.io.*;
import java.util.Collections;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Recursive backtracking algorithm to generate random mazes.
 * <a href="http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking">Reference</a>
 */
public class Maze {
    public static final int NORTH = 0b1;
    public static final int SOUTH = 0b10;
    public static final int EAST = 0b100;
    public static final int WEST = 0b1000;
    private final int width;
    private final int height;
    private final int[][] maze;

    /**
     * Constructs a square maze.
     *
     * @param size the width and height of the maze
     */
    public Maze(int size) {
        this(size, size);
    }

    /**
     * Reads a maze from a string and generates the maze matrix.
     *
     * @param string the file path of the input file
     * @param xPad the number of chars per column
     * @param yPad the number of chars per row
     *
     * @return returns a new Maze instance with data from the new file
     */
    static public Maze fromString(String string, int xPad, int yPad) {
        String[] lines = string.split("\n");

        String[] dimensions = lines[0].trim().split("\s");

        int height = Integer.parseInt(dimensions[0]);
        int width = Integer.parseInt(dimensions[1]);

        int[][] maze = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int row = (j * yPad) + 1; // +1 for the first line
                int col = (i * xPad);
                maze[j][i] |= (lines[row].charAt(col + 1) == ' ' ? Maze.NORTH : 0)
                               | (lines[row + yPad].charAt(col + 1) == ' ' ? Maze.SOUTH : 0)
                               | (lines[row + 1].charAt(col + xPad) == ' ' ? Maze.EAST : 0)
                               | (lines[row + 1].charAt(col) == ' ' ? Maze.WEST : 0);
            }
        }

        return new Maze(maze);
    }

    /**
     * Construct a maze from a bitmask matrix from an
     * already generated maze.
     *
     * @param maze the bitmask matrix representation
     *             of the maze
     */
    public Maze(int[][] maze) {
        this.width = maze.length;
        this.height = maze[0].length;
        this.maze = maze;
    }

    /**
     * Constructs a rectangular maze.
     *
     * @param width the width of the maze
     * @param height the height of the maze
     */
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        maze = new int[width][height];
        generateMaze(0, 0);
    }

    /**
     * Provides a visual representation of the maze as
     * a string.
     *
     * @return a string representing the maze
     */
    public String displayMaze() {
        return displayMaze((row, col) -> " ");
    }

    /**
     * Generates a visual representation of the maze
     * as a string using the callback to determine
     * which characters to place in the halls of the
     * maze.
     *
     * @param getter a callback that defines which characters
     *               to place in each coordinate of the maze
     *
     * @return @return a string representing the maze
     */
    public String displayMaze(MazeGetter getter) {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);

        for (int i = 0; i < width; i++) {
            // draw the north edge
            for (int j = 0; j < height; j++) {
                out.print((maze[i][j] & NORTH) == 0 ? "+---" : "+   ");
            }
            out.println("+");
            // draw the west edge
            for (int j = 0; j < height; j++) {
                out.print((maze[i][j] & WEST) == 0 ? "| " + getter.get(i, j) + " " : "  " + getter.get(i, j) + " ");
            }
            out.println("|");
        }

        // draw the bottom line
        for (int j = 0; j < height; j++) {
            out.print((maze[width - 1][j] & SOUTH) == 0 ? "+---" : "+   ");
        }

        out.println("+");

        return writer.toString();
    }

    /**
     * Testing utility to export the bitmask matrix, so it can be
     * loaded back in to test issues on the same matrix.
     *
     * @return a string that can be copied into an <code>int[][]</code> variable
     * and passed to the <code>Maze(int[][])</code> constructor.
     */
    public String exportTextCase() {
        return String.format("{{%s}}", Arrays.stream(maze).map(row -> Arrays.stream(row)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "))).collect(Collectors.joining("}, {")));
    }

    /**
     * Recursive perfect maze generator, using a modified DFS
     * (cx,cy) coordinates of current cell, and (nx, ny) coordinates of neighbor cell
     *
     * @param cx current cell x coordinate
     * @param cy current cell y coordinate
     */
    private void generateMaze(int cx, int cy) {
        Direction[] directions = Direction.values();
        Collections.shuffle(Arrays.asList(directions));
        for (Direction direction : directions) {
            //find neighbor cell
            int nx = cx + direction.dx;
            int ny = cy + direction.dy;
            //if neighbor exists and not visited
            if (between(nx, width) && between(ny, height) && (maze[nx][ny] == 0)) {
                //remove walls
                //update current cell using or (|) bit operations
                //example if a cell has north (1) and south (2) neighbor openings, maze holds 3
                //example if a cell has east (4) and west (8) neighbor openings, maze holds 12

                maze[cx][cy] |= direction.bit;

                //update neighbor cell
                maze[nx][ny] |= direction.opposite.bit;
                //recursive call to neighbor cell
                generateMaze(nx, ny);
//                maze[nx][ny] ^= direction.opposite.bit;
            }
        }
    }

    /**
     * Make sure the index is within the proper bounds.
     *
     * @param v index to test
     * @param upper upper bound
     *
     * @return weather the index falls between the bounds
     */
    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    /**
     * Enum type for all directions
     */
    private enum Direction {
        //direction(bit, column move, row move)
        //bit 1 is North, 2 is South, 4 is East and 8 is West
        //example North N(1,0,-1).
        N(NORTH, -1, 0), S(SOUTH, 1, 0), E(EAST, 0, 1), W(WEST, 0, -1);
        private final int bit;
        private final int dx;
        private final int dy;
        private Direction opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        Direction(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }

    public int[][] getMaze() {
        return maze;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
