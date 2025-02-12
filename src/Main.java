import javafx.application.Application;
import javafx.stage.Stage;
import ro.minesweeper.EasyLevel;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }
    public void start(Stage primaryStage) {
        EasyLevel easyLevel = new EasyLevel();
    }
}
