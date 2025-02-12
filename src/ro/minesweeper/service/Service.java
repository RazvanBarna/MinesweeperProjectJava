package ro.minesweeper.service;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import ro.minesweeper.buttons.BombButton;
import ro.minesweeper.buttons.EmptyButton;

import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Service {

    public static int getNrBombs(EmptyButton[][] map, int i, int j) {
        int nr = 0;

        if (i + 1 < map.length && map[i + 1][j].getClass().equals(BombButton.class)) {
            nr++;
        }
        if (j + 1 < map[i].length && map[i][j + 1].getClass().equals(BombButton.class)) {
            nr++;
        }
        if (i - 1 >= 0 && map[i - 1][j].getClass().equals(BombButton.class)) {
            nr++;
        }
        if (j - 1 >= 0 && map[i][j - 1].getClass().equals(BombButton.class)) {
            nr++;
        }

        if (i - 1 >= 0 && j - 1 >= 0 && map[i - 1][j - 1].getClass().equals(BombButton.class)) {
            nr++;
        }
        if (i - 1 >= 0 && j + 1 < map[i].length && map[i - 1][j + 1].getClass().equals(BombButton.class)) {
            nr++;
        }
        if (i + 1 < map.length && j - 1 >= 0 && map[i + 1][j - 1].getClass().equals(BombButton.class)) {
            nr++;
        }
        if (i + 1 < map.length && j + 1 < map[i].length && map[i + 1][j + 1].getClass().equals(BombButton.class)) {
            nr++;
        }

        return nr;
    }

    public static void setNrBombsAdiacent(EmptyButton[][] map, GridPane grid) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (!map[i][j].getClass().equals(BombButton.class)) {
                    map[i][j].setNrAdiacent(getNrBombs(map, i, j));
                }
                if (map[i][j].getNrAdiacent() == 0) {
                    EmptyButton emptyButton = new EmptyButton("Empty",i,j);
                    grid.getChildren().remove(map[i][j]);
                    grid.add(emptyButton, j, i);
                    map[i][j] = emptyButton;
                }
            }
        }
    }

    public static void buttonPressedLeft(EmptyButton button) {
        button.setPrefSize(button.getPrefWidth(), button.getPrefHeight());
        if (button.getNrAdiacent() == -1) {
            buttonUpdateImage(button,"resources/images/bomb.png");
        } else {
            switch (button.getNrAdiacent()) {
                case 0:
                    buttonUpdateImage(button,"resources/images/pressed.png");
                    break;
                    case 1:
                        buttonUpdateImage(button,"resources/images/1.png");
                        break;
                        case 2:
                            buttonUpdateImage(button,"resources/images/2.png");
                            break;
                            case 3:
                                buttonUpdateImage(button,"resources/images/3.png");
                                break;
                                case 4:
                                    buttonUpdateImage(button,"resources/images/4.png");
                                    break;
                                    case 5:
                                        buttonUpdateImage(button,"resources/images/5.png");
                                        break;
                                        case 6:
                                            buttonUpdateImage(button,"resources/images/6.png");
                                            break;
                                            case 7:
                                                buttonUpdateImage(button,"resources/images/7.png");
                                                break;
                                                default:
                                                    buttonUpdateImage(button,"resources/images/8.png");
            }
        }
    }


    public static void buttonUpdateImage(EmptyButton button,String string) {
        Image image = new Image(string);
        ImageView iv = new ImageView(image);
        iv.setFitHeight(button.getHeight());
        iv.setFitWidth(button.getWidth());
        iv.setPreserveRatio(true);
        iv.setTranslateX(-6);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(iv);
        button.setGraphic(stackPane);

        button.setPrefWidth(80);
        button.setPrefHeight(80);
        button.setMinWidth(80);
        button.setMinHeight(80);
        button.setMaxWidth(80);
        button.setMaxHeight(80);
    }

    public static void bfsEmpty(EmptyButton[][] map, int i, int j) {
        int[] dir={-1 , 0 , 1};
        System.out.println(i+" "+j);
        if(i<0 || i>=map.length || j<0 || j>=map[i].length) {
            return;
        }
        Queue<EmptyButton> queue = new LinkedList<>();
        queue.add(map[i][j]);
        while(!queue.isEmpty()) {
            EmptyButton emptyButton = queue.poll();

            if (emptyButton.getNrAdiacent() == -1 || emptyButton.isRevealed()){
                continue;//next iteration
            }
            emptyButton.setRevealed(true);
            Service.buttonPressedLeft(emptyButton);
            if(emptyButton.getNrAdiacent()>0){
                continue;
            }
            
            for(int di: dir){
                for (int dj: dir){
                    int x = emptyButton.getRow()+di;
                    int y = emptyButton.getCol()+dj;
                    if (x>=0 && x<map.length && y>=0 && y<map[i].length) {
                        EmptyButton emptyButton2 = map[x][y];
                        if(!emptyButton2.isRevealed() && emptyButton2.getNrAdiacent() != -1) {
                            queue.add(emptyButton2);
                        }
                    }
                }
            }

        }
    }

    public static void gameOver(EmptyButton[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                Service.buttonPressedLeft(map[i][j]);
                map[i][j].setRevealed(true);
            }
        }
    }


}
