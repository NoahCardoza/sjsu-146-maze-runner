import java.util.LinkedList;
import java.util.Iterator;
import java.util.Stack;

/**
 * A Breadth first search implementation to solve the maze
 */
public class BFSMazeRunner extends MazeRunner{

    private LinkedList<Point> queue;
    /**
     * Constructs a BFS based maze runner
     * @param maze, the maze that will be solved
     */
    public BFSMazeRunner(Maze maze){
        super(maze);
    }

    @Override
    public void run() {
        queue = new LinkedList<>();
        run(entrance);
    }

    public boolean run(Point point){
    point.setState(Point.VISITED);
    point.setDistance(0);
    point.setSeenAt(visited++);
    queue.add(point);
    while(!queue.isEmpty()){
        Point current = queue.remove();
        current.setSeenAt(visited++);
        for (Iterator<Point> it = graph.iterateEdges(current); it.hasNext(); ) {
            Point nextPath = it.next();
            if (nextPath.getState() == Point.UNVISITED){
                nextPath.setState(Point.VISITED);
                nextPath.setParent(current);
                nextPath.setDistance(nextPath.getParent().getDistance() + 1);
                queue.add(nextPath);
            }
        }

    }

    Point current = exit;
    boolean finished = false;
    while (!finished){
        current.setState(Point.CHOSEN);
        path.add(current);
        if (current.equals(entrance))
            finished = true;
        current = path.peek().getParent();

    }


    return true;
    }


}
