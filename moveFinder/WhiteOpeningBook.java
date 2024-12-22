
package moveFinder;

public class WhiteOpeningBook
{  
   Board theBoard;
   int numBlackMovesMade = 0;
   
   java.util.LinkedList<Square> blackFromMovesMade = new java.util.LinkedList<Square>();
   java.util.LinkedList<Square> blackToMovesMade = new java.util.LinkedList<Square>();
   
   Square lastBlackFromSq = new Square(Board.COL_A, Board.ROW_1);
   Square lastBlackToSq = new Square(Board.COL_A, Board.ROW_1);
	
   enum Quarter {TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT};
   Quarter blackToMove1Quarter;
   
   
   public WhiteOpeningBook(Board theBoard)
   {
	  this.theBoard = theBoard;
	  this.numBlackMovesMade = 0;
   }
 	
   public void blackMoveMade(final Square from, final Square to)
   {
      this.numBlackMovesMade++;
      
      blackFromMovesMade.addLast(from);	   
      blackToMovesMade.addLast(to);	   
	   
      lastBlackFromSq = from;
      lastBlackToSq = to; 
   }	
  	
   private Quarter findQuarter(final boolean topHalf, final boolean rightHalf)
   {
     Quarter result = Quarter.TOP_RIGHT;
     
     if(topHalf)
     {
	   if(rightHalf)
	     result = Quarter.TOP_RIGHT;
	   else
	     result = Quarter.TOP_LEFT;
	 }
	 else
	 {
	   if(rightHalf)
	     result = Quarter.BOTTOM_RIGHT;
	   else
	     result = Quarter.BOTTOM_LEFT;
	 }
	 return result;  
   }
   
   public boolean findMove(final Square from, final Square to)
   {
	    if(this.numBlackMovesMade == 1)
	    {
          findMove1(from, to);
	      return true;
        }  
	    else if(this.numBlackMovesMade == 2)
	    {
	      return findMove2(from, to);
        }  

     return false;	   
   }
   
   private void findMove1(Square from, Square to)
   {
	  // White's first move - move far away from black's first move
	  // Also stop black from being able to move centre pieces into a 
      // 2x2 position
 	
      Square blackToSq1 = blackToMovesMade.get(0);
      Square blackFromSq1 = blackFromMovesMade.get(0);
	 
   	  boolean topHalf = false;
	  boolean rightHalf = false;
	 
	  if(blackToSq1.col > Board.COL_F)
	    rightHalf = true;
	    
	  if(blackToSq1.row > Board.ROW_6)
	    topHalf = true;
	 
	  blackToMove1Quarter = findQuarter(topHalf, rightHalf);
	 
	  if(blackToMove1Quarter == Quarter.TOP_RIGHT)
	  { 
		 if(blackFromSq1.equals(Square.J6) )
		 {
            from.set(Square.E5);
            to.set(Square.B5);		   
		 }
		 else
		 {
            from.set(Square.E5);
            to.set(Square.E2);		   
         }   
	  }
	  else if(blackToMove1Quarter == Quarter.TOP_LEFT)
	  {
		 if(blackFromSq1.equals(Square.F10) )
         {
            from.set(Square.G5);
            to.set(Square.G2);		   
         }
		 else
         {
            from.set(Square.G5);
            to.set(Square.J5);		   
         }
      }
	  else if(blackToMove1Quarter == Quarter.BOTTOM_LEFT)
	  {
		 if(blackFromSq1.equals(Square.B6) )
         {
            from.set(Square.G7);
            to.set(Square.J7);		   
         }
		 else
         {
            from.set(Square.G7);
            to.set(Square.G10);		   
         }    
	  }  
      else if(blackToMove1Quarter == Quarter.BOTTOM_RIGHT)
      {
         if(blackFromSq1.equals(Square.F2) )
         {
            from.set(Square.E7);
            to.set(Square.E10);		   
         }
         else
         {
            from.set(Square.E7);
            to.set(Square.B7);                   
         }
	  }  
       
    }
   
    private boolean findMove2(final Square from, final Square to)
    {
        boolean result = false;
     
        if(blackToMove1Quarter == Quarter.TOP_RIGHT)
        {
           from.set(Square.F5);
           to.set(Square.B5);
        } 
        else if(blackToMove1Quarter == Quarter.TOP_LEFT)
        {
           from.set(Square.F5);
           to.set(Square.J5);           
        } 
        else if(blackToMove1Quarter == Quarter.BOTTOM_LEFT)
        {
           from.set(Square.F7);
           to.set(Square.J7);           
        } 
        else if(blackToMove1Quarter == Quarter.BOTTOM_RIGHT)
        {
           from.set(Square.F7);
           to.set(Square.B7);           
        } 
         
        result = BoardUtils.isValidMove(from, to, this.theBoard);
     
        if(!result)
        {
        // Try 2nd choice
            if(blackToMove1Quarter == Quarter.TOP_RIGHT)
            {
               from.set(Square.E6);
               to.set(Square.E3);             
            }   
            else if(blackToMove1Quarter == Quarter.TOP_LEFT)
            {
               from.set(Square.G6);
               to.set(Square.G3);           
            }   
            else if(blackToMove1Quarter == Quarter.BOTTOM_LEFT)
            {
                from.set(Square.G6);
                to.set(Square.G9);           
            }   
            else if(blackToMove1Quarter == Quarter.BOTTOM_RIGHT)
            {
                from.set(Square.E6);
                to.set(Square.E9);           
            }   
        
            result = BoardUtils.isValidMove(from, to, this.theBoard);
        }   
      
        return result;
   }   
   
};
