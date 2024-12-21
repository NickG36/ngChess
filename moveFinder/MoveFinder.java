
package moveFinder;

public class MoveFinder
{
    final int MOVE_DEPTH = 3;
//    final int MOVE_DEPTH = 2;
    
    Board realBoard;
    
    public MoveFinder(final Board theBoard)
    {
        this.realBoard = theBoard;
    }
    
    public static boolean isGameOver(final MovesRecord moves)
    {
        return moves.nextMoveWins();    
    }
    
    public MovesRecord calculateMove(final Move penultimateMove, 
    		Move nextMove)
    {
      
      int alpha = -100000000; // White's min
      int beta = 100000000; // Black's max
      
      MovesRecord moves = new MovesRecord(true);
//System.out.println("In MF::Calculating move");      
//System.out.println("Board:" + this.realBoard);

      if(this.realBoard.isBlackMove)
      {
         moves.blackMovesInitially = true;
         
         Square openingFrom = new Square();
         Square openingTo = new Square();
         boolean openingFound = false;
         
         findBestBlackMove(MOVE_DEPTH, alpha, beta, this.realBoard, moves, nextMove);
      }
      else
      {
         moves.blackMovesInitially = false;
         
         Square openingFrom = new Square();
         Square openingTo = new Square();
         boolean openingFound = false;
         
         findBestWhiteMove(MOVE_DEPTH, alpha, beta, this.realBoard, penultimateMove, moves, nextMove);
      }
      
//System.out.println("Calculated moves: " + moves + ".");

      // Calculating has finished. Set the best move as the move to make.
      // This may have been done already but not if the next move is a winning
      // one.
      //Controller.setMove(moves.getNextMove() );            
      nextMove = moves.getNextMove();
      return moves;
    }
    
    private int findBestBlackMove(int remSearchDepth, 
                                  int white_min,
                                  int black_max,
                                  final Board theBoard,
                                  MovesRecord moves,
                                  Move nextMove)
    {
	  /*if(Controller.timeUp)
	  {
//System.out.println("TIME UP! QUITTING");		  
         return 9999999;		  
	  }	*/
	  
        int bestScore = 10000000;
        boolean winningMove = false;
//System.out.println("In MF: fBBM. Rem depth: " + remSearchDepth);        
        MovesRecord bestMoveSeq = new MovesRecord(true); 

        // Then consider black pawn moves
        for(int black_idx = 0; black_idx < theBoard.blackPieces.size(); ++black_idx)
        {
           Square from_sq = theBoard.blackPieces.get(black_idx);
            
           java.util.List<Square> validToSquares = theBoard.validPawnMovesFromThisSquare(from_sq);
            
           Square best_w_to_square = new Square(); // Declared outside for loop to reduce num ctor calls
           for(Square to_sq : validToSquares)
           {
//               if(remSearchDepth == MOVE_DEPTH)
               //{
//System.out.println("Considering 1st move: " + from_sq + "-" + to_sq);				   
//				 currNextMove.set(from_sq, to_sq);   
		       //}
			   			   
               MovesRecord currMoveSeq = new MovesRecord(true);
               
               Board newBoard = new Board(theBoard);
               winningMove = newBoard.moveBlackPieceTo(black_idx, from_sq, to_sq);
               
               currMoveSeq.blackMoveMade(from_sq, to_sq, black_idx);
               
               if(winningMove)
               {
				 // Give bigger credit to a quicker win
                 moves.score = BoardRater.BLACK_WIN - remSearchDepth;
                 moves.add(currMoveSeq);
                 
                 return moves.score;             
               }
            
               int score = 0;
               if(remSearchDepth == 0)
               {
                  score = BoardRater.rateBoard(newBoard);
                  currMoveSeq.score = score;
               } 
               else
               {
                  Integer best_w_piece_idx = -1;
                  Move bestMove = new Move(); // NOT USED?
                  score = findBestWhiteMove(remSearchDepth - 1, 
                                            white_min,
                                            black_max,
                                            newBoard,
                                            Move.NullMove,
                                            currMoveSeq,
                                            bestMove);
               }
               
               /*if(remSearchDepth == MOVE_DEPTH)
               {
System.out.println("Score: " + score);				   
		       }*/
            
               if(score < bestScore)
               {
                 bestScore = score;
                 
                 if(black_max > bestScore)
                   black_max = bestScore;
                 
                 bestMoveSeq = currMoveSeq;
                 
                 if(remSearchDepth == MOVE_DEPTH)
                 {
//                   System.out.println("Best moves for B:" + bestMoveSeq); 
                   System.out.println(bestMoveSeq); 
                   nextMove = bestMoveSeq.getNextMove();
                   //Controller.setMove(bestMoveSeq.getNextMove() );            
                 }
               }
               
               if(black_max <= white_min)
               {
                 return bestScore;
               }
           }
        }
         moves.add(bestMoveSeq);
         
         return bestScore;
    }
    
    /*
     ** Returns best move seq + score
     */
    private int findBestWhiteMove(int remSearchDepth, 
                                  int white_min,
                                  int black_max,
                                  final Board theBoard,
                                  final Move penultimateMove,
                                  MovesRecord moves,
                                  Move bestNextMove)
    {
        boolean winningMove = false;

         // Consider king's moves first...
          
         java.util.List<Square> kingMoves = theBoard.validKingMovesFromThisSquare(theBoard.kingLocn);
         int bestScore = -10000000;
         // Store further best moves from this point on:         
         MovesRecord furtherBestMoveSeq = new MovesRecord(false); 
        
         for(Square currSquare : kingMoves)
         {
//            if(remSearchDepth == MOVE_DEPTH)
            //{
      	       //currNextMove.set(theBoard.kingLocn, currSquare);   
//System.out.println("Curr next move set to :" + currNextMove);				 
  	        //}
  	        
  	        // For time being, forbid a king move that was made 2 moves ago.
  	        /*if( (remSearchDepth == MOVE_DEPTH) &&
  	             penultimateMove.to.equals(currSquare) &&
  	             penultimateMove.from.equals(theBoard.kingLocn) ) 
  	        {
				System.out.println("REPITITION");
//				continue;
			}*/
  	        
            Board newBoard = new Board(theBoard);
            
            winningMove = newBoard.moveKingTo(currSquare);
            
            MovesRecord currMoveSeq = new MovesRecord(false);
            currMoveSeq.whiteMoveMade(theBoard.kingLocn, currSquare, -1);
            
            if(winningMove)
            {
                moves.add(currMoveSeq);
                // Give extra credit to this position if it is an early win
                // (we don't need to descend another level)
                moves.score = BoardRater.WHITE_WIN + remSearchDepth;
                
//System.out.println("White win: moves:" + moves);                
//System.out.println("White win: currNextMove:" + currNextMove);                
//                Controller.setMove(currNextMove);            
                
                return moves.score;
            }
            
            int score = 0;
            
            Square best_b_to_square = new Square(); // Declared outside for loop to reduce num ctor calls
            if(remSearchDepth == 0)
            {
                score = BoardRater.rateBoard(newBoard);
                currMoveSeq.score = score;
            } 
            else
            {
                Integer best_b_piece_idx = -1;
                Move nextMove = new Move();   // NOT USED???
                score = findBestBlackMove(remSearchDepth - 1, 
                                          white_min,
                                          black_max,
                                          newBoard,
                                          currMoveSeq,
                                          nextMove);
            }
            
  	        // For time being, forbid a king move that was made 2 moves ago.
  	        if( (remSearchDepth == MOVE_DEPTH) &&
  	             penultimateMove.to.equals(currSquare) &&
  	             penultimateMove.from.equals(theBoard.kingLocn) ) 
  	        {
				System.out.println("REPITITION");
				score -= BoardRater.WHITE_REPITITION_DETERRENT;
				System.out.println("REPITITION. Reducing the score from " + (score + BoardRater.WHITE_REPITITION_DETERRENT) + " to " + score);
//				continue;
			}
			
			
            if(score > bestScore)
            {
              bestScore = score;
              
              if(bestScore > white_min)
                white_min = bestScore;  
                  
              if(black_max <= white_min)
              {
                return bestScore;
              }
              
              furtherBestMoveSeq = currMoveSeq;
              
              if(remSearchDepth == MOVE_DEPTH)
              {
//                System.out.println("Best moves for W:" + furtherBestMoveSeq);                 
                System.out.println(furtherBestMoveSeq);                 
                   
                // This is the best move so far so store it
//                Controller.myNextMove.set(furtherBestMoveSeq.getNextMove() );
                bestNextMove = furtherBestMoveSeq.getNextMove();
                //Controller.setMove(furtherBestMoveSeq.getNextMove() );            
              }
              
            }
         }
         
         
         // Then consider white pawn moves
         for(int white_idx = 0; white_idx < theBoard.whitePieces.size(); ++white_idx)
         {
            Square from_sq = theBoard.whitePieces.get(white_idx);
            
            java.util.List<Square> validToSquares = theBoard.validPawnMovesFromThisSquare(from_sq);
            for(Square to_sq : validToSquares)
            {
/*               if(remSearchDepth == MOVE_DEPTH)
               {
				 currNextMove.set(from_sq, to_sq);   
//System.out.println("Curr next move set to :" + currNextMove);				 
			   }
			   */ 
  /*             if(remSearchDepth == MOVE_DEPTH)
               {
System.out.println("Considering 1st move: " + from_sq + "-" + to_sq);				   
		       }*/
				
               MovesRecord currMoveSeq = new MovesRecord(false);
            
               Board newBoard = new Board(theBoard);
               newBoard.moveWhitePieceTo(white_idx, from_sq, to_sq);
               currMoveSeq.whiteMoveMade(from_sq, to_sq, white_idx);
            
               int score = 0;
               if(remSearchDepth == 0)
               {
                 score = BoardRater.rateBoard(newBoard);
                 currMoveSeq.score = score;
               } 
               else
               {
                  Integer best_b_piece_idx = -1;
                  Square best_b_to_square = new Square(); // Declared outside for loop to reduce num ctor calls
                  
                  Move nextMove = new Move(); // USED??
                  score = findBestBlackMove(remSearchDepth - 1, 
                                            white_min,
                                            black_max,
                                            newBoard,
                                            currMoveSeq,
                                            nextMove);
               }
            
/*               if(remSearchDepth == MOVE_DEPTH)
               {
System.out.println("Score: " + score);				   
		       }
		       */ 
		       
               if(score > bestScore)
               {
                   bestScore = score;
                   
                   if(bestScore > white_min)
                     white_min = bestScore;  
                  
                   if(black_max <= white_min)
                   {
                     return bestScore;
                   }
                   
                   furtherBestMoveSeq = currMoveSeq;

                   if(remSearchDepth == MOVE_DEPTH)
                   {
//System.out.println("W: Best move seq: " + furtherBestMoveSeq);                   
System.out.println(furtherBestMoveSeq);                   
//                      System.out.println("Best next W move:" + furtherBestMoveSeq.getNextMove() ); 
                   
                      // This is the best move so far so store it
//                      Controller.myNextMove.set(furtherBestMoveSeq.getNextMove() );            
					   bestNextMove = furtherBestMoveSeq.getNextMove();
                      //Controller.myNextMove.set(furtherBestMoveSeq.getNextMove() );            
                   }
               }
            }
            // See if best score
         }
         moves.add(furtherBestMoveSeq);
         
         return bestScore;
    }
    
};
