
package com.nick.movefinder;

/**
 *
 * @author Nicholas
 */
public class Square {
    public int col = Board.COL_A;
    public int row = Board.ROW_1;
    
    public final static Square TOP_LEFT = new Square(Board.COL_A, Board.ROW_11);
    public final static Square TOP_RIGHT = new Square(Board.COL_K, Board.ROW_11);
    public final static Square BOTTOM_LEFT = new Square(Board.COL_A, Board.ROW_1);
    public final static Square BOTTOM_RIGHT = new Square(Board.COL_K, Board.ROW_1);
    
    public final static Square CENTRE = new Square(Board.COL_F, Board.ROW_6);

    public final static Square BARRICADE_TL_1 = new Square(Board.COL_A, Board.ROW_9);
    public final static Square BARRICADE_TL_2 = new Square(Board.COL_B, Board.ROW_10);
    public final static Square BARRICADE_TL_3 = new Square(Board.COL_C, Board.ROW_11);
    
    public final static Square BARRICADE_TR_1 = new Square(Board.COL_I, Board.ROW_11);
    public final static Square BARRICADE_TR_2 = new Square(Board.COL_J, Board.ROW_10);
    public final static Square BARRICADE_TR_3 = new Square(Board.COL_K, Board.ROW_9);
    
    public final static Square BARRICADE_BL_1 = new Square(Board.COL_A, Board.ROW_3);
    public final static Square BARRICADE_BL_2 = new Square(Board.COL_B, Board.ROW_2);
    public final static Square BARRICADE_BL_3 = new Square(Board.COL_C, Board.ROW_1);
    
    public final static Square BARRICADE_BR_1 = new Square(Board.COL_I, Board.ROW_1);
    public final static Square BARRICADE_BR_2 = new Square(Board.COL_J, Board.ROW_2);
    public final static Square BARRICADE_BR_3 = new Square(Board.COL_K, Board.ROW_3);

    public enum BarricadeType {NONE, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT};
    
    public final static Square NEXT_TO_CORNER_1 = new Square(Board.COL_A, Board.ROW_10);
    public final static Square NEXT_TO_CORNER_2 = new Square(Board.COL_A, Board.ROW_2);
    public final static Square NEXT_TO_CORNER_3 = new Square(Board.COL_B, Board.ROW_1);
    public final static Square NEXT_TO_CORNER_4 = new Square(Board.COL_B, Board.ROW_11);
    public final static Square NEXT_TO_CORNER_5 = new Square(Board.COL_J, Board.ROW_11);
    public final static Square NEXT_TO_CORNER_6 = new Square(Board.COL_J, Board.ROW_1);
    public final static Square NEXT_TO_CORNER_7 = new Square(Board.COL_K, Board.ROW_10);
    public final static Square NEXT_TO_CORNER_8 = new Square(Board.COL_K, Board.ROW_2);
    
    
    
    public final static Square A1 = new Square(Board.COL_A, Board.ROW_1);
    public final static Square A2 = new Square(Board.COL_A, Board.ROW_2);
    public final static Square A4 = new Square(Board.COL_A, Board.ROW_4);
    public final static Square A8 = new Square(Board.COL_A, Board.ROW_8);
    public final static Square A10 = new Square(Board.COL_A, Board.ROW_10);
   
    public final static Square B1 = new Square(Board.COL_B, Board.ROW_1);
    public final static Square B2 = new Square(Board.COL_B, Board.ROW_2);
    public final static Square B3 = new Square(Board.COL_B, Board.ROW_3);
    public final static Square B5 = new Square(Board.COL_B, Board.ROW_5);
    public final static Square B6 = new Square(Board.COL_B, Board.ROW_6);
    public final static Square B7 = new Square(Board.COL_B, Board.ROW_7);
    public final static Square B9 = new Square(Board.COL_B, Board.ROW_9);
    public final static Square B10 = new Square(Board.COL_B, Board.ROW_10);
    public final static Square B11 = new Square(Board.COL_B, Board.ROW_11);
   
    public final static Square C2 = new Square(Board.COL_C, Board.ROW_2);
    public final static Square C3 = new Square(Board.COL_C, Board.ROW_3);
    public final static Square C7 = new Square(Board.COL_C, Board.ROW_7);
    public final static Square C9 = new Square(Board.COL_C, Board.ROW_9);
    public final static Square C10 = new Square(Board.COL_C, Board.ROW_10);

    public final static Square D1 = new Square(Board.COL_D, Board.ROW_1);
    public final static Square D6 = new Square(Board.COL_D, Board.ROW_6);
    public final static Square D11 = new Square(Board.COL_D, Board.ROW_11);
    
    public final static Square E2 = new Square(Board.COL_E, Board.ROW_2);
    public final static Square E3 = new Square(Board.COL_E, Board.ROW_3);
    public final static Square E5 = new Square(Board.COL_E, Board.ROW_5);
    public final static Square E6 = new Square(Board.COL_E, Board.ROW_6);
    public final static Square E7 = new Square(Board.COL_E, Board.ROW_7);
    public final static Square E9 = new Square(Board.COL_E, Board.ROW_9);
    public final static Square E10 = new Square(Board.COL_E, Board.ROW_10);
   
    public final static Square F2 = new Square(Board.COL_F, Board.ROW_2);
    public final static Square F4 = new Square(Board.COL_F, Board.ROW_4);
    public final static Square F5 = new Square(Board.COL_F, Board.ROW_5);
    public final static Square F6 = new Square(Board.COL_F, Board.ROW_6);
    public final static Square F7 = new Square(Board.COL_F, Board.ROW_7);
    public final static Square F8 = new Square(Board.COL_F, Board.ROW_8);
    public final static Square F10 = new Square(Board.COL_F, Board.ROW_10);
   
    public final static Square G2 = new Square(Board.COL_G, Board.ROW_2);
    public final static Square G3 = new Square(Board.COL_G, Board.ROW_3);
    public final static Square G5 = new Square(Board.COL_G, Board.ROW_5);
    public final static Square G6 = new Square(Board.COL_G, Board.ROW_6);
    public final static Square G7 = new Square(Board.COL_G, Board.ROW_7);
    public final static Square G9 = new Square(Board.COL_G, Board.ROW_9);
    public final static Square G10 = new Square(Board.COL_G, Board.ROW_10);
   
    public final static Square H1 = new Square(Board.COL_H, Board.ROW_1);
    public final static Square H6 = new Square(Board.COL_H, Board.ROW_6);
    public final static Square H11 = new Square(Board.COL_H, Board.ROW_11);
    
    public final static Square I2 = new Square(Board.COL_I, Board.ROW_2);
    public final static Square I3 = new Square(Board.COL_I, Board.ROW_3);
    public final static Square I9 = new Square(Board.COL_I, Board.ROW_9);
    public final static Square I10 = new Square(Board.COL_I, Board.ROW_10);
    
    public final static Square J1 = new Square(Board.COL_J, Board.ROW_1);
    public final static Square J2 = new Square(Board.COL_J, Board.ROW_2);
    public final static Square J3 = new Square(Board.COL_J, Board.ROW_3);
    public final static Square J5 = new Square(Board.COL_J, Board.ROW_5);
    public final static Square J6 = new Square(Board.COL_J, Board.ROW_6);
    public final static Square J7 = new Square(Board.COL_J, Board.ROW_7);
    public final static Square J9 = new Square(Board.COL_J, Board.ROW_9);
    public final static Square J10 = new Square(Board.COL_J, Board.ROW_10);
    public final static Square J11 = new Square(Board.COL_J, Board.ROW_11);
   
    public final static Square K2 = new Square(Board.COL_K, Board.ROW_2);
    public final static Square K4 = new Square(Board.COL_K, Board.ROW_4);
    public final static Square K8 = new Square(Board.COL_K, Board.ROW_8);
    public final static Square K10 = new Square(Board.COL_K, Board.ROW_10);
    
    
    public Square()
    {
        
    }
    public Square(int col, int row)
    {
       this.col = col;
       this.row = row;
    }
    
    public Square(final Square rhs)
    {
       this.col = rhs.col;
       this.row = rhs.row;
    }
    
    public BarricadeType isBarricadeSquare()
    {
      BarricadeType barType = BarricadeType.NONE;
      if( this.equals(BARRICADE_TL_1) || this.equals(BARRICADE_TL_2) || this.equals(BARRICADE_TL_3) )
      {
         barType = BarricadeType.TOP_LEFT;
      }
      else if( this.equals(BARRICADE_BL_1) || this.equals(BARRICADE_BL_2) || this.equals(BARRICADE_BL_3) )
      {
         barType = BarricadeType.BOTTOM_LEFT;
      }
      else if( this.equals(BARRICADE_TR_1) || this.equals(BARRICADE_TR_2) || this.equals(BARRICADE_TR_3) )
      {
         barType = BarricadeType.TOP_RIGHT;
      }
      else if( this.equals(BARRICADE_BR_1) || this.equals(BARRICADE_BR_2) || this.equals(BARRICADE_BR_3) )
      {
         barType = BarricadeType.BOTTOM_RIGHT;
      }
      
      return barType;
    }
    
    public boolean isNextToCorner()
    {
      boolean result = false;  
        
      if(this.equals(NEXT_TO_CORNER_1) ||
         this.equals(NEXT_TO_CORNER_2) ||
         this.equals(NEXT_TO_CORNER_3) ||
         this.equals(NEXT_TO_CORNER_4) ||
         this.equals(NEXT_TO_CORNER_5) ||
         this.equals(NEXT_TO_CORNER_6) ||
         this.equals(NEXT_TO_CORNER_7) ||
         this.equals(NEXT_TO_CORNER_8) )
        result = true;
      
      return result;
    }
    
    public boolean isAnEdge()
    {
      boolean result = false;  
      
      if( (this.row == Board.ROW_1) || (this.row == Board.ROW_11) ||
          (this.col == Board.COL_A) || (this.col == Board.COL_K) )
        result = true;
      
      return result;
    }
    /*
     ** Returns true if the square is a corner or the centre, false ow
     */
    public boolean isAKingSquare()
    {
      boolean result = false;
      
      if( this.equals(TOP_LEFT) || this.equals(TOP_RIGHT) || this.equals(BOTTOM_LEFT) || this.equals(BOTTOM_RIGHT) ||
          this.equals(CENTRE))
        result = true;
      
      return result;
    }
    
    public boolean isACorner()
    {
      boolean result = false;
      
      if( this.equals(TOP_LEFT) || this.equals(TOP_RIGHT) || this.equals(BOTTOM_LEFT) || this.equals(BOTTOM_RIGHT) )
        result = true;
      
      return result;
    }
    
    public boolean isNextTo(final Square rhs)
    {
       boolean result = false;
       if( sameRow(rhs) || sameCol(rhs) )
       {
         if(sameRow(rhs))
         {
           // See if in adjacent columns
           if( (this.col == rhs.col + 1) || (this.col == rhs.col - 1))
           {
              result = true;  
           }
         }
         else
         {
           // See if in adjacent row
           if( (this.row == rhs.row + 1) || (this.row == rhs.row - 1))
           {
              result = true;  
           }
         }
       }
       
       // Just check that squares aren't the same
       if(result && equals(rhs))
           result = false;
       return result;
    }

    public boolean isIn3x3FromCorner()
    {
       boolean result = false;

       if(this.row == Board.ROW_3)
       {
         if(  (this.col == Board.COL_C) || (this.col == Board.COL_I) )
         {
           result = true; 
         }
       }
       else if(this.row == Board.ROW_9)
       {
         if(  (this.col == Board.COL_C) || (this.col == Board.COL_I) )
         {
           result = true; 
         }
       }

       return result;
    }
    
    public boolean isInRowOrCol2Or3() 
    {
	  boolean result = false;
	  
	  if(  (this.row == Board.ROW_2) || (this.row == Board.ROW_3) ||
	       (this.row == Board.ROW_9) || (this.row == Board.ROW_10) ||
	       (this.col == Board.COL_B) || (this.col == Board.COL_C) ||
	       (this.col == Board.COL_I) || (this.col == Board.COL_J) )
      {
		 result = true; 
	  }	       
		
	  return result;	
	}
	
    public boolean equals(final Square rhs)
    {
       if(  (this.col == rhs.col) &&
            (this.row == rhs.row) )
         return true;
       else
         return false;
    }
    
    public boolean sameRow(final Square rhs)
    {
      if(this.row == rhs.row)
         return true;
      else
         return false;
    }
    
    public boolean sameCol(final Square rhs)
    {
      if(this.col == rhs.col)
         return true;
      else
         return false;
    }
    public int distanceFrom(final Square rhs)
    {
	  int result = 0;	
	  
	  result = Math.abs(rhs.col - this.col) + Math.abs(rhs.row - this.row);
	  	
      return result;	
	}
	
    public void set(final Square rhs)
    {
	  this.col = rhs.col;
	  this.row = rhs.row;	
	}
	
    public String toString()
    {
       String currString = "";
       
       if(col == Board.COL_A)
           currString += 'a';
       else if(col == Board.COL_B)
           currString += 'b';
       else if(col == Board.COL_C)
           currString += 'c';
       else if(col == Board.COL_D)
           currString += 'd';
       else if(col == Board.COL_E)
           currString += 'e';
       else if(col == Board.COL_F)
           currString += 'f';
       else if(col == Board.COL_G)
           currString += 'g';
       else if(col == Board.COL_H)
           currString += 'h';
       else if(col == Board.COL_I)
           currString += 'i';
       else if(col == Board.COL_J)
           currString += 'j';
       else if(col == Board.COL_K)
           currString += 'k';
       
       if(row == Board.ROW_1)
           currString += '1';
       else if(row == Board.ROW_2)
           currString += '2';
       else if(row == Board.ROW_3)
           currString += '3';
       else if(row == Board.ROW_4)
           currString += '4';
       else if(row == Board.ROW_5)
           currString += '5';
       else if(row == Board.ROW_6)
           currString += '6';
       else if(row == Board.ROW_7)
           currString += '7';
       else if(row == Board.ROW_8)
           currString += '8';
       else if(row == Board.ROW_9)
           currString += '9';
       else if(row == Board.ROW_10)
           currString += "10";
       else if(row == Board.ROW_11)
           currString += "11";
       
       return currString;
    }
    
    public String toStringTxConvention()
    {
       String currString = "";
       
       if(col == Board.COL_A)
           currString += 'a';
       else if(col == Board.COL_B)
           currString += 'b';
       else if(col == Board.COL_C)
           currString += 'c';
       else if(col == Board.COL_D)
           currString += 'd';
       else if(col == Board.COL_E)
           currString += 'e';
       else if(col == Board.COL_F)
           currString += 'f';
       else if(col == Board.COL_G)
           currString += 'g';
       else if(col == Board.COL_H)
           currString += 'h';
       else if(col == Board.COL_I)
           currString += 'i';
       else if(col == Board.COL_J)
           currString += 'j';
       else if(col == Board.COL_K)
           currString += 'k';
       
       if(row == Board.ROW_1)
           currString += '0';
       else if(row == Board.ROW_2)
           currString += '1';
       else if(row == Board.ROW_3)
           currString += '2';
       else if(row == Board.ROW_4)
           currString += '3';
       else if(row == Board.ROW_5)
           currString += '4';
       else if(row == Board.ROW_6)
           currString += '5';
       else if(row == Board.ROW_7)
           currString += '6';
       else if(row == Board.ROW_8)
           currString += '7';
       else if(row == Board.ROW_9)
           currString += '8';
       else if(row == Board.ROW_10)
           currString += "9";
       else if(row == Board.ROW_11)
           currString += "10";
       
       return currString;
    }
}
