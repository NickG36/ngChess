
package moveFinder;

import java.io.PrintWriter; 
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Controller {

   static boolean moveLock = false;
   static Move myNextMove = new Move(Square.A1, Square.A1, -2);  

   static boolean opponentsMovePending = false;  // Rx move and haven't processed it yet
   
   static boolean waitingForOpponent = false; // Opponent is still thinking

   Board theBoard;
   MoveFinder moveFinder;
   MovesRecord gameMoves;
   static java.util.ArrayList<Move> myMoves = new java.util.ArrayList<Move>();
   
   
   boolean iAmBlack = true;
   boolean stillInOpenings = true;
   BlackOpeningBook blackOpenings;
   WhiteOpeningBook whiteOpenings;

//   public static final String black_Move_Prefix = "Black_move:";
//   public static final String white_Move_Prefix = "White_move:";
   
   public Controller(final Board theBoard,
                     final MoveFinder moveFinder,
                     final MovesRecord gameMoves)
   {
     this.theBoard = theBoard;
     this.moveFinder = moveFinder;
     this.gameMoves = gameMoves;
     this.blackOpenings = new BlackOpeningBook(theBoard);
     this.whiteOpenings = new WhiteOpeningBook(theBoard);
   }

   
      private Move calculateMove() // throws java.lang.InterruptedException
      {
         System.out.println("In calculateMove");

         Square openingFrom = new Square();
         Square openingTo = new Square();
         boolean openingFound = false;
         
         if(stillInOpenings)
         {
            if(iAmBlack)
            {
               openingFound = blackOpenings.findMove(openingFrom, openingTo);
            }
            else
            {
               openingFound = whiteOpenings.findMove(openingFrom, openingTo);
            }
            
            if(!openingFound)
            {
              stillInOpenings = false;
System.out.println("Leaving opening book.");            
            }
         }
            
         if(openingFound)
         {
            myNextMove.set(openingFrom, openingTo);
System.out.println("Opening found. Using this move");            
         }
         else
         {
            Move penultimateMove = Move.NullMove;
            
            if(myMoves.size() > 2)
              penultimateMove = myMoves.get(myMoves.size() - 2);
              
//System.out.println("Penultimate move:" + penultimateMove);              
             
            moveFinder.calculateMove(penultimateMove, myNextMove);
         }
         
         System.out.println("Finishing calculateMove");
         return myNextMove;
      }
   
   public static boolean ParseInput(String input, int input_length, Square from, Square to, boolean blacksTurn)
   {
      int inputMarker = 0;
      final int COLOUR_SIZE = 11; // [Black/white]_move:
      
      String colour = input.substring(0, COLOUR_SIZE);
//System.out.println("colour:'" + colour + "'");
      
      boolean isBlackMove = false;
      
      if( (colour.indexOf("hite") > 0) || (colour.indexOf("HITE") > 0) )
      {
        isBlackMove = false;
      }
      else if( (colour.indexOf("lack") > 0) || (colour.indexOf("LACK") > 0) )
      {
        isBlackMove = true;
      }
      else
      {
System.out.println("Unknown colour in message: " + input + ", ignoring");
         return false;
      }

      if(blacksTurn && !isBlackMove)
      {
        System.out.println("Msg relates to our (white) move- error****");
        return false;
      }
      else if(!blacksTurn && isBlackMove)
      {
        System.out.println("Msg relates to our (black) move- error*****");
        return false;
      }
      
      String fromCol = input.substring(COLOUR_SIZE, COLOUR_SIZE+1);
      String toCol;
      
      // Rows range from 1->12 from the user's perspective, but are 
      // stored from 0->11. Store the user and idx values separately
      String fromRowUser;
      String toRowUser;
      
      if(input.substring(COLOUR_SIZE + 2, COLOUR_SIZE + 3).equals("-") )
      {
         // Two digit from square
         fromRowUser = input.substring(COLOUR_SIZE + 1, COLOUR_SIZE + 2);
         toCol = input.substring(COLOUR_SIZE + 3, COLOUR_SIZE + 4);
         inputMarker = COLOUR_SIZE + 4;
      }
      else
      {
         // Three digit from square
         fromRowUser = input.substring(COLOUR_SIZE + 1, COLOUR_SIZE + 3);
         toCol = input.substring(COLOUR_SIZE + 4, COLOUR_SIZE + 5);
         inputMarker = COLOUR_SIZE + 5;
      }
      
//      String afterMarkerP1 = input.substring(inputMarker + 1, inputMarker + 2);

      //int sqBracketIdx = input.indexOf('<');
//System.out.println("sqBracketIdx:" + sqBracketIdx);
      int dashIdx = input.indexOf('-');
//System.out.println("dashIdx:" + dashIdx);

      if(input_length > dashIdx + 3 )
      {
//System.out.println("2 digit row");
         toRowUser = input.substring(inputMarker, inputMarker + 2);
      }
      else
      {
//System.out.println("1 digit row");
         toRowUser = input.substring(inputMarker, inputMarker + 1);
      }
//System.out.println("toRow:'" + toRow + "'");

      if(!ParseCol(fromCol, fromRowUser, from) )
      {
         System.out.println("Invalid from");
      } 
      else if(!ParseCol(toCol, toRowUser, to) )
      {
         System.out.println("Invalid to");
         
//         System.out.println(ParseCol(toCol, short_to_row, to) );
      }
     return true;  
   }
                           

   public void Go(boolean IAmBlack_param)
   {

	  System.out.println("In go");
      iAmBlack = IAmBlack_param;
      
      Square opponentMoveFrom = new Square();
      Square opponentMoveTo = new Square();
      
      if(iAmBlack)
      {
        System.out.println("Playing as black");
        System.out.println("In Go: opponentsMovePending:" + opponentsMovePending);
      }
      else
      {
        System.out.println("Playing as white");
      }
       
      for(;;)
      {
    	 Move myMove = calculateMove();

    	 BoardUtils.makeMoveIfPossible(myMove.from, myMove.to, theBoard);     
         System.out.println("Posn after my move:\n" + theBoard); 
         myMoves.add(myMove);                                               
    	 
         boolean humanEnteredValidMove = false;
         while(!humanEnteredValidMove)
         {
        	 System.out.println("Enter move in format 'white_move:f4-f2':");
        	 Scanner myObj = new Scanner(System.in); // Create a Scanner object

        	 String userMove = myObj.nextLine(); // Read user input
        	 System.out.println("Move is: " + userMove);            	
        
        	 boolean isOpponentMove = ParseInput(userMove, userMove.length(),
                                                opponentMoveFrom, opponentMoveTo, theBoard.isBlackMove);
            
        	 if(!isOpponentMove)
        	 {
        		 System.out.println("***** Invalid turn - logic error");     
        	 }
              
        	 boolean validMove = 
            		BoardUtils.makeMoveIfPossible(opponentMoveFrom, 
                                          opponentMoveTo,
                                          theBoard);     
        
        	 if(!validMove)
        	 {
            	//throw new Exception("Invalid move played");
        		 System.out.println("***** Invalid move played");     
        	 }
        	 else
        	 {
        		 humanEnteredValidMove = true;
        	 }
         } // end while
         
         System.out.println("Posn after your move:\n" + theBoard); 
         if(stillInOpenings)
         {                                  
            if(iAmBlack)
            {
                blackOpenings.whiteMoveMade(opponentMoveFrom, opponentMoveTo);    
            }
            else
            {
                whiteOpenings.blackMoveMade(opponentMoveFrom, opponentMoveTo);    
            }
          }
      } // end for ever
   }   
   
   /**
    * userRow ranges from 1->11
    * Returns true if successful
    */
   static boolean ParseCol(String col, String userRow, Square ref)
   {
	   
      boolean result = true;

      if(col.equals("a") )
         ref.col = Board.COL_A;
      else if(col.equals("b") )
         ref.col = Board.COL_B;
      else if(col.equals("c") )
         ref.col = Board.COL_C;
      else if(col.equals("d") )
         ref.col = Board.COL_D;
      else if(col.equals("e") )
         ref.col = Board.COL_E;
      else if(col.equals("f") )
         ref.col = Board.COL_F;
      else if(col.equals("g") )
         ref.col = Board.COL_G;
      else if(col.equals("h") )
         ref.col = Board.COL_H;
      else if(col.equals("i") )
         ref.col = Board.COL_I;
      else if(col.equals("j") )
         ref.col = Board.COL_J;
      else if(col.equals("k") )
         ref.col = Board.COL_K;
      else if(col.equals("A") )
         ref.col = Board.COL_A;
      else if(col.equals("B") )
         ref.col = Board.COL_B;
      else if(col.equals("C") )
         ref.col = Board.COL_C;
      else if(col.equals("D") )
         ref.col = Board.COL_D;
      else if(col.equals("E") )
         ref.col = Board.COL_E;
      else if(col.equals("F") )
         ref.col = Board.COL_F;
      else if(col.equals("G") )
         ref.col = Board.COL_G;
      else if(col.equals("H") )
         ref.col = Board.COL_H;
      else if(col.equals("I") )
         ref.col = Board.COL_I;
      else if(col.equals("J") )
         ref.col = Board.COL_J;
      else if(col.equals("K") )
         ref.col = Board.COL_K;
      else
         return false;
         
      if(userRow.equals("1") )
         ref.row = Board.ROW_1;
      else if(userRow.equals("2") )
         ref.row = Board.ROW_2;
      else if(userRow.equals("3") )
         ref.row = Board.ROW_3;
      else if(userRow.equals("4") )
         ref.row = Board.ROW_4;
      else if(userRow.equals("5") )
         ref.row = Board.ROW_5;
      else if(userRow.equals("6") )
         ref.row = Board.ROW_6;
      else if(userRow.equals("7") )
         ref.row = Board.ROW_7;
      else if(userRow.equals("8") )
         ref.row = Board.ROW_8;
      else if(userRow.equals("9") )
         ref.row = Board.ROW_9;
      else if(userRow.equals("10") )
         ref.row = Board.ROW_10;
      else if(userRow.equals("11") )
         ref.row = Board.ROW_11;
      else
         return false;

      return true;   
   }
};
