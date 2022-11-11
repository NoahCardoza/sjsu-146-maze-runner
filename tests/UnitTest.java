import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Stack;

public class UnitTest {
    private final Maze maze;
    private final BFSMazeRunner bfs;
    private final DFSMazeRunner dfs;
    private final Result bfsResult;
    private final Result dfsResult;

    record Result(int visited, Stack<Point> path) { }

    public UnitTest(File file) throws IOException {
        int yPad = 2;
        String testContents = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        maze = Maze.fromString(testContents, 4, yPad);

        String[] lines = testContents.split("\n");
        lines = Arrays.copyOfRange(lines, yPad * maze.getHeight() + 2, lines.length);

        int pathLength = Integer.parseInt(lines[0]);

        Stack<Point> path = new Stack<>();
        while (--pathLength >= 0) {
            path.push(new Point (Arrays.stream(lines[1 + pathLength].split(",")).mapToInt(Integer::parseInt).toArray()));
        }

        dfsResult = new Result(Integer.parseInt(lines[lines.length - 2]), path);
        bfsResult = new Result(Integer.parseInt(lines[lines.length - 1]), path);

        bfs = new BFSMazeRunner(maze);
        dfs = new DFSMazeRunner(maze);
    }

    public UnitTest(Maze maze) {
        this.maze = maze;
        bfs = new BFSMazeRunner(maze);
        dfs = new DFSMazeRunner(maze);
        dfsResult = null;
        bfsResult = null;
    }

    public void run() {
        bfs.run();
        dfs.run();
    }

    public void saveAs(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        PrintStream out = new PrintStream(fileOutputStream);

        out.printf("%s %s%n", maze.getHeight(), maze.getWidth());
        out.print(maze.displayMaze());

        Stack<Point> path = dfs.getPath();
        out.println(path.size());
        while (!path.isEmpty()) {
            Point point = path.pop();
            out.printf("%s,%s%n", point.getCol(), point.getRow());
        }

        out.println(dfs.getVisited());
        out.println(bfs.getVisited());

        fileOutputStream.close();
        out.close();
    }

    public Maze getMaze() {
        return maze;
    }

    public DFSMazeRunner getDfs() {
        return dfs;
    }

    public BFSMazeRunner getBfs() {
        return bfs;
    }

    public Result getBfsResult() {
        return bfsResult;
    }

    public Result getDfsResult() {
        return dfsResult;
    }
}
