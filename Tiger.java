package Piece;

import board.GameBoard;
import view.Cell;

public class Tiger extends Piece {
    public Tiger(int side) {
        super(side, "tiger");
        rank = 6;
        index = 1 - side;
    }

}
