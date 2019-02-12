import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Computer {

    public static void computerTurn(String[][] gameBoard) {
        String[][] copiedBoard = copyBoard(gameBoard); // board we will work with

        // Find Optimal Move
        // Get Possible Moves
        // Pick Best Move
        miniMaxAI(gameBoard);
        gameLogic.printBoard(gameBoard);
        // int getScore = value(copiedBoard);

        // System.out.println("Computer Turn");
        // System.out.println("Current value of the board" + getScore);
    }

    public static String[][] copyBoard(String[][] gameBoard) {
        int geti = gameBoard.length;
        int getj = gameBoard[0].length;

        String[][] returnCopy = new String[geti][getj];
        for (int i = 0; i < gameBoard.length; i++)
            for (int j = 0; j < gameBoard[i].length; j++)
                returnCopy[i][j] = gameBoard[i][j];

        return returnCopy;
    }
    // The double if statement only works in a 3x3 if we are gonna do the normal
    // connect 4 we gotta do a back and forth like the github

    public static void threeDumbAI(String[][] gameBoard) {
        ArrayList < Integer > validMovesObj = getValidMoves(gameBoard); // Get Valid Moves
        gameLogic.dropPiece(gameBoard, validMovesObj.get(0), "O");

    }

    public static void miniMaxAI(String[][] gameBoard) {
        ArrayList < Integer > validMovesObj = getValidMoves(gameBoard); // Get Valid Moves
        // System.out.println("MiniMaxAI");
        doMiniMAX(gameBoard, 5, true);

    }

    public static int doMiniMAX(String[][] gameBoard, int depth, boolean isMaximizing) {
        if (checkTerminalStates.checkGameOver(gameBoard, "X")) {
            return value(gameBoard);
        }
        if (checkTerminalStates.checkGameOver(gameBoard, "O")) {
            return value(gameBoard);
        }

        // DO MAX
        
        String[][] copiedBoard = copyBoard(gameBoard);
        if (isMaximizing) {
            ArrayList < Integer > validMovesObj = getValidMoves(copiedBoard); // Get Valid Moves
            int max = Integer.MIN_VALUE;
            int currI = 0;
            for (int i = 0; i < validMovesObj.size(); i++) {
                String[][] SubcopiedBoard = copyBoard(gameBoard);
                if (gameLogic.canDropPiece(SubcopiedBoard, i)) {
                    gameLogic.dropPiece(SubcopiedBoard, i, "X");
                }
                max = Math.max(max, doMiniMAX(SubcopiedBoard, depth - 1, false));

            }
            System.out.println(" Max: " + max);
            return max;

            // DO MINI

        } else {

            ArrayList < Integer > validMovesObj = getValidMoves(copiedBoard); // Get Valid Moves
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < validMovesObj.size(); i++) {
                String[][] SubcopiedBoard = copyBoard(gameBoard);

                if (gameLogic.canDropPiece(SubcopiedBoard, i)) {
                    gameLogic.dropPiece(SubcopiedBoard, i, "X");
                }
                min = Math.min(min, doMiniMAX(SubcopiedBoard, depth - 1, true));

            }
            //System.out.println(min);
            System.out.println(" Min: " + min);


            return min;

        }

    }

    public static int value(String[][] gameBoard) {
        int score = 0;
        // Get Score from All Directions
        score += getHorizontalHeuristic(gameBoard) + getVerticalHeuristic(gameBoard) +
            getDiagonalUpperHeuristic(gameBoard) + getDiagonalLowerHeuristic(gameBoard);

        return score;

    }

    public static int getHorizontalHeuristic(String[][] gameBoard) {
        int score = 0;

        /* Check Horizontals */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <= 0; j++) {

                // MAX
                if (gameBoard[i][j] == "X" && gameBoard[i][j + 1] == "X" && gameBoard[i][j + 2] == "X") {
                    score += 1000;
                    // System.out.println("Score + 100 Horizontal");

                }

                if (gameBoard[i][j] == "X" && gameBoard[i][j + 1] == "X" && gameBoard[i][j + 2] == "?") {
                    score += 100;
                    // System.out.println("Score + 100 Horizontal");

                } else if (gameBoard[i][j] == "?" && gameBoard[i][j + 1] == "X" && gameBoard[i][j + 2] == "X") {
                    score += 100;
                    // System.out.println("Score + 100 Horizontal");

                }

                if (gameBoard[i][j] == "X" && gameBoard[i][j + 1] == "?" && gameBoard[i][j + 2] == "?") {
                    score += 5;
                    // System.out.println("Score + 5 Horizontal");

                } else if (gameBoard[i][j] == "?" && gameBoard[i][j + 1] == "?" && gameBoard[i][j + 2] == "X") {
                    score += 5;
                    // System.out.println("Score + 5 Horizontal");

                }
                // MIN

                if (gameBoard[i][j] == "O" && gameBoard[i][j + 1] == "O" && gameBoard[i][j + 2] == "O") {
                    score += -1000;
                    // System.out.println("Score + 100 Horizontal");

                }

                if (gameBoard[i][j] == "O" && gameBoard[i][j + 1] == "O" && gameBoard[i][j + 2] == "?") {
                    score += -100;
                    // System.out.println("Score + 100 Horizontal");

                } else if (gameBoard[i][j] == "?" && gameBoard[i][j + 1] == "O" && gameBoard[i][j + 2] == "O") {
                    score += -100;
                    // System.out.println("Score + 100 Horizontal");

                }

                if (gameBoard[i][j] == "O" && gameBoard[i][j + 1] == "?" && gameBoard[i][j + 2] == "?") {
                    score += -5;
                    // System.out.println("Score + 5 Horizontal");

                } else if (gameBoard[i][j] == "?" && gameBoard[i][j + 1] == "?" && gameBoard[i][j + 2] == "O") {
                    score += -5;
                    // System.out.println("Score + 5 Horizontal");

                }

                // If Two Across ?? Score +100 Game Ending

            }
        }
        return score;

    }

    public static int getVerticalHeuristic(String[][] gameBoard) {
        int score = 0;
        /* Check Verticals */
        // System.out.println("Checking Vertical");
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i <= 0; i++) {

                // MAX

                if (gameBoard[i][j] == "X" && gameBoard[i + 1][j] == "X" && gameBoard[i][i + 2] == "X") {
                    score += 1000;
                    // System.out.println("Score +100 Vertical");

                }
                if (gameBoard[i][j] == "X" && gameBoard[i + 1][j] == "X" && gameBoard[i][i + 2] == "?") {
                    score += 100;
                    // System.out.println("Score +100 Vertical");

                } else if (gameBoard[i][j] == "?" && gameBoard[i + 1][j] == "X" && gameBoard[i][i + 2] == "X") {
                    score += 100;
                    // System.out.println("Score +100 Vertical");

                }
                if (gameBoard[i][j] == "X" && gameBoard[i + 1][j] == "?" && gameBoard[i + 2][j] == "?") {
                    score += 5;
                    // System.out.println("Score + 5 Vertical");
                } else if (gameBoard[i][j] == "?" && gameBoard[i + 1][j] == "?" && gameBoard[i + 2][j] == "X") {
                    score += 5;
                    // System.out.println("Score + 5 Vertical");
                }

                // MIn

                if (gameBoard[i][j] == "O" && gameBoard[i + 1][j] == "O" && gameBoard[i][i + 2] == "O") {
                    score += -1000;
                    // System.out.println("Score +100 Vertical");

                }
                if (gameBoard[i][j] == "O" && gameBoard[i + 1][j] == "O" && gameBoard[i][i + 2] == "?") {
                    score += -100;
                    // System.out.println("Score +100 Vertical");

                } else if (gameBoard[i][j] == "?" && gameBoard[i + 1][j] == "O" && gameBoard[i][i + 2] == "O") {
                    score += -100;
                    // System.out.println("Score +100 Vertical");

                }
                if (gameBoard[i][j] == "O" && gameBoard[i + 1][j] == "?" && gameBoard[i + 2][j] == "?") {
                    score += -5;
                    // System.out.println("Score + 5 Vertical");
                } else if (gameBoard[i][j] == "?" && gameBoard[i + 1][j] == "?" && gameBoard[i + 2][j] == "O") {
                    score += -5;
                    // System.out.println("Score + 5 Vertical");
                }

            }
        }
        return score;
    }

    public static int getDiagonalUpperHeuristic(String[][] gameBoard) {
        int score = 0;
        // System.out.println("Checking diagnols");
        for (int i = 2; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length - 2; j++) {
                // System.out.println("Got" + i + "" + j + "Checking" + (i - 1) + (j + 1) +
                // "and" + (i - 2) + (j + 2));

                // MAX

                if (gameBoard[i][j] == "X" && gameBoard[i - 1][j + 1] == "X" && gameBoard[i - 2][j + 2] == "X") {
                    score += 1000;
                    // System.out.println("Score + 100 DiagnolUpper");
                }
                if (gameBoard[i][j] == "X" && gameBoard[i - 1][j + 1] == "X" && gameBoard[i - 2][j + 2] == "?") {
                    score += 100;
                    // System.out.println("Score + 100 DiagnolUpper");

                } else if (gameBoard[i][j] == "?" && gameBoard[i - 1][j + 1] == "X" && gameBoard[i - 2][j + 2] == "X") {
                    score += 100;
                    // System.out.println("Score + 100 DiagnolUpper");

                }
                if (gameBoard[i][j] == "X" && gameBoard[i - 1][j + 1] == "?" && gameBoard[i - 1][j + 2] == "?") {
                    score += 5;
                    // System.out.println("Score + 5 DiagnaolUpper");

                } else if (gameBoard[i][j] == "?" && gameBoard[i - 1][j + 1] == "?" && gameBoard[i - 1][j + 2] == "X") {
                    score += 5;
                    // System.out.println("Score + 5 DiagnaolUpper");

                }

                // MIN

                if (gameBoard[i][j] == "O" && gameBoard[i - 1][j + 1] == "O" && gameBoard[i - 2][j + 2] == "O") {
                    score += -1000;
                    // System.out.println("Score + 100 DiagnolUpper");

                }
                if (gameBoard[i][j] == "O" && gameBoard[i - 1][j + 1] == "O" && gameBoard[i - 2][j + 2] == "?") {
                    score += -100;
                    // System.out.println("Score + 100 DiagnolUpper");

                } else if (gameBoard[i][j] == "?" && gameBoard[i - 1][j + 1] == "O" && gameBoard[i - 2][j + 2] == "O") {
                    score += -100;
                    // System.out.println("Score + 100 DiagnolUpper");

                }

                if (gameBoard[i][j] == "O" && gameBoard[i - 1][j + 1] == "?" && gameBoard[i - 1][j + 2] == "?") {
                    score += -5;
                    // System.out.println("Score + 5 DiagnaolUpper");

                } else if (gameBoard[i][j] == "?" && gameBoard[i - 1][j + 1] == "?" && gameBoard[i - 1][j + 2] == "O") {
                    score += -5;
                    // System.out.println("Score + 5 DiagnaolUpper");

                }

            }
        }
        return score;

    }

    public static int getDiagonalLowerHeuristic(String[][] gameBoard) {
        int score = 0;
        // Check Diagnols Going down
        // System.out.println("Checking second diganols");
        for (int i = gameBoard.length - 1; i > 1; i--) {
            for (int j = gameBoard.length - 1; j > 1; j--) {
                // System.out.println("Got" + i + "" + j + "Checking" + (i - 1) + (j - 1) +
                // "and" + (i - 2) + (j - 2));

                // MAX

                if (gameBoard[i][j] == "X" && gameBoard[i - 1][j - 1] == "X" && gameBoard[i - 2][j - 2] == "X") {
                    score += 1000;
                    // System.out.println("Score + 100 Diagnaol Lower");

                }

                if (gameBoard[i][j] == "X" && gameBoard[i - 1][j - 1] == "X" && gameBoard[i - 2][j - 2] == "?") {
                    score += 100;
                    // System.out.println("Score + 100 Diagnaol Lower");

                } else if (gameBoard[i][j] == "?" && gameBoard[i - 1][j - 1] == "X" && gameBoard[i - 2][j - 2] == "X") {
                    score += 100;
                    // System.out.println("Score + 100 Diagnaol Lower");

                }

                if (gameBoard[i][j] == "X" && gameBoard[i - 1][j - 1] == "?" && gameBoard[i - 2][j - 2] == "?") {
                    score += 5;
                    // System.out.println("Score + 100 Diagnaol Lower");

                } else if (gameBoard[i][j] == "?" && gameBoard[i - 1][j - 1] == "?" && gameBoard[i - 2][j - 2] == "X") {
                    score += 5;
                    // System.out.println("Score + 100 Diagnaol Lower");
                    //
                }

                // MIN
                if (gameBoard[i][j] == "O" && gameBoard[i - 1][j - 1] == "O" && gameBoard[i - 2][j - 2] == "O") {
                    score += -1000;
                    // System.out.println("Score + 100 Diagnaol Lower");

                }
                if (gameBoard[i][j] == "O" && gameBoard[i - 1][j - 1] == "X" && gameBoard[i - 2][j - 2] == "?") {
                    score += -100;
                    // System.out.println("Score + 100 Diagnaol Lower");

                } else if (gameBoard[i][j] == "?" && gameBoard[i - 1][j - 1] == "O" && gameBoard[i - 2][j - 2] == "O") {
                    score += -100;
                    // System.out.println("Score + 100 Diagnaol Lower");

                }

                if (gameBoard[i][j] == "O" && gameBoard[i - 1][j - 1] == "?" && gameBoard[i - 2][j - 2] == "?") {
                    score += -5;
                    // System.out.println("Score + 100 Diagnaol Lower");

                } else if (gameBoard[i][j] == "?" && gameBoard[i - 1][j - 1] == "?" && gameBoard[i - 2][j - 2] == "O") {
                    score += -5;
                    // System.out.println("Score + 100 Diagnaol Lower");
                    //
                }

            }

        }
        return score;
    }

    public static ArrayList < Integer > getValidMoves(String[][] gameBoard) {
        ArrayList < Integer > validMovesObj = new ArrayList < Integer > ();
        for (int i = 0; i < gameBoard.length; i++) {
            // System.out.println("Dropping into" + 0 + i );
            if (gameBoard[0][i] == "?") {
                // System.out.println("valid Move here" + 0 + i);
                validMovesObj.add(i);

            }

        }
        // System.out.println("Valid Moves" + Arrays.toString(validMovesObj.toArray()));

        return validMovesObj;
    }
}

// Find open spots
// Add to List
// Recursive(n)
// Call until baord is filled or win
// Return
/*
 * 
 * /* public static Object MiniMax(String[][] copiedBoard) { return
 * doMax(copiedBoard);
 * 
 * public static ArrayList<Integer> getPossibleMoves(String[][] gameBoard) {
 * ArrayList<Integer> q = new ArrayList<Integer>(); // Create an ArrayList
 * object System.out.println("Starting Check move"); // Get all possible moves
 * for (int intCol = 0; intCol < gameBoard.length; intCol++) { for (int intRow =
 * gameBoard.length - 1; intRow >= 0; intRow--) { if (gameBoard[intRow][intCol]
 * == "?") { // Put all possible moves on queue
 * System.out.println("Checking Possible Moves" + intRow + intCol);
 * q.add(intCol); break; }
 * 
 * }
 * 
 * } // do Max for all moves in q return q;
 * 
 * } }
 */

/*
 * public static Object doMax(String[][] copiedBoard) { int best_move = 0;
 * 
 * if (checkTerminalStates.checkGameOver(copiedBoard, "X")) { // If game over }
 * else { ArrayList<Integer> moves = new ArrayList<Integer>(); // Create an
 * ArrayList object moves = getPossibleMoves(copiedBoard); for (Integer i :
 * moves) { if ((checkMoveValue(i) > checkMoveValue(best_move))) { best_move =
 * i; } }
 * 
 * } return best_move;
 * 
 * }
 * 
 * 
 * private static int checkMoveValue(String[][] gameBoard) { // TODO
 * Auto-generated method stub return 0; }
 * 
 * 
 * public static void doMini(String[][] copiedBoard) { int best_move = 0;
 * ArrayList<Integer> moves = new ArrayList<Integer>(); // Create an ArrayList
 * object getPossibleMoves(copiedBoard); for (Integer i : moves) { if
 * ((checkMoveValue(i) > checkMoveValue(best_move))) { best_move = i; } } }
 */
