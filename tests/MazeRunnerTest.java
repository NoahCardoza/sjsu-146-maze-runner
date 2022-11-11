import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


class MazeRunnerTest {
    @Test
    public void updateFileTests() throws IOException {
        File folder = new File("./sample-inputs");
        for (final File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(".txt")) {
                UnitTest unitTest = new UnitTest(Maze.fromString(Files.readString(file.toPath()), 2, 2));
                unitTest.run();
//                unitTest.getDfs().report();
                unitTest.saveAs(new File("./tests/mazes/" + file.getName()));
            }
        }
    }
    @Test
    public void fileTests() throws IOException {
        File folder = new File("./tests/mazes");
        for (final File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith("maze4.txt")) {
                UnitTest unitTest = new UnitTest(file);

                unitTest.run();

                assertEquals(unitTest.getDfsResult().visited(), unitTest.getDfs().getVisited());
                assertEquals(unitTest.getBfsResult().visited(), unitTest.getBfs().getVisited());

                assertArrayEquals(unitTest.getDfsResult().path().toArray(), unitTest.getDfs().getPath().toArray());
                assertArrayEquals(unitTest.getBfsResult().path().toArray(), unitTest.getBfs().getPath().toArray());
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
        Stack<Point> bfsPoints, dfsPoints;


        for (int i = 0; i < 7; i++) {
            maze = new Maze((int) Math.pow(2, i));
            bfsMazeRunner = new BFSMazeRunner(maze);
            bfsMazeRunner.run();
            dfsMazeRunner = new DFSMazeRunner(maze);
            dfsMazeRunner.run();

            bfsMazeRunner.report();
            dfsMazeRunner.report();

            dfsPoints = dfsMazeRunner.getPath();
            bfsPoints = bfsMazeRunner.getPath();

            assertArrayEquals(dfsPoints.toArray(), bfsPoints.toArray());
        }
    }
}