import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MineSweeperGame {
    private static int difficulty;
    private static int row;
    private static int col;
    private static int mines;
    private static ArrayList<Integer> mineList;
    private static GameGrid myGrid;
    private static long startTime;
    private static double elapsedTime;
    private static double[] recordTime;

    public static void main(String[] args) {

        // Initializes all records to be 0 seconds
        recordTime= new double[] { 0, 0, 0 };

        setDifficultyScreen();

    }

    /*
     * Menu Screen that gives you 3 options: Play New Game, Change Difficulty, or Show past records
     */
    public static void menuScreen() {

        System.out.println("------------------------------------------------------");
        System.out.println(" 1 - New Game / 2 - Set Difficulty / 3 - Show Records ");
        System.out.println("------------------------------------------------------");
        Scanner sc= new Scanner(System.in);
        int menuInput= sc.nextInt();

        if (menuInput == 1) {
            playMineSweeper();
        } else if (menuInput == 2) {
            setDifficultyScreen();
        } else if (menuInput == 3) {
            showRecords();
        }

    }

    /*
     * Method that's called when game starts
     * Initializes the game board.
     */
    public static void startGame() {
        // New game creates my grid and solution grid
        myGrid= new GameGrid(row, col, mines);

        placeMines();

        return;

    }

    /*
     * Method that places mines on the board randomly
     */
    public static void placeMines() {
        // Place mines randomly on the solution board
        mineList= new ArrayList<>();

        for (int i= 0; i < row * col; i++ ) {
            mineList.add(i);
        }
        Collections.shuffle(mineList);
        for (int i= 0; i < mines; i++ ) {
            myGrid.getGrid()[mineList.get(i) / col][mineList.get(i) % col].setAsMine();
        }

        return;
    }

    /*
     * UI method that only prints the current game screen to users
     */
    public static void currentGameScreen() {

        System.out.println(
            "-----------------------------------------Current Game-----------------------------------------");
        System.out.print("   ");
        for (int i= 1; i <= myGrid.getCol(); i++ ) {
            if (i < 10) {
                System.out.print("  " + i);
            }

            else {
                System.out.print(" " + i);
            }
        }
        System.out.print("\n\n");

        for (int i= 1; i <= myGrid.getRow(); i++ ) {
            if (i < 10) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }

            for (int j= 0; j < myGrid.getCol(); j++ ) {
                System.out.print("  " + myGrid.getGrid()[i - 1][j].display());
            }

            System.out.print("\n");

        }
        System.out.println(
            "-----------------------------------------------------------------------------------------------");
        System.out.println("Mines Remainig: " + myGrid.getMinesRemaining());
        if (myGrid.getGameOverState() && myGrid.getTilesLeft() == 0) {

            // When new record is made, save the new record
            if (elapsedTime < recordTime[difficulty] || recordTime[difficulty] == 0) {
                recordTime[difficulty]= elapsedTime / 1000000000;
            }

            // Print how much time was elapsed after player wins a game
            System.out.println("Time: " + String.format("%.2f", elapsedTime / 1000000000));
        }

        return;

    }

    /*
     * Method that gets the player's next move
     */
    public static void nextGameAction() {

        Scanner sc= new Scanner(System.in);

        System.out.println("Flagmode: " + myGrid.isFlagMode());
        System.out.println("Type \"toggle\" to switch modes");
        System.out.println("\"(Row, Column)\" : ");

        String input= sc.nextLine();

        // When toggle is typed
        if (input.equals("toggle")) {
            myGrid.toggleFlagMode();
            nextGameAction();
        }

        // When coordinates are typed in the format (row,col)

        else if (input.contains(",")) {
            String[] coordinate= input.split(",");

            // if input is (x, y)
            // then parts[0] is "(x"
            // and parts[1] is " y)"

            int r= Integer.valueOf(coordinate[0].trim().substring(1).trim()) - 1;
            int c= Integer.valueOf(
                coordinate[1].trim().substring(0, coordinate[1].trim().length() - 1).trim()) - 1;

            if (myGrid.isFlagMode()) {
                if (myGrid.isValid(r, c)) {
                    myGrid.flag(r, c);
                    return;
                } else {
                    System.out.print("Type again correctly.");
                    nextGameAction();
                }
            } else {
                if (myGrid.isValid(r, c)) {

                    // Player's first move
                    if (myGrid.isNewGame()) {
                        // New game starts here
                        myGrid.newGameStarted();
                        startTime= System.nanoTime();

                        myGrid.setNumberTile(r, c);
                        int cnt= 0;

                        // Loop the first selected tile + its adjacent tiles until none of them have
                        // a mine anymore
                        while (myGrid.getGrid()[r][c].getAdjacentMines() +
                            boolToInt(myGrid.getGrid()[r][c].isMine()) > 0) {
                            for (int i= r - 1; i < r + 2; i++ ) {
                                for (int j= c - 1; j < c + 2; j++ ) {
                                    if (myGrid.isValid(i, j) && myGrid.getGrid()[i][j].isMine()) {

                                        // Indicated tile is no longer a mine
                                        myGrid.getGrid()[i][j].noLongerMine();

                                        // Next element in the mine array list is the replaced mine
                                        myGrid.getGrid()[mineList.get(mines + cnt) / col][mineList
                                            .get(mines + cnt) %
                                            col].setAsMine();

                                        // Moves onto the next mine on the mine list
                                        cnt++ ;
                                    }
                                }
                            }

                            // update number of adjacent mine tiles to adjust while loop conditions
                            myGrid.setNumberTile(r, c);
                        }

                        myGrid.assignAdjacentSquares();
                    }

                    myGrid.open(r, c);
                    return;
                } else {
                    System.out.print("Type again correctly.");
                    nextGameAction();
                }
            }
        } else {
            System.out.print("Type again correctly.");
            nextGameAction();
        }

    }

    /*
     * 1 if true, 0 if false
     */
    private static int boolToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    /*
     * Play Mine Sweeper
     */
    public static void playMineSweeper() {
        /*
         * 1. Initialize game
         *  1.1 Start game method
         *  1.2 Place mines on the solution grid
         * 2. While the game is not over
         *  2.1 Keep looping the game play
         *  2.2 Game over when either mine is touched or when all tiles are cleared
         */

        startGame();

        while (!myGrid.getGameOverState()) {

            currentGameScreen();

            nextGameAction();

            // Won the game
            if (!myGrid.getGameOverState() && myGrid.getTilesLeft() == 0) {
                System.out
                    .println(
                        "-------------------------------------------Game Won-------------------------------------------\n");
                myGrid.gameOver();
                elapsedTime= (double) System.nanoTime() - startTime;
            }

            // Lost the game
            else if (myGrid.getGameOverState()) {
                System.out
                    .println(
                        "--------------------------------------------Retry?--------------------------------------------\n");
            }

        }

        currentGameScreen();
        menuScreen();

    }

    /*
     * UI for choosing difficulty
     * Show current difficulty
     * 1-Beginner, 2-Intermediate, 3-Advanced
     */
    public static void setDifficultyScreen() {
        Scanner sc= new Scanner(System.in);
        System.out.println("------------------------------------------------------");
        if (difficulty <= 0 && difficulty > 3) {
            System.out.println(" Please choose again from the following. \n");
        }
        System.out.println(" Choose difficulty: ");
        System.out.println(" 1 - BEGINNER                       (9 x 9, 10 Mines) ");
        System.out.println(" 2 - INTERMEDIATE                 (16 x 16, 40 Mines) ");
        System.out.println(" 3 - ADVANCED                     (16 x 30, 99 Mines) ");
        System.out.println("------------------------------------------------------");

        difficulty= sc.nextInt() - 1;
        if (difficulty >= 0 && difficulty < 3) {
            applyDifficulty(difficulty);
            menuScreen();

        } else {
            setDifficultyScreen();
        }

    }

    /*
     * Method that applies the selected difficulty to game board.
     */
    public static void applyDifficulty(int difficulty) {
        if (difficulty == 0) {
            row= 9;
            col= 9;
            mines= 10;
        } else if (difficulty == 1) {
            row= 16;
            col= 16;
            mines= 40;
        } else if (difficulty == 2) {
            row= 16;
            col= 30;
            mines= 99;
        }
    }

    /*
     * UI that shows past records
     * Display records for each difficulty
     * Once done, ask to go back to main screen
     */
    public static void showRecords() {

        /*
         * TODO Show records
         */
        System.out.println("/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\\n");
        System.out.println("Records \n");
        System.out.println("Beginner:      " + String.format("%.2f", recordTime[0]));
        System.out.println("Intermediate:  " + String.format("%.2f", recordTime[1]));
        System.out.println("Advanced:      " + String.format("%.2f", recordTime[2]) + "\n");
        System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");

        System.out.println("Type anything to exit");

        Scanner sc= new Scanner(System.in);
        String recordInput= sc.nextLine();
        if (recordInput != null) {
            menuScreen();
        }
    }
}
