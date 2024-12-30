/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.nick.ngchessapp;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.nick.movefinder.Board;
import com.nick.movefinder.Board.Piece;
import com.nick.movefinder.BoardRater;
import com.nick.movefinder.BoardUtils;
import com.nick.movefinder.Controller;
import com.nick.movefinder.MoveFinder;
import com.nick.movefinder.MovesRecord;
import com.nick.movefinder.Square;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author nick_
 */
public class BoardGUI extends javax.swing.JFrame {

    public class ButtonListener implements ActionListener
    {
        void buttonPressed(int guiRow, int col)
        {
            //System.out.println("buttonPressed: row: " + guiRow + ", col:" + col + " pressed");
            int boardRow = NUM_ROWS - guiRow - 1;
            Square asSquare = new Square(col, boardRow);
            
            if(!fromAlreadySelected)
            {
                fromAlreadySelected = true;
                fromSquare = asSquare;
                fromText.setText(asSquare.toString() );
            }
            else
            {
                System.out.println("human move: " + fromSquare + "->" + asSquare);                
                boolean validMove = controller.makeHumanMove(fromSquare, asSquare);
/*          		BoardUtils.makeMoveIfPossible(fromSquare, 
                                        asSquare,
                                        theBoard);     */
                System.out.println(theBoard);
      
                if(!validMove)    
                {
                    System.out.println("Invalid move from:" + fromSquare + " to " + asSquare);
                }
                else
                {
                    BoardGUI.updateBoard();
                    
                    // Computer's move
                    controller.makeComputerMove();
                    System.out.println(theBoard);
                    BoardGUI.updateBoard();
                }
                fromAlreadySelected = false;
            }
        }
        
        public ButtonListener(int guiRow, int col)
        {
            this.guiRow = guiRow;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonPressed(this.guiRow, this.col);
        }
        private int guiRow;
        private int col;
        
        private static boolean fromAlreadySelected = false;
        private static Square fromSquare;
    };
    
    /**
     * Creates new form BoardGUI
     */
    public BoardGUI(/*Board theBoard*/) {
       this.theBoard = new Board();
       BoardRater boardRater = new BoardRater(); // New bit; ctor has side-effects
       MovesRecord gameMoves = new MovesRecord(true);
       MoveFinder moveFinder = new MoveFinder(theBoard);

       System.out.println("Start:" + theBoard);
       controller = new Controller(theBoard,
                                              moveFinder,
                                              gameMoves);
        
        this.theBoard = theBoard;
        initComponents();
        final java.awt.Color BLACK_BACKGROUND = new java.awt.Color(0, 0, 0);
        
        theButtons = new ArrayList<>();
        
        jPanel1.setLayout(new GridLayout(NUM_ROWS + 1, NUM_COLS + 1)); // Set GridLayout Manager
        
        final int SQUARE_SIZE = 60;
        for(int rowsIdx = 0; rowsIdx < NUM_ROWS + 1; ++rowsIdx)
        {
            ArrayList<javax.swing.JButton> theRow = new ArrayList<>();
            
            for(int colsIdx = 0; colsIdx < NUM_COLS + 1; ++colsIdx)
            {
                javax.swing.JButton newButton = new javax.swing.JButton();
                newButton.setBackground(new java.awt.Color(255, 255, 255));
                newButton.setForeground(Color.black);
                Dimension preferredSize = new Dimension(SQUARE_SIZE, SQUARE_SIZE);
                newButton.setPreferredSize(preferredSize);
                newButton.setName("R" + rowsIdx + "C" + colsIdx);
                theRow.add(newButton);
                jPanel1.add(newButton);

                if(isBlackSquare(rowsIdx, colsIdx))
                {
                    newButton.setBackground(BLACK_BACKGROUND);
                }
            }
            
            theButtons.add(theRow);
        }
        
        // Set the grid label squares:
        for(int colIdx = 1; colIdx < NUM_COLS + 1; ++colIdx)
        {
            JButton currButton = theButtons.get(NUM_ROWS).get(colIdx);
            currButton.setBackground(Color.LIGHT_GRAY);
            currButton.setEnabled(false);
        }
        
        // Set the grid label squares:
        for(int rowIdx = 0; rowIdx < NUM_ROWS + 1; ++rowIdx)
        {
            JButton currButton = theButtons.get(rowIdx).get(0);
            currButton.setText( (NUM_ROWS - rowIdx) + "");
            currButton.setBackground(Color.LIGHT_GRAY);
            currButton.setEnabled(false);
        }
        
        theButtons.get(NUM_ROWS).get(0).setText(" ");
        theButtons.get(NUM_ROWS).get(1).setText("a");
        theButtons.get(NUM_ROWS).get(2).setText("b");
        theButtons.get(NUM_ROWS).get(3).setText("c");
        theButtons.get(NUM_ROWS).get(4).setText("d");
        theButtons.get(NUM_ROWS).get(5).setText("e");
        theButtons.get(NUM_ROWS).get(6).setText("f");
        theButtons.get(NUM_ROWS).get(7).setText("g");
        theButtons.get(NUM_ROWS).get(8).setText("h");
        theButtons.get(NUM_ROWS).get(9).setText("i");
        theButtons.get(NUM_ROWS).get(10).setText("j");
        theButtons.get(NUM_ROWS).get(11).setText("k");
        
        setupListeners();
        
        updateBoard();
    }
    
    private void setupListeners()
    {
        // Indices here are wrt positions in theBoard button matrix
        ButtonListener listener00 = new ButtonListener(0,0);
        ButtonListener listener01 = new ButtonListener(0,1);
        ButtonListener listener02 = new ButtonListener(0,2);
        ButtonListener listener03 = new ButtonListener(0,3);
        ButtonListener listener04 = new ButtonListener(0,4);
        ButtonListener listener05 = new ButtonListener(0,5);
        ButtonListener listener06 = new ButtonListener(0,6);
        ButtonListener listener07 = new ButtonListener(0,7);
        ButtonListener listener08 = new ButtonListener(0,8);
        ButtonListener listener09 = new ButtonListener(0,9);
        ButtonListener listener010 = new ButtonListener(0, 10);
        
        ButtonListener listener10 = new ButtonListener(1,0);
        ButtonListener listener11 = new ButtonListener(1,1);
        ButtonListener listener12 = new ButtonListener(1,2);
        ButtonListener listener13 = new ButtonListener(1,3);
        ButtonListener listener14 = new ButtonListener(1,4);
        ButtonListener listener15 = new ButtonListener(1,5);
        ButtonListener listener16 = new ButtonListener(1,6);
        ButtonListener listener17 = new ButtonListener(1,7);
        ButtonListener listener18 = new ButtonListener(1,8);
        ButtonListener listener19 = new ButtonListener(1,9);
        ButtonListener listener110 = new ButtonListener(1, 10);

        ButtonListener listener20 = new ButtonListener(2,0);
        ButtonListener listener21 = new ButtonListener(2,1);
        ButtonListener listener22 = new ButtonListener(2,2);
        ButtonListener listener23 = new ButtonListener(2,3);
        ButtonListener listener24 = new ButtonListener(2,4);
        ButtonListener listener25 = new ButtonListener(2,5);
        ButtonListener listener26 = new ButtonListener(2,6);
        ButtonListener listener27 = new ButtonListener(2,7);
        ButtonListener listener28 = new ButtonListener(2,8);
        ButtonListener listener29 = new ButtonListener(2,9);
        ButtonListener listener210 = new ButtonListener(2, 10);

        ButtonListener listener30 = new ButtonListener(3,0);
        ButtonListener listener31 = new ButtonListener(3,1);
        ButtonListener listener32 = new ButtonListener(3,2);
        ButtonListener listener33 = new ButtonListener(3,3);
        ButtonListener listener34 = new ButtonListener(3,4);
        ButtonListener listener35 = new ButtonListener(3,5);
        ButtonListener listener36 = new ButtonListener(3,6);
        ButtonListener listener37 = new ButtonListener(3,7);
        ButtonListener listener38 = new ButtonListener(3,8);
        ButtonListener listener39 = new ButtonListener(3,9);
        ButtonListener listener310 = new ButtonListener(3, 10);

        ButtonListener listener40 = new ButtonListener(4,0);
        ButtonListener listener41 = new ButtonListener(4,1);
        ButtonListener listener42 = new ButtonListener(4,2);
        ButtonListener listener43 = new ButtonListener(4,3);
        ButtonListener listener44 = new ButtonListener(4,4);
        ButtonListener listener45 = new ButtonListener(4,5);
        ButtonListener listener46 = new ButtonListener(4,6);
        ButtonListener listener47 = new ButtonListener(4,7);
        ButtonListener listener48 = new ButtonListener(4,8);
        ButtonListener listener49 = new ButtonListener(4,9);
        ButtonListener listener410 = new ButtonListener(4, 10);

        ButtonListener listener50 = new ButtonListener(5,0);
        ButtonListener listener51 = new ButtonListener(5,1);
        ButtonListener listener52 = new ButtonListener(5,2);
        ButtonListener listener53 = new ButtonListener(5,3);
        ButtonListener listener54 = new ButtonListener(5,4);
        ButtonListener listener55 = new ButtonListener(5,5);
        ButtonListener listener56 = new ButtonListener(5,6);
        ButtonListener listener57 = new ButtonListener(5,7);
        ButtonListener listener58 = new ButtonListener(5,8);
        ButtonListener listener59 = new ButtonListener(5,9);
        ButtonListener listener510 = new ButtonListener(5, 10);

        ButtonListener listener60 = new ButtonListener(6,0);
        ButtonListener listener61 = new ButtonListener(6,1);
        ButtonListener listener62 = new ButtonListener(6,2);
        ButtonListener listener63 = new ButtonListener(6,3);
        ButtonListener listener64 = new ButtonListener(6,4);
        ButtonListener listener65 = new ButtonListener(6,5);
        ButtonListener listener66 = new ButtonListener(6,6);
        ButtonListener listener67 = new ButtonListener(6,7);
        ButtonListener listener68 = new ButtonListener(6,8);
        ButtonListener listener69 = new ButtonListener(6,9);
        ButtonListener listener610 = new ButtonListener(6, 10);

        ButtonListener listener70 = new ButtonListener(7,0);
        ButtonListener listener71 = new ButtonListener(7,1);
        ButtonListener listener72 = new ButtonListener(7,2);
        ButtonListener listener73 = new ButtonListener(7,3);
        ButtonListener listener74 = new ButtonListener(7,4);
        ButtonListener listener75 = new ButtonListener(7,5);
        ButtonListener listener76 = new ButtonListener(7,6);
        ButtonListener listener77 = new ButtonListener(7,7);
        ButtonListener listener78 = new ButtonListener(7,8);
        ButtonListener listener79 = new ButtonListener(7,9);
        ButtonListener listener710 = new ButtonListener(7, 10);

        ButtonListener listener80 = new ButtonListener(8,0);
        ButtonListener listener81 = new ButtonListener(8,1);
        ButtonListener listener82 = new ButtonListener(8,2);
        ButtonListener listener83 = new ButtonListener(8,3);
        ButtonListener listener84 = new ButtonListener(8,4);
        ButtonListener listener85 = new ButtonListener(8,5);
        ButtonListener listener86 = new ButtonListener(8,6);
        ButtonListener listener87 = new ButtonListener(8,7);
        ButtonListener listener88 = new ButtonListener(8,8);
        ButtonListener listener89 = new ButtonListener(8,9);
        ButtonListener listener810 = new ButtonListener(8, 10);

        ButtonListener listener90 = new ButtonListener(9,0);
        ButtonListener listener91 = new ButtonListener(9,1);
        ButtonListener listener92 = new ButtonListener(9,2);
        ButtonListener listener93 = new ButtonListener(9,3);
        ButtonListener listener94 = new ButtonListener(9,4);
        ButtonListener listener95 = new ButtonListener(9,5);
        ButtonListener listener96 = new ButtonListener(9,6);
        ButtonListener listener97 = new ButtonListener(9,7);
        ButtonListener listener98 = new ButtonListener(9,8);
        ButtonListener listener99 = new ButtonListener(9,9);
        ButtonListener listener910 = new ButtonListener(9, 10);

        ButtonListener listener100 = new ButtonListener(10,0);
        ButtonListener listener101 = new ButtonListener(10,1);
        ButtonListener listener102 = new ButtonListener(10,2);
        ButtonListener listener103 = new ButtonListener(10,3);
        ButtonListener listener104 = new ButtonListener(10,4);
        ButtonListener listener105 = new ButtonListener(10,5);
        ButtonListener listener106 = new ButtonListener(10,6);
        ButtonListener listener107 = new ButtonListener(10,7);
        ButtonListener listener108 = new ButtonListener(10,8);
        ButtonListener listener109 = new ButtonListener(10,9);
        ButtonListener listener1010 = new ButtonListener(10, 10);

        theButtons.get(0).get(1).addActionListener(listener00);
        theButtons.get(0).get(2).addActionListener(listener01);
        theButtons.get(0).get(3).addActionListener(listener02);
        theButtons.get(0).get(4).addActionListener(listener03);
        theButtons.get(0).get(5).addActionListener(listener04);
        theButtons.get(0).get(6).addActionListener(listener05);
        theButtons.get(0).get(7).addActionListener(listener06);
        theButtons.get(0).get(8).addActionListener(listener07);
        theButtons.get(0).get(9).addActionListener(listener08);
        theButtons.get(0).get(10).addActionListener(listener09);
        theButtons.get(0).get(11).addActionListener(listener010);
        
        theButtons.get(1).get(1).addActionListener(listener10);
        theButtons.get(1).get(2).addActionListener(listener11);
        theButtons.get(1).get(3).addActionListener(listener12);
        theButtons.get(1).get(4).addActionListener(listener13);
        theButtons.get(1).get(5).addActionListener(listener14);
        theButtons.get(1).get(6).addActionListener(listener15);
        theButtons.get(1).get(7).addActionListener(listener16);
        theButtons.get(1).get(8).addActionListener(listener17);
        theButtons.get(1).get(9).addActionListener(listener18);
        theButtons.get(1).get(10).addActionListener(listener19);
        theButtons.get(1).get(11).addActionListener(listener110);

        theButtons.get(2).get(1).addActionListener(listener20);
        theButtons.get(2).get(2).addActionListener(listener21);
        theButtons.get(2).get(3).addActionListener(listener22);
        theButtons.get(2).get(4).addActionListener(listener23);
        theButtons.get(2).get(5).addActionListener(listener24);
        theButtons.get(2).get(6).addActionListener(listener25);
        theButtons.get(2).get(7).addActionListener(listener26);
        theButtons.get(2).get(8).addActionListener(listener27);
        theButtons.get(2).get(9).addActionListener(listener28);
        theButtons.get(2).get(10).addActionListener(listener29);
        theButtons.get(2).get(11).addActionListener(listener210);

        theButtons.get(3).get(1).addActionListener(listener30);
        theButtons.get(3).get(2).addActionListener(listener31);
        theButtons.get(3).get(3).addActionListener(listener32);
        theButtons.get(3).get(4).addActionListener(listener33);
        theButtons.get(3).get(5).addActionListener(listener34);
        theButtons.get(3).get(6).addActionListener(listener35);
        theButtons.get(3).get(7).addActionListener(listener36);
        theButtons.get(3).get(8).addActionListener(listener37);
        theButtons.get(3).get(9).addActionListener(listener38);
        theButtons.get(3).get(10).addActionListener(listener39);
        theButtons.get(3).get(11).addActionListener(listener310);

        theButtons.get(4).get(1).addActionListener(listener40);
        theButtons.get(4).get(2).addActionListener(listener41);
        theButtons.get(4).get(3).addActionListener(listener42);
        theButtons.get(4).get(4).addActionListener(listener43);
        theButtons.get(4).get(5).addActionListener(listener44);
        theButtons.get(4).get(6).addActionListener(listener45);
        theButtons.get(4).get(7).addActionListener(listener46);
        theButtons.get(4).get(8).addActionListener(listener47);
        theButtons.get(4).get(9).addActionListener(listener48);
        theButtons.get(4).get(10).addActionListener(listener49);
        theButtons.get(4).get(11).addActionListener(listener410);

        theButtons.get(5).get(1).addActionListener(listener50);
        theButtons.get(5).get(2).addActionListener(listener51);
        theButtons.get(5).get(3).addActionListener(listener52);
        theButtons.get(5).get(4).addActionListener(listener53);
        theButtons.get(5).get(5).addActionListener(listener54);
        theButtons.get(5).get(6).addActionListener(listener55);
        theButtons.get(5).get(7).addActionListener(listener56);
        theButtons.get(5).get(8).addActionListener(listener57);
        theButtons.get(5).get(9).addActionListener(listener58);
        theButtons.get(5).get(10).addActionListener(listener59);
        theButtons.get(5).get(11).addActionListener(listener510);

        theButtons.get(6).get(1).addActionListener(listener60);
        theButtons.get(6).get(2).addActionListener(listener61);
        theButtons.get(6).get(3).addActionListener(listener62);
        theButtons.get(6).get(4).addActionListener(listener63);
        theButtons.get(6).get(5).addActionListener(listener64);
        theButtons.get(6).get(6).addActionListener(listener65);
        theButtons.get(6).get(7).addActionListener(listener66);
        theButtons.get(6).get(8).addActionListener(listener67);
        theButtons.get(6).get(9).addActionListener(listener68);
        theButtons.get(6).get(10).addActionListener(listener69);
        theButtons.get(6).get(11).addActionListener(listener610);

        theButtons.get(7).get(1).addActionListener(listener70);
        theButtons.get(7).get(2).addActionListener(listener71);
        theButtons.get(7).get(3).addActionListener(listener72);
        theButtons.get(7).get(4).addActionListener(listener73);
        theButtons.get(7).get(5).addActionListener(listener74);
        theButtons.get(7).get(6).addActionListener(listener75);
        theButtons.get(7).get(7).addActionListener(listener76);
        theButtons.get(7).get(8).addActionListener(listener77);
        theButtons.get(7).get(9).addActionListener(listener78);
        theButtons.get(7).get(10).addActionListener(listener79);
        theButtons.get(7).get(11).addActionListener(listener710);

        theButtons.get(8).get(1).addActionListener(listener80);
        theButtons.get(8).get(2).addActionListener(listener81);
        theButtons.get(8).get(3).addActionListener(listener82);
        theButtons.get(8).get(4).addActionListener(listener83);
        theButtons.get(8).get(5).addActionListener(listener84);
        theButtons.get(8).get(6).addActionListener(listener85);
        theButtons.get(8).get(7).addActionListener(listener86);
        theButtons.get(8).get(8).addActionListener(listener87);
        theButtons.get(8).get(9).addActionListener(listener88);
        theButtons.get(8).get(10).addActionListener(listener89);
        theButtons.get(8).get(11).addActionListener(listener810);

        theButtons.get(9).get(1).addActionListener(listener90);
        theButtons.get(9).get(2).addActionListener(listener91);
        theButtons.get(9).get(3).addActionListener(listener92);
        theButtons.get(9).get(4).addActionListener(listener93);
        theButtons.get(9).get(5).addActionListener(listener94);
        theButtons.get(9).get(6).addActionListener(listener95);
        theButtons.get(9).get(7).addActionListener(listener96);
        theButtons.get(9).get(8).addActionListener(listener97);
        theButtons.get(9).get(9).addActionListener(listener98);
        theButtons.get(9).get(10).addActionListener(listener99);
        theButtons.get(9).get(11).addActionListener(listener910);

        theButtons.get(10).get(1).addActionListener(listener100);
        theButtons.get(10).get(2).addActionListener(listener101);
        theButtons.get(10).get(3).addActionListener(listener102);
        theButtons.get(10).get(4).addActionListener(listener103);
        theButtons.get(10).get(5).addActionListener(listener104);
        theButtons.get(10).get(6).addActionListener(listener105);
        theButtons.get(10).get(7).addActionListener(listener106);
        theButtons.get(10).get(8).addActionListener(listener107);
        theButtons.get(10).get(9).addActionListener(listener108);
        theButtons.get(10).get(10).addActionListener(listener109);
        theButtons.get(10).get(11).addActionListener(listener1010);
    }
    /**
     * Update the board GUI dependent on the state of the Board object
     */
    public static void updateBoard()
    {
        for(int guiRowIdx = 0; guiRowIdx < NUM_ROWS; ++guiRowIdx)
        {
            int boardRowIdx = NUM_ROWS - guiRowIdx - 1;
            
            for(int guiColsIdx = 1; guiColsIdx < NUM_COLS + 1; ++guiColsIdx)
            {
                final int boardColsIdx = guiColsIdx - 1;
                javax.swing.JButton currButton = theButtons.get(guiRowIdx).get(guiColsIdx);
                Piece currPiece = theBoard.getPiece(boardColsIdx, boardRowIdx);
                
                String symbol = "";
                if(null != currPiece)
                    
                switch (currPiece) {
                    case BLACK -> {
                        symbol = "B";
                    }
                    case WHITE -> {
                        symbol = "W";
                    }
                    case KING -> symbol = "K";
                    default -> {
                      }
                }
            
                currButton.setText(symbol);

                boolean isCorner = isBlackSquare(guiRowIdx, guiColsIdx);
                if(isCorner)
                {
                    currButton.setForeground(Color.white);
                }
                else
                {
                    currButton.setForeground(Color.black);
                }
            }
        }
    }

    static boolean isBlackSquare(int guiRowIdx, int colIdx)
    {
        boolean result = false;
        
        if( (guiRowIdx == 0) && ((colIdx == 1) || (colIdx == NUM_COLS)) )
        {
            result = true;
        }
        else if( (guiRowIdx == NUM_COLS -1) && ((colIdx == 1) || (colIdx == NUM_COLS)) )
        {
            result = true;
        }
        else if( (guiRowIdx == MIDDLE_IDX -1 ) && (colIdx == MIDDLE_IDX )  )
        {
            result = true;
        }
        return result;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        fromText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1095, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 151, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.setText("My Text here");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jToggleButton1.setText("jToggleButton1");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        fromText.setText("from");

        jLabel1.setText("fromLbl");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(249, 249, 249)
                                .addComponent(jToggleButton1)
                                .addGap(98, 98, 98)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(fromText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(72, 72, 72))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton1))
                .addGap(63, 63, 63)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(835, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fromText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        System.out.println("In BoardGUI main");
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BoardGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BoardGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BoardGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BoardGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
/*
        /* Create and display the form */
/*        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BoardGUI().setVisible(true);
            }
        });
        */
    }

    static final int NUM_ROWS = Board.NUM_ROWS;
    static final int NUM_COLS = Board.NUM_COLS;
    static final int MIDDLE_IDX = 6;    
    static ArrayList<ArrayList<javax.swing.JButton>> theButtons;
    static Board theBoard;
    static Controller controller;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField fromText;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
