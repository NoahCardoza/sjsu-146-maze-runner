import java.util.ArrayList;
import java.util.Stack;

/**
 * An abstract class containing the general methods to parse
 * and report the solution of solved mazes.
 */
public abstract class MazeRunner {
    private final Maze maze;
    protected final AdjacencyList<Point> graph;
    private final ArrayList<ArrayList<Point>> matrix;

    protected Point entrance;
    protected Point exit;

    protected final Stack<Point> path;
    protected int visited;

    /**
     * Construct a new MazeRunner.
     *
     * @param maze the maze to run
     */
    public MazeRunner(Maze maze){
        graph = new AdjacencyList<>();
        matrix = new ArrayList<>();
        path = new Stack<>();
        visited = 0;

        this.maze = maze;
        this.mazeToGraph();
    }

    /**
     * Converts the maze matrix into an adjacency list and matrix.
     */
    private void mazeToGraph(){
        int [][] mazeArray = maze.getMaze();
        for (int row = 0; row < mazeArray.length; row++) {
            ArrayList<Point> points = new ArrayList<>();
            for (int col = 0; col < mazeArray[0].length; col++) {
                points.add(new Point(row, col));
            }
            this.matrix.add(points);
        }


        for (int row = 0; row < mazeArray.length; row++){
            for (int col = 0; col < mazeArray[0].length; col++){
                int node = mazeArray[row][col];

                Point current = matrix.get(row).get(col);
                Point neighbor;

                if ((node & Maze.NORTH) == Maze.NORTH && row - 1 >= 0){
                    neighbor = matrix.get(row - 1).get(col);
                    graph.addEdge(current, neighbor);
                }

                if ((node & Maze.SOUTH) == Maze.SOUTH && row + 1 < maze.getHeight()){
                    neighbor = matrix.get(row + 1).get(col);
                    graph.addEdge(current, neighbor);
                }

                if ((node & Maze.EAST) == Maze.EAST){
                    neighbor = matrix.get(row).get(col + 1);
                    graph.addEdge(current, neighbor);
                }

                if ((node & Maze.WEST) == Maze.WEST){
                    neighbor = matrix.get(row).get(col - 1);
                    graph.addEdge(current, neighbor);
                }
            }
        }

        entrance = matrix.get(0).get(0);
        exit = matrix.get(mazeArray.length - 1).get(mazeArray[0].length - 1);
    }

    /**
     * Shorthand for accessing a point in the matrix.
     *
     * @param row the row to access
     * @param col the column to access
     *
     * @return the point at the specified indexes from the inner matrix
     */
    private Point get(int row, int col) {
        return matrix.get(row).get(col);
    }

    /**
     * Generates a report on the maze solution.
     *
     * TODO: return a string rather then print to STDOUT
     */
    public void report() {
        maze.displayMaze((row, col) -> {
            String lastSeen = Integer.toString(get(row, col).getSeenAt());
            return lastSeen.substring(lastSeen.length() - 1);
        });

        maze.displayMaze((row, col) -> get(row, col).getState() == Point.CHOSEN ? "#" : " ");

        System.out.print("Path: ");
        int pathLength = 0;
        while (!path.isEmpty()) {
            Point point = path.pop();
            System.out.printf("(%s, %s) ", point.getCol(), point.getRow());
            pathLength++;
        }
        System.out.println();
        System.out.printf("Length of path: %s%n", pathLength);
        System.out.printf("Visited cells: %s%n", visited);
    }

    /**
     * Solves the maze.
     */
    public abstract void run();
}
