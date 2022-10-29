import java.util.ArrayList;
public class MazeToGraph {

    private AdjacencyList<Point> vertexes;
    private ArrayList<ArrayList<Point>> maze;
    public MazeToGraph(){
        vertexes = new AdjacencyList<Point>();
        maze = new ArrayList<ArrayList<Point>>();
    }

    /**
     * Converts the maze matrix into an adjacency list and also an array list of array lists
     *
     */
    public void mazeToGraph(int[][] maze){
        for (int row = 0; row < maze.length; row++){
            ArrayList<Point> points = new ArrayList<>();
            for (int col = 0; col <maze[0].length; col++){

                int node = maze[row][col];
                Point current = new Point(row, col);
                points.add(current);

                if ((node & MazeGenerator.NORTH) == MazeGenerator.NORTH){
                    Point neighbor = new Point(row - 1, col);
                    if(!vertexes.searchEdge(neighbor, current)){
                        vertexes.addEdge(current, neighbor);
                    }
                }

                if ((node & MazeGenerator.SOUTH) == MazeGenerator.SOUTH){
                    Point neighbor = new Point(row + 1, col);
                    if(!vertexes.searchEdge(neighbor, current)){
                        vertexes.addEdge(current, neighbor);
                    }
                }

                if ((node & MazeGenerator.EAST) == MazeGenerator.EAST){
                    Point neighbor = new Point(row, col+1);
                    if(!vertexes.searchEdge(neighbor, current)){
                        vertexes.addEdge(current, neighbor);
                    }
                }

                if ((node & MazeGenerator.WEST) == MazeGenerator.WEST){
                    Point neighbor = new Point(row, col-1);
                    if(!vertexes.searchEdge(neighbor, current)){
                        vertexes.addEdge(current, neighbor);
                    }
                }

            }
            this.maze.add(points);
        }
    }
}
