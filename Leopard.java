package Piece;

public class Leopard extends Piece {
    public Leopard( int side){
        super( side,"");
        this.name="leopard";
        rank = 5;
        index=9-side;
    }
}
