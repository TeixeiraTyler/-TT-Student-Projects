/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import core.Constants;
import core.Disc;
import core.Game;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Tyler
 */
public class BoardUi extends JPanel
{
    private JButton[][] board;
    private BoardListener listener;
    private Game game;
    private GameUi gameUi;
    
    public BoardUi(Game game, GameUi gameUi)
    {
        this.game = game;
        this.gameUi = gameUi;
        initComponents();
        listener.updateUi();
    }
    
    private void initComponents()
    {
        this.setPreferredSize(new Dimension(400, 400));
        this.setMinimumSize(new Dimension(400, 400));
        // FlowLayout isn't going to work here, explicitly 
        // set to GridLayout
        this.setLayout(new GridLayout(Constants.ROWS, Constants.COLUMNS));
        
        board = new JButton[Constants.ROWS][Constants.COLUMNS];
        listener = new BoardListener();
        
        for(int row = 0; row < Constants.ROWS; row++)
        {
            for(int col = 0; col < Constants.COLUMNS; col++)
            {
                board[row][col] = new JButton();
                board[row][col].putClientProperty("row", row);
                board[row][col].putClientProperty("col", col);
                board[row][col].putClientProperty("color", Constants.EMPTY);
                board[row][col].setBackground(Color.GREEN);
                // registering action listener
                board[row][col].addActionListener(listener);
                
                this.add(board[row][col]);
            }
        }    
    }

    private class BoardListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            if(ae.getSource() instanceof JButton)
            {
                JButton button = (JButton)ae.getSource();
                int row = (int)button.getClientProperty("row");
                int col = (int)button.getClientProperty("col");
                Color color = (Color)button.getClientProperty("color");
              
                if (isValidMove(row, col, game.getCurrentPlayer().getDiscColor()))
                {
                    updateUi();
                    changePlayer();
                }
                else
                    JOptionPane.showMessageDialog(button, "Move is not valid");
                
                // add logic here later, for now just set a disc color
//                if(color == null)
//                {
//                    ImageIcon disc = new ImageIcon( getClass().getResource("../images/DARK.png"));
//                    disc = imageResize(disc);
//                    board[row][col].setIcon(disc);
//                }
            }
        }
        
        private boolean isValidMove(int row, int col, Color color)
        {
            boolean valid = false;
            
            if (color == Constants.EMPTY)
                valid = false;
            else if (game.getBoard().isValidMove(row, col, color))
                valid = true;
            return valid;
        }
        
        private void updateUi()
        {
            Disc[][] discs = game.getBoard().getBoard();
            ImageIcon disc = null;
            
            for (int row = 0; row < Constants.ROWS; row++)
            {
                for (int col = 0; col < Constants.COLUMNS; col++)
                {
                    if (discs[row][col].getColor() == Constants.DARK)
                    {
                        disc = new ImageIcon(getClass().getResource("../images/DARK.png"));
                        disc = imageResize(disc);
                        board[row][col].setIcon(disc);
                    }
                    else if (discs[row][col].getColor() == Constants.LIGHT)
                    {
                        disc = new ImageIcon(getClass().getResource("../images/LIGHT.png"));
                        disc = imageResize(disc);
                        board[row][col].setIcon(disc);
                    }
                }
                //changes score by pulling from Player.
                gameUi.getScoreOne().setText(String.valueOf(game.getPlayers().get(Constants.PLAYER_ONE).getScore()));
                gameUi.getScoreTwo().setText(String.valueOf(game.getPlayers().get(Constants.PLAYER_TWO).getScore()));
            }
        }
        
        //changes player from current to other.
        public void changePlayer()
        {
            if (game.getPlayers().get(0) == game.getCurrentPlayer())
                game.setCurrentPlayer(game.getPlayers().get(1));
            else if (game.getPlayers().get(1) == game.getCurrentPlayer())
                game.setCurrentPlayer(game.getPlayers().get(0));
        }
        
        private ImageIcon imageResize(ImageIcon icon)
        {
            Image image = icon.getImage();
            Image newImage = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImage);
            return icon;
        }
    }
}
