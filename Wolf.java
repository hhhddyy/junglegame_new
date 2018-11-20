package Piece;

public class Wolf extends Piece {
    public Wolf( int side){
        super(side,"wolf");
        rank = 4;
        index=7-side;
    }
}
