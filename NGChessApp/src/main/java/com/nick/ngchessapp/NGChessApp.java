/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nick.ngchessapp;

import com.nick.movefinder.Board;
import com.nick.movefinder.BoardRater;
import com.nick.movefinder.Controller;
import com.nick.movefinder.MoveFinder;
import com.nick.movefinder.MovesRecord;

/**
 *
 * @author nick_
 */
public class NGChessApp {
interface ButtonPressedInterface{
    void registerWhichButton();
    //jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    //}                                        

}

    public static void buttonPressed(int row, int col)
    {
        
    }
    
   /* public static void takesLambda((void)fnName(int, int))
    {
        fnName(1, 2);
    }*/
    public static void main(String[] args) {
        System.out.println("In NG Chess App main");
    
        ButtonPressedInterface uses00 = () ->{buttonPressed(0,0);};
        ButtonPressedInterface uses01 = () ->{buttonPressed(0,1);};
        ButtonPressedInterface uses02 = () ->{buttonPressed(0,2);};
        
        Board theBoard = new Board();
        BoardRater boardRater = new BoardRater(); // New bit; ctor has side-effects
        MovesRecord gameMoves = new MovesRecord(true);
        MoveFinder moveFinder = new MoveFinder(theBoard);

        System.out.println("Start:" + theBoard);
        Controller controller = new Controller(theBoard,
                                               moveFinder,
                                               gameMoves);
        
        javax.swing.JFrame theFrame = new BoardGUI(theBoard);
        theFrame.setVisible(true);
    }
}
