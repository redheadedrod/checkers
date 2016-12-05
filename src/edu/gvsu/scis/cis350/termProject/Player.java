package edu.gvsu.scis.cis350.termProject;

/**
 * Enum class that keeps track of who's turn it is.
 * 
 * @author Andrew Olesak
 * @version September 17, 2016
 */
public enum Player {
  /**
   * two colors which denote the player.
   */
  Black, Red;

  /**
   * changes the turn of the player.
   * 
   * @return the player whose turn is next
   */
  public Player nextTurn() {
    /*
     * this made checkstlye unhappy on account of inline conditional. 
     * keeping in case mine causes bugs or slowdown. thos solution 
     * is cooler though, i may ignore checkstlye for it return this
     * == Black ? Red : Black;
     */
    if (this == Black) {
      return Red;
    } else {
      return Black;
    }
  }
}