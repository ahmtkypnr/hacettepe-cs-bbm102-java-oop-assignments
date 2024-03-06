public class Board {
    private int score, x, y, xLength, yLength;
    private boolean run;
    private String[][] board;
    private String[] moveList;
    private String movements;

    public int getScore() {
        return score;
    }
    public String[][] getBoard() {
        return board;
    }
    public String getMovements() {
        return movements;
    }

    public Board (String[] input, String[] moveList) {
        this.score = 0;
        this.x = 0;
        this.y = 0;
        this.board = createBoard(input);
        this.xLength = this.board.length;
        this.yLength = this.board[0].length;
        this.moveList = moveList[0].split(" ");
        this.run = true;
        this.movements = "";
    }
    public String[][] createBoard (String[] input) {
        int inputXLength = input.length;
        int inputYLength = input[0].length();
        String[][] board = new String[inputXLength][inputYLength];
        for (int i = 0; i < inputXLength; i++) {
            board[i] = input[i].split(" ");
        }
        return board;
    }
    public void findCoorOfBall () {
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                if (this.board[i][j].equals("*")) {
                    this.x = i;
                    this.y = j;
                }
            }
        }
    }
    public void moveFunc (int x1, int x2, int y1, int y2, String reverse) {
        String ball = "*";
        String cross = "X";
        switch (this.board[x2][y2]) {
            case "R" :
                this.board[x1][y1] = cross;
                this.board[x2][y2] = ball;
                this.score += 10;
                this.x = x2;
                this.y = y2;
                break;
            case "Y":
                this.board[x1][y1] = cross;
                this.board[x2][y2] = ball;
                this.score += 5;
                this.x = x2;
                this.y = y2;
                break;
            case "B":
                this.board[x1][y1] = cross;
                this.board[x2][y2] = ball;
                this.score -= 5;
                this.x = x2;
                this.y = y2;
                break;
            case "H":
                this.board[x1][y1] = " ";
                this.run = false;
                break;
            case "W":
                playTurn(reverse);
                break;
            default:
                this.board[x1][y1] = this.board[x2][y2];
                this.board[x2][y2] = ball;
                this.x = x2;
                this.y = y2;
                break;
        }
    }
    public void playTurn (String move) {
        switch (move) {
            case "U":
                if (this.x == 0) {
                    moveFunc (this.x, xLength - 1, this.y, this.y, "D");
                }
                else {
                    moveFunc (this.x, this.x - 1, this.y, this.y, "D");
                }
                break;
            case "D":
                if (this.x == xLength - 1) {
                    moveFunc (this.x, 0, this.y, this.y, "U");
                }
                else {
                    moveFunc (this.x, this.x + 1, this.y, this.y, "U");
                }
                break;
            case "L":
                if (this.y == 0) {
                    moveFunc (this.x, this.x, this.y, yLength - 1, "R");
                }
                else {
                    moveFunc (this.x, this.x, this.y, this.y - 1, "R");
                }
                break;
            case "R":
                if (this.y == yLength - 1) {
                    moveFunc (this.x, this.x, this.y, 0, "L");
                }
                else {
                    moveFunc (this.x, this.x, this.y, this.y + 1, "L");
                }
                break;
        }
    }
    public void play () {
        for (String move : moveList) {
            if (this.run) {
                playTurn(move);
                this.movements += move + " ";
            }
            else {
                return;
            }
        }
    }
}