/**
 * Author: Ross Marshall
 * 
 * I read about the Weasel program in "The Greatest Show On Earth" by
 * Richard Dawkins.  I thought it was pretty cool, and was inspired to code 
 * up a version.
 * 
 * From Wikipedia:
 * Although Dawkins did not provide the source code for his program, 
 * a "Weasel" style algorithm could run as follows.
 *
 *  1. Start with a random string of 28 characters.
 *  2. Make 100 copies of this string, with a 5% chance per character of that 
 *     character being replaced with a random character.
 *  3. Compare each new string with the target "METHINKS IT IS LIKE A WEASEL", 
 *     and give each a score (the number of letters in the string that are 
 *     correct and in the correct position).
 *  4. If any of the new strings has a perfect score (28), halt.
 *  5. Otherwise, take the highest scoring string, and go to step 2.
 *
 * For these purposes, a "character" is any uppercase letter, or a space. 
 * The number of copies per generation, and the chance of mutation per letter 
 * are not specified in Dawkins' book; 100 copies and a 5% mutation rate are
 * examples. Correct letters are not "locked": each correct letter may become 
 * incorrect in subsequent generations.
 * 
 * Algorithm from Wikipedia: http://en.wikipedia.org/wiki/Weasel_program
 * 
 */
package weasel;

public class Weasel {

   private static String target; 
   private static String darwin;
   private static String random;
   
   private static int darwinScore;

   private static final int LENGTH = 28;
   private static final int MIN    = 65;
   private static final int MAX    = 90;
   private static final int SPACE  = 32;

   private String [] selectionGeneration = new String[100];
                       //         1    1    2    2
                       //....5....0....5....0....5..
   private String dna = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

   Weasel() {
       //                  1    1    2    2
       //        0....5....0....5....0....5..
       target = "METHINKS IT IS LIKE A WEASEL";
   }

   public boolean percentChance( int percent ) {
      if ( (int)(Math.random() * 100) > percent ) 
         return true;
      else
         return false; 
   }

   public String makeCopy( String str ) {
      String returnString = "";
      for ( int i = 0 ; i < LENGTH ; i++ ) {
	  if ( percentChance( 5 ) ) {
            returnString = returnString + Character.toString( str.charAt(i) );
          } else {
            returnString = returnString + 
                    Character.toString( (char)getRandomChar() );
          }
      }
      return returnString;
   }

   public int scoreString( String gen ) {
      int match = 0;
      for ( int i = 0 ; i < LENGTH ; i++ ) {
          if ( (int)(gen.charAt(i)) == (int)(target.charAt(i)) ) {
              match++;
          }
      }

      return( match );
   }

   public String scoreGeneration(String [] generation) {
      int bestIndex = 0;
      int score;

      for ( int i = 0 ; i < 100 ; i++ ) {
         score = scoreString( generation[i] ); 
         if ( score > bestIndex ) {
            bestIndex = i;
            darwinScore = score;
         } 
      } 
      
      return( generation[bestIndex] );
   }

   public String getDarwinGeneration( String initial ) {
      selectionGeneration[0] = initial; 
            
      for ( int i = 1 ; i < 100 ; i++ ) {
          selectionGeneration[i] = makeCopy( initial );       
      }

      return( scoreGeneration( selectionGeneration ) );
   }

   public int getRandomChar() {
      return (int)dna.charAt( (int)(Math.random() * 27) );
   }

   public String getRandomGeneration() {
      // A-Z (65-90) or space (32)
      int   randomNum;
      String generation = "";

      for ( int i = 0 ; i < LENGTH ; i++ ) {
         randomNum = getRandomChar();
         generation = generation + Character.toString( (char)randomNum );
      }

      return generation;
   }
   
   public void commandLinePrint(long count) {
       System.out.println( count + 
                          ": Random: [" + random +
                          "] Darwin: [" + darwin + 
                          "] score: " + darwinScore );
   }

   
   public static void main ( String [] args ) {
      char ch;
      long count = 0;

      Weasel w = new Weasel();
      System.out.println( w.target.charAt(1) ); 
      System.out.println( w.target.charAt(17) ); 

      ch = (char)MIN;
      System.out.println( ch );

      ch = (char)MAX;
      System.out.println( ch );

      ch = (char)SPACE;
      System.out.println( ch );
     
      System.out.println( "------------- Begin Weasel --------------" );

      darwin = w.getRandomGeneration();
 
      while ( !darwin.equals(target) ) {
         random = w.getRandomGeneration();
         darwin = w.getDarwinGeneration( darwin );
         w.commandLinePrint(count++);
      }
   }
}
