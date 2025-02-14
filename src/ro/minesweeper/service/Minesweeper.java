package ro.minesweeper.service;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Minesweeper extends Stage {

    public Minesweeper() {

        Text title = new Text("Welcome to Minesweeper");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Text subtitle = new Text("Choose Your Level");
        subtitle.setStyle("-fx-font-size: 18px;");

        Button easyButton = new Button("Easy");
        Button mediumButton = new Button("Medium");
        Button hardButton = new Button("Hard");

        easyButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        mediumButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        hardButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

        easyButton.setOnAction(e ->{
            new Level(10,9,9);
            this.close();
        } );
        mediumButton.setOnAction(e ->{
            new Level(40,16,16);
            this.close();
        });
        hardButton.setOnAction(e ->{
            new Level(99,16,16);
            this.close();
        } );

        VBox layout = new VBox(15, title, subtitle, easyButton, mediumButton, hardButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px;");

        Scene scene = new Scene(layout, 500, 400);
        this.setScene(scene);
        this.setTitle("Minesweeper - Main Menu");
        this.show();
    }

}
