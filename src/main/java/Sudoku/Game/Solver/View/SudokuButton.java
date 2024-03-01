package Sudoku.Game.Solver.View;

public enum SudokuButton {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), C(0);

    private final int value;

    SudokuButton(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
