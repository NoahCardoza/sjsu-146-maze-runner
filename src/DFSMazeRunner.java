import java.util.Iterator;

/**
 * A Depth first search implementation of the maze runner.
 */
public class DFSMazeRunner extends MazeRunner {
    @Override
    protected String getName() {
        return "DFS";
    }

    /**
     * Constructs a DFS based maze runner.
     *
     * @param maze the maze to solve
     */
    public DFSMazeRunner(Maze maze) {
        super(maze);
    }
    @Override
    public void run() {
        run(entrance);
    }

    /**
     * Recursive DFS solution to solving the maze.
     *
     * @param point a point in the maze to process
     *
     * @return whether the end was found
     */
    private boolean run(Point point) {
        if (point.getState() == Point.VISITED) {
            return false;
        }

        point.setSeenAt(visited++);

        if (point == exit) {
            path.push(point);
            point.setState(Point.CHOSEN);
            return true;
        }

        point.setState(Point.VISITED);

        for (Iterator<Point> it = graph.iterateEdges(point); it.hasNext(); ) {
            Point nextPath = it.next();
            if (run(nextPath)) {
                path.push(point);
                point.setState(Point.CHOSEN);
                return true;
            }
        }

        return false;
    }
}
