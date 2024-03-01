package Sudoku.Game.Solver;

import Sudoku.Game.Solver.View.SudokuView;
import Sudoku.Game.Solver.model.SudokuGrid;
import Sudoku.Game.Solver.model.SudokuUtilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudokuApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        SudokuGrid model = new SudokuGrid(SudokuUtilities.SudokuLevel.EASY);
        SudokuView sudokuView = new SudokuView(model);
        Scene scene = new Scene(sudokuView.getRoot(), 562,562);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku Game");
        primaryStage.show();
    }





    public static void main(String[] args) {
        launch(args);
    }
}







