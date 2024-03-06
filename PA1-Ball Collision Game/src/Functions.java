public class Functions {
    public static String toTable (String[][] board) {
        String output = "";
        for (String[] line : board) {
            output += "\n";
            for (String square : line) {
                output += square + " ";
            }
            output = output.trim();
        }
        return output;
    }
    public static String createOutput (String board1, String board2, String movements, int score) {
        String output;
        output = "Game board:\n" + board1 + "\n\nYour movement is:\n" + movements + "\n\nYour output is:\n"
                + board2 + String.format("\n\nGame Over!\nScore: %d", score);
        return output;
    }
}
