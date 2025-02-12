package ro.minesweeper.service;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.stage.Stage;

public class GameOver extends Stage {

    public static void showGameOver() {
        Stage gameOverStage = new Stage();

        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", 50));
        gameOverText.setStyle("-fx-fill: red;");

        Button closeButton = new Button("Închide jocul");
        closeButton.setOnAction(e -> gameOverStage.close());

        StackPane layout = new StackPane();
        layout.getChildren().addAll(gameOverText, closeButton);
        StackPane.setAlignment(closeButton, javafx.geometry.Pos.BOTTOM_CENTER);

        Scene gameOverScene = new Scene(layout, 400, 300);
        gameOverStage.setScene(gameOverScene);
        gameOverStage.setTitle("Game Over");
        gameOverStage.show();
    }
}

