package Piece;

public class Cat extends Piece{
    public Cat( int side){
        super( side,"");
        this.name="cat";
        rank = 2;
        index=3-side;
    }
}
