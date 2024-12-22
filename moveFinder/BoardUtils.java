
package moveFinder;

import static moveFinder.Board.COL_F;
import static moveFinder.Board.NUM_COLS;
import static moveFinder.Board.NUM_ROWS;
import static moveFinder.Board.ROW_6;

/**
 *
 * @author Nicholas
 */
public class BoardUtils {
    public static void clearBoard(Board theBoard)
    {
       for(int col_idx = 0; col_idx < NUM_COLS; col_idx++)
       {
         for(int row_idx = 0; row_idx < NUM_COLS; row_idx++)
         {
            theBoard.pieces[col_idx][row_idx] = Board.Piece.EMPTY;
         }
       }
    }
    
    public static void increaseBarricadeScoreIfNecc(Board theBoard, 
                                                    Square toSquare, 
                                                    boolean movingBlackPiece)
    {
       final Square.BarricadeType barricade = toSquare.isBarricadeSquare();
          
       if(barricade != Square.BarricadeType.NONE)
       {
         increaseBarricadeScore(theBoard, barricade, toSquare, movingBlackPiece);
       }
    }            
            
    public static void decreaseBarricadeScoreIfNecc(Board theBoard, 
                                                    Square fromSquare, 
                                                    boolean movingBlackPiece)
    {
       final Square.BarricadeType barricade = fromSquare.isBarricadeSquare();
        
       if(barricade != Square.BarricadeType.NONE)
       {
         decreaseBarricadeScore(theBoard, barricade, fromSquare, movingBlackPiece);
       }
    }            
            
    public static void increaseBarricadeScore(Board theBoard, 
                                              final Square.BarricadeType barricade, 
                                              final Square toSquare,
                                              boolean movingBlackPiece)
    {
       if(movingBlackPiece)
       {
         if(barricade == Square.BarricadeType.BOTTOM_LEFT)
         {
            if(toSquare.col == Board.COL_B)
            {
               theBoard.barricades.bottomLeft.byBlackCentre++; 
               theBoard.barricades.totBlackCentresOccupied++;   
            }
            else  
            {
 //              theBoard.barricades.bottomLeft.byBlackEnd++;    
               theBoard.barricades.totBlackEndsOccupied++;   
            }
             
            theBoard.barricades.bottomLeft.byBlackTotal++;
         }
         else if(barricade == Square.BarricadeType.BOTTOM_RIGHT)
         {
            if(toSquare.col == Board.COL_J)
            {
               theBoard.barricades.bottomRight.byBlackCentre++;    
               theBoard.barricades.totBlackCentresOccupied++;   
        }
            else  
            {
 //              theBoard.barricades.bottomRight.byBlackEnd++;    
               theBoard.barricades.totBlackEndsOccupied++;   
         }
               
            theBoard.barricades.bottomRight.byBlackTotal++;    
         }
         else if(barricade == Square.BarricadeType.TOP_RIGHT)
         {
            if(toSquare.col == Board.COL_J)
            {
               theBoard.barricades.topRight.byBlackCentre++;    
               theBoard.barricades.totBlackCentresOccupied++;   
            } 
            else  
            {
 //              theBoard.barricades.topRight.byBlackEnd++;    
               theBoard.barricades.totBlackEndsOccupied++;   
            }  
               
               
             theBoard.barricades.topRight.byBlackTotal++;    
         }
         else if(barricade == Square.BarricadeType.TOP_LEFT)
         {
            if(toSquare.col == Board.COL_B)
            {
               theBoard.barricades.topLeft.byBlackCentre++;    
               theBoard.barricades.totBlackCentresOccupied++;   
            }
            else  
            {
 //              theBoard.barricades.topLeft.byBlackEnd++;    
               theBoard.barricades.totBlackEndsOccupied++;   
            }
               
            theBoard.barricades.topLeft.byBlackTotal++;    
         }
       }
       else
       {
         if(barricade == Square.BarricadeType.BOTTOM_LEFT)
         {
         if(toSquare.col == Board.COL_B)
         {
 //              theBoard.barricades.bottomLeft.byWhiteCentre++;    
               theBoard.barricades.totWhiteCentresOccupied++;   
         }
            else  
            {
 //              theBoard.barricades.bottomLeft.byWhiteEnd++;    
               theBoard.barricades.totWhiteEndsOccupied++;   
         }
         }
         else if(barricade == Square.BarricadeType.BOTTOM_RIGHT)
         {
            if(toSquare.col == Board.COL_J)
            {
 //              theBoard.barricades.bottomRight.byWhiteCentre++;    
               theBoard.barricades.totWhiteCentresOccupied++;   
            }
            else  
            {
 //              theBoard.barricades.bottomRight.byWhiteEnd++;    
               theBoard.barricades.totWhiteEndsOccupied++;   
            }
         }
         else if(barricade == Square.BarricadeType.TOP_RIGHT)
         {
            if(toSquare.col == Board.COL_J)
            {
 //              theBoard.barricades.topRight.byWhiteCentre++;    
               theBoard.barricades.totWhiteCentresOccupied++;   
            } 
            else  
            {
 //              theBoard.barricades.topRight.byWhiteEnd++;    
              theBoard.barricades.totWhiteEndsOccupied++;   
         }
         }
         else if(barricade == Square.BarricadeType.TOP_LEFT)
         {
            if(toSquare.col == Board.COL_B)
            {
 //              theBoard.barricades.topLeft.byWhiteCentre++;    
               theBoard.barricades.totWhiteCentresOccupied++;   
            }
            else  
            {
 //              theBoard.barricades.topLeft.byWhiteEnd++;    
               theBoard.barricades.totWhiteEndsOccupied++;   
            }
         }
       }
    }
    
    public static void decreaseBarricadeScore(Board theBoard, 
                                              final Square.BarricadeType barricade, 
                                              final Square fromSquare,
                                              boolean movingBlackPiece)
    {
       if(movingBlackPiece)
       {
         if(barricade == Square.BarricadeType.BOTTOM_LEFT)
         {
          if(fromSquare.col == Board.COL_B)
         {
               theBoard.barricades.bottomLeft.byBlackCentre--;    
               theBoard.barricades.totBlackCentresOccupied--;   
         }
            else  
         {
 //              theBoard.barricades.bottomLeft.byBlackEnd--;    
               theBoard.barricades.totBlackEndsOccupied--;   
         }
               
             theBoard.barricades.bottomLeft.byBlackTotal--;    
         }
         else if(barricade == Square.BarricadeType.BOTTOM_RIGHT)
         {
          if(fromSquare.col == Board.COL_J)
         {
               theBoard.barricades.bottomRight.byBlackCentre--;    
               theBoard.barricades.totBlackCentresOccupied--;   
         }
            else  
            {
 //              theBoard.barricades.bottomRight.byBlackEnd--;    
               theBoard.barricades.totBlackEndsOccupied--;   
         }
               
             theBoard.barricades.bottomRight.byBlackTotal--;    
         }
         else if(barricade == Square.BarricadeType.TOP_RIGHT)
         {
          if(fromSquare.col == Board.COL_J)
        {
               theBoard.barricades.topRight.byBlackCentre--;    
               theBoard.barricades.totBlackCentresOccupied--;   
        }
            else  
            {
//              theBoard.barricades.topRight.byBlackEnd--;    
               theBoard.barricades.totBlackEndsOccupied--;   
        }
               
            theBoard.barricades.topRight.byBlackTotal--;    
         }
         else if(barricade == Square.BarricadeType.TOP_LEFT)
         {
          if(fromSquare.col == Board.COL_B)
         {
               theBoard.barricades.topLeft.byBlackCentre--;    
               theBoard.barricades.totBlackCentresOccupied--;   
        }
            else  
            {
//              theBoard.barricades.topLeft.byBlackEnd--;    
               theBoard.barricades.totBlackEndsOccupied--;   
        }
               
            theBoard.barricades.topLeft.byBlackTotal--;    
         }
       }
       else
       {
         if(barricade == Square.BarricadeType.BOTTOM_LEFT)
         {
          if(fromSquare.col == Board.COL_B)
        {
              theBoard.barricades.totWhiteCentresOccupied--;   
        } 
            else  
            {
              theBoard.barricades.totWhiteEndsOccupied--;   
        }
         }
         else if(barricade == Square.BarricadeType.BOTTOM_RIGHT)
         {
       if(fromSquare.col == Board.COL_J)
       {
               theBoard.barricades.totWhiteCentresOccupied--;   
           }
           else  
           {
               theBoard.barricades.totWhiteEndsOccupied--;   
            }
         }
         else if(barricade == Square.BarricadeType.TOP_RIGHT)
         {
            if(fromSquare.col == Board.COL_J)
            {
              theBoard.barricades.totWhiteCentresOccupied--;   
            }
            else  
            {
              theBoard.barricades.totWhiteEndsOccupied--;   
            }
         }
         else if(barricade == Square.BarricadeType.TOP_LEFT)
         {
          if(fromSquare.col == Board.COL_B)
            {
              theBoard.barricades.totWhiteCentresOccupied--;   
        }
            else  
            {
              theBoard.barricades.totWhiteEndsOccupied--;   
        }
              
         }
       }
    }
    
    public static void setStartingBoard(Board theBoard)
    {
       clearBoard(theBoard);
       
       theBoard.numBlackPieces = 6 * 4;
       theBoard.numWhitePieces = 12;
       theBoard.kingLocn = new Square(COL_F, ROW_6);
       
       theBoard.addPiece(0, 3, Board.Piece.BLACK);
       theBoard.addPiece(0, 4, Board.Piece.BLACK);
       theBoard.addPiece(0, 5, Board.Piece.BLACK);
       theBoard.addPiece(0, 6, Board.Piece.BLACK);
       theBoard.addPiece(0, 7, Board.Piece.BLACK);
       theBoard.addPiece(1, 5, Board.Piece.BLACK);
       
       theBoard.addPiece(10, 3, Board.Piece.BLACK);
       theBoard.addPiece(10, 4, Board.Piece.BLACK);
       theBoard.addPiece(10, 5, Board.Piece.BLACK);
       theBoard.addPiece(10, 6, Board.Piece.BLACK);
       theBoard.addPiece(10, 7, Board.Piece.BLACK);
       theBoard.addPiece(9, 5, Board.Piece.BLACK);
       
       theBoard.addPiece(3, 0, Board.Piece.BLACK);
       theBoard.addPiece(4, 0, Board.Piece.BLACK);
       theBoard.addPiece(5, 0, Board.Piece.BLACK);
       theBoard.addPiece(6, 0, Board.Piece.BLACK);
       theBoard.addPiece(7, 0, Board.Piece.BLACK);
       theBoard.addPiece(5, 1, Board.Piece.BLACK);
       
       theBoard.addPiece(3, 10, Board.Piece.BLACK);
       theBoard.addPiece(4, 10, Board.Piece.BLACK);
       theBoard.addPiece(5, 10, Board.Piece.BLACK);
       theBoard.addPiece(6, 10, Board.Piece.BLACK);
       theBoard.addPiece(7, 10, Board.Piece.BLACK);
       theBoard.addPiece(5, 9, Board.Piece.BLACK);
       
       theBoard.addPiece(4, 4, Board.Piece.WHITE);
       theBoard.addPiece(4, 5, Board.Piece.WHITE);
       theBoard.addPiece(4, 6, Board.Piece.WHITE);
       theBoard.addPiece(5, 4, Board.Piece.WHITE);
       theBoard.addPiece(5, 5, Board.Piece.KING);
       theBoard.addPiece(5, 6, Board.Piece.WHITE);
       theBoard.addPiece(6, 4, Board.Piece.WHITE);
       theBoard.addPiece(6, 5, Board.Piece.WHITE);
       theBoard.addPiece(6, 6, Board.Piece.WHITE);
       
       theBoard.addPiece(3, 5, Board.Piece.WHITE);
       theBoard.addPiece(7, 5, Board.Piece.WHITE);
       theBoard.addPiece(5, 3, Board.Piece.WHITE);
       theBoard.addPiece(5, 7, Board.Piece.WHITE);
    }
    
    public static void setDebugBoard(Board theBoard)
    {
       clearBoard(theBoard);
       
       theBoard.numBlackPieces = 15;
       theBoard.numWhitePieces = 3;
       theBoard.kingLocn = new Square(Board.COL_G, Board.ROW_2);
       
       theBoard.addPiece(Board.COL_J, Board.ROW_7, Board.Piece.WHITE);
       theBoard.addPiece(Board.COL_H, Board.ROW_1, Board.Piece.WHITE);
       theBoard.addPiece(Board.COL_E, Board.ROW_4, Board.Piece.WHITE);
       
       theBoard.addPiece(Board.COL_A, Board.ROW_3, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_B, Board.ROW_2, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_C, Board.ROW_1, Board.Piece.BLACK);

       theBoard.addPiece(Board.COL_A, Board.ROW_9, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_B, Board.ROW_10, Board.Piece.BLACK);
       
       theBoard.addPiece(Board.COL_C, Board.ROW_11, Board.Piece.BLACK);
       
       theBoard.addPiece(Board.COL_I, Board.ROW_1, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_J, Board.ROW_2, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_K, Board.ROW_3, Board.Piece.BLACK);
       
       theBoard.addPiece(Board.COL_I, Board.ROW_11, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_J, Board.ROW_10, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_K, Board.ROW_9, Board.Piece.BLACK);
       
       theBoard.addPiece(Board.COL_B, Board.ROW_3, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_B, Board.ROW_7, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_K, Board.ROW_7, Board.Piece.BLACK);
        
       theBoard.addPiece(Board.COL_G, Board.ROW_2, Board.Piece.KING);
       
       theBoard.numMovesMade = 46;
    }
    
    public static void setSmallBoard(Board theBoard)
    {
       clearBoard(theBoard);
       
       theBoard.numBlackPieces = 5;
       theBoard.numWhitePieces = 3;
       theBoard.kingLocn = new Square(Board.COL_I, Board.ROW_11);
       
       theBoard.addPiece(Board.COL_I, Board.ROW_11, Board.Piece.KING);
       
       theBoard.addPiece(Board.COL_I, Board.ROW_9, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_B, Board.ROW_10, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_J, Board.ROW_10, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_C, Board.ROW_11, Board.Piece.BLACK);
       theBoard.addPiece(Board.COL_D, Board.ROW_5, Board.Piece.BLACK);

       theBoard.addPiece(Board.COL_G, Board.ROW_6, Board.Piece.WHITE);
       theBoard.addPiece(Board.COL_E, Board.ROW_6, Board.Piece.WHITE);
       theBoard.addPiece(Board.COL_F, Board.ROW_3, Board.Piece.WHITE);
    }
    
    public static Board.MovingDirn findMovingDirn(final Square from, final Square to)
    {
       Board.MovingDirn result = Board.MovingDirn.SAME;
       
       boolean sameCol = from.sameCol(to);
       boolean sameRow = from.sameRow(to);
       
       if(sameCol && sameRow)
       {
         result = Board.MovingDirn.SAME;
       }
       else if(sameCol)
       {
         // Up or down
         if(to.row > from.row)
           result = Board.MovingDirn.UP;
         else
           result = Board.MovingDirn.DOWN;
       }
       else if(sameRow)
       {
         // Left or right
         if(to.col > from.col)
           result = Board.MovingDirn.RIGHT;
         else
           result = Board.MovingDirn.LEFT;
       }
       else
       {
         result = Board.MovingDirn.DIAGONAL;
       }
       return result;
    }
    
    public static int findIdxOfPiece(Board theBoard, int col, int row, boolean isBlackPiece)
    {
      int index = -1;
      
      Square currPiece = new Square(col, row);
      
      if(isBlackPiece)
      {
        for(int idx = 0; idx < theBoard.blackPieces.size(); ++idx)
        {
//System.out.println("Black piece at: " + theBoard.blackPieces.get(idx) );
           if(theBoard.blackPieces.get(idx).equals(currPiece) )
           {
//               System.out.println("Found match in black pieces list");
               index = idx;
               break;
           }
        }
      }
      else
      {
        for(int idx = 0; idx < theBoard.whitePieces.size(); ++idx)
        {
           if(theBoard.whitePieces.get(idx).equals(currPiece) )
           {
               index = idx;
               break;
           }
        }
      }
      return index;  
    }
    
    public static void recalcKingFreedomScore(Board theBoard)
    {
         // Recalculate king's freedom (number of available squares)
         int freedom = 0;
         
         // Can king move right?
         int sq_idx = theBoard.kingLocn.col + 1;
         
         while( (sq_idx < NUM_COLS) && (theBoard.pieces[sq_idx][theBoard.kingLocn.row] == Board.Piece.EMPTY) )
         {
            freedom++;
            sq_idx++;
         }
//System.out.println("After looking right, freedom:" + freedom);
         // Can king move down?
         sq_idx = theBoard.kingLocn.col - 1;
         
         while( (sq_idx >= 0) && (theBoard.pieces[sq_idx][theBoard.kingLocn.row] == Board.Piece.EMPTY) )
         {
            freedom++;
            sq_idx--;
         }
//System.out.println("After looking left, freedom:" + freedom);
         
         // Can king move up?
         sq_idx = theBoard.kingLocn.row + 1;
         
         while( (sq_idx < NUM_ROWS) && (theBoard.pieces[theBoard.kingLocn.col][sq_idx] == Board.Piece.EMPTY) )
         {
            freedom++;
            sq_idx++;
         }

//System.out.println("After looking up, freedom:" + freedom);
         // Can king move down?
         sq_idx = theBoard.kingLocn.row - 1;
         
         while( (sq_idx >= 0) && (theBoard.pieces[theBoard.kingLocn.col][sq_idx] == Board.Piece.EMPTY) )
         {
            freedom++;
            sq_idx--;
         }
//System.out.println("After looking down, freedom:" + freedom);
         
         theBoard.kingFreedom = freedom;
    }
    
    public static void takePieceIfNecessary(final Square to, final Board.MovingDirn movingDirn, Board theBoard)
    {
       // Look up
       if(movingDirn != Board.MovingDirn.DOWN)
       {
         if(to.row < NUM_ROWS - 2)
         {
            if(theBoard.isBlackMove)
            {
               if( (theBoard.pieces[to.col][to.row + 1] == Board.Piece.WHITE ) &&
                   (theBoard.pieces[to.col][to.row + 2] == Board.Piece.BLACK ) )
               {
                 theBoard.removePiece(to.col, to.row + 1);
               }
            }
            else
            {
               if( (theBoard.pieces[to.col][to.row + 1] == Board.Piece.BLACK) &&
                     (  (theBoard.pieces[to.col][to.row + 2] == Board.Piece.WHITE) || 
                        (theBoard.pieces[to.col][to.row + 2] == Board.Piece.KING)) )
               {
                 theBoard.removePiece(to.col, to.row + 1);
               }
            }
         } // end if inside board
       } // end looking up
       
       // Look down
       if(movingDirn != Board.MovingDirn.UP)
       {
         if(to.row > 1)
         {
            if(theBoard.isBlackMove)
            {
               if( (theBoard.pieces[to.col][to.row - 1] == Board.Piece.WHITE ) &&
                   (theBoard.pieces[to.col][to.row - 2] == Board.Piece.BLACK ) )
               {
                 theBoard.removePiece(to.col, to.row - 1);
               }
            }
            else
            {
               if( (theBoard.pieces[to.col][to.row - 1] == Board.Piece.BLACK) &&
                   (  (theBoard.pieces[to.col][to.row - 2] == Board.Piece.WHITE) ||
                      (theBoard.pieces[to.col][to.row - 2] == Board.Piece.KING )))
               {
                 theBoard.removePiece(to.col, to.row - 1);
               }
            }
         } // end if inside board
       } // end looking down
       
       // Look right
       if(movingDirn != Board.MovingDirn.LEFT)
       {
         if(to.col < NUM_COLS - 2)
         {
            if(theBoard.isBlackMove)
            {
               if( (theBoard.pieces[to.col + 1][to.row] == Board.Piece.WHITE ) &&
                   (theBoard.pieces[to.col + 2][to.row] == Board.Piece.BLACK ) )
               {
                 theBoard.removePiece(to.col + 1, to.row);
               }
            }
            else
            {
               if( (theBoard.pieces[to.col + 1][to.row] == Board.Piece.BLACK) &&
                   (  (theBoard.pieces[to.col + 2][to.row] == Board.Piece.WHITE ) ||
                      (theBoard.pieces[to.col + 2][to.row] == Board.Piece.KING)) )
               {
                 theBoard.removePiece(to.col + 1, to.row);
               }
            }
         } // end if inside board
       } // end looking up
       
       // Look left
       if(movingDirn != Board.MovingDirn.RIGHT)
       {
         if(to.col > 1)
         {
            if(theBoard.isBlackMove)
            {
               if( (theBoard.pieces[to.col - 1][to.row] == Board.Piece.WHITE ) &&
                   (theBoard.pieces[to.col - 2][to.row] == Board.Piece.BLACK ) )
               {
                 theBoard.removePiece(to.col - 1, to.row);
               }
            }
            else
            {
               if( (theBoard.pieces[to.col - 1][to.row] == Board.Piece.BLACK) &&
                   (  (theBoard.pieces[to.col - 2][to.row] == Board.Piece.WHITE) ||
                      (theBoard.pieces[to.col - 2][to.row] == Board.Piece.KING) ) )
               {
                 theBoard.removePiece(to.col - 1, to.row);
               }
            }
         } // end if inside board
       } // end looking down
       
       // Captures against a corner
       if( (to.col == Board.COL_A) || (to.col == Board.COL_K) )
       {
          if(to.row == Board.ROW_3)
          {
             if(theBoard.isBlackMove && (theBoard.pieces[to.col][to.row - 1] == Board.Piece.WHITE) )
             {
               // Trap white piece against corner
               theBoard.removePiece(to.col, Board.ROW_2);
             }
             else if(!theBoard.isBlackMove && (theBoard.pieces[to.col][to.row - 1] == Board.Piece.BLACK) )
             {
               // Trap black piece against corner
               theBoard.removePiece(to.col, Board.ROW_2);
             }
          }
          else if(to.row == Board.ROW_9)
          {
             if(theBoard.isBlackMove && (theBoard.pieces[to.col][to.row + 1] == Board.Piece.WHITE) )
             {
               // Trap white piece against corner
               theBoard.removePiece(to.col, Board.ROW_10);
             }
             else if(!theBoard.isBlackMove && (theBoard.pieces[to.col][to.row + 1] == Board.Piece.BLACK) )
             {
               // Trap black piece against corner
               theBoard.removePiece(to.col, Board.ROW_10);
             }
          }
       }
       
       if( (to.row == Board.ROW_1) || (to.row == Board.ROW_11) )
       {
          if(to.col == Board.COL_C)
          {
             if(theBoard.isBlackMove && (theBoard.pieces[to.col - 1][to.row] == Board.Piece.WHITE) )
             {
               // Trap white piece against corner
                theBoard.removePiece(to.col - 1, to.row);
             }
             else if(!theBoard.isBlackMove && (theBoard.pieces[to.col - 1][to.row] == Board.Piece.BLACK) )
             {
               // Trap black piece against corner
               theBoard.removePiece(to.col - 1, to.row);
             }
          }
          else if(to.col == Board.COL_I)
          {
             if(theBoard.isBlackMove && (theBoard.pieces[to.col + 1][to.row] == Board.Piece.WHITE) )
             {
               // Trap white piece against corner
                theBoard.removePiece(to.col + 1, to.row);
             }
             else if(!theBoard.isBlackMove && (theBoard.pieces[to.col + 1][to.row] == Board.Piece.BLACK) )
             {
               // Trap black piece against corner
               theBoard.removePiece(to.col + 1, to.row);
             }
          }
       }
       
       // Trap piece against centre
       if(!theBoard.isBlackMove)
       {
         if( (to.col == Board.COL_D) && (to.row == Board.ROW_6) && (theBoard.pieces[Board.COL_E][Board.ROW_6] == Board.Piece.BLACK) )  
         {
            theBoard.removePiece(Board.COL_E, Board.ROW_6);
         }    
         else if( (to.col == Board.COL_H) && (to.row == Board.ROW_6) && (theBoard.pieces[Board.COL_G][Board.ROW_6] == Board.Piece.BLACK) )  
         {
            theBoard.removePiece(Board.COL_G, Board.ROW_6);
         }    
         else if( (to.col == Board.COL_F) && (to.row == Board.ROW_4) && (theBoard.pieces[Board.COL_F][Board.ROW_5] == Board.Piece.BLACK) )  
         {
            theBoard.removePiece(Board.COL_F, Board.ROW_5);
         }    
         else if( (to.col == Board.COL_F) && (to.row == Board.ROW_8) && (theBoard.pieces[Board.COL_F][Board.ROW_7] == Board.Piece.BLACK) )  
         {
            theBoard.removePiece(Board.COL_F, Board.ROW_7);
         }    
       }
       else
       {
         // Black can only capture against the centre if the king isn't on the centre
         if(!theBoard.kingLocn.equals(Square.CENTRE) )
         {
           if( (to.col == Board.COL_D) && (to.row == Board.ROW_6) && (theBoard.pieces[Board.COL_E][Board.ROW_6] == Board.Piece.WHITE) )  
           {
              theBoard.removePiece(Board.COL_E, Board.ROW_6);
           }    
           else if( (to.col == Board.COL_H) && (to.row == Board.ROW_6) && (theBoard.pieces[Board.COL_G][Board.ROW_6] == Board.Piece.WHITE) )  
           {
              theBoard.removePiece(Board.COL_G, Board.ROW_6);
           }    
           else if( (to.col == Board.COL_F) && (to.row == Board.ROW_4) && (theBoard.pieces[Board.COL_F][Board.ROW_5] == Board.Piece.WHITE) )  
           {
              theBoard.removePiece(Board.COL_F, Board.ROW_5);
           }    
           else if( (to.col == Board.COL_F) && (to.row == Board.ROW_8) && (theBoard.pieces[Board.COL_F][Board.ROW_7] == Board.Piece.WHITE) )  
           {
              theBoard.removePiece(Board.COL_F, Board.ROW_7);
           }
         }
       }   
    }
    
    /*
     ** Returns true if move is possible and makes move.
     *  otherwise does nothing
     */
    public static boolean makeMoveIfPossible(final Square from,
                                             final Square to,
                                             Board theBoard)
    {
       boolean isMovePossible = isValidMove(from, to, theBoard);
// TODO - isValidMove could return moving dirn so we don't have to recalculate it
       if(isMovePossible)
       {       
          //
          // Move is valid
          //
          Board.MovingDirn movingDirn = findMovingDirn(from, to);
          Board.Piece movingPiece = theBoard.pieces[from.col][from.row];
          movePieceIgnoreTaking(from, to, movingPiece, theBoard);
       
          takePieceIfNecessary(to, movingDirn, theBoard);
       
          theBoard.isBlackMove = !theBoard.isBlackMove;
          theBoard.numMovesMade++;
      }
      return isMovePossible;          
    }
    
    public static boolean isValidMove(final Square from,
                                      final Square to,
                                      Board theBoard)
    {
       Board.MovingDirn movingDirn = findMovingDirn(from, to);
       Board.Piece movingPiece = theBoard.pieces[from.col][from.row];
       
       if( (movingDirn == Board.MovingDirn.SAME) || (movingDirn == Board.MovingDirn.DIAGONAL))
       {
    	 System.out.println("***Invalid move " + from + "->" + to + ", must move up, down, left or right");
         return false;
       }
       
       if(theBoard.pieces[to.col][to.row] != Board.Piece.EMPTY)
       {
      	 System.out.println("***Invalid move " + from + "->" + to + ", trying to move to blocked square");
          return false;
       }
       
       if(movingPiece != Board.Piece.KING)
       {
         if(to.col == Board.COL_K)
         {
            if( (to.row == Board.ROW_1) || (to.row == Board.ROW_11))
            {
               System.out.println("***Invalid move " + from + "->" + to + ", only king can move to corner square");
               return false;
            }
         }
         else if (to.col == Board.COL_A)
         {
            if( (to.row == Board.ROW_1) || (to.row == Board.ROW_11))
            {
               System.out.println("***Invalid move " + from + "->" + to + ", king can't move to corner square");
               return false;
            }
         }
         else if( (to.col == Board.COL_F) && (to.row == Board.ROW_6))
         {
           System.out.println("***Invalid move " + from + "->" + to + ", white can't move to centre square");
           return false;  
         }           
       }
       
       // Squares differ and are in the same row or col
       // Check dest is a valid type and that intermediary squares are vacant.
       boolean blocked = false;
       
       if(movingDirn == Board.MovingDirn.DOWN)
       {
           // Trying to move down
           for(int idx = from.row - 1; idx >= to.row; --idx)
           {
              if(theBoard.pieces[from.col][idx] != Board.Piece.EMPTY)
              {
                 System.out.println("***Invalid move " + from + "->" + to + ", trying to move through an occupied square");
                 blocked = true;  
                 break;
              }
           }
        }
        else if(movingDirn == Board.MovingDirn.UP)
        {
            // Trying to move up
            for(int idx = from.row + 1; idx <= to.row; ++idx)
            {
              if(theBoard.pieces[from.col][idx] != Board.Piece.EMPTY)
              {
                 System.out.println("***Invalid move " + from + "->" + to + ", trying to move through an occupied square");
                 blocked = true;  
                 break;
              }
            }
        }
        else if(movingDirn == Board.MovingDirn.LEFT)
        {
            // Trying to move left
            for(int idx = from.col - 1; idx >= to.col; --idx)
            {
              if(theBoard.pieces[idx][from.row] != Board.Piece.EMPTY)
              {
                 System.out.println("***Invalid move " + from + "->" + to + ", trying to move through an occupied square");
                 blocked = true;  
                 break;
              }
            }
        }
        else if(movingDirn == Board.MovingDirn.RIGHT)
        {
            // Trying to move right
            for(int idx = from.col + 1; idx <= to.col; ++idx)
            {
              if(theBoard.pieces[idx][from.row] != Board.Piece.EMPTY)
              {
                 System.out.println("***Invalid move " + from + "->" + to + ", trying to move through an occupied square");
                 blocked = true;  
                 break;
              }
            }
        }
       
        if(blocked)
           return false;
       
       return true;       
    }
    
    /*
     ** Returns true if there is an appropriate colour on the requested square
     * Only used from GUI
     */
    public static boolean isValidFromSq(final Board theBoard, int col, int row)
    {
       if( (theBoard.isBlackMove && theBoard.pieces[col][row] == Board.Piece.BLACK) ||
           (!theBoard.isBlackMove && ( (theBoard.pieces[col][row] == Board.Piece.WHITE) ||
                                       (theBoard.pieces[col][row] == Board.Piece.KING))  ) )
       {
           validMovesFromThisSquare(theBoard, col, row);           
           return true;
       }
       else
       {
           return false;
       }
    }
    

    public static void movePieceIgnoreTaking(final Square from, final Square to, final Board.Piece movingPiece, Board theBoard)
    {
       theBoard.pieces[from.col][from.row] = Board.Piece.EMPTY;
       
       // Are we moving the king?
       if(movingPiece == Board.Piece.KING)
       {
         theBoard.pieces[to.col][to.row] = Board.Piece.KING;
         
         theBoard.kingLocn.col = to.col;
         theBoard.kingLocn.row = to.row;
       }
       else if(theBoard.isBlackMove)
       {
          theBoard.pieces[to.col][to.row] = Board.Piece.BLACK;
         
          int pieceIndex = findIdxOfPiece(theBoard, from.col, from.row, true);
          if(pieceIndex != -1)
          {
            theBoard.blackPieces.set(pieceIndex, new Square(to.col, to.row));
          }            
          else
          {
             System.out.println("Error! Can't find black piece..");
          }
          
          increaseBarricadeScoreIfNecc(theBoard, to, true);
          decreaseBarricadeScoreIfNecc(theBoard, from, true);

       }
       else
       {
         theBoard.pieces[to.col][to.row] = Board.Piece.WHITE;
         
         int pieceIndex = findIdxOfPiece(theBoard, from.col, from.row, false);
         if(pieceIndex != -1)
         {
            theBoard.whitePieces.set(pieceIndex, new Square(to.col, to.row));
         }            
         else
         {
            System.out.println("Error! Can't find white piece..");
         }
          
         increaseBarricadeScoreIfNecc(theBoard, to, false);
         decreaseBarricadeScoreIfNecc(theBoard, from, false);
       }
       
       recalcKingFreedomScore(theBoard);
    }
    
    public static java.util.List<Square> validMovesFromThisSquare(final Board theBoard, int col, int row)
    {
       java.util.List<Square> validMoves = new java.util.ArrayList<Square>();
       
       Square from = new Square(col, row);
       
       Board.Piece piece = theBoard.pieces[col][row];
       
       boolean blocked = false;
       
       // Trying to move down
       for(int idx = from.row - 1; idx >= 0; --idx)
       {
         if(theBoard.pieces[from.col][idx] != Board.Piece.EMPTY)
         {
            blocked = true;  
            break;
         }
         else
         {
           Square toSquare = new Square(from.col, idx);
           
           if( (piece == Board.Piece.KING) || !toSquare.isAKingSquare()  )
           {
//              System.out.println("Can move down to " + toSquare);
              validMoves.add(toSquare);
           }
         }
       }
       
       // Trying to move up
       for(int idx = from.row + 1; idx < NUM_ROWS; ++idx)
       {
         if(theBoard.pieces[from.col][idx] != Board.Piece.EMPTY)
         {
            blocked = true;  
            break;
         }
         else
         {
           Square toSquare = new Square(from.col, idx);
           
           if( (piece == Board.Piece.KING) || !toSquare.isAKingSquare()  )
           {
//              System.out.println("Can move up to " + toSquare);
              validMoves.add(toSquare);
           }
         }
       }
       
       // Trying to move right
       for(int idx = from.col + 1; idx < NUM_COLS; ++idx)
       {
          if(theBoard.pieces[idx][from.row] != Board.Piece.EMPTY)
          {
             blocked = true;  
             break;
          }
          else
          {
            Square toSquare = new Square(idx, from.row);
           
            if( (piece == Board.Piece.KING) || !toSquare.isAKingSquare()  )
            {
//              System.out.println("Can move right to " + toSquare);
              validMoves.add(toSquare);
            }
          }
       }

       // Trying to move left
       for(int idx = from.col - 1; idx >= 0; --idx)
       {
          if(theBoard.pieces[idx][from.row] != Board.Piece.EMPTY)
          {
             blocked = true;  
             break;
          }
          else
          {
            Square toSquare = new Square(idx, from.row);
           
            if( (piece == Board.Piece.KING) || !toSquare.isAKingSquare()  )
            {
//              System.out.println("Can move left to " + toSquare);
              validMoves.add(toSquare);
            }
          }
       }
       return validMoves;
    }

}
