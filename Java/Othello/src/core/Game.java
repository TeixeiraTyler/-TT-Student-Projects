/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Tyler
 */
public class Game 
{
    // diamond brackets, angle brackets, v brackets(cuz i'm ghetto)
    private ArrayList<Player> players;
    private Board board;
    private Player currentPlayer;
    private Player otherPlayer;
    
    public Game()
    {
        initObjects();
    }
    
    private void initObjects()
    {
        board = new Board();
        createPlayers();
        board.setPlayers(players);
        currentPlayer = players.get(Constants.PLAYER_ONE);
        otherPlayer = players.get(Constants.PLAYER_TWO);
    }
    
    private void createPlayers()
    {
        players = new ArrayList<>();
        
        for(int i = 0; i < Constants.MAX_PLAYERS; i++)
        {
            String name = JOptionPane.showInputDialog(null, "Enter player's name");
            Player player = new Player();
            player.setName(name);
            
            if(i == Constants.PLAYER_ONE)
                player.setDiscColor(Constants.DARK);
            else if(i == Constants.PLAYER_TWO)
                player.setDiscColor(Constants.LIGHT);
            
            players.add(player);
        }
    }
    
    private void printPlayers()
    {
        System.out.println("The game has the following players:");
        
            // for each object in the list
            // create an instance of it called player

        // for each 
                          //for my ArrayList of Player objects
        for(Player player : players)
        {
            // now I can use the data by calling its methods
            System.out.println("Player " + player.getName() +
                    " is playing disc color " + player.getDiscColor());
        }
    }
    
    public void calculateScore()
    {
        board.calculateScore();
        players.get(Constants.PLAYER_ONE).setScore(board.getDarkCount());
        players.get(Constants.PLAYER_TWO).setScore(board.getLightCount());
    }

    public ArrayList<Player> getPlayers() 
    {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) 
    {
        this.players = players;
    }

    public Board getBoard() 
    {
        return board;
    }
    
    void setBoard(Board board) 
    {
        this.board = board;
    }
    
    public void setCurrentPlayer(Player currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
    
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    public void setOtherPlayer(Player otherPlayer)
    {
        this.otherPlayer = otherPlayer;
    }
    
    public Player getOtherPlayer()
    {
        return otherPlayer;
    }
}