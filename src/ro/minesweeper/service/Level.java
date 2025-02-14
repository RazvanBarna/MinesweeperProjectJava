package ro.minesweeper.service;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ro.minesweeper.buttons.BombButton;
import ro.minesweeper.buttons.EmptyButton;
import ro.minesweeper.buttons.NonBombButton;

public class Level extends Stage {
    private static EmptyButton[][] buttons; // 4 rows, 5 columns
    private ArrayList<EmptyButton> flags;
    private ArrayList<EmptyButton> bombs=new ArrayList<>();
    private int nrBombs;
    private Label flagsLabel;
    private Label bombsLabel;
    private int nrFlags=0;
    private int rows;
    private int columns;


    public Level(int nrBombs, int rows, int columns) {
        buttons = new EmptyButton[rows][columns];
        Random rand = new Random();
        GridPane grid = new GridPane();
        Button finishButton = new Button("Finish");
        this.rows=rows;
        this.columns=columns;
        this.nrBombs=nrBombs;

        flagsLabel = new Label("Flags left: "+nrBombs);
        bombsLabel = new Label("Bombs: "+nrBombs);
        Label titleLabel = new Label("Easy Level");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        flags = new ArrayList<>(nrBombs);
        ArrayList<String> positions = new ArrayList<>();
        for (int i=0;i<rows;i++) {
            for (int j=0;j<columns;j++) {
                positions.add(i+","+j);
            }
        }
        Collections.shuffle(positions,rand);
        this.createRandomButton(nrBombs,positions,grid);
        Service.setNrBombsAdiacent(buttons,grid);


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttons[i][j].setPrefSize(40, 40);
                buttons[i][j].setStyle("-fx-background-color: gray;");

                EmptyButton auxButton = buttons[i][j];
                buttons[i][j].setOnMouseClicked(e ->{
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        if(!auxButton.isRevealed() && !auxButton.isFlagged()) {
                            if(auxButton.getNrAdiacent()==0) {
                                Service.bfsEmpty(buttons, auxButton.getRow(),auxButton.getCol());
                            }
                            else if(auxButton.getNrAdiacent()==-1) {
                                Service.gameOver(buttons);
                                finishButton.setDisable(true);
                                titleLabel.setText("Game Over");
                            }
                            else {
                                auxButton.setRevealed(true);
                                Service.buttonPressedLeft(auxButton);
                            }
                        }
                    }
                    else if (e.getButton().equals(MouseButton.SECONDARY)) {
                        if(!auxButton.isRevealed()) {
                            if(auxButton.isFlagged()) {
                                nrFlags--;
                                auxButton.setFlagged(false);
                                Service.buttonUpdateImage(auxButton, "resources/images/pressed.png");
                                flags.remove(auxButton);
                            }
                            else {
                                if(nrFlags < nrBombs) {
                                    nrFlags++;
                                    auxButton.setFlagged(true);
                                    flagsWithBombs(flags, auxButton);
                                }
                            }
                            updateFlagsCount();
                        }
                    }

                });
            }
        }



        for (int i = 0; i < rows; i++) {
            grid.getRowConstraints().add(new javafx.scene.layout.RowConstraints());
        }
        for (int j = 0; j < columns; j++) {
            grid.getColumnConstraints().add(new javafx.scene.layout.ColumnConstraints());
        }

        HBox topBox = new HBox(20, flagsLabel, titleLabel, bombsLabel);
        topBox.setStyle("-fx-alignment: center;");

        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        restartButton.setOnAction(e -> {
            new Minesweeper();
            this.close();
        });


        finishButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        finishButton.setOnAction(e -> {
            disableAllButtons();
            restartButton.setDisable(false);
            if (finishGame()) {
                titleLabel.setText("You Win!");
            } else {
                titleLabel.setText("Try Again!");
            }
        });

        HBox bottomBox = new HBox(finishButton, restartButton);
        bottomBox.setStyle("-fx-alignment: center; -fx-padding: 20px;");


        VBox mainLayout = new VBox(10, topBox, grid, bottomBox);
        mainLayout.setStyle("-fx-padding: 10px;");
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 1200, 1000);
        String css = this.getClass().getResource("/ro/minesweeper/service/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        this.setScene(scene);
        this.setTitle("Minesweeper  Level");
        this.show();
    }

    public void createRandomButton(int nrBombs,ArrayList<String> positions,GridPane grid ) {
        for (int i = 0; i < nrBombs; i++) {
            String[] pos = positions.get(i).split(",");
            int row = Integer.parseInt(pos[0]);
            int col = Integer.parseInt(pos[1]);
            buttons[row][col] = new BombButton();
            buttons[row][col].getStyleClass().add("BombButton");
            bombs.add(buttons[row][col]);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (buttons[i][j] == null) {
                    buttons[i][j] = new NonBombButton(0, "NonBomb Square");
                    buttons[i][j].getStyleClass().add("NonBombButton");
                }
                grid.add(buttons[i][j], j, i);
                buttons[i][j].setRow(i);
                buttons[i][j].setCol(j);
            }

    }
    }

    public void flagsWithBombs( ArrayList<EmptyButton> flags,EmptyButton button) {
        flags.add(button);
        Service.buttonUpdateImage(button,"resources/images/flag.png");
    }

    private void updateFlagsCount() {
        flagsLabel.setText("Flags left: " + (nrBombs - flags.size()));
    }

    private void disableAllButtons() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }

    private boolean finishGame(){
        if (flags.size() == bombs.size() && flags.containsAll(bombs)) {
            return true;
        }
        else {
           return false;
        }
    }
}
