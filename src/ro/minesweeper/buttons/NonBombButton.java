package ro.minesweeper.buttons;
import javafx.scene.control.Button;

public class NonBombButton extends EmptyButton {

    public NonBombButton(int nrAdiacent,String name) {
        super(nrAdiacent,name);
    }

    public NonBombButton(String name,int row,int col) {
        super(name,row,col);
    }
}
