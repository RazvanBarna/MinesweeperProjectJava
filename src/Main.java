import javafx.application.Application;
import javafx.stage.Stage;
import ro.minesweeper.service.Level;
import ro.minesweeper.service.Minesweeper;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }
    public void start(Stage primaryStage) {
       Minesweeper minesweeper = new Minesweeper();
    }
}
