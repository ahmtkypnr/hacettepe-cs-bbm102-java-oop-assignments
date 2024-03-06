public class Main {
    public static void main(String[] args) {
        FileOutput.writeToFile("output.txt","",false,false);

        String[] input = FileInput.readFile(args[0], true, true);
        String[] moveList = FileInput.readFile(args[1], true, true);

        Board board = new Board(input, moveList);

        String board1 = Functions.toTable(board.getBoard());

        board.findCoorOfBall();
        board.play();

        String movements = board.getMovements().trim();
        String board2 = Functions.toTable(board.getBoard());
        int score = board.getScore();

        String output = Functions.createOutput(board1, board2, movements, score);

        FileOutput.writeToFile("output.txt", output,true,true);
    }
}