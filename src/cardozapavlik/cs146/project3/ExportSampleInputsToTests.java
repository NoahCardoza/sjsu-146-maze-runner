package cardozapavlik.cs146.project3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class ExportSampleInputsToTests {
    /**
     * Coverts sample inputs provided to tests.
     *
     * @throws IOException if the files cannot be found
     */
    public static void main(String[] args) throws IOException {
        // ensures hidden files like .DS_Store's aren't read
        File folder = new File("./sample-inputs");
        for (final File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(".txt")) {
                UnitTest unitTest = new UnitTest(Maze.fromString(Files.readString(file.toPath()), 2, 2));
                unitTest.run();
                unitTest.saveAs(new File("./tests/mazes/" + file.getName()));
            }
        }
    }
}
