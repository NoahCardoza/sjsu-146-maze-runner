import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


class MazeRunnerTest {
    /**
     * Loads the test cases from the files and tests the algorithms.
     *
     * @throws IOException if the test cannot be loaded from the filesystem
     */
    @Test
    public void fileTests() throws IOException {
        File folder = new File("./tests/mazes");
        Object[] bfsPoints, dfsPoints;

        for (final File file : Objects.requireNonNull(folder.listFiles())) {
            // ensures hidden files like .DS_Store's aren't read
            if (file.getName().endsWith(".txt")) {
                // load the maze and expected results from the file
                UnitTest unitTest = new UnitTest(file);

                // run the dfs and bfs algorithms
                unitTest.run();

                // verify their visited count matched the expected value
                assertEquals(unitTest.getDfsResult().visited(), unitTest.getDfs().getVisited());
                assertEquals(unitTest.getBfsResult().visited(), unitTest.getBfs().getVisited());

                // make a copy of the array before reporting
                // because the report will consume the path
                // stack
                dfsPoints = unitTest.getDfs().getPath().toArray();
                bfsPoints = unitTest.getBfs().getPath().toArray();

                // ensure the paths match the expected paths
                assertArrayEquals(unitTest.getDfsResult().path().toArray(), dfsPoints);
                assertArrayEquals(unitTest.getBfsResult().path().toArray(), bfsPoints);

                // display a report of each algorithm
                System.out.println(unitTest.getDfs().report());
                System.out.println(unitTest.getBfs().report());
            }
        }
    }

    /**
     * Tests the DFS implementation against the BSF implementation.
     */
    @Test
    public void crossReferenceTests(){
        Maze maze;
        MazeRunner bfsMazeRunner, dfsMazeRunner;
        Object[] bfsPoints, dfsPoints;

        for (int i = 0; i < 7; i++) {
            // create a random maze
            maze = new Maze((int) Math.pow(2, i));

            // load the maze into the solvers
            bfsMazeRunner = new BFSMazeRunner(maze);
            dfsMazeRunner = new DFSMazeRunner(maze);

            // run the soling algorithms
            bfsMazeRunner.run();
            dfsMazeRunner.run();

            // make a copy of the array before reporting
            // because the report will consume the path
            // stack
            dfsPoints = dfsMazeRunner.getPath().toArray();
            bfsPoints = bfsMazeRunner.getPath().toArray();

            // verify the paths are the same for both algorithms
            assertArrayEquals(dfsPoints, bfsPoints);

            // print out the reports
            System.out.println(bfsMazeRunner.report());
            System.out.println(dfsMazeRunner.report());
        }
    }
}