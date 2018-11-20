package Piece;

public class Elephant extends Piece {
    public Elephant( int side){
        super(side,"");
        this.name="elephant";
        rank = 8;
        index=5-side;
    }
}
