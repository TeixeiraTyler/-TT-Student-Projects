/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Tyler
 */
public class Board 
{
    // member variable board
    private Disc[][] board;
    private int darkCount;
    private int lightCount;
    private ArrayList<Player> players;
    
    public Board()
    {
        initObjects();
    }
    
    public void initObjects()
    {
        // declare the size of the array
        board = new Disc[Constants.ROWS][Constants.COLUMNS];        
        
        // initialize each object in the array
        for(int row = 0; row < Constants.ROWS; row++)
        {
            for(int col = 0; col < Constants.COLUMNS; col++)
            {
                // calling no argument constructor for
                // class Disc
                board[row][col] = new Disc();
            }
        }
        
        // initial setup of the board is the following:
        
        // position 3,3 is white
        // position 3,4 is black
        // position 4,3 is black
        // position 4,4 is white
        // Each element is an instance of class Disc 
        board[3][3].setColor(Constants.LIGHT);
        board[3][4].setColor(Constants.DARK);
        board[4][3].setColor(Constants.DARK);
        board[4][4].setColor(Constants.LIGHT);
    }

    public void calculateScore()
    {
        darkCount = 0;
        lightCount = 0;
        
        for (int row = 0; row < Constants.ROWS; row++)
        {
            for (int col = 0; col < Constants.COLUMNS; col++)
            {
                if (board[row][col].getColor() == Constants.DARK)
                    darkCount++;
                else if (board[row][col].getColor() == Constants.LIGHT)
                    lightCount++;
            }
        }
        players.get(Constants.PLAYER_ONE).setScore(darkCount);
        players.get(Constants.PLAYER_TWO).setScore(lightCount);
    }
    
    public boolean isValidMove(int row, int col, Color color)
    {
        boolean isValid = false;
        int valid = 0;
        
        //checks every direction so that method won't short-circuit after finding first possible.
        if (checkUp(row, col, color))
            valid++;
        if (checkUpLeft(row, col, color))
            valid++;
        if (checkUpRight(row, col, color))
            valid++;
        if (checkLeft(row, col, color))
            valid++;
        if (checkRight(row, col, color))
            valid++;
        if (checkDownLeft(row, col, color))
            valid++;
        if (checkDownRight(row, col, color))
            valid++;
        if (checkDown(row, col, color))
            valid++;
        
        if (valid > 0)
        {
            isValid = true;
            board[row][col].setColor(color);
        }
        calculateScore();
        gameOver();
        
        if (gameOver())
        {
            if (players.get(0).getScore() > players.get(1).getScore())
                System.out.println("Game is Over, " + players.get(0).getName() + " wins!");
            else
                System.out.println("Game is Over, " + players.get(1).getName() + " wins!");
        }
            
        return isValid;
    }
    
    private boolean checkUp (int row, int col, Color color)
    {
        int flipSquares = 0;
        int checkRow = row - 1;
        boolean matchFound = false;
        boolean validMove = false;
        
        while (checkRow >= 0 && !matchFound)
        {
            if (board[checkRow][col].getColor() == Constants.EMPTY)
                break;
            else if (board[checkRow][col].getColor() != color)
                flipSquares++;
            else
                matchFound = true;
            checkRow--;
        }
        
        while (matchFound && flipSquares > 0)
        {
            validMove = true;
            row--;
            flipSquares--;
            board[row][col].setColor(color);
        }

        return validMove;
    }
    
    private boolean checkUpLeft (int row, int col, Color color)
    {
        int flipSquares = 0;
        int checkRow = row - 1;
        int checkCol = col - 1;
        boolean matchFound = false;
        boolean validMove = false;
        
        while (checkRow >= 0 && checkCol >= 0 && !matchFound)
        {
            if (board[checkRow][checkCol].getColor() == Constants.EMPTY)
                break;
            else if (board[checkRow][checkCol].getColor() != color)
                flipSquares++;
            else
                matchFound = true;
            checkRow--;
            checkCol--;
        }
        
        while (matchFound && flipSquares > 0)
        {
            validMove = true;
            row--;
            col--;
            flipSquares--;
            board[row][col].setColor(color);
        }

        return validMove;
    }
    
    private boolean checkUpRight (int row, int col, Color color)
    {
        int flipSquares = 0;
        int checkRow = row - 1;
        int checkCol = col + 1;
        boolean matchFound = false;
        boolean validMove = false;
        
        while (checkRow >= 0 && checkCol < 8 && !matchFound)
        {
            if (board[checkRow][checkCol].getColor() == Constants.EMPTY)
                break;
            else if (board[checkRow][checkCol].getColor() != color)
                flipSquares++;
            else
                matchFound = true;
            
            checkRow--;
            checkCol++;
        }
        
        while (matchFound && flipSquares > 0)
        {
            validMove = true;
            row--;
            col++;
            flipSquares--;
            board[row][col].setColor(color);
        }

        return validMove;
    }
    
    private boolean checkDown (int row, int col, Color color)
    {
        int flipSquares = 0;
        int checkRow = row + 1;
        boolean matchFound = false;
        boolean validMove = false;
        
        while (checkRow < 8 && !matchFound)
        {
            if (board[checkRow][col].getColor() == Constants.EMPTY)
                break;
            else if (board[checkRow][col].getColor() != color)
                flipSquares++;
            else
                matchFound = true;
            checkRow++;
        }
        
        while (matchFound && flipSquares > 0)
        {
            validMove = true;
            row++;
            flipSquares--;
            board[row][col].setColor(color);
        }

        return validMove;
    }
    
    private boolean checkDownLeft (int row, int col, Color color)
    {
        int flipSquares = 0;
        int checkRow = row + 1;
        int checkCol = col - 1;
        boolean matchFound = false;
        boolean validMove = false;
        
        while (checkRow < 8 && checkCol >= 0 && !matchFound)
        {
            if (board[checkRow][checkCol].getColor() == Constants.EMPTY)
                break;
            else if (board[checkRow][checkCol].getColor() != color)
                flipSquares++;
            else
                matchFound = true;
            checkRow++;
            checkCol--;
        }
        
        while (matchFound && flipSquares > 0)
        {
            validMove = true;
            row++;
            col--;
            flipSquares--;
            board[row][col].setColor(color);
        }

        return validMove;
    }
    
    private boolean checkDownRight (int row, int col, Color color)
    {
        int flipSquares = 0;
        int checkRow = row + 1;
        int checkCol = col + 1;
        boolean matchFound = false;
        boolean validMove = false;
        
        while (checkRow < 8 && checkCol < 8 && !matchFound)
        {
            if (board[checkRow][checkCol].getColor() == Constants.EMPTY)
                break;
            else if (board[checkRow][checkCol].getColor() != color)
                flipSquares++;
            else
                matchFound = true;
            checkRow++;
            checkCol++;
        }
        
        while (matchFound && flipSquares > 0)
        {
            validMove = true;
            row++;
            col++;
            flipSquares--;
            board[row][col].setColor(color);
        }

        return validMove;
    }
    
    private boolean checkLeft (int row, int col, Color color)
    {
        int flipSquares = 0;
        int checkCol = col - 1;
        boolean matchFound = false;
        boolean validMove = false;
        
        while (checkCol >= 0 && !matchFound)
        {
            if (board[row][checkCol].getColor() == Constants.EMPTY)
                break;
            else if (board[row][checkCol].getColor() != color)
                flipSquares++;
            else
                matchFound = true;
            checkCol--;
        }
        
        while (matchFound && flipSquares > 0)
        {
            validMove = true;
            col--;
            flipSquares--;
            board[row][col].setColor(color);
        }

        return validMove;
    }
    
    private boolean checkRight (int row, int col, Color color)
    {
        int flipSquares = 0;
        int checkCol = col + 1;
        boolean matchFound = false;
        boolean validMove = false;
        
        while (checkCol < 8 && !matchFound)
        {
            if (board[row][checkCol].getColor() == Constants.EMPTY)
                break;
            else if (board[row][checkCol].getColor() != color)
                flipSquares++;
            else
                matchFound = true;
            checkCol++;
        }
        
        while (matchFound && flipSquares > 0)
        {
            validMove = true;
            col++;
            flipSquares--;
            board[row][col].setColor(color);
        }

        return validMove;
    }
    
    public Disc[][] getBoard() 
    {
        return board;
    }

    public void setBoard(Disc[][] board) 
    {
        this.board = board;
    }
    
    public int getDarkCount()
    {
        return darkCount;
    }
    
    public void setDarkCount(int darkCount)
    {
        this.darkCount = darkCount;
    }
    
    public int getLightCount()
    {
        return lightCount;
    }
    
    public void setLightCount(int lightCount)
    {
        this.lightCount = lightCount;
    }
    
    public ArrayList<Player> getPlayers()
    {
        return players;
    }
    
    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }
    
    public boolean gameOver()
    {
        boolean end = false;
        int count = 0;
        
        for (int row = 0; row < Constants.ROWS; row++)
            for (int col = 0; col < Constants.COLUMNS; col++)
                if (board[row][col].getColor() != Constants.EMPTY)
                    count++;
        
        if (count == 64)
            end = true;
        return end;
    }
}
