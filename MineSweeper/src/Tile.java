//TODO needs row, column, Type (mine, number, empty), Checked (open, close), Flag (flagged or unflagged)

public class Tile {
    private final int row;
    private final int col;
    private boolean isMine;
    private boolean openTile; // true if revealed, false otherwise
    private boolean flagTile; // true if flagged, false otherwise
    private boolean wrongFlagTile; // trust if flagged at wrong tile at end of game
    private int adjacentMines;
    private int adjacentFlags;

    /*
     *Constructor method
     */
    public Tile(int row, int col) {
        this.row= row;
        this.col= col;
        isMine= false;
        openTile= false;
        flagTile= false;
        wrongFlagTile= false;
        adjacentMines= 0;
        adjacentFlags= 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {

        return col;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isOpenTile() {
        return openTile;
    }

    public boolean isFlagTile() {
        return flagTile;
    }

    // sets that flagged tile is wrong by the end of game
    public void isWrongFlagTile() {
        wrongFlagTile= true;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public int getAdjacentFlags() {
        return adjacentFlags;
    }

    public void setAdjacentFlags(int num) {
        adjacentFlags= num;
    }

    public void setAdjacentMines(int num) {
        adjacentMines= num;
    }

    public void noLongerMine() {
        isMine= false;
    }

    public void setAsMine() {
        isMine= true;
    }

    /*
     * Displays the tile on the game screen
     * "/" for flag
     * "-" for unopened
     * "*" for mine
     * "X" for wrong flags
     * " " for tiles with no adjacent mines
     * "integer" for tiles adjacent mines
     */
    public String display() {
        if (flagTile && wrongFlagTile) {
            return "X";
        } else if (flagTile) {
            return "/";
        } else if (!openTile) {
            return "-";
        } else if (isMine) {
            return "*";
        } else if (adjacentMines == 0) {
            return " ";
        } else {
            return String.valueOf(adjacentMines);
        }
    }

    /*
     * Method to open a tile
     */
    public void open() {
        if (flagTile) {
            return;
        } else {
            openTile= true;
        }
    }

    /*
     * Method to flag a tile
     * Flag if unflagged, and unflag if flagged
     */
    public void flag() {

        if (flagTile) {
            flagTile= false;
        } else {
            flagTile= true;
        }

    }

}
