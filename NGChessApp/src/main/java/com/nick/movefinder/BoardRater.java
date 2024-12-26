
package com.nick.movefinder;

public class BoardRater
{
    public final static int WHITE_WIN = 100000;
    public final static int BLACK_WIN = -100000;
    
    final static int KING_NEXT_TO_CORNER = 50000;

    final static int MIDDLE_GAME_NUM_MOVES = 35; // After this many moves the middle game is started declared
    final static int ENDGAME_NUM_BARRICADE_SQ = 12; // Number of barricade squares that need to be filled
    final static int ENDGAME_NUM_MOVES = 55; // After this many moves the endgame is started
    // for game to be in the endgame

    // A position has this subtracted from its rating if the next move was already
    // made two moves ago.
    public final static int WHITE_REPITITION_DETERRENT = 400;    
    
    class DynamicValues
    {
      int KingFreedomFactor;
      int WhitePieceScore;
      int BlackPieceScore;
      
      int BlackPceInEndBarricadeSquare;
      int BlackPceInCentreBarricadeSquare;
      int WhitePceInEndBarricadeSquare;
      int WhitePceInCentreBarricadeSquare;
      
      int KingInBarricadeSquareScore;
      int KingIn3x3FromCornerScore;  // Also used if king in 3x2 or 2x3 square
      int KingInRowOrCol2Or3Score;
      
      public DynamicValues(int KingFreedomFactor,
                           int WhitePieceScore,
                           int BlackPieceScore,
                           int BlackPceInEndBarricadeSquare,
                           int BlackPceInCentreBarricadeSquare,
                           int WhitePceInEndBarricadeSquare,
                           int WhitePceInCentreBarricadeSquare,
                           int KingInBarricadeSquareScore,
                           int KingIn3x3FromCornerScore,
                           int KingInRowOrCol2Or3Score)
      {
        this.KingFreedomFactor = KingFreedomFactor;
        this.WhitePieceScore = WhitePieceScore;
        this.BlackPieceScore = BlackPieceScore;
      
        this.BlackPceInEndBarricadeSquare = BlackPceInEndBarricadeSquare;
        this.BlackPceInCentreBarricadeSquare = BlackPceInCentreBarricadeSquare;
        this.WhitePceInEndBarricadeSquare = WhitePceInEndBarricadeSquare;
        this.WhitePceInCentreBarricadeSquare = WhitePceInCentreBarricadeSquare;
      
        this.KingInBarricadeSquareScore = KingInBarricadeSquareScore;
        this.KingIn3x3FromCornerScore = KingIn3x3FromCornerScore;
        this.KingInRowOrCol2Or3Score = KingInRowOrCol2Or3Score;
      }
      
      public String toString()
      {
         String result;
         result = "KingFdm:" + KingFreedomFactor + " " + " w piece: " + WhitePieceScore + " " + " b piece:" + BlackPieceScore +
                  " b end b " + BlackPceInEndBarricadeSquare + " b centre b " + BlackPceInCentreBarricadeSquare +
                  " w end b " + WhitePceInEndBarricadeSquare + " w centre b " + WhitePceInCentreBarricadeSquare +
                  " k bar " + KingInBarricadeSquareScore + " k 3x3 " + KingIn3x3FromCornerScore + " k in 2/3: " + KingInRowOrCol2Or3Score;
 
         return result; 
      }
    };

    static DynamicValues EarlyValues = null;
    static DynamicValues MidgameValues = null;
    static DynamicValues EndgameValues = null;
    
    static int KING_FREEDOM_FACTOR_EARLY = 10;
    static int KING_FREEDOM_FACTOR_MID = 6;
    static int KING_FREEDOM_FACTOR_ENDGAME = 2;
      
    static int WHITE_PCE_IN_END_BARRICADE_SQUARE_SCORE = 5;
    static int WHITE_PCE_IN_CENTRE_BARRICADE_SQUARE_SCORE = 5;
      
    static int KING_CLOSENESS_TO_UNBARRICADED_CORNER = 0; // Use 6 properly
    
    public BoardRater()
    {
      // king freedom is important early on, but less so when the corners get barricaded,
      // at which point it's more important to not allow pieces to be captured otherwise
      // a loss for white is possible    
       
      final int WHITE_PIECE_SCORE_EARLY = 5;
      final int WHITE_PIECE_SCORE_MID = 10;
      final int WHITE_PIECE_SCORE_ENDGAME = 15;

      final int BLACK_PIECE_SCORE_EARLY = -5;
      final int BLACK_PIECE_SCORE_MID = -10;
      final int BLACK_PIECE_SCORE_ENDGAME = -15;
       
      final int BLACK_PCE_IN_END_BARRICADE_SQUARE_SCORE = 25;
      final int BLACK_PCE_IN_CENTRE_BARRICADE_SQUARE_SCORE = 35;
       
      final int KING_IN_BARRICADE_SQUARE_SCORE_EARLY = 1000;
      final int KING_IN_BARRICADE_SQUARE_SCORE_MID = 600;
      final int KING_IN_BARRICADE_SQUARE_SCORE_ENDGAME = 600;
      
      final int KING_IN_3X3_FROM_CORNER_SCORE_EARLY = 500; // Used if corner isn't barricaded
      final int KING_IN_3X3_FROM_CORNER_SCORE_MID = 300; // Used if corner isn't barricaded
      final int KING_IN_3X3_FROM_CORNER_SCORE_ENDGAME = 1; // Used if corner isn't barricaded
      
    //final static int KING_IN_ROW_OR_COL_2_OR_3_SCORE = 10;
      final int KING_IN_ROW_OR_COL_2_OR_3_SCORE_EARLY = 100;
      final int KING_IN_ROW_OR_COL_2_OR_3_SCORE_MID = 60;
      final int KING_IN_ROW_OR_COL_2_OR_3_SCORE_ENDGAME = 1;
    
      if(EarlyValues == null)
      {
        EarlyValues = 
           new DynamicValues(KING_FREEDOM_FACTOR_EARLY, WHITE_PIECE_SCORE_EARLY, BLACK_PIECE_SCORE_EARLY,
                             BLACK_PCE_IN_END_BARRICADE_SQUARE_SCORE, BLACK_PCE_IN_CENTRE_BARRICADE_SQUARE_SCORE,
                             WHITE_PCE_IN_END_BARRICADE_SQUARE_SCORE, WHITE_PCE_IN_CENTRE_BARRICADE_SQUARE_SCORE,
                             KING_IN_BARRICADE_SQUARE_SCORE_EARLY, KING_IN_3X3_FROM_CORNER_SCORE_EARLY, KING_IN_ROW_OR_COL_2_OR_3_SCORE_EARLY);
      }    
        
      if(MidgameValues == null)
      {
        MidgameValues = 
           new DynamicValues(KING_FREEDOM_FACTOR_MID, WHITE_PIECE_SCORE_MID, BLACK_PIECE_SCORE_MID,
                             BLACK_PCE_IN_END_BARRICADE_SQUARE_SCORE, BLACK_PCE_IN_CENTRE_BARRICADE_SQUARE_SCORE,
                             WHITE_PCE_IN_END_BARRICADE_SQUARE_SCORE, WHITE_PCE_IN_CENTRE_BARRICADE_SQUARE_SCORE,
                             KING_IN_BARRICADE_SQUARE_SCORE_MID, KING_IN_3X3_FROM_CORNER_SCORE_MID, KING_IN_ROW_OR_COL_2_OR_3_SCORE_MID);
      }    
      
      if(EndgameValues == null)
      {
        EndgameValues = 
           new DynamicValues(KING_FREEDOM_FACTOR_ENDGAME, WHITE_PIECE_SCORE_ENDGAME, BLACK_PIECE_SCORE_ENDGAME,
                             BLACK_PCE_IN_END_BARRICADE_SQUARE_SCORE, BLACK_PCE_IN_CENTRE_BARRICADE_SQUARE_SCORE,
                             WHITE_PCE_IN_END_BARRICADE_SQUARE_SCORE, WHITE_PCE_IN_CENTRE_BARRICADE_SQUARE_SCORE,
                             KING_IN_BARRICADE_SQUARE_SCORE_ENDGAME, KING_IN_3X3_FROM_CORNER_SCORE_ENDGAME, KING_IN_ROW_OR_COL_2_OR_3_SCORE_ENDGAME);
      }    
    }   

       
    /*
     ** Gives a score - higher is better for white
     */
     
    public static int rateBoard(final Board board)
    {
       BoardUtils.recalcKingFreedomScore(board);
       
       // Has either side won?
       if(board.kingLocn.equals(Square.TOP_LEFT) || board.kingLocn.equals(Square.TOP_RIGHT) ||
          board.kingLocn.equals(Square.BOTTOM_LEFT) || board.kingLocn.equals(Square.BOTTOM_RIGHT) )
       {
         return WHITE_WIN;  
       }
       
       if( isKingSurrounded(board) )
       {
//System.out.println("King is surrounded. Value = " + BLACK_WIN);           
         return BLACK_WIN;
       }
       
       if( board.kingLocn.isNextToCorner() )
       {
           // Big score unless king is next to a black piece
           if( board.kingLocn.equals(Square.B1) && 
               board.pieces[Board.COL_C][Board.ROW_1] != Board.Piece.BLACK)
             return KING_NEXT_TO_CORNER;  
           
           if( board.kingLocn.equals(Square.J1) && 
               board.pieces[Board.COL_I][Board.ROW_1] != Board.Piece.BLACK)
             return KING_NEXT_TO_CORNER;  
           
           if( board.kingLocn.equals(Square.B11) && 
               board.pieces[Board.COL_C][Board.ROW_11] != Board.Piece.BLACK)
             return KING_NEXT_TO_CORNER;  
           
           if( board.kingLocn.equals(Square.J11) && 
               board.pieces[Board.COL_I][Board.ROW_11] != Board.Piece.BLACK)
             return KING_NEXT_TO_CORNER;  

           if( board.kingLocn.equals(Square.A2) && 
               board.pieces[Board.COL_A][Board.ROW_3] != Board.Piece.BLACK)
             return KING_NEXT_TO_CORNER;  
           
           if( board.kingLocn.equals(Square.K2) && 
               board.pieces[Board.COL_K][Board.ROW_3] != Board.Piece.BLACK)
             return KING_NEXT_TO_CORNER;  

           if( board.kingLocn.equals(Square.A10) && 
               board.pieces[Board.COL_A][Board.ROW_9] != Board.Piece.BLACK)
           return KING_NEXT_TO_CORNER;  
           
           if( board.kingLocn.equals(Square.K10) && 
               board.pieces[Board.COL_K][Board.ROW_9] != Board.Piece.BLACK)
           return KING_NEXT_TO_CORNER;  
       }
           
       int blackBarricadeSquaresEnd = board.barricades.totBlackEndsOccupied;
       int whiteBarricadeSquaresEnd = board.barricades.totWhiteEndsOccupied;
       int blackBarricadeSquaresCentre = board.barricades.totBlackCentresOccupied;
       int whiteBarricadeSquaresCentre = board.barricades.totWhiteCentresOccupied;
                                 
       DynamicValues currValues = null;
       if( (board.numMovesMade > ENDGAME_NUM_MOVES) ||
           (blackBarricadeSquaresEnd + blackBarricadeSquaresCentre >= ENDGAME_NUM_BARRICADE_SQ) )
       {
         currValues = EndgameValues;
//System.out.println("In endgame. Vals:" + currValues);         
       }
       else if( board.numMovesMade > MIDDLE_GAME_NUM_MOVES) 
       {
         currValues = MidgameValues;
//System.out.println("In midgame. Vals:" + currValues);         
       }
       else
       {
         currValues = EarlyValues;
//System.out.println("In early game. Vals:" + currValues);         
       }
           
       int result = whiteBarricadeSquaresEnd * currValues.WhitePceInEndBarricadeSquare +
                    whiteBarricadeSquaresCentre * currValues.WhitePceInCentreBarricadeSquare -
                    blackBarricadeSquaresEnd * currValues.BlackPceInEndBarricadeSquare -
                    blackBarricadeSquaresCentre * currValues.BlackPceInCentreBarricadeSquare;

       // Are we in the endgame (when barricade squares are nearly filled)?
//System.out.println("Num Moves Made:" + board.numMovesMade);
       result += board.numWhitePieces * currValues.WhitePieceScore +
                 board.kingFreedom * currValues.KingFreedomFactor + 
                 board.numBlackPieces * currValues.BlackPieceScore;

       result += kingLocnBonus(board, currValues);
       return result;               
    }
    
    
    private static int kingLocnBonus(final Board board, final DynamicValues currValues)
    {
       final Square kingLocn = board.kingLocn;
       
       int result = 0;

       int numTopLeftBarricaded = board.barricades.topLeft.byBlackTotal;
       int numTopRightBarricaded = board.barricades.topRight.byBlackTotal;
       int numBottomRightBarricaded = board.barricades.bottomRight.byBlackTotal;
       int numBottomLeftBarricaded = board.barricades.bottomLeft.byBlackTotal;
                                       
       if(kingLocn.isBarricadeSquare() != Square.BarricadeType.NONE)
       {
         result += currValues.KingInBarricadeSquareScore;
       }
       else if(kingLocn.isIn3x3FromCorner() )
       {
          final boolean isBlackMove = board.isBlackMove;
          
          // Add in a square bonus if corner isn't barricaded.
          if(kingLocn.equals(Square.C3) && (numBottomLeftBarricaded < 3) )
          {
            result += currValues.KingIn3x3FromCornerScore;
          }
          else if(kingLocn.equals(Square.C9) && (numTopLeftBarricaded < 3) )
          {
            result += currValues.KingIn3x3FromCornerScore;
          }
          else if(kingLocn.equals(Square.I9) && (numTopRightBarricaded < 3) )
          {
            result += currValues.KingIn3x3FromCornerScore;
          }
          else if( kingLocn.equals(Square.I3) && (numBottomRightBarricaded < 3) )
          {
            result += currValues.KingIn3x3FromCornerScore;
          }
       }
       // If there is a gap in centre barricade then give credit to king being
       // next to it.
       else if( (board.barricades.topLeft.byBlackCentre == 0) &&
                 (kingLocn.equals(Square.B9) || kingLocn.equals(Square.C10) ) )
       {
          result += currValues.KingIn3x3FromCornerScore * 1.2;
       }
       else if( (board.barricades.topRight.byBlackCentre == 0) &&
                 (kingLocn.equals(Square.I10) || kingLocn.equals(Square.J9) ) )
       {
          result += currValues.KingIn3x3FromCornerScore * 1.2;
       }
       else if( (board.barricades.bottomRight.byBlackCentre == 0) &&
                 (kingLocn.equals(Square.I2) || kingLocn.equals(Square.J3) ) )
       {
          result += currValues.KingIn3x3FromCornerScore * 1.2;
       }
       else if( (board.barricades.bottomLeft.byBlackCentre == 0) &&
                 (kingLocn.equals(Square.B3) || kingLocn.equals(Square.C2) ) )
       {
          result += currValues.KingIn3x3FromCornerScore * 1.2;
       }
       
       if(kingLocn.isInRowOrCol2Or3() )
       {
         result += currValues.KingInRowOrCol2Or3Score;
       }
       
       // Give credit to being nearer to an unbarricaded corner
   
       if(numTopLeftBarricaded < 3)
       {
          int squaresAwayFromCorner = Square.TOP_LEFT.distanceFrom(kingLocn); 
          result += (20-squaresAwayFromCorner) * KING_CLOSENESS_TO_UNBARRICADED_CORNER * (3 - numTopLeftBarricaded); 
       }
       
       if(numBottomLeftBarricaded < 3)
       {
          int squaresAwayFromCorner = Square.BOTTOM_LEFT.distanceFrom(kingLocn); 
          result += (20-squaresAwayFromCorner) * KING_CLOSENESS_TO_UNBARRICADED_CORNER * (3 - numBottomLeftBarricaded); 
       }
       
       if(numBottomRightBarricaded < 3)
       {
          int squaresAwayFromCorner = Square.BOTTOM_RIGHT.distanceFrom(kingLocn); 
          result += (20-squaresAwayFromCorner) * KING_CLOSENESS_TO_UNBARRICADED_CORNER  * (3 - numBottomRightBarricaded);
       }
       
       if(numTopRightBarricaded < 3)
       {
          int squaresAwayFromCorner = Square.TOP_RIGHT.distanceFrom(kingLocn); 
          result += (20-squaresAwayFromCorner) * KING_CLOSENESS_TO_UNBARRICADED_CORNER * (3 - numTopRightBarricaded); 
       }
       
       return result;
    }
    
    public static boolean isKingSurrounded(final Board board)
    {
       // Look right
       if((board.kingLocn.col + 1 < Board.NUM_COLS) &&
           ( (board.pieces[board.kingLocn.col + 1][board.kingLocn.row] != Board.Piece.BLACK) &&
              !isCornerOrCentre(board.kingLocn.col + 1, board.kingLocn.row) )  )
          return false;
        
       // Look left
       if((board.kingLocn.col >= 1) &&
           ( (board.pieces[board.kingLocn.col - 1][board.kingLocn.row] != Board.Piece.BLACK) &&
              !isCornerOrCentre(board.kingLocn.col - 1, board.kingLocn.row) ) )
          return false;
       
       // Look up
       if((board.kingLocn.row + 1 < Board.NUM_ROWS) &&
           ( (board.pieces[board.kingLocn.col][board.kingLocn.row + 1] != Board.Piece.BLACK) &&
              !isCornerOrCentre(board.kingLocn.col, board.kingLocn.row + 1) ) )
          return false;
       
       // Look down
       if((board.kingLocn.row >= 1) &&
           ( (board.pieces[board.kingLocn.col][board.kingLocn.row - 1] != Board.Piece.BLACK) &&
              !isCornerOrCentre(board.kingLocn.col, board.kingLocn.row - 1) ) )
          return false;
       
       return true;
    }
   
   private static boolean isCornerOrCentre(int col, int row)
   {
     boolean result = false;
 
     // If in first or last col and first or last row
     if(  ( (col == Board.COL_A) || (col == Board.COL_K) ) &&
           ( (row == Board.ROW_1) || (row == Board.ROW_11) ) )
     {
       result = true;
     }      
     else if( (col == Board.COL_F) && (row == Board.ROW_6) )
     {
          result = true;
     }
     
     return result;
   }
}
