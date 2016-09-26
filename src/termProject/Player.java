package termProject;

public enum Player {
	Black, Red;
	
	/**
	 * changes the turn of the player
	 * @return the player whose turn is next
	 */
	public Player nextTurn(){
		return this == Black? Red : Black;
	}
}
