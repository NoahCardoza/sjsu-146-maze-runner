public class Main {
    public static void main(String[] args) {
        MazeGenerator maze33 = new MazeGenerator(3);

        maze33.displayCells();
        maze33.displayMaze();

        MazeGenerator maze58 = new MazeGenerator(5, 8);

        maze58.displayCells();
        maze58.displayMaze();

        MazeGenerator maze55 = new MazeGenerator(5);

        maze55.displayCells();
        maze55.displayMaze();
    }
}
