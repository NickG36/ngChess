
package moveFinder;

import java.io.PrintWriter; 
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Controller {
   static long THINKING_PERIOD = 9000; // in ms
//   static long THINKING_PERIOD = 9200; // in ms
   static long WAIT_PERIOD = 50; // in ms
   WaitingThread waitingThread = new WaitingThread();
   ProcessingThread processingThread = new ProcessingThread();

   static boolean moveLock = false;
   static Move myNextMove = new Move(Square.A1, Square.A1, -2);  
   
   public static boolean timeUp = false;
   static boolean startTimerNow = false;
   static boolean opponentsMovePending = false;  // Rx move and haven't processed it yet
   
   static boolean waitingForOpponent = false; // Opponent is still thinking

   Board theBoard;
   MoveFinder moveFinder;
   MovesRecord gameMoves;
   static java.util.ArrayList<Move> myMoves = new java.util.ArrayList<Move>();
   
   
   boolean iAmBlack = true;
   boolean stillInOpenings = true;
   //boolean stillInOpenings = false; // TO DO: remove
   BlackOpeningBook blackOpenings;
   WhiteOpeningBook whiteOpenings;
   static DatagramSocket sending_dsocket;
   static DatagramSocket receiving_dsocket;
   static int TxPortNumber;
   static InetAddress board_addr;
   public static final String black_Move_Prefix = "Black_move:";
   public static final String white_Move_Prefix = "White_move:";
   
   public Controller(final Board theBoard,
                     final MoveFinder moveFinder,
                     final MovesRecord gameMoves,
                     final DatagramSocket sending_dsocket, final DatagramSocket receiving_dsocket,
                     final InetAddress board_addr,
                     final int TxPortNumber)
   {
     this.theBoard = theBoard;
     this.moveFinder = moveFinder;
     this.gameMoves = gameMoves;
     this.blackOpenings = new BlackOpeningBook(theBoard);
     this.whiteOpenings = new WhiteOpeningBook(theBoard);
     this.sending_dsocket = sending_dsocket;
     this.receiving_dsocket = receiving_dsocket;
     this.board_addr = board_addr;
     this.TxPortNumber = TxPortNumber;
   }


   /*
    ** Waits for lock on move, then sets value
    */
   static public void setMove(Move newMove)
   {
      try
      {
         while(moveLock)
         {
           Thread.sleep(WAIT_PERIOD, 0);
           System.out.println("Finishing calculateMove");
         }
     myNextMove.set(newMove);
      } 
      catch(java.lang.InterruptedException exc)
      {
        System.out.println("Exception caught in sleep call");
      }
   }

   private class WaitingThread extends java.lang.Thread
   {
      public void run()
      {
         System.out.println("In WaitingThread::run");

         try
         {
            for(;;)
            {
               while(!startTimerNow)
               {
                  sleep(WAIT_PERIOD);
               }

               startTimerNow = false;

               sleep(THINKING_PERIOD);
               timeUp = true;

               System.out.println("End timer");
               
               final Move myMove = new Move(getMove() );
               System.out.println("In waiting thread: move is " + myMove);
               
               // Send move to board server:
               // Format message:
               String moveMsg;
                  
/*               if(theBoard.isBlackMove)
               {
                  moveMsg = Controller.black_Move_Prefix;   
               }    
               else
               {
                  moveMsg = Controller.white_Move_Prefix;   
               }    
*/
               moveMsg = myMove.toStringShort() + "<EOF>";
                  
//                  
               byte[] buf = new byte[1000];
               buf = moveMsg.getBytes();
               System.out.println("Sending message:" + moveMsg);
               DatagramPacket out = new DatagramPacket(buf, buf.length, board_addr, TxPortNumber);
                  
               try
               {
                  sending_dsocket.send(out);
               }
               catch(IOException exc)
               {
                  System.out.println("Error raised sending msg:" + exc);  
               }
//
                  waitingForOpponent = true;
                  
                  // Make the move on our board too
                  BoardUtils.makeMoveIfPossible(myMove.from, myMove.to, theBoard);     
                  System.out.println("Posn after my move:\n" + theBoard); 
                  myMoves.add(myMove);                                               
            }
         }
         catch(java.lang.InterruptedException exc)
         {
             System.out.println("Exception caught!");
         } 
      }

      /*
       ** Waits for lock on move, then returns value
       */
      public Move getMove()
      {
         try
         {
            while(moveLock)
            {
               sleep(WAIT_PERIOD);
System.out.println("Waiting for count lock");
            }
         }
         catch(java.lang.InterruptedException exc)
         { 
System.out.println("Exception caught in sleep fn");
         }
         return myNextMove;
     }
     
     
   }

   private class ProcessingThread extends java.lang.Thread
   {
      public void run()
      {
   
         try
         {
            for(;;)
            {
               while(!opponentsMovePending)
               {
                  sleep(WAIT_PERIOD);
               }
//System.out.println("Starting thinking");
               calculateMove();
            }
        }
        catch(java.lang.InterruptedException exc)
        {
System.out.println("Exception caught");

        }

      }

      private void calculateMove() throws java.lang.InterruptedException
      {
         opponentsMovePending = false;
         
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
//            myNextMove 
            Move penultimateMove = Move.NullMove;
            
            if(myMoves.size() > 2)
              penultimateMove = myMoves.get(myMoves.size() - 2);
              
//System.out.println("Penultimate move:" + penultimateMove);              
             
            moveFinder.calculateMove(penultimateMove);
         }
         
         System.out.println("Finishing calculateMove");
      }
      
   }
   
   private void opponentsMoveMade()
   {
      opponentsMovePending = true;
//      System.out.println("Should start timer now");
      startTimerNow = true;
       
      // Wait until thinking finished
      
      timeUp = false;
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
//System.out.println("is not BlackMove");
      }
      else if( (colour.indexOf("lack") > 0) || (colour.indexOf("LACK") > 0) )
      {
        isBlackMove = true;
//System.out.println("isBlackMove");
      }
      else
      {
System.out.println("Unknown colour in message: " + input + ", ignoring");
         return false;
      }

      if(blacksTurn && !isBlackMove)
      {
        System.out.println("Msg relates to our (white) move- ignoring");
        return false;
      }
      else if(!blacksTurn && isBlackMove)
      {
        System.out.println("Msg relates to our (black) move- ignoring");
        return false;
      }
      
      String fromCol = input.substring(COLOUR_SIZE, COLOUR_SIZE+1);
      String fromRow;
      
      String toCol;
      String toRow;
      
      if(input.substring(COLOUR_SIZE + 2, COLOUR_SIZE + 3).equals("-") )
      {
         // Two digit from square
         fromRow = input.substring(COLOUR_SIZE + 1, COLOUR_SIZE + 2);
         toCol = input.substring(COLOUR_SIZE + 3, COLOUR_SIZE + 4);
         inputMarker = COLOUR_SIZE + 4;
      }
      else
      {
         // Three digit from square
         fromRow = input.substring(COLOUR_SIZE + 1, COLOUR_SIZE + 3);
         toCol = input.substring(COLOUR_SIZE + 4, COLOUR_SIZE + 5);
         inputMarker = COLOUR_SIZE + 5;
      }
      
//      String afterMarkerP1 = input.substring(inputMarker + 1, inputMarker + 2);

      int sqBracketIdx = input.indexOf('<');
//System.out.println("sqBracketIdx:" + sqBracketIdx);
      int dashIdx = input.indexOf('-');
//System.out.println("dashIdx:" + dashIdx);

      if(sqBracketIdx > dashIdx + 3 )
      {
//System.out.println("2 digit row");
         toRow = input.substring(inputMarker, inputMarker + 2);
      }
      else
      {
//System.out.println("1 digit row");
         toRow = input.substring(inputMarker, inputMarker + 1);
      }
//System.out.println("toRow:'" + toRow + "'");

      if(!ParseCol(fromCol, fromRow, from) )
      {
         System.out.println("Invalid from");
      } 
      else if(!ParseCol(toCol, toRow, to) )
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
        opponentsMovePending = true;
        startTimerNow = true;
        waitingForOpponent = false;
        System.out.println("In Go: opponentsMovePending:" + opponentsMovePending);
      }
      else
      {
        System.out.println("Playing as white");
        opponentsMovePending = false;
        startTimerNow = false;
        waitingForOpponent = true;
      }
       
      waitingThread.start();
      processingThread.start();
      
      System.out.println("Waiting for opponent's move:");
      System.out.println("waitingForOpponent:" + waitingForOpponent);
      
      byte[] buf = new byte[1000];
      DatagramPacket dgp = new DatagramPacket(buf, buf.length);
      
      for(;;)
      {
         try
         {
            while(!waitingForOpponent)
            {
               try
               {
                  Thread.sleep(Controller.WAIT_PERIOD, 0);
               }
               catch (Exception exc)
               {
                  System.out.println("Exception caught trying to sleep");
               }
//   System.out.println("Sleeping whilst I wait for opponent");
               
            } 
            
            // Moving these up:
//            byte[] buf = new byte[1000];
//            DatagramPacket dgp = new DatagramPacket(buf, buf.length);

            System.out.println("Waiting for msg receipt");
            try
            {
               receiving_dsocket.receive(dgp);
            }
            catch(IOException exc)
            {
               System.out.println("Error raised receiving msg:" + exc);  
            }
        
            String dgp_str = new String(dgp.getData() );
            String rcvd = new String(dgp.getData(), 0, dgp.getLength()) + ", from address: " +
                                                                                dgp.getAddress();
            System.out.println("Move received:" + rcvd);
// CHANGED:            
//            if(rcvd.startsWith("Black_Wins") || rcvd.startsWith("White_Wins") )
            if(dgp_str.startsWith("Black_Wins") || dgp_str.startsWith("White_Wins") )
            {
              // Game over
                System.out.println("Game over! Exiting");
              return;
            }
            boolean isOpponentMove = ParseInput(dgp_str, dgp_str.length(),
                                                opponentMoveFrom, opponentMoveTo, theBoard.isBlackMove);
            
            if(!isOpponentMove)
              continue;
              
            waitingForOpponent = false;
            
            BoardUtils.makeMoveIfPossible(opponentMoveFrom, 
                                          opponentMoveTo,
                                          theBoard);     
        
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

//            System.out.println("After opponent's move:\n" + theBoard);     
         
            opponentsMoveMade();          
         }
         catch(java.lang.StringIndexOutOfBoundsException exc)
         {
            System.out.println("Exception raised parsing opponent's move");
         }

      }
       
   }   
   
   /*
    * Returns true if successful
    */
   static boolean ParseCol(String col, String row, Square ref)
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
         
      if(row.equals("0") )
         ref.row = Board.ROW_1;
      else if(row.equals("1") )
         ref.row = Board.ROW_2;
      else if(row.equals("2") )
         ref.row = Board.ROW_3;
      else if(row.equals("3") )
         ref.row = Board.ROW_4;
      else if(row.equals("4") )
         ref.row = Board.ROW_5;
      else if(row.equals("5") )
         ref.row = Board.ROW_6;
      else if(row.equals("6") )
         ref.row = Board.ROW_7;
      else if(row.equals("7") )
         ref.row = Board.ROW_8;
      else if(row.equals("8") )
         ref.row = Board.ROW_9;
      else if(row.equals("9") )
         ref.row = Board.ROW_10;
      else if(row.equals("10") )
         ref.row = Board.ROW_11;
      else
         return false;

      return true;   
   }
};
