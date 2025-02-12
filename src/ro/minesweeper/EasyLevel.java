package ro.minesweeper;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

import ro.minesweeper.buttons.BombButton;
import ro.minesweeper.buttons.EmptyButton;
import ro.minesweeper.buttons.NonBombButton;
import ro.minesweeper.service.Service;

public class EasyLevel extends Stage {
    private static EmptyButton[][] buttons; // 4 rows, 5 columns
    private ArrayList<EmptyButton> flags;
    private ArrayList<EmptyButton> bombs=new ArrayList<>();
    private int nrBombs;
    private Label flagsLabel;
    private Label bombsLabel;
    private int nrFlags=0;


    public EasyLevel() {
        buttons = new EmptyButton[4][5];
        Random rand = new Random();
        GridPane grid = new GridPane();
        Button finishButton = new Button("Finish");

        flagsLabel = new Label("Flags left: 5");
        bombsLabel = new Label("Bombs: 5");
        Label titleLabel = new Label("Easy Level");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                int randomNum = rand.nextInt(2);
                buttons[i][j] = createRandomButton(randomNum);
                grid.add(buttons[i][j], j, i);
                buttons[i][j].setRow(i);
                buttons[i][j].setCol(j);
            }
        }
        Service.setNrBombsAdiacent(buttons,grid);
        flags = new ArrayList<>(nrBombs);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setPrefSize(80, 80);
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



        for (int i = 0; i < 4; i++) {
            grid.getRowConstraints().add(new javafx.scene.layout.RowConstraints());
        }
        for (int j = 0; j < 5; j++) {
            grid.getColumnConstraints().add(new javafx.scene.layout.ColumnConstraints());
        }

        HBox topBox = new HBox(20, flagsLabel, titleLabel, bombsLabel);
        topBox.setStyle("-fx-alignment: center;");

        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        restartButton.setOnAction(e -> {
            new EasyLevel();
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


        Scene scene = new Scene(mainLayout, 330, 400);
        String css = this.getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        this.setScene(scene);
        this.setTitle("Minesweeper - Easy Level");
        this.show();
    }

    public EmptyButton createRandomButton(int rand) {
        EmptyButton button;
        switch (rand) {
            case 0:
                if(nrBombs<5) {
                    button = new BombButton();
                    nrBombs++;
                    bombs.add(button);
                }
                else {
                    button= new NonBombButton(0,"NonBomb Square");
                }
                break;
            default:
                button= new NonBombButton(0,"NonBomb Square");
        }
        return button;
    }

    public void flagsWithBombs( ArrayList<EmptyButton> flags,EmptyButton button) {
        flags.add(button);
        Service.buttonUpdateImage(button,"resources/images/flag.png");
    }

    private void updateFlagsCount() {
        flagsLabel.setText("Flags left: " + (5 - flags.size()));
    }

    private void disableAllButtons() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
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
