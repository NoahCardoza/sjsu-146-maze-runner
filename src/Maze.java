import java.util.Collections;
import java.util.Arrays;
import java.util.stream.Collectors;

/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class Maze {
    public static final int NORTH = 0b1;
    public static final int SOUTH = 0b10;
    public static final int EAST = 0b100;
    public static final int WEST = 0b1000;

    //coordinates row x, column y
    private final int width;
    private final int height;
    //stores the cells
    private final int[][] maze;

    public Maze(int size) {
        this(size, size);
    }

    public Maze(int[][] maze) {
        this.width = maze.length;
        this.height = maze[0].length;
        this.maze = maze;
    }

    //Constructor
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        maze = new int[width][height];
        generateMaze(0, 0);
    }

    //prints the maze with the cells and walls removed
    public void displayMaze() {
        displayMaze((row, col) -> " ");
    }

    public void displayMaze(MazeGetter solution) {
        for (int i = 0; i < width; i++) {
            // draw the north edge
            for (int j = 0; j < height; j++) {
                System.out.print((maze[i][j] & NORTH) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < height; j++) {
                System.out.print((maze[i][j] & WEST) == 0 ? "| " + solution.get(i, j) + " " : "  " + solution.get(i, j) + " ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < height; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }


    //recursive perfect maze generator, using a modified DFS
    //(cx,cy) coordinates of current cell, and (nx,ny) coordinates of neighbor cell
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

    //prints the value of maze array
    public void displayCells() {
        for (int i = 0; i< width; i++) {
            for (int j = 0; j< height; j++)
                System.out.print(" "+ maze[i][j]);
            System.out.println();
        }
    }

    //checks if 0<=v<upper
    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    public int[][] getMaze() {
        return maze;
    }

    // enum type for all directions
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

    public String exportTextCase() {
        return String.format("{{%s}}", Arrays.stream(maze).map(row -> Arrays.stream(row)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "))).collect(Collectors.joining("}, {")));
    }
}
