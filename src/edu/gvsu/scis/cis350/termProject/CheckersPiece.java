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
	 * Constructor creates a checkersPiece object.
	 * @param o the color of the player
	 */
	public CheckersPiece(final Player o) {
		this.isKing = false;
		this.owner = o;
	}
	
	/**
	 * @return the type of the current player
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
	
}
