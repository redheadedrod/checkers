package termProject;

/**
 * Enum class that keeps track of who's turn it is.
 * 
 * @author Andrew Olesak
 * @version September 17, 2016
 */
public enum Player {
	Black, Red;

	/**
	 * changes the turn of the player
	 * 
	 * @return the player whose turn is next
	 */
	public Player nextTurn() {
		return this == Black ? Red : Black;
	}
}
