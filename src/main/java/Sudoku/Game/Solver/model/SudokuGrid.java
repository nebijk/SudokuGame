package Sudoku.Game.Solver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Represents the grid of a Sudoku game. This class includes the game grid, solution grid, current game level,
 * and the editability status of each tile in the grid.
 */
public class SudokuGrid implements Serializable {
    private static final int GRID_SIZE = 9;
    private Tile[][] grid;
    private Tile[][] solutionGrid;
    private SudokuUtilities.SudokuLevel currentLevel;

    private boolean[][] editableTiles; // Håller reda på om en ruta är redigerbar eller inte

    /**
     * Constructs a new SudokuGrid with a specified difficulty level.
     * Initializes the game grid and sets up the game based on the given level.
     *
     * @param level The difficulty level for the Sudoku game, influencing the initial setup of the grid.
     */

    public SudokuGrid(SudokuUtilities.SudokuLevel level) {
        this.currentLevel = level;
        this.grid = new Tile[GRID_SIZE][GRID_SIZE];
        initializeGrid(level);

    }
    /**
     * Retrieves the solution grid for the Sudoku game.
     * This grid contains the correct values for each cell in the Sudoku puzzle, against which the player's inputs are checked.
     *
     * @return A 2D array of Tile objects representing the solution grid of the Sudoku game.
     */
    public Tile[][] getSolutionGrid() {
        return solutionGrid;
    }
    /**
     * Starts a new game of Sudoku at the  difficulty level.
     * This method resets the current game level and reinitializes the grid with new values  for the chosen level.
     *
     * @param level The new difficulty level for the Sudoku game, which determines the complexity of the puzzle.
     */

    public void startNewGame(SudokuUtilities.SudokuLevel level) {
        this.currentLevel = level;
        initializeGrid(level);
    }
    /**
     * Initializes the Sudoku grid based on the specified difficulty level.
     * It sets up the grid with initial values, marks editable tiles, and prepares the solution grid.
     *
     * @param level The difficulty level of the Sudoku game.
     */

    private void initializeGrid(SudokuUtilities.SudokuLevel level) {
        int[][][] sudokuMatrix = SudokuUtilities.generateSudokuMatrix(level);
        editableTiles = new boolean[GRID_SIZE][GRID_SIZE];
        solutionGrid = new Tile[GRID_SIZE][GRID_SIZE]; // Skapar en ny matris för lösningen

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int fromsStartValue = sudokuMatrix[row][col][0];
                int correctValue = sudokuMatrix[row][col][1];
                boolean isfromStart = fromsStartValue != 0;

                grid[row][col] = new Tile(correctValue,isfromStart);
                solutionGrid[row][col] = new Tile(correctValue, true); // Spara lösningen

                if (isfromStart) {
                    grid[row][col].setUserValue(fromsStartValue);
                }
                editableTiles[row][col] = !isfromStart;
            }
        }
    }
    /**
     * Sets the value of a cell in the Sudoku grid if the cell is editable.
     *
     * @param row   The row number of the cell.
     * @param col   The column number of the cell.
     * @param value The value to set in the cell.
     */

    public void setCellValue(int row, int col, int value) {
        if(isTileEditable(row, col)) {
            grid[row][col].setUserValue(value);
        }
    }

    /**
     * Checks if the Sudoku board is completely filled with non-zero values.
     *
     * @return true if the board is completely filled; false if there are any empty cells.
     */
    public boolean isBoardComplete() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col].getUserValue() == 0) {
                    return false; // Hittade en tom cell, brädet är inte fullständigt
                }
            }
        }
        return true; // Inga tomma celler, brädet är fullständigt
    }
    /**
     * Clears all editable cells on the Sudoku board, resetting them to their initial state (empty or zero).
     */
    public void clearBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (editableTiles[row][col]) {
                    grid[row][col].setUserValue(0);
                }
            }
        }
    }
    /**
     * Copies the state from another SudokuGrid instance to this instance.
     * This method is used to update the current game grid with the state of another game grid,
     * which includes copying both the values of the cells and their editability status.
     *
     * This can be useful for features like loading a saved game, restoring the game to a previous state,
     * or copying the state for testing and debugging purposes.
     *
     * @param otherGrid The SudokuGrid instance whose state is to be copied.
     */
    public void copyState(SudokuGrid otherGrid) {
        // Copy grid values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                this.grid[row][col].setUserValue(otherGrid.grid[row][col].getUserValue());
            }
        }

        // Copy editable status
        for (int row = 0; row < GRID_SIZE; row++) {
            System.arraycopy(otherGrid.editableTiles[row], 0, this.editableTiles[row], 0, GRID_SIZE);
        }
    }

    /**
     * Provides a hint by selecting a random empty cell and revealing its correct value.
     * If there are no empty cells, no hint can be provided.
     *
     * @return An array containing the row and column of the empty cell and its correct value,
     *         or null if no hint is available.
     */
    public int[] getHint() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col].getUserValue() == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        if (emptyCells.isEmpty()) {
            return null; // No available hints
        }
        // Randomly select an empty cell for a hint
        Random random = new Random();
        int[] selectedCell = emptyCells.get(random.nextInt(emptyCells.size()));
        int row = selectedCell[0];
        int col = selectedCell[1];
        int correctValue = solutionGrid[row][col].getCorrectValue();
        return new int[]{row, col, correctValue};
    }
    /**
     * Checks if the current state of the Sudoku board is correct.
     * Only cells with user-entered values are checked against the solution.
     *
     * @return true if all entered values are correct; false if any entered value is incorrect.
     */
    public boolean isCurrentStateCorrect() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Tile tile = grid[row][col];

                if (tile.getUserValue() != 0 && !tile.isUserInputCorrect()) {
                    return false; // Found an incorrect value
                }
            }
        }
        return true; // All entered values are correct
    }


    /**
     * Restarts the Sudoku game by reinitializing the grid based on the current difficulty level.
     * This method is typically called to start a new game or reset the current game.
     */
    public void restartGame() {
        initializeGrid(this.currentLevel);
    }

    /**
     * Retrieves the Tile object located at the specified row and column in the Sudoku grid.
     *
     * @param row The row number of the tile (zero-based index).
     * @param col The column number of the tile (zero-based index).
     * @return The Tile object at the specified location.
     */

    public Tile getTile(int row, int col) {
        return grid[row][col];
    }
    /**
     * Sets a specific Tile at the specified row and column in the Sudoku grid.
     *
     * @param row  The row number where the tile will be placed (zero-based index).
     * @param col  The column number where the tile will be placed (zero-based index).
     * @param tile The Tile object to be placed at the specified location.
     */
    public void setTile(int row, int col, Tile tile) {
        grid[row][col] = tile;
    }
    /**
     * Checks whether the current state of the Sudoku grid matches the solution.
     * This method iterates through the entire grid, comparing each user input with the solution.
     *
     * @return true if the current grid configuration matches the solution; false otherwise.
     */
    public boolean checkSolution() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col].getUserValue() != solutionGrid[row][col].getUserValue()) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Determines if a specific tile in the Sudoku grid is editable.
     * This method is useful for ensuring that the initial, pre-filled tiles in the puzzle cannot be altered.
     *
     * @param row The row number of the tile to be checked (zero-based index).
     * @param col The column number of the tile to be checked (zero-based index).
     * @return true if the tile is editable; false otherwise.
     */
    public boolean isTileEditable(int row, int col) {
        return editableTiles[row][col];

    }


    /**
     * Validates if a move is valid in the Sudoku grid. It checks the specified row, column, and section
     * to ensure that the same value does not  exist already .
     *
     * @param row   The row number where the move is being made.
     * @param col   The column number where the move is being made.
     * @param value The value to be placed in the specified row and column.
     * @return true if the move is valid; false otherwise.
     */
    public boolean isValidMove(int row, int col, int value) {
        System.out.println("isValidMove called with row=" + row + ", col=" + col + ", value=" + value);
        for (int i = 0; i < GRID_SIZE; i++) {
            if (grid[row][i].getUserValue() == value) {
                return false;
            }
        }

        // Kontrollera kolumnen
        for (int i = 0; i < GRID_SIZE; i++) {
            if (grid[i][col].getUserValue() == value) {
                return false; // Värdet finns redan i kolumnen
            }
        }

        int startRow = row / 3 * 3;
        int startCol = col / 3 * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (grid[i][j].getUserValue() == value) {
                    return false;
                }
            }
        }
        // Om inget fel hittades, är draget giltigt
        return true;
    }

    /**
     * Sets  value of a tile in the Sudoku grid if the tile is editable.
     *
     * @param row   The row number of  tile.
     * @param col   The column number of  tile.
     * @param value The value to set in the specified tile.
     */
    public void setTileValue(int row, int col, int value) {
        if(isTileEditable(row, col)) {
            grid[row][col].setUserValue(value);
        }
    }
    /**
     * Get  current level of the Sudoku game.
     *
     * @return The current level of the Sudoku game.
     */
    public SudokuUtilities.SudokuLevel getLevel() {
        return this.currentLevel;
    }

    /**
     * Retrieves the current state of the Sudoku grid.
     *
     * @return A 2D array of Tile objects representing the current state of the Sudoku grid.
     */
    public Tile[][] getGrid() {
        return grid;
    }



}