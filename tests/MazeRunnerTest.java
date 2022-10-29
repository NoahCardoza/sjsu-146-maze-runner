import org.junit.jupiter.api.Test;

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
}