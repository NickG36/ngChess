
package com.nick.movefinder;

public class Board {
    /**
     * Constructor for subclasses.
     */
      
    public final static int NUM_COLS = 11;
    public final static int NUM_ROWS = 11;
    
    public enum Piece {EMPTY, BLACK, WHITE, KING};
    
    public Piece[][] pieces = new Piece[NUM_COLS][NUM_ROWS];
    public Square kingLocn;
    public java.util.List<Square> whitePieces = new java.util.ArrayList<Square>();
    public java.util.List<Square> blackPieces = new java.util.ArrayList<Square>();
    
    public NextMove nextMove = new NextMove();
    public int numMovesMade = 0;
    
    public class Barricades
    {
	public BarricadeOccupied topLeft;	
	public BarricadeOccupied topRight;	
	public BarricadeOccupied bottomLeft;	
	public BarricadeOccupied bottomRight;	
	public int totBlackCentresOccupied;
	public int totWhiteCentresOccupied;
	public int totBlackEndsOccupied;
	public int totWhiteEndsOccupied;
	   
	public Barricades()
	{
         this.topLeft = new BarricadeOccupied();	
         this.topRight = new BarricadeOccupied();	
         this.bottomLeft = new BarricadeOccupied();	
         this.bottomRight = new BarricadeOccupied();
         
         this.totBlackCentresOccupied = 0;
         this.totWhiteCentresOccupied = 0;
         this.totBlackEndsOccupied = 0;
         this.totWhiteEndsOccupied = 0;
	}
	   
  	public Barricades(final Barricades rhs)
        {
         this.topLeft = new BarricadeOccupied(rhs.topLeft);	
         this.topRight = new BarricadeOccupied(rhs.topRight);	
         this.bottomLeft = new BarricadeOccupied(rhs.bottomLeft);	
         this.bottomRight = new BarricadeOccupied(rhs.bottomRight);
         
         this.totBlackCentresOccupied = rhs.totBlackCentresOccupied;
         this.totWhiteCentresOccupied = rhs.totWhiteCentresOccupied;
         this.totBlackEndsOccupied = rhs.totBlackEndsOccupied;
         this.totWhiteEndsOccupied = rhs.totWhiteEndsOccupied;
	}
    }
    
    public class BarricadeOccupied
    {
       // Store how many barricade squares are occupied by each side.
       // Discriminate on end squares (2 of them) and centre square
       public int byBlackCentre;
      
       public int byBlackTotal;
       
       public BarricadeOccupied() 
       {
         this.byBlackCentre = 0;
         this.byBlackTotal = 0;
       }
      
       public BarricadeOccupied(final BarricadeOccupied orig)
       {
         this.byBlackCentre = orig.byBlackCentre;  
         this.byBlackTotal = orig.byBlackTotal;  
       }
    }
    
    public Barricades barricades = new Barricades();
    
    public int numBlackPieces = 6 * 4;
    public int numWhitePieces = 12;
    public int kingFreedom = 0;
    
    public boolean isBlackMove = true;
    
    public final static int COL_A = 0;
    public final static int COL_B = 1;
    public final static int COL_C = 2;
    public final static int COL_D = 3;
    public final static int COL_E = 4;
    public final static int COL_F = 5;
    public final static int COL_G = 6;
    public final static int COL_H = 7;
    public final static int COL_I = 8;
    public final static int COL_J = 9;
    public final static int COL_K = 10;
    
    public final static int ROW_1 = 0;
    public final static int ROW_2 = 1;
    public final static int ROW_3 = 2;
    public final static int ROW_4 = 3;
    public final static int ROW_5 = 4;
    public final static int ROW_6 = 5;
    public final static int ROW_7 = 6;
    public final static int ROW_8 = 7;
    public final static int ROW_9 = 8;
    public final static int ROW_10 = 9;
    public final static int ROW_11 = 10;
    
    public enum MovingDirn {SAME, UP, DOWN, LEFT, RIGHT, DIAGONAL};
    
    public Board() {
       BoardUtils.setStartingBoard(this); 
//        BoardUtils.setSmallBoard(this); 
       //BoardUtils.setDebugBoard(this); 
    }
    
    public Board(final Board board)
    {
       for(int col_idx = 0; col_idx < NUM_COLS; ++col_idx)
       {           
         for(int row_idx = 0; row_idx < NUM_COLS; ++row_idx)
         {           
            this.pieces[col_idx][row_idx] = board.pieces[col_idx][row_idx];
         }
       }
       this.kingLocn = new Square(board.kingLocn);
       
       this.whitePieces = new java.util.ArrayList<Square>(board.whitePieces);
       this.blackPieces = new java.util.ArrayList<Square>(board.blackPieces);

       this.barricades = new Barricades(board.barricades);
       
       this.numBlackPieces = board.numBlackPieces;
       this.numWhitePieces = board.numWhitePieces;
       this.kingFreedom = board.kingFreedom;
       this.isBlackMove = board.isBlackMove;
       this.numMovesMade = board.numMovesMade;
    }
    
    /*
     ** Adds a piece to the board
     *  and updates all the relevant attributes of the board
     */
    public void addPiece(int col, int row, Piece type)
    {
       Square newPiece = new Square(col, row);
       
       if(type == Piece.BLACK)
       {
          pieces[col][row] = Piece.BLACK;
          blackPieces.add(newPiece);
          BoardUtils.increaseBarricadeScoreIfNecc(this, newPiece, true);
       }                  
       else if(type == Piece.WHITE)
       {
          pieces[col][row] = Piece.WHITE;
          whitePieces.add(newPiece);
          BoardUtils.increaseBarricadeScoreIfNecc(this, newPiece, false);
       }                  
       else
       {
          pieces[col][row] = Piece.KING;
//          System.out.println("*** ERROR - Can't add white king");   
       }
       BoardUtils.recalcKingFreedomScore(this);
    }
    
    public void removePiece(int col, int row)
    {
//       System.out.println("In remove piece");
       this.pieces[col][row] = Piece.EMPTY;

       if(isBlackMove)
       {
          int pieceIndex = BoardUtils.findIdxOfPiece(this, col, row, false);
          if(pieceIndex != -1)
          {
            whitePieces.remove(pieceIndex);
            numWhitePieces--;
            
            BoardUtils.decreaseBarricadeScoreIfNecc(this, new Square(col, row), false);
          }
          else
          {
            System.out.println("Error, can't find white piece to remove");  
          }
       }
       else
       {
          int pieceIndex = BoardUtils.findIdxOfPiece(this, col, row, true);
          if(pieceIndex != -1)
          {
            blackPieces.remove(pieceIndex);
            numBlackPieces--;
            BoardUtils.decreaseBarricadeScoreIfNecc(this, new Square(col, row), true);
          }
          else
          {
            System.out.println("Error, can't find black piece to remove");  
          }
       }
       BoardUtils.recalcKingFreedomScore(this);
    }

    /*
     ** Returns true if this is a winning move, false ow
     */
    public boolean moveKingTo(final Square newSquare)
    {
      final Square fromSq = kingLocn;
      this.pieces[fromSq.col][fromSq.row] = Piece.EMPTY;
      this.pieces[newSquare.col][newSquare.row] = Piece.KING;
      
      kingLocn.col = newSquare.col;  
      kingLocn.row = newSquare.row;  
      
      MovingDirn movingDirn = BoardUtils.findMovingDirn(fromSq, newSquare);
      this.isBlackMove = false;    
      BoardUtils.takePieceIfNecessary(newSquare, movingDirn, this);
      BoardUtils.decreaseBarricadeScoreIfNecc(this, fromSq, false);
      BoardUtils.increaseBarricadeScoreIfNecc(this, newSquare, false);
      
      return newSquare.isACorner();
    }
    
    /*
     ** Returns true if this is a winning move, false ow
     */
    public boolean moveWhitePieceTo(int whitePieceIdx, 
                                 final Square fromSq,
                                 final Square toSq)
    {
      this.pieces[fromSq.col][fromSq.row] = Piece.EMPTY;
      this.pieces[toSq.col][toSq.row] = Piece.WHITE;

      whitePieces.set(whitePieceIdx, toSq);
      
      MovingDirn movingDirn = BoardUtils.findMovingDirn(fromSq, toSq);
      this.isBlackMove = false;    
      BoardUtils.takePieceIfNecessary(toSq, movingDirn, this);
      BoardUtils.decreaseBarricadeScoreIfNecc(this, fromSq, false);
      BoardUtils.increaseBarricadeScoreIfNecc(this, toSq, false);

      return false;
    }
    
    /*
     ** Returns true if this is a winning move, false ow
     */
    public boolean moveBlackPieceTo(int blackPieceIdx, 
                                    final Square fromSq,
                                    final Square toSq)
    {
      boolean result = false;
      
      this.pieces[fromSq.col][fromSq.row] = Piece.EMPTY;
      this.pieces[toSq.col][toSq.row] = Piece.BLACK;

      blackPieces.set(blackPieceIdx, toSq);
      
      MovingDirn movingDirn = BoardUtils.findMovingDirn(fromSq, toSq);
      this.isBlackMove = true;    
      BoardUtils.takePieceIfNecessary(toSq, movingDirn, this);
      BoardUtils.decreaseBarricadeScoreIfNecc(this, fromSq, true);
      BoardUtils.increaseBarricadeScoreIfNecc(this, toSq, true);
      // See if we have just won
      
      if(BoardRater.isKingSurrounded(this) )
      {
          result = true;
      }

      return result;
    }
    
    public java.util.List<Square> validKingMovesFromThisSquare(final Square from)
    {

       java.util.List<Square> validMoves = new java.util.ArrayList<Square>();
       
       boolean blocked = false;
       
       // Trying to move down
       for(int idx = from.row - 1; idx >= 0; --idx)
       {
         if(this.pieces[from.col][idx] != Piece.EMPTY)
         {
            blocked = true;  
            break;
         }
         else
         {
           Square toSquare = new Square(from.col, idx);
           validMoves.add(toSquare);
         }
       }
       
       // Trying to move up
       for(int idx = from.row + 1; idx < NUM_ROWS; ++idx)
       {
         if(this.pieces[from.col][idx] != Piece.EMPTY)
         {
            blocked = true;  
            break;
         }
         else
         {
           Square toSquare = new Square(from.col, idx);
           validMoves.add(toSquare);
         }
       }
//System.out.println("After U moves - valid moves:" + validMoves.size() );        
       
       // Trying to move right
       for(int idx = from.col + 1; idx < NUM_COLS; ++idx)
       {
          if(this.pieces[idx][from.row] != Piece.EMPTY)
          {
             blocked = true;  
             break;
          }
          else
          {
            Square toSquare = new Square(idx, from.row);
            validMoves.add(toSquare);
          }
       }

       // Trying to move left
       for(int idx = from.col - 1; idx >= 0; --idx)
       {
          if(this.pieces[idx][from.row] != Piece.EMPTY)
          {
             blocked = true;  
             break;
          }
          else
          {
            Square toSquare = new Square(idx, from.row);
            validMoves.add(toSquare);
          }
       }
       return validMoves;
    }
    
    public java.util.List<Square> validPawnMovesFromThisSquare(final Square from)
    {
       java.util.List<Square> validMoves = new java.util.ArrayList<Square>();
       
       boolean blocked = false;
       
       // Trying to move down
       for(int idx = from.row - 1; idx >= 0; --idx)
       {
         if(this.pieces[from.col][idx] != Piece.EMPTY)
         {
            blocked = true;  
            break;
         }
         else
         {
           Square toSquare = new Square(from.col, idx);
           
           if( !toSquare.equals(Square.CENTRE) &&  
               !toSquare.equals(Square.BOTTOM_LEFT) &&
               !toSquare.equals(Square.BOTTOM_RIGHT) )
           {
             validMoves.add(toSquare);
           }
         }
       }
       
       // Trying to move up
       for(int idx = from.row + 1; idx < NUM_ROWS; ++idx)
       {
         if(this.pieces[from.col][idx] != Piece.EMPTY)
         {
            blocked = true;  
            break;
         }
         else
         {
           Square toSquare = new Square(from.col, idx);
           
           if( !toSquare.equals(Square.CENTRE) &&  
               !toSquare.equals(Square.TOP_LEFT) &&
               !toSquare.equals(Square.TOP_RIGHT) )
           {
             validMoves.add(toSquare);
           }
         }
       }
       
       // Trying to move right
       for(int idx = from.col + 1; idx < NUM_COLS; ++idx)
       {
          if(this.pieces[idx][from.row] != Piece.EMPTY)
          {
             blocked = true;  
             break;
          }
          else
          {
            Square toSquare = new Square(idx, from.row);
            
            if( !toSquare.equals(Square.CENTRE) &&  
                !toSquare.equals(Square.TOP_RIGHT) &&
                !toSquare.equals(Square.BOTTOM_RIGHT) )
            {
              validMoves.add(toSquare);
            }
          }
       }

       // Trying to move left
       for(int idx = from.col - 1; idx >= 0; --idx)
       {
          if(this.pieces[idx][from.row] != Piece.EMPTY)
          {
             blocked = true;  
             break;
          }
          else
          {
            Square toSquare = new Square(idx, from.row);
            
            if( !toSquare.equals(Square.CENTRE) &&  
                !toSquare.equals(Square.TOP_LEFT) &&
                !toSquare.equals(Square.BOTTOM_LEFT) )
            {
              validMoves.add(toSquare);
            }
          }
       }
       return validMoves;
    }
    

    public String toString()
    {
        String result = "   abcdefghijk\n";
        for(int row_idx = NUM_ROWS-1; row_idx >=0 ; --row_idx)
        {           
          if(row_idx > 8)
            result += "" + (row_idx+1) + ' ';
          else
            result += " " + (row_idx+1) + ' ';
          
              
          for(int col_idx = 0; col_idx < NUM_COLS; ++col_idx)
          {           
             if(this.pieces[col_idx][row_idx] == Piece.EMPTY)
                result += '.';
             else if(this.pieces[col_idx][row_idx] == Piece.WHITE)
                result += 'w';
             else if(this.pieces[col_idx][row_idx] == Piece.KING)
                result += 'K';
             else 
                result += 'B';
          }
          result += '\n';
        }
        result += "   abcdefghijk";

        if(isBlackMove)
          result += "\nBlack to move";
        else
          result += "\nWhite to move";

        result += "\nMoves made:" + this.numMovesMade;
          
        return result;
    }
}
