package board;


import Piece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

import view.*;

public class GameBoard extends JPanel {
    private static final int WIDTH = 7;
    private static final int LENGTH = 9;
    public Area[][] board = new Area[WIDTH][LENGTH];
    public Piece[][] pieceBoard = new Piece[WIDTH][LENGTH];
    public Cell[][] boardstate=new Cell[WIDTH][LENGTH];
    public GameBoard(int[][] save)
    {
        for(int i=0;i<WIDTH;i++)
        {
            for(int j=0;j<LENGTH;j++)
            {
                if(save[i][j]==1) // odd number for the side1
                {
                    pieceBoard[i][j]=new Tiger(0);
                    pieceBoard[i][j].setPATH("tiger.png");
                }
                else if(save[i][j]==3) // odd number for the side1
                {
                    pieceBoard[i][j]=new Cat(0);
                    pieceBoard[i][j].setPATH("cat.png");
                }
                else if(save[i][j]==5) // odd number for the side1
                {
                    pieceBoard[i][j]=new Elephant(0);
                    pieceBoard[i][j].setPATH("elephant.png");
                }
                else if(save[i][j]==7) // odd number for the side1
                {
                    pieceBoard[i][j]=new Wolf(0);
                    pieceBoard[i][j].setPATH("icon_wolf.png");
                }
                else if(save[i][j]==9) // odd number for the side1
                {
                    pieceBoard[i][j]=new Leopard(0);
                    pieceBoard[i][j].setPATH("leopard.png");
                }
                else if(save[i][j]==11) // odd number for the side1
                {
                    pieceBoard[i][j]=new Dog(0);
                    pieceBoard[i][j].setPATH("dog.png");
                }
                else if(save[i][j]==13) // odd number for the side1
                {
                    pieceBoard[i][j]=new Lion(0);
                    pieceBoard[i][j].setPATH("lion.png");
                }
                else if(save[i][j]==15) // odd number for the side1
                {
                    pieceBoard[i][j]=new Rat(0);
                    pieceBoard[i][j].setPATH("rat.png");
                }
                if(save[i][j]==0) // odd number for the side1
                {
                    pieceBoard[i][j]=new Tiger(1);
                    pieceBoard[i][j].setPATH("tiger1.png");
                }
                else if(save[i][j]==2) // odd number for the side1
                {
                    pieceBoard[i][j]=new Cat(1);
                    pieceBoard[i][j].setPATH("cat1.png");
                }
                else if(save[i][j]==4) // odd number for the side1
                {
                    pieceBoard[i][j]=new Elephant(1);
                    pieceBoard[i][j].setPATH("elephant1.png");
                }
                else if(save[i][j]==6) // odd number for the side1
                {
                    pieceBoard[i][j]=new Wolf(1);
                    pieceBoard[i][j].setPATH("icon_wolf1.png");
                }
                else if(save[i][j]==8) // odd number for the side1
                {
                    pieceBoard[i][j]=new Leopard(1);
                    pieceBoard[i][j].setPATH("leopard1.png");
                }
                else if(save[i][j]==10) // odd number for the side1
                {
                    pieceBoard[i][j]=new Dog(1);
                    pieceBoard[i][j].setPATH("dog1.png");
                }
                else if(save[i][j]==12) // odd number for the side1
                {
                    pieceBoard[i][j]=new Lion(1);
                    pieceBoard[i][j].setPATH("lion1.png");
                }
                else if(save[i][j]==14) // odd number for the side1
                {
                    pieceBoard[i][j]=new Rat(1);
                    pieceBoard[i][j].setPATH("rat1.png");
                }
            }
        }
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(pieceBoard[i][j]!=null)
                    boardstate[i][j]=new Cell(i,j,pieceBoard[i][j]);
                else
                    boardstate[i][j]=new Cell(i,j,null);


            }
        }

        board[2][0] = Area.TRAP;
        board[3][0] = Area.DEN;// Coordinate 3,0
        board[4][0] = Area.TRAP;
        board[3][1] = Area.TRAP;
        // Player1 side
        board[2][8] = Area.TRAP;
        board[3][8] = Area.DEN;// Coordinate 3,8
        board[4][8] = Area.TRAP;
        board[3][7] = Area.TRAP;
        // Player2 side

        for (int i = 1; i < 3; i++){
            for(int j = 3; j < 6; j++) {
                board[i][j] = Area.RIVER;
                board[i + 3][j] = Area.RIVER;
            }
        }
    }



    public GameBoard(){
        for(int i=0;i<7;i++)
        {
            for(int k=0;k<9;k++)
                board[i][k]=Area.NORMAL;
        }
        pieceBoard[0][0] = new Tiger(0);
        pieceBoard[0][0].setPATH("tiger.png");
        pieceBoard[1][1] = new Cat(0);
        pieceBoard[1][1].setPATH("cat.png");
        pieceBoard[0][2] = new Elephant(0);
        pieceBoard[0][2].setPATH("elephant.png");
        pieceBoard[2][2] = new Wolf(0);
        pieceBoard[2][2].setPATH("icon_wolf.png");
        pieceBoard[4][2] = new Leopard(0);
        pieceBoard[4][2].setPATH("leopard.png");
        pieceBoard[5][1] = new Dog(0);
        pieceBoard[5][1].setPATH("dog.png");
        pieceBoard[6][0] = new Lion(0);
        pieceBoard[6][0].setPATH("lion.png");
        pieceBoard[6][2] = new Rat(0);
        pieceBoard[6][2].setPATH("rat.png");
        // Player2 side
        pieceBoard[0][8] = new Lion(1);
        pieceBoard[0][8].setPATH("lion1.png");
        pieceBoard[0][6] = new Rat(1);
        pieceBoard[0][6].setPATH("rat1.png");
        pieceBoard[2][6] = new Leopard(1);
        pieceBoard[2][6].setPATH("leopard1.png");
        pieceBoard[4][6] = new Wolf(1);
        pieceBoard[4][6].setPATH("icon_wolf1.png");
        pieceBoard[6][6] = new Elephant(1);
        pieceBoard[6][6].setPATH("elephant1.png");
        pieceBoard[1][7] = new Dog(1);
        pieceBoard[1][7].setPATH("dog1.png");
        pieceBoard[5][7] = new Cat(1);
        pieceBoard[5][7].setPATH("cat1.png");
        pieceBoard[6][8] = new Tiger(1);
        pieceBoard[6][8].setPATH("tiger1.png");
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(pieceBoard[i][j]!=null)
                    boardstate[i][j]=new Cell(i,j,pieceBoard[i][j]);
                else
                    boardstate[i][j]=new Cell(i,j,null);


            }
        }

        board[2][0] = Area.TRAP;
        board[3][0] = Area.DEN;// Coordinate 3,0
        board[4][0] = Area.TRAP;
        board[3][1] = Area.TRAP;
        // Player1 side
        board[2][8] = Area.TRAP;
        board[3][8] = Area.DEN;// Coordinate 3,8
        board[4][8] = Area.TRAP;
        board[3][7] = Area.TRAP;
        // Player2 side

        for (int i = 1; i < 3; i++){
            for(int j = 3; j < 6; j++) {
                board[i][j] = Area.RIVER;
                board[i + 3][j] = Area.RIVER;
            }
        }

        // Player1 side

    }




    public boolean isAdj(int oldX, int oldY, int newX, int newY){
        if (oldX == newX){
            if (newY == oldY + 1 || newY == oldY - 1) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (oldY == newY) {
            if (newX == oldX + 1 || newX == oldX - 1) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean isRiver(int x, int y){
        return board[x][y] == Area.RIVER;
    }

    public boolean isTrap(int x, int y){ // This method is not complete
        return board[x][y] == Area.TRAP;
    }

    public boolean isAcrossWater(int oldX, int oldY, int newX, int newY) {
        if (newX == oldX){
            if(oldY<newY) {//jump rightward
                for (int i = oldY + 1; i < newY; i++) {
                    if (board[oldX][i] != Area.RIVER || boardstate[oldX][i].getPiece() != null) {
                        return false;
                    }
                }
            }
            else{//jump leftward
                    for(int i = oldY-1;i>newY;i--){
                        if(board[oldX][i] != Area.RIVER||boardstate[oldX][i].getPiece()!=null){
                            return false;
                        }
                    }
                }
                return true;
            }
            else if (newY == oldY){
                if(oldX<newX){//jump downward
                    for(int i = oldX+1; i <newX;i++){
                        if(board[i][oldY] != Area.RIVER||boardstate[i][oldY].getPiece()!=null){
                            return false;
                        }
                    }
                }
                else{//jump upward
                    for(int i = oldX-1;i>newX;i--){
                        if(board[i][oldY] != Area.RIVER||boardstate[i][oldY].getPiece()!=null){
                            return false;
                        }
                    }
                }
                return true;
            }
            else {
                return false;
            }
        }
    }



