import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Stack;

/**
 * An abstract class containing the general methods to parse,
 * solve, and report the solution of a solved maze.
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
     * Construct a new MazeRunner from a Maze.
     *
     * @param maze the maze to solve and report on
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
     * Converts the maze bitmask matrix into an adjacency list and matrix
     * of Point class instances.
     */
    private void mazeToGraph(){
        int [][] mazeArray = maze.getMaze();
        for (int row = 0; row < maze.getHeight(); row++) {
            ArrayList<Point> points = new ArrayList<>();
            for (int col = 0; col < maze.getWidth(); col++) {
                points.add(new Point(row, col));
            }
            this.matrix.add(points);
        }

        for (int row = 0; row < maze.getHeight(); row++){
            for (int col = 0; col < maze.getWidth(); col++){
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
     * @return a report of the solution as a string
     */
    public String report() {
        if (path.isEmpty()) {
            throw new RuntimeException("The solve path has already been consumed or the .run() hasn't been called yet.");
        }

        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);

        out.printf("Report: %s (%s, %s)%n%n", getName(), maze.getWidth(), maze.getHeight());

        out.print(maze.displayMaze((row, col) -> {
            if (row == 0 && col == 0) {
                return "0";
            }

            int seenAt = get(row, col).getSeenAt();
            String lastSeen = Integer.toString(seenAt);
            return seenAt == 0 ? " " : lastSeen.substring(lastSeen.length() - 1);
        }));

        out.print(maze.displayMaze((row, col) -> get(row, col).getState() == Point.CHOSEN ? "#" : " "));

        out.print("Path: ");
        int pathLength = 0;
        while (!path.isEmpty()) {
            Point point = path.pop();
            out.printf("(%s, %s) ", point.getCol(), point.getRow());
            pathLength++;
        }
        out.println();
        out.printf("Length of path: %s%n", pathLength);
        out.printf("Visited cells: %s%n", visited);

        return writer.toString();
    }

    protected abstract String getName();

    /**
     * Solves the maze.
     */
    public abstract void run();

    /**
     * Accesses the shortest path stack.
     *
     * @return a stack representing the shortest path
     * from the entrance to the exit of the maze
     */
    public Stack<Point> getPath() {
        return path;
    }

    /**
     * Accesses the count of visited nodes.
     *
     * @return the number of nodes visited in the maze
     * while searching for the exit of the maze
     */
    public int getVisited() {
        return visited;
    }
}
