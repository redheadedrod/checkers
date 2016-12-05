package edu.gvsu.scis.cis350.termProject;

/**
 * Class provides all of the functionality of a checkers piece.
 * 
 * @author Andrew Olesak
 * @version September 13,2016
 */
public class CheckersPiece {

  /**
   * a boolean to determine if a piece is king.
   */
  private boolean isKing;
  /**
   * a Player type object to determine who owns a piece.
   */
  private Player owner;

  /**
   * Constructor for the checkers piece.
   * 
   * @param o
   *          is a player object of a certain color
   * @param king
   *          is a boolean to determine if it is a king or not
   */
  public CheckersPiece(final Player o, final boolean king) {
    this.isKing = king;
    this.owner = o;
  }

  /**
   * Constructor creates a checkersPiece object.
   * 
   * @param o
   *          the color of the player
   */
  public CheckersPiece(final Player o) {
    this.isKing = false;
    this.owner = o;
  }

  /**
   * @return the type of the current player.
   */
  public final Player getPlayer() {
    return this.owner;
  }

  /**
   * @return true if the checkersPiece is a king, otherwise false
   */
  public final boolean isKing() {
    return isKing;
  }

  /**
   * Sets whether or not the piece is a king.
   * 
   * @param k
   *          true if the piece is a king, otherwise false
   */
  public final void setKing(final boolean k) {
    this.isKing = k;
  }
}
