
package com.nick.movefinder;

public class Move
{
    public Square from = new Square();
    public Square to = new Square();
    public Integer pieceIdx = -1;
    
    public Move()
    {
    }
    
    public Move(final Square from, final Square to, final int pieceIdx)
    {
       this.from.set(from);        
       this.to.set(to);
       this.pieceIdx = pieceIdx;    
    }

    public Move(final Move move)
    {
      this.from.set(move.from);
      this.to.set(move.to);
      this.pieceIdx = move.pieceIdx;
    }

    static public Move NullMove = new Move(Square.A1, Square.A1, -2);
    
    public void set(final Move move)
    {
      this.from.set(move.from);
      this.to.set(move.to);
      this.pieceIdx = move.pieceIdx;
    }
    
    public void set(final Square from, final Square to)
    {
      this.from.set(from);
      this.to.set(to);
      this.pieceIdx = -2;
    }

    public String toStringShort()
    {
      // Exclude the piece index
      String result;
      
      result = from.toStringTxConvention() + "-" + to.toStringTxConvention();
              
      return result;
    }
    
    public boolean equals(final Move rhs)
    {
	  boolean result = false;
		
	  if(this.from.equals(rhs.from) && this.to.equals(rhs.to) )
	    result = true;
		
	  return result;	
	}
    
    public String toString()
    {
      String result;
      
      result = from + "-" + to + " " + "(" + pieceIdx + ") ";
              
      return result;
    }
};
