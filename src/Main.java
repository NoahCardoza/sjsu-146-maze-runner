public class Main {
    public static void main(String[] args) {
        Maze maze55 = new Maze(10);

        System.out.println(maze55.exportTextCase());

        MazeRunner mazeRunner = new DFSMazeRunner(maze55);
        mazeRunner.run();

        System.out.println(mazeRunner.report());

        MazeRunner mr = new BFSMazeRunner(maze55);
        mr.run();
        System.out.println(mr.report());
    }
}
