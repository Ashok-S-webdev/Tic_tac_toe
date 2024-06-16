import java.util.Scanner;

public class TicTacToe {
    private static final int EMPTY = 0;
    private static final int PLAYER_X = 1;
    private static final int PLAYER_O = 2;
    private static final int BOARD_SIZE = 3;
    private static final int INFINITY = 1000;

    public static void main(String[] args) {
        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver(board)) {
            printBoard(board);
            System.out.println("Enter row and column (0-2) separated by space:");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if (isValidMove(board, row, col)) {
                board[row][col] = PLAYER_X;
                if (!isGameOver(board)) {
                    int[] bestMove = findBestMove(board);
                    board[bestMove[0]][bestMove[1]] = PLAYER_O;
                }
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
        printBoard(board);
        int winner = getWinner(board);
        if (winner == PLAYER_X) {
            System.out.println("You win!");
        } else if (winner == PLAYER_O) {
            System.out.println("Computer wins!");
        } else {
            System.out.println("It's a draw!");
        }
        scanner.close();
    }

    private static boolean isGameOver(int[][] board) {
        return getWinner(board) != EMPTY || isBoardFull(board);
    }

    private static boolean isBoardFull(int[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int getWinner(int[][] board) {
        // Check rows and columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return board[i][0];
            }
            if (board[0][i] != EMPTY && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return board[0][i];
            }
        }
        // Check diagonals
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2];
        }
        return EMPTY; // No winner
    }

    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                char symbol = cell == EMPTY ? '-' : cell == PLAYER_X ? 'X' : 'O';
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }

    private static boolean isValidMove(int[][] board, int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && board[row][col] == EMPTY;
    }

    private static int[] findBestMove(int[][] board) {
        int bestScore = -INFINITY;
        int[] bestMove = new int[]{-1, -1};

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    board[row][col] = PLAYER_O;
                    int score = minimax(board, 0, false);
                    board[row][col] = EMPTY;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        return bestMove;
    }
    private static int minimax(int[][] board, int depth, boolean isMaximizing) {
        int winner = getWinner(board);
        if (winner != EMPTY) {
            return winner == PLAYER_O ? 1 : -1; // If computer wins return 1, else return -1
        }
        if (isBoardFull(board)) {
            return 0; // Draw
        }

        if (isMaximizing) {
            int bestScore = -INFINITY;
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (board[row][col] == EMPTY) {
                        board[row][col] = PLAYER_O;
                        bestScore = Math.max(bestScore, minimax(board, depth + 1, false));
                        board[row][col] = EMPTY;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = INFINITY;
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (board[row][col] == EMPTY) {
                        board[row][col] = PLAYER_X;
                        bestScore = Math.min(bestScore, minimax(board, depth + 1, true));
                        board[row][col] = EMPTY;
                    }
                }
            }
            return bestScore;
        }
    }
}