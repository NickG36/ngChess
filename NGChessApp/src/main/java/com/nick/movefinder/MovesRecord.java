
package com.nick.movefinder;

/**
 *
 * @author Nicholas
 */
public class MovesRecord {
    public boolean blackMovesInitially = true; // In following moves, black is first
    private java.util.ArrayList<Move> blackMoves = new java.util.ArrayList<Move>();
    private java.util.ArrayList<Move> whiteMoves = new java.util.ArrayList<Move>();
    public int score = -99999;
    
    public MovesRecord(boolean blackMovesInitially)
    {
       this.blackMovesInitially = blackMovesInitially;        
    }
    
    public MovesRecord(final MovesRecord orig)
    {
      this.blackMovesInitially = orig.blackMovesInitially;
      
      for(Move currMove : orig.blackMoves)
      {
        blackMoves.add(currMove);
          }
          
      for(Move currMove : orig.whiteMoves)
      {
        whiteMoves.add(currMove);
      }

      this.score = orig.score;
                
    }
        
    public Move getNextMove()
    {
        Move result;
        if(blackMovesInitially)
        {
            result = blackMoves.get(0);
        }
        else
        {
            result = whiteMoves.get(0);
        }
        return result;
    }
        
    /*
     ** Returns true if next move wins, false o.w.
     */
    public boolean nextMoveWins()
    {
        boolean result = false;
      
        if(this.blackMovesInitially)
        {
            if(  (this.blackMoves.size() == 1) && (this.score == BoardRater.BLACK_WIN) )
            result = true;
            
        }
        else
        {
            if(  (this.whiteMoves.size() == 1) && (this.score == BoardRater.WHITE_WIN) )
              result = true;
        
        }  
        return result;
    }
        
    public void set(final MovesRecord rhs)
    {
      this.blackMovesInitially = rhs.blackMovesInitially;
      
      blackMoves.clear();
      for(Move currMove : rhs.blackMoves)
      {
        this.blackMoves.add(currMove);
      }
          
      whiteMoves.clear();
      for(Move currMove : rhs.whiteMoves)
      {
        this.whiteMoves.add(currMove);
      }

      this.score = rhs.score;
    }
      
    public void add(final MovesRecord rhs)
    {
      for(Move currMove : rhs.blackMoves)
      {
        this.blackMoves.add(currMove);
      }
          
      for(Move currMove : rhs.whiteMoves)
      {
        this.whiteMoves.add(currMove);
      }
          
      this.score = rhs.score;
     }
      
    public void moveMade(final Square from, final Square to, boolean isBlackMove, int pieceIdx)
    {
      if(isBlackMove)
         blackMoveMade(from, to, pieceIdx);
      else
         whiteMoveMade(from, to, pieceIdx);
    }
    
    public boolean getPenultimateMove(Square from,
                                      Square to,
                                      boolean isBlackMove)
    {
       boolean result = true;       
       
       Square storedFrom = null;
       Square storedTo = null;
       
       if(isBlackMove)
       {
         if(blackMoves.size() <= 1)
         {
           result = false;
         }  
         else
         {
           storedFrom = blackMoves.get(blackMoves.size() -2 ).from;
           storedTo = blackMoves.get(blackMoves.size() -2 ).to;
         }
       }
       else
       {
         if(whiteMoves.size() <= 1)
         {
           result = false;
         }  
         else
         {
           storedFrom = whiteMoves.get(whiteMoves.size() -2 ).from;
           storedTo = whiteMoves.get(whiteMoves.size() -2 ).to;
         }
       }   
       
       if(result)
       {
         from.col = storedFrom.col;
         from.row = storedFrom.row;
         
         to.col = storedTo.col;
         to.row = storedTo.row;
       }
       else
       {
         from = new Square(Board.COL_A, Board.ROW_1);  
         to = new Square(Board.COL_A, Board.ROW_1);  
       }
       
       return result;
    }
    
    public void whiteMoveMade(final Square from, final Square to, final int pieceIdx)
    {
       Move move = new Move(from, to, pieceIdx);
       whiteMoves.add(move);           
    }
    
    public void blackMoveMade(final Square from, final Square to, final int pieceIdx)
    {
       Move move = new Move(from, to, pieceIdx);
       blackMoves.add(move);           
    }
    
    public String toString()
    {
           String result = "";
           
           int numBlackMoves = blackMoves.size();
           int numWhiteMoves = whiteMoves.size();

//       result += "black:" + numBlackMoves + " , white:" + numWhiteMoves + "\n";
           if(blackMovesInitially)
           {
                 for(int moveIdx = 0; moveIdx < numWhiteMoves; ++moveIdx)
                 {
                    result += blackMoves.get(moveIdx);
                    result += whiteMoves.get(moveIdx);
                 }  
                 
                 if(numBlackMoves > numWhiteMoves)
                 {
                   result += blackMoves.get(numBlackMoves - 1);
             }
           }
           else
           {
                 for(int moveIdx = 0; moveIdx < numBlackMoves; ++moveIdx)
                 {
                    result += whiteMoves.get(moveIdx);
                    result += blackMoves.get(moveIdx);
                 }  
                 
                 if(numWhiteMoves > numBlackMoves)
                 {
                   result += whiteMoves.get(numWhiteMoves - 1);
             }
           }
           
           result += ",score:" + this.score;
           return result;
   }        
}
