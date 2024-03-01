package Sudoku.Game.Solver.View;

import Sudoku.Game.Solver.Controller.SudokuController;
import Sudoku.Game.Solver.model.SudokuGrid;
import Sudoku.Game.Solver.model.SudokuUtilities;
import Sudoku.Game.Solver.model.Tile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GridView {

    private final MenuBar menuBar;
    private final SudokuGrid model;
    private SudokuController kontroller;
    private BorderPane borderPane;
    private final Label[][] numberTiles;
    private TilePane numberPane;

    private Button checkButton;
    private Button hintButton;
    private Button[] numberButtons;

    public GridView(SudokuGrid model, SudokuController kontroller) {
        this.model=model;
        this.kontroller=kontroller;
        initButtons();
        numberTiles = new Label[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
        initBorderPane();
        menuBar= createGameMenu();
    }

    private void initBorderPane(){
        borderPane = new BorderPane();
        borderPane.setCenter(numberPane);


        GridPane leftButtonsPane = new GridPane();
        leftButtonsPane.add(checkButton, 0,0);
        leftButtonsPane.add(hintButton,0,1);
        leftButtonsPane.setAlignment(Pos.CENTER);
        leftButtonsPane.setPadding(new Insets(10));
        leftButtonsPane.setVgap(5);
        borderPane.setLeft(leftButtonsPane);


        GridPane numbersPane = new GridPane();
        for (int i= 0; i < numberButtons.length; i++){
            //              knapp,
            numbersPane.add(numberButtons[i], 0,i);
        }
        numbersPane.setAlignment(Pos.CENTER);
        numbersPane.setVgap(1);
        numbersPane.setPadding(new Insets(15));
        borderPane.setRight(numbersPane);

        //top
        GridPane topPane = new GridPane();
        topPane.setPadding(new Insets(5));
        borderPane.setTop(topPane);

        //bottom
        GridPane bottomPane = new GridPane();
        bottomPane.setPadding(new Insets(10));
        borderPane.setBottom(bottomPane);
    }

    private void initButtons(){
        checkButton = new Button("Check");
        checkButton.setOnAction(new EventHandler<ActionEvent>() { //connect till händelsekällan
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.checkCurrentState();
            }
        });

        hintButton = new Button("Hint");
        hintButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.getHint();
            }
        });

        numberButtons = new Button[10];
        SudokuButton[] numberButtonValues = SudokuButton.values(); //hämta alla värden
        for(int i = 0; i < numberButtons.length-1; i++){
            int index = i;
            numberButtons[i] = new Button(String.valueOf(i+1)); //siffran som kommer visas på knappen
            numberButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    kontroller.handleButtonGuess(numberButtonValues[index]); //koppla knapp till enum
                }
            });
        }
        numberButtons[9] = new Button("C");
        numberButtons[9].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.handleButtonGuess(SudokuButton.C); //koppla knapp till enum
            }
        });

    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        MenuItem LoadItem = new MenuItem("Load game");
        LoadItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                kontroller.handleLoadGame();
            }
        });

        MenuItem SaveItem = new MenuItem("Save game");
        SaveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.handleSaveGame();
            }
        });

        fileMenu.getItems().add(LoadItem);
        fileMenu.getItems().add(SaveItem);
        fileMenu.getItems().add(exitItem);
        return fileMenu;

    }

    private Menu creatGameMenu() {

        Menu sudokuMenu = new Menu("Game"); //kanske lägga till de olika nivåerna man måste lägga till
        MenuItem playAgainItem = new MenuItem("Play again");

        playAgainItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.handlePlayAgain(model.getLevel());
            }
        });


        MenuItem easyLevelItem = new MenuItem("Easy");
        easyLevelItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.handlePlayAgain(SudokuUtilities.SudokuLevel.EASY);
            }
        });

        MenuItem mediumLevelItem = new Menu("Medium");
        mediumLevelItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.handlePlayAgain(SudokuUtilities.SudokuLevel.MEDIUM);
            }
        });


        MenuItem hardLevelItem = new Menu("Hard");
        hardLevelItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.handlePlayAgain(SudokuUtilities.SudokuLevel.HARD);
            }
        });

        sudokuMenu.getItems().add(playAgainItem);
        sudokuMenu.getItems().add(easyLevelItem);
        sudokuMenu.getItems().add(mediumLevelItem);
        sudokuMenu.getItems().add(hardLevelItem);
        return sudokuMenu;
    }


    private Menu createHelpMenu(){

        Menu HelpMenu= new Menu("Help");

        MenuItem clearItem= new MenuItem("Clear");
        clearItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.clearBoard();
            }
        });

        MenuItem CheckItem= new MenuItem("Check");
        CheckItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.checkSolution();
            }
        });



        MenuItem rulesItem= new MenuItem("Rules");
        rulesItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                kontroller.showRules();
            }
        });
        HelpMenu.getItems().add(rulesItem);
        HelpMenu.getItems().add(CheckItem);
        HelpMenu.getItems().add(clearItem);

        return HelpMenu;
    }

    public MenuBar getMenuBar(){

        return this.menuBar;
    }

    public MenuBar createGameMenu(){
        MenuBar menuBar= new MenuBar();
        menuBar.getMenus().addAll(createFileMenu(),creatGameMenu(),createHelpMenu());

        return menuBar;
    }


    // use this method to get a reference to the number (called by some other class)
    public TilePane getNumberPane() {
        return numberPane;
    }

    // called by constructor (only)
    private final  void initNumberTiles() {
        Font font;
        Tile[][] cells= model.getGrid();

        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                
                Label tile;
                if (cells[row][col].getUserValue() != 0) {
                    tile = new Label(String.valueOf(cells[row][col].getUserValue()));
                }else{
                    tile= new Label(" ");
                }
                if (cells[row][col].isFromStart()) {
                    font = Font.font("Monospaced", FontWeight.BOLD, 20);
                } else {
                    font = Font.font("Monospaced", FontWeight.NORMAL, 20);
                }

                tile.setPrefWidth(48);
                tile.setPrefHeight(48);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;"); // css style
                tile.setOnMouseClicked(new tileCLickHandler()); // add your custom event handler
                // add new tile to grid
                numberTiles[row][col] = tile;
            }
        }
    }



    private final TilePane makeNumberPane() {
        // create the root tile pane
        TilePane root = new TilePane();
        root.setPrefColumns(SudokuUtilities.SECTIONS_PER_ROW);
        root.setPrefRows(SudokuUtilities.SECTIONS_PER_ROW);
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        TilePane[][] sections = new TilePane[SudokuUtilities.SECTIONS_PER_ROW][SudokuUtilities.SECTIONS_PER_ROW];
        int i = 0;
        for (int srow = 0; srow < SudokuUtilities.SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SudokuUtilities.SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SudokuUtilities.SECTION_SIZE);
                section.setPrefRows(SudokuUtilities.SECTION_SIZE);
                section.setStyle( "-fx-border-color: black; -fx-border-width: 0.5px;");

                for (int row = 0; row < SudokuUtilities.SECTION_SIZE; row++) {
                    for (int col = 0; col < SudokuUtilities.SECTION_SIZE; col++) {
                        section.getChildren().add(
                                numberTiles[srow * SudokuUtilities.SECTION_SIZE + row][scol * SudokuUtilities.SECTION_SIZE + col]);
                    }
                }
                root.getChildren().add(section);
            }
        }
        return root;
    }


    public BorderPane getBorderPane() {
        return borderPane;
    }


    private class tileCLickHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            for(int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
                for(int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                    if(event.getSource() == numberTiles[row][col]) {
                        if(event.getSource() == numberTiles[row][col]) {
                            kontroller.cellClicked(row, col);
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }
}


