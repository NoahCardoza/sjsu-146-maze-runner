import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


class MazeRunnerTest {
    @Test
    public void testMazeRunner() {
        int[][] test = {
                {2, 4, 14, 12, 10},
                {5, 10, 3, 2, 3},
                {6, 9, 5, 9, 3},
                {3, 4, 14, 12, 11},
                {5, 12, 9, 4, 9}
        };

        Maze maze = new Maze(test);

        maze.displayMaze();

        System.out.println(maze.exportTextCase());

        MazeRunner mazeRunner = new DFSMazeRunner(maze);
        mazeRunner.run();

        mazeRunner.report();
    }


    @Test
    public void randomTestMazeRunner() {
        Maze maze = new Maze(5);

        maze.displayMaze();

        System.out.println(maze.exportTextCase());

        MazeRunner mazeRunner = new DFSMazeRunner(maze);
        mazeRunner.run();
        mazeRunner.report();
    }

    @Test
    public void fromFile() throws IOException {
        Path path = Path.of("sample-inputs/maze4.txt");
        Maze maze = Maze.fromPath(path);
        maze.displayMaze();
        MazeRunner mazeRunner = new DFSMazeRunner(maze);
        mazeRunner.run();
        mazeRunner.report();
    }

    @Test
    public void sampleInputs() throws IOException {
        File folder = new File("./sample-inputs");
        for (final File file : folder.listFiles()) {
            if (file.getName().endsWith(".txt")) {
                Maze maze = Maze.fromPath(file.toPath());
                maze.displayMaze();

                MazeRunner mazeRunner = new DFSMazeRunner(maze);
                mazeRunner.run();
                mazeRunner.report();
            }
        }
    }
}