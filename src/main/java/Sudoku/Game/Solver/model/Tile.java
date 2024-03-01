package Sudoku.Game.Solver.model;

import java.io.Serializable;

/**
 * Represents a tile in a puzzle game, which holds values entered by a user as well as the correct value.
 * It  keeps track of whether the tile is an initial tile (not editable) or not.
 */
public class Tile implements Serializable {
    private int correctValue;
    private int userValue;
    private boolean fromStart;
    /**
     * Constructor  creating  a Tile object.
     *
     * @param correctValue the correct value of the tile
     * @param fromStart   flag indicating whether this tile is an initial tile or not
     */
    public Tile(int correctValue, boolean fromStart) {
        this.correctValue = correctValue;
        this.fromStart = fromStart;
        if (fromStart) {
            this.userValue = correctValue;
        } else {
            this.userValue = 0; // Represents an empty cell
        }
    }
    /**
     * to get  the correct value of the tile.
     *
     * @return the correct value of this tile
     */
    public int getCorrectValue() {
        return correctValue;
    }

    /**
     * Set the correct value of the tile.
     *
     * @param correctValue the correct value to set
     */
    public void setCorrectValue(int correctValue) {
        this.correctValue = correctValue;
    }

    /**
     * Gets the user-entered value of the tile.
     *
     * @return the value entered by the user
     */
    public int getUserValue() {
        return userValue;
    }

    /**
     * Checks if the  input is correct by comparing it with  correct value.
     *
     * @return true if user input is correct, false otherwise
     */
    public boolean isUserInputCorrect() {
        return userValue == correctValue;
    }

    /**
     * Checks if the tile is editable.
     *
     * @return true if the tile is not an initial tile, false otherwise
     */
    public boolean isEditable() {
        return !fromStart;
    }
    /**
     * Sets the user-entered value for the tile.
     *
     * @param userValue the value entered by the user
     */
    public void setUserValue(int userValue) {
        this.userValue = userValue;
    }

    /**
     * Checks if the tile is an initial tile.
     *
     * @return true if this is an initial tile, false otherwise
     */
    public boolean isFromStart() {
        return fromStart;
    }

}
