package cardozapavlik.cs146.project3;

import java.util.LinkedList;
import java.util.Iterator;

/**
 * A Breadth first search implementation to solve the maze
 */
public class BFSMazeRunner extends MazeRunner{
    private final LinkedList<Point> queue;

    @Override
    protected String getName() {
        return "BFS";
    }

    /**
     * Constructs a BFS based maze runner
     *
     * @param maze the maze that will be solved
     */
    public BFSMazeRunner(Maze maze){
        super(maze);
        queue = new LinkedList<>();
    }

    /**
     * Iterative BFS solution to solve the maze.
     */
    @Override
    public void run() {
        Point current = null;

        entrance.setState(Point.VISITED);
        entrance.setDistance(0);
        queue.add(entrance);

        while(!queue.isEmpty()){
            current = queue.remove();
            current.setSeenAt(visited++);
            if (current == exit) {
                queue.clear();
            } else {
                for (Iterator<Point> it = graph.iterateEdges(current); it.hasNext(); ) {
                    Point nextPath = it.next();
                    if (nextPath.getState() == Point.UNVISITED) {
                        nextPath.setState(Point.VISITED);
                        nextPath.setParent(current);
                        nextPath.setDistance(nextPath.getParent().getDistance() + 1);
                        queue.add(nextPath);
                    }
                }
            }
        }

        boolean finished = false;
        while (!finished){
            current.setState(Point.CHOSEN);
            path.add(current);
            if (current.equals(entrance))
                finished = true;
            current = path.peek().getParent();

        }
    }
}
