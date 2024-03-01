package Sudoku.Game.Solver.Controller;

import javafx.stage.FileChooser;
import Sudoku.Game.Solver.View.SudokuView;
import Sudoku.Game.Solver.model.SudokuGrid;
import Sudoku.Game.Solver.model.SudokuUtilities.SudokuLevel;
import Sudoku.Game.Solver.model.FileHandler;

import Sudoku.Game.Solver.View.SudokuButton;

import java.io.File;
import java.io.IOException;

public class SudokuController {
    private SudokuGrid model;
    private SudokuView vview;
    private int selectedNumber = 0;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public SudokuController(SudokuGrid model, SudokuView view) {
        this.model = model;
        this.vview = view;
    }

    public void startNewGame(SudokuLevel level) {
        try {
            model.startNewGame(level);
            vview.updateView();
        } catch (Exception e) {
            vview.showAlert("Fel vid start av nytt spel: " + e.getMessage());
        }
    }
    public void handlePlayAgain(SudokuLevel level) {
        try {
            model.startNewGame(level);
            vview.updateView();
        } catch (Exception e) {
            vview.showAlert("Fel vid start av att spela igen spel: " + e.getMessage());
        }
    }

    public void handleSaveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                FileHandler.serializeToFile(selectedFile, model);
            } catch (IOException e) {
                vview.showFileNotSaved();
            }
        } else {
            vview.showFileNotSaved();
        }
    }

    public void checkSolution() {
        if (model.checkSolution()) {
            vview.showAlert("Grattis! Du har löst Sudokut.");
        } else {
            vview.showAlert("Lösningen är inte korrekt. Försök igen.");
        }
    }

    public void setSelectedNumber(int number) {
        this.selectedNumber = number;
    }


    public void fillCell(int row, int col,int selectedNumber) {
        try {
            model.setCellValue(row, col, selectedNumber);
            vview.updateView();
            if (model.isBoardComplete()) {
                if (model.checkSolution()) {
                    vview.updateView();
                    vview.showAlert("Grattis! Du har löst Sudokut.");
                } else {
                    vview.showAlert("Lösningen är inte korrekt. Försök igen.");
                }
            }
        } catch (Exception e) {
            vview.showAlert("Error filling cell: " + e.getMessage());
        }
    }

    public void checkCurrentState() {
        if (model.isCurrentStateCorrect()) {
            vview.showAllInputsSoFarRight();
        } else {
            vview.showAllInputsSoFarWrong();
        }
    }

    public void clearBoard() {
        try {
            model.clearBoard();
            vview.updateView();
        } catch (Exception e) {
            vview.showAlert("Fel vid rensning av brädet: " + e.getMessage());
        }
    }

    public void getHint() {
        try {
            int[] hint = model.getHint();
            if (hint != null) {
                fillCell(hint[0], hint[1],hint[2]);
            } else {
                vview.showAlert("Ingen ledtråd tillgänglig just nu.");
            }
        } catch (Exception e) {
            vview.showAlert("Fel vid hämtning av ledtråd: " + e.getMessage());
        }
    }

    public void handleLoadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                SudokuGrid loadedModel = FileHandler.deSerializeFromFile(selectedFile);
                model.copyState(loadedModel);
                vview.updateView();
            } catch (IOException | ClassNotFoundException e) {
                vview.showFileNotOpened();
            }
        } else {
            vview.showFileNotOpened();
        }
    }

    public void showRules() {
        vview.showRules();
    }

    public void handleButtonGuess(SudokuButton button) {
        setSelectedNumber(button.getValue()); //
    }
    public void cellClicked(int row, int col) {
        fillCell(row, col, selectedNumber);
    }
}

