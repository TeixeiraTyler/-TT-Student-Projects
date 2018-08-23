/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import core.Constants;
import core.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tyler
 */
public class GameUi extends JPanel 
{
    private JLabel nameOne;
    private JLabel nameTwo;
    private JLabel scoreOne;
    private JLabel scoreTwo;
    private Game game;
    
    public GameUi(Game game)
    {
        this.game = game;
        initComponents();
    }
    
    private void initComponents()
    {
        this.setPreferredSize(new Dimension(400, 50));
        this.setMinimumSize(new Dimension(400, 50));
        this.setBackground(Color.LIGHT_GRAY);
        
        // temporary data until we link backend to frontend
        nameOne = new JLabel(game.getCurrentPlayer().getName());
        nameOne.setMinimumSize(new Dimension(100, 50));
        nameOne.setPreferredSize(new Dimension(100, 50));
        nameOne.setBackground(Color.LIGHT_GRAY);
        nameOne.setFont(new Font("Serif", Font.BOLD, 22));

        // temporary data until we link backend to frontend
        nameTwo = new JLabel(game.getOtherPlayer().getName());
        nameTwo.setMinimumSize(new Dimension(100, 50));
        nameTwo.setPreferredSize(new Dimension(100, 50));
        nameTwo.setBackground(Color.LIGHT_GRAY);
        nameTwo.setFont(new Font("Serif", Font.BOLD, 22));

        // temporary data until we link backend to frontend
        scoreOne = new JLabel("2");
        scoreOne.setMinimumSize(new Dimension(100, 50));
        scoreOne.setPreferredSize(new Dimension(100, 50));
        scoreOne.setBackground(Color.LIGHT_GRAY);
        scoreOne.setFont(new Font("Serif", Font.BOLD, 22));

        // temporary data until we link backend to frontend
        scoreTwo = new JLabel("2");
        scoreTwo.setMinimumSize(new Dimension(100, 50));
        scoreTwo.setPreferredSize(new Dimension(100, 50));
        scoreTwo.setBackground(Color.LIGHT_GRAY);
        scoreTwo.setFont(new Font("Serif", Font.BOLD, 22));
        
        // JPanel used FlowLayout, adds left to right on a single
        // row until run out of space then creates new row
        this.add(nameOne);
        this.add(scoreOne);
        this.add(nameTwo);
        this.add(scoreTwo);        
    }
    
    public Game getGame()
    {
        return game;
    }
    
    public void setGame(Game game)
    {
        this.game = game;
    }
    
    public JLabel getScoreOne()
    {
        return scoreOne;
    }
    
    public void setScoreOne(JLabel scoreOne)
    {
        this.scoreOne = scoreOne;
    }
    
    public JLabel getScoreTwo()
    {
        return scoreTwo;
    }
    
    public void setScoreTwo(JLabel scoreTwo)
    {
        this.scoreTwo = scoreTwo;
    }
    
    private ImageIcon imageResize(ImageIcon icon)
    {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }
}