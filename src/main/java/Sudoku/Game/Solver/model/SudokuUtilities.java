package Sudoku.Game.Solver.model;

import java.util.Random;

public class SudokuUtilities {
    public enum SudokuLevel {EASY, MEDIUM, HARD}
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;
    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param level The level, i.e. the difficulty, of the initial standing.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty
    cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is
    not 2*81 characters and
     * for characters other than '0'-'9'.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String representationString;
        switch (level) {
            case EASY: representationString = easy; break;
            case MEDIUM: representationString = medium; break;
            case HARD: representationString = hard; break;
            default: representationString = medium;
        }
        int[][][] matrix = convertStringToIntMatrix(representationString);
        RandomTransform(matrix);

        return matrix;
    }
    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param stringRepresentation A string of 2*81 characters, 0-9. The first 81
    characters represents
     * the initial values, '0' representing an empty
    cell.
     * The following 81 characters represents the
    solution.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty
    cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is
    not 2*81 characters and
     * for characters other than '0'-'9'.
     */
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " +
                    stringRepresentation.length());
        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();
        int charIndex = 0;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] =
                convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        return values;
    }
    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " +
                ch);
        return ch - '0';
    }
    private static final String easy =
            "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" +
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";
    private static final String medium =
            "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";
    private static final String hard =
            "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";


    private static void RandomTransform(int[][][] matrix) {
        Random random = new Random();
        int transformation = random.nextInt(3); // Välj en av tre transformationer
        switch (transformation) {
            case 0:
                swapThreesAndFives(matrix);
                break;
            case 1:
                reflectBoardHorizontal(matrix);
                break;
            case 2:
                reflectBoardVertical(matrix);
                break;
        }
    }

    /**
     * Reflects the board horizontally. It swaps the left and right halves of the board.
     *
     * @param matrix A 3D matrix representing the game board. The first two dimensions represent the board's grid,
     *               and the third dimension holds two values (initial values and solutions).
     */
    private static void reflectBoardHorizontal(int[][][] matrix) {
        int N = matrix.length;
        for (int row = 0; row < N; row++)
        {
            for (int col = 0; col < N / 2; col++)
            {
                for (int z = 0; z < 2; z++)
                {
                 int temporary = matrix[row][col][z];
                matrix[row][col][z] = matrix[row][N - 1 - col][z];
                matrix[row][N - 1 - col][z] = temporary;
                }
            }
        }
    }

    /**
     * Reflects the board vertically. It swaps the top and bottom halves of the board.
     *
     * @param matrix A 3D matrix representing the game board. The first two dimensions represent the board's grid,
     *               and the third dimension holds two values (initial values and solutions).
     */
    private static void reflectBoardVertical(int[][][] matrix) {
        int N = matrix.length;
        for (int col = 0; col < N; col++) {
            for (int row = 0; row < N / 2; row++) {
                for (int z = 0; z < 2; z++) {
                int temporary = matrix[row][col][z];
                matrix[row][col][z] = matrix[N - 1 - row][col][z];
                matrix[N - 1 - row][col][z] = temporary;
                }
            }
        }
    }

    /**
     * Swaps the numbers 3 and 5 in the entire board. This method iterates through the entire board
     * and swaps the values 3 and 5 wherever they appear.
     *
     * @param matrix A 3D matrix representing the game board. The first two dimensions represent the board's grid,
     *               and the third dimension holds two values (initial values and solutions).
     */
    private static void swapThreesAndFives(int[][][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                for (int z = 0; z < 2; z++) {
                    if (matrix[row][col][z] == 3) {
                        matrix[row][col][z] = 5;
                    } else if (matrix[row][col][z] == 5) {
                        matrix[row][col][z] = 3;
                    }
                }
            }
        }
    }
}
