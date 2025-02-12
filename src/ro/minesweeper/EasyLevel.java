package ro.minesweeper;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
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
    private ArrayList<EmptyButton> flags=new ArrayList<>();

    public EasyLevel() {
        buttons = new EmptyButton[4][5];
        Random rand = new Random();
        GridPane grid = new GridPane();

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
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                //button.setText("");
                buttons[i][j].setMinSize(80, 80);
                buttons[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                buttons[i][j].setStyle("-fx-background-color: gray;");

                EmptyButton auxButton = buttons[i][j];
                buttons[i][j].setOnMouseClicked(e ->{
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        if(!auxButton.isRevealed() && !auxButton.isFlagged()) {
                            if(auxButton.getNrAdiacent()==0) {
                                Service.bfsEmpty(buttons, auxButton.getRow(),auxButton.getCol());
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
                                auxButton.setFlagged(false);
                                Service.buttonUpdateImage(auxButton, "resources/images/pressed.png");
                                flags.remove(auxButton);
                            }
                            else {
                                auxButton.setFlagged(true);
                                flagsWithBombs(flags,auxButton);
                            }
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

        Scene scene = new Scene(grid, 400, 320);
        String css = this.getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        this.setScene(scene);
        this.setTitle("GridPane Center");
        this.show();
    }

    public EmptyButton createRandomButton(int rand) {
        EmptyButton button;
        switch (rand) {
            case 0:
                button= new BombButton();
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
}
