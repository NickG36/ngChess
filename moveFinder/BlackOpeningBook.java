
package moveFinder;

public class BlackOpeningBook
{
   int numWhiteMovesMade = 0;
   Board theBoard;
   
   java.util.LinkedList<Square> whiteFromMovesMade = new java.util.LinkedList<Square>();
   java.util.LinkedList<Square> whiteToMovesMade = new java.util.LinkedList<Square>();
   
   Square lastWhiteFromSq = new Square(Board.COL_A, Board.ROW_1);
   Square lastWhiteToSq = new Square(Board.COL_A, Board.ROW_1);
	
   public BlackOpeningBook(Board theBoard)
   {
	  this.theBoard = theBoard;
	  this.numWhiteMovesMade = 0;
   }	


   public void whiteMoveMade(final Square from, final Square to)
   {
      this.numWhiteMovesMade++;
      
      whiteFromMovesMade.addLast(from);	   
      whiteToMovesMade.addLast(to);	   
	   
	  lastWhiteFromSq = from;
	  lastWhiteToSq = to; 
   }	
   
   /*
    ** Returns false if no move found in openings,
    *  otherwise sets details of the move
    */
    public boolean findMove(Square from, Square to)
    {
       if(this.numWhiteMovesMade == 0)
       {
	     findMove1(from, to);
	     return true;
	   }		
	   
	   boolean result = false;
	   
	   // Leave openings if King has a space next to it
	   if(theBoard.kingFreedom > 0)
	   {
          return false;
	   }
	   
	   // 
	   // Extremity moved?
	   //
	   if(lastWhiteFromSq.equals(Square.F8) )
	   {
		  // Try to move a8-f8 or k8-f8
		  if(BoardUtils.isValidMove(Square.A8, Square.F8, this.theBoard) )
	      {
			  from.set(Square.A8);
			  to.set(Square.F8);
              result = true;			  
		  }
		  else if(BoardUtils.isValidMove(Square.K8, Square.F8, this.theBoard) )
	      {
			  from.set(Square.K8);
			  to.set(Square.F8);
              result = true;			  
		  }
		  
	   }
	   else if(lastWhiteFromSq.equals(Square.F4) )
	   {
		  if(BoardUtils.isValidMove(Square.A4, Square.F4, this.theBoard) )
		  {
			  from.set(Square.A4);
			  to.set(Square.F4);
              result = true;			  
		  }
		  else if(BoardUtils.isValidMove(Square.K4, Square.F4, this.theBoard) )
		  {
			  from.set(Square.K4);
			  to.set(Square.F4);
              result = true;			  
		  }
       }
	   else if(lastWhiteFromSq.equals(Square.D6) )
	   {
		  if(BoardUtils.isValidMove(Square.D1, Square.D6, this.theBoard) )
		  {
			  from.set(Square.D1);
			  to.set(Square.D6);
              result = true;			  
		  }
		  else if(BoardUtils.isValidMove(Square.D11, Square.D6, this.theBoard) )
		  {
			  from.set(Square.D11);
			  to.set(Square.D6);
              result = true;			  
		  }
       }
	   else if(lastWhiteFromSq.equals(Square.H6) )
	   {
		  if(BoardUtils.isValidMove(Square.H1, Square.D6, this.theBoard) )
		  {
			  from.set(Square.H1);
			  to.set(Square.H6);
              result = true;			  
		  }
		  else if(BoardUtils.isValidMove(Square.H11, Square.D6, this.theBoard) )
		  {
			  from.set(Square.H11);
			  to.set(Square.D6);
              result = true;			  
		  }
	   }
	   else 
	   {
		  // Move a middle piece if possible
		  if(BoardUtils.isValidMove(Square.B6, Square.B2, this.theBoard) )
		  {
			  from.set(Square.B6);
			  to.set(Square.B2);
              result = true;			  
		  }
		  else if(BoardUtils.isValidMove(Square.F2, Square.J2, this.theBoard) )
		  {
			  from.set(Square.F2);
			  to.set(Square.J2);
              result = true;			  
		  }
		  else if(BoardUtils.isValidMove(Square.J6, Square.J10, this.theBoard) )
		  {
			  from.set(Square.J6);
			  to.set(Square.J10);
              result = true;			  
		  }
		  else if(BoardUtils.isValidMove(Square.F10, Square.B10, this.theBoard) )
		  {
			  from.set(Square.F10);
			  to.set(Square.B10);
              result = true;			  
		  }
	   }
	   
       return result;
	}
  
    private void findMove1(final Square from, final Square to)
    {
       from.set(Square.F2);
	   to.set(Square.J2);
	} 
};
