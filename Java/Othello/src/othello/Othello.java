/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import core.Game;
import userInterface.OthelloUi;

/**
 *
 * @author Tyler
 */
//main class.
public class Othello 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Game game = new Game();
        // when the UI is developed we will pass object game
        // to the main UI class
        OthelloUi ui = new OthelloUi(game);
    }
    
}
