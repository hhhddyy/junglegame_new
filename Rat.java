package Piece;
import board.GameBoard;
import view.Cell;

import javax.swing.*;
import java.util.ArrayList;

public class Rat extends Piece {
    public Rat(int side) {
        super(side, "rat");
        rank = 1;
        index = 15 - side;
    }


    @Override
    public boolean ableToCapture(Cell other, GameBoard gameBoard) {
        if (other.getPiece().rank <= this.rank) {
            return true;
        } else if (gameBoard.isTrap(other.getx(), other.gety())) {
            return true;
        } else if (other.getPiece().rank == 8) {
            return true;
        } else {
            return false;
        }
    }
}


