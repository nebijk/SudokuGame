module Sudoku.Game.Solver {
    requires javafx.controls;
    requires javafx.fxml;


    opens Sudoku.Game.Solver to javafx.fxml;
    exports Sudoku.Game.Solver;
    exports Sudoku.Game.Solver.View;
    opens Sudoku.Game.Solver.View to javafx.fxml;
}