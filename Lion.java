package Piece;

public class Lion extends Piece {
    public Lion(int side){
        super(side,"");
        this.name="lion";
        rank = 7;
        index=13-side;
    }
}
