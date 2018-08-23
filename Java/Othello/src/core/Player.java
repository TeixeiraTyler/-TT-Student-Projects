/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.Color;

/**
 *
 * @author Tyler
 */
public class Player 
{
    // a static member variable means there is only one of these
    // regardless of how many times I create an object/instance
    private String name;
    private Color discColor;
    private int score;

    public String getName() 
    {
        return name;
    }

    public void setName(String inName) 
    {
        // data validation to make sure everything is okay before
        // using the data
        
        name = inName;
    }

    public Color getDiscColor() 
    {
        return discColor;
    }

    public void setDiscColor(Color discColor) 
    {
        this.discColor = discColor;
    }
    
    public void setScore(int score)
    {
        this.score = score;
    }
    
    public int getScore()
    {
        return score;
    }
}
