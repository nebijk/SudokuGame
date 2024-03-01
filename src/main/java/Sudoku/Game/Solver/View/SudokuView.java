package Sudoku.Game.Solver.View;

import Sudoku.Game.Solver.Controller.SudokuController;
import Sudoku.Game.Solver.model.SudokuGrid;
import Sudoku.Game.Solver.model.SudokuUtilities;
// Updated import

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import java.util.Optional;

public class SudokuView {


    private static SudokuGrid model;
    private Button clearbutton;
    private SudokuController controller;
    private GridView gridView;

    private MenuBar menuBar;

    private SudokuUtilities.SudokuLevel level;
    private final BorderPane sudokuBoard;
    private Alert alert;


    public SudokuView(SudokuGrid model) {
        this.model = model;
        this.controller = new SudokuController(model, this);
        this.gridView = new GridView(this.model, this.controller);
        sudokuBoard = gridView.getBorderPane();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
    }


    public VBox getRoot() {
        VBox root = new VBox();
        root.getChildren().addAll(gridView.getMenuBar(), sudokuBoard);
        return root;
    }

    public boolean showRules() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sudoku game rules");
        alert.setHeaderText(" ");
        alert.setContentText("Each row must contain all the numers from 1 to 9, with no repition. Each column must also contain all the numbers from 1 to 9, with no repetition. Each 3x3 box must containt all the numbers from 1 to 9, with no repetition. Some cells are initially filled with number and serve as help for solving the puzzle.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void updateView() {
        gridView = new GridView(model, controller);
        sudokuBoard.setCenter(gridView.getNumberPane());


    }

    public File getFiletoSave() {

        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku-filer", "*.sudoku"));
        return fileChooser.showSaveDialog(primaryStage);
    }

    public File getFiletoOpen() {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku-filer", "*.sudoku"));
        return fileChooser.showOpenDialog(primaryStage);
    }

    public boolean showWonMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("You are a winner!");
        alert.setHeaderText(" ");
        alert.setContentText("You solved the sudoku.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

    }

    public boolean showFileNotOpened() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(" ");
        alert.setTitle("Error");
        alert.setContentText("Could not open file");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

    }

    public boolean showFileNotSaved() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(" ");
        alert.setTitle("Error");
        alert.setContentText("Could not save file");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

    }

    public boolean showLooseMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game over");
        alert.setHeaderText(" ");
        alert.setContentText("You didn't solve the sudoku.");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

    }

    public boolean showAllInputsSoFarRight() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Checking all inputs so far ");
        alert.setHeaderText(" ");
        alert.setContentText("All inputs so far are correct.");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

    }

    public boolean showAllInputsSoFarWrong() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Checking all inputs so far ");
        alert.setHeaderText(" ");
        alert.setContentText("All inputs so far are wrong.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }


}




