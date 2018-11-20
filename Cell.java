package view;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import Piece.*;
import board.GameBoard;
import Piece.Piece;

public class Cell extends JPanel {
    public boolean ispossibledestination;
    private JLabel content;
    private Piece piece;
    int x, y;                             //is public because this is to be accessed by all the other class
    private boolean isSelected = false;
    private boolean ischeck = false;

    public Cell(int x, int y, Piece p) {
        this.x = x;
        this.y = y;
        ispossibledestination = false;

        setLayout(new BorderLayout());
        if (x == 2 && (y == 0 || y == 8))
            setBackground(Color.black);//trap
        if (x == 3 && (y == 0 || y == 8))
            setBackground(Color.orange);//den
        if (x == 4 && (y == 0 || y == 8))
            setBackground(Color.black);//trap
        if (x == 3 && (y == 1 || y == 7))
            setBackground(Color.black);//trap
        if (x >= 1 && x < 3 && y >= 3 && y < 6)
            setBackground(Color.green);
        if (x >= 4 && x < 6 && y >= 3 && y < 6)
            setBackground(Color.green);


        if (p != null)
            setPiece(p);
    }

    public void setPiece(Piece p)    //Function to inflate a cell with a piece
    {
        piece = p;
        ImageIcon img = new javax.swing.ImageIcon(this.getClass().getResource(p.getPath()));
        content = new JLabel(img);
        this.add(content);
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getx() {
        return this.x;
    }

    public int gety() {
        return this.y;
    }

    public void select()       //Function to mark a cell indicating it's selection
    {
        this.setBorder(BorderFactory.createLineBorder(Color.red, 6));
        this.isSelected = true;
    }

    public void deselect()      //Function to delselect the cell
    {
        this.setBorder(null);
        this.isSelected = false;
    }

    public void removePiece()      //Function to remove a piece from the cell
    {
        piece = null;
        this.remove(content);

    }

    public void setpossibledestination()     //Function to highlight a cell to indicate that it is a possible valid move
    {
        this.setBorder(BorderFactory.createLineBorder(Color.blue, 4));
        this.ispossibledestination = true;
    }

    public void removepossibledestination()      //Remove the cell from the list of possible moves
    {
        this.setBorder(null);
        this.ispossibledestination = false;
    }

    public boolean ispossibledestination()    //Function to check if the cell is a possible destination
    {
        return this.ispossibledestination;
    }

    public ArrayList<Cell> move(GameBoard gameBoard) {
        ArrayList<Cell> avaiPieces = new ArrayList<Cell>();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.isAvailable(i, j, gameBoard)) {
                    avaiPieces.add(gameBoard.boardstate[i][j]);
                }
            }
        }
        return avaiPieces;
    }

    public boolean isAvailable(int newXPosition, int newYPosition, GameBoard gameBoard) {
        if (this.getPiece() instanceof Rat) {
            if (!gameBoard.isAdj(this.getx(), this.gety(), newXPosition, newYPosition)) {
                return false; // If two blocks are not adjacent then the command is in invalid.
            }
            Cell other = gameBoard.boardstate[newXPosition][newYPosition];// To get the piece from the newX, newY position
            if (other.getPiece() == null) {
                return true;
            } else {
                if (this.getPiece().isSameSide(other.getPiece())) {
                    return false;
                }
                if (gameBoard.isRiver(this.getx(), this.gety()) && (!gameBoard.isRiver(newXPosition, newYPosition))
                        || gameBoard.isRiver(newXPosition, newYPosition) && (!gameBoard.isRiver(this.getx(), this.gety()))) {
                    return false;
                } else if (this.getPiece().ableToCapture(other, gameBoard)) {
                    return true;
                } else return false;
            }

        } else if (this.getPiece() instanceof Tiger||this.getPiece() instanceof Lion) {
            if (gameBoard.isRiver(newXPosition, newYPosition)){// If the new position is type RIVER then return.
                return false;
            }
            else if (!(gameBoard.isAcrossWater(this.getx(), this.gety(), newXPosition, newYPosition) )&& !gameBoard.isAdj(this.getx(), this.gety(), newXPosition, newYPosition) ){
                return false;// If the tiger neither moves to adjacent position nor jumps to the other side over the river then return.
            }
            Cell other = gameBoard.boardstate[newXPosition][newYPosition];
            if (other.getPiece() == null){// There is no piece on it and we can directly move to the new position
                return true;
            }
            else{// If there is a piece on it
                if (this.getPiece().isSameSide(other.getPiece())){// If the piece is on the same side then cannot capture it
                    return false;
                }
                if (this.getPiece().ableToCapture(other, gameBoard))
                { // If it can capture the target
                    return true;
                }
                else return false;
            }
        }

         else {
            if (!gameBoard.isAdj(this.getx(), this.gety(), newXPosition, newYPosition)) {
                return false; // If two blocks are not adjacent then the command is in invalid.
            } else if (gameBoard.isRiver(newXPosition, newYPosition)) {// If the new position is type RIVER then return.
                return false;
            }
            Cell other = gameBoard.boardstate[newXPosition][newYPosition];
            if (other.getPiece() == null) {// There is no piece on it and we can directly move to the new position
                return true;
            } else {// If there is a piece on it
                if (this.getPiece().isSameSide(other.getPiece())) {// If the piece is on the same side then cannot capture it
                    return false;
                }
                if (this.getPiece().ableToCapture(other, gameBoard)) { // If it can capture the target
                    return true;
                }
                else return false;
            }
        }

    }
}