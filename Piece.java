package Piece;

import board.GameBoard;
import view.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public  class Piece extends JLabel {
    int rank;
    String path;
    public int side; // 0 or 1
    public int index;
    public String name;

    public Piece( int side, String name) {
        this.side = side;
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPATH(String path) {
        this.path = path;
    }




    public boolean isSameSide(Piece other){
        if (this.getSide() != other.getSide()){
            return false;
        }
        else return true;
    }
    public boolean ableToCapture(Cell c, GameBoard gameBoard){
        if(c.getPiece().rank <= this.rank || gameBoard.isTrap(c.getx(), c.gety())){
            return true;
        }
        else{
            return false;
        }
    }

    public int getSide(){
        return side;
    }








}