package ro.minesweeper.buttons;
import javafx.scene.control.Button;

public class EmptyButton extends Button {
    private int nrAdiacent;
    private String nameOfButton;
    private boolean revealed;
    private boolean flagged;
    private int row;
    private int col;

    public EmptyButton(int nrAdiacent, String nameOfButton) {
        super(nameOfButton);
        this.nrAdiacent = nrAdiacent;
        this.nameOfButton = nameOfButton;
        this.revealed = false;
        this.flagged = false;
        this.row = 0;
        this.col = 0;
    }

    public EmptyButton(String nameOfButton,int row,int col) {
        super(nameOfButton);
        this.nrAdiacent = 0;
        this.nameOfButton =nameOfButton ;
        this.revealed = false;
        this.flagged = false;
        this.row = row;
        this.col = col;
    }

    public int getNrAdiacent() {
        return nrAdiacent;
    }

    public void setNrAdiacent(int nrAdiacent) {
        this.nrAdiacent = nrAdiacent;
    }

    public String getNameOfButton() {
        return nameOfButton;
    }

    public void setNameOfButton(String nameOfButton) {
        this.nameOfButton = nameOfButton;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
