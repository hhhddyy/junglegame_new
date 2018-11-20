package Piece;

public class Dog extends Piece {
    public Dog(int side){
        super( side,"");
        this.name="dog";
        rank = 3;
        index=11-side;
    }
}
