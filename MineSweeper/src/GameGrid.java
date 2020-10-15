
public class GameGrid {
    private Tile[][] grid;
    private int row;
    private int col;
    private int minesRemaining;
    private int tilesLeft;
    private boolean gameOverState;
    private boolean newGameIndex;
    private boolean flagMode;

    public GameGrid(int row, int col, int minesRemaining) {
        grid= new Tile[row][col];
        this.row= row;
        this.col= col;
        this.minesRemaining= minesRemaining;
        tilesLeft= row * col - minesRemaining;
        gameOverState= false;
        newGameIndex= true;
        flagMode= false;

        for (int i= 0; i < row; i++ ) {
            for (int j= 0; j < col; j++ ) {
                grid[i][j]= new Tile(i, j);
            }
        }
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getMinesRemaining() {
        return minesRemaining;
    }

    public int getTilesLeft() {
        return tilesLeft;
    }

    public boolean getGameOverState() {
        return gameOverState;
    }

    public boolean isNewGame() {
        return newGameIndex;
    }

    public boolean isFlagMode() {
        return flagMode;
    }

    // Called when game is over
    public void gameOver() {
        gameOverState= true;

        // Display all remaining mines once game is over
        for (int i= 0; i < row; i++ ) {
            for (int j= 0; j < col; j++ ) {
                if (grid[i][j].isMine()) {
                    grid[i][j].open();
                } else if (grid[i][j].isFlagTile()) {
                    grid[i][j].isWrongFlagTile();
                }
            }
        }
    }

    // Called when first move is made
    public void newGameStarted() {
        newGameIndex= false;
    }

    public void toggleFlagMode() {
        if (flagMode) {
            flagMode= false;
        } else {
            flagMode= true;
        }
    }

    // A Utility Function to check whether given cell (row, col)
    // is a valid cell or not
    public boolean isValid(int row, int col) {
        // Returns true if row number and column number
        // is in range
        return row >= 0 && row < this.row && col >= 0 && col < this.col;
    }

    /*
     * Method that sets neighborMines according to number of adjacent mines
     */
    public void setNumberTile(int row, int col) {
        int cnt= 0;

        for (int i= row - 1; i < row + 2; i++ ) {
            for (int j= col - 1; j < col + 2; j++ ) {
                if (isValid(i, j) && !(row == i && col == j) && grid[i][j].isMine()) {
                    cnt++ ;
                }
            }
        }
        grid[row][col].setAdjacentMines(cnt);

    }

    /*
     * Method to flag a tile
     * Flag if unflagged, and unflag if flagged
     * Flagged tiles cannot be opened
     * Adjusts adjacent tiles' adjacent flag numbers to support the open method for number tiles
     */
    public void flag(int row, int col) {

        // Ignore if player tries to flag an open tile
        if (grid[row][col].isOpenTile()) {
            return;
        }

        // Player flags a tile
        else if (!grid[row][col].isFlagTile()) {
            minesRemaining-- ;
            for (int i= row - 1; i < row + 2; i++ ) {
                for (int j= col - 1; j < col + 2; j++ ) {
                    if (isValid(i, j) && !(row == i && col == j)) {
                        grid[i][j].setAdjacentFlags(grid[i][j].getAdjacentFlags() + 1);
                    }
                }
            }
        }

        // Player unflags a tile
        else {
            minesRemaining++ ;
            for (int i= row - 1; i < row + 2; i++ ) {
                for (int j= col - 1; j < col + 2; j++ ) {
                    if (isValid(i, j) && !(row == i && col == j)) {
                        grid[i][j].setAdjacentFlags(grid[i][j].getAdjacentFlags() - 1);
                    }
                }
            }
        }
        grid[row][col].flag();
    }

    /*
     * Method to open a tile
     * open adjacent tiles only if adjacent matches the number tile
     */
    public void open(int row, int col) {

        // Ignore if player tries to open a flag tile
        if (grid[row][col].isFlagTile()) {
            return;
        }

        // Player opens a mine
        else if (!grid[row][col].isOpenTile() && grid[row][col].isMine()) {
            grid[row][col].open();
            gameOverState= true;
            gameOver();

            return;
        }

        // Player opens a not revealed tile
        else if (!grid[row][col].isOpenTile()) {
            grid[row][col].open();
            tilesLeft-- ;

            // The NOT revealed tile has 0 adjacent tiles
            // Reveal all adjacent tiles
            if (grid[row][col].getAdjacentMines() == 0) {
                for (int i= row - 1; i < row + 2; i++ ) {
                    for (int j= col - 1; j < col + 2; j++ ) {
                        if (isValid(i, j) && !(row == i && col == j)) {
                            open(i, j);
                        }
                    }
                }

                return;
            }

            // The NOT revealed tile has 1+ adjacent tile
            else {
                return;
            }
        }

        // Player attempts to open an already revealed tile
        // if it's a tile with 0 adjacent tiles, then ignore
        // if it's a tile with 1+ adjacent tiles, then compare with adjacent flags to open adjacent
        // tiles
        else if (grid[row][col].isOpenTile() &&
            grid[row][col].getAdjacentMines() == grid[row][col].getAdjacentFlags() &&
            grid[row][col].getAdjacentMines() > 0) {
                for (int i= row - 1; i < row + 2; i++ ) {
                    for (int j= col - 1; j < col + 2; j++ ) {
                        if (isValid(i, j) && !(row == i && col == j) &&
                            !grid[i][j].isOpenTile()) {
                            open(i, j);
                        }
                    }
                }
                return;
            }

    }

    /*
     * Assigns number of adjacent mine numbers to each tile in the game grid
     */
    public void assignAdjacentSquares() {

        for (int i= 0; i < row; i++ ) {
            for (int j= 0; j < col; j++ ) {
                setNumberTile(i, j);
            }
        }
        return;
    }

}
