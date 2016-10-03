package termProject;

/**
 * Class creates a virtual layout for the board and provides the game logic
 * 
 * @author Andrew Olesak
 * @Version September 13, 2016
 */
public class CheckersDesign {

	private CheckersPiece board[][];
	private CheckersPiece piece;
	private Player player;
	
	/**
	 * Constructor creates the virtual board and places the pieces
	 */
	public CheckersDesign() {
		this.player = Player.Red;
		this.piece = null;
		this.board = new CheckersPiece[8][8];

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row == 0 || row == 2) {
					if (col % 2 == 1) {
						this.board[row][col] = new CheckersPiece(Player.Red);
					}
				}
				if (row == 1) {
					if (col % 2 == 0) {
						this.board[row][col] = new CheckersPiece(Player.Red);
					}
				}
				if (row == 5 || row == 7) {
					if (col % 2 == 0) {
						this.board[row][col] = new CheckersPiece(Player.Black);
					}
				}
				if (row == 6) {
					if (col % 2 == 1) {
						this.board[row][col] = new CheckersPiece(Player.Black);
					}
				}
			}
		}
	}

	/**
	 * Moves a piece from one spot on the board to another
	 * 
	 * @param startRow
	 *            the row of the piece to be moved
	 * @param startCol
	 *            the column of the piece to be moved
	 * @param endRow
	 *            the row of the location to which the piece will be moved
	 * @param endCol
	 *            the column of the location to which the piece will be moved
	 */
	public void movePiece(int startRow, int startCol, int endRow, int endCol) {
		this.piece = this.board[startRow][startCol];
		this.board[startRow][startCol] = null;
		this.board[endRow][endCol] = piece;
	}

	/**
	 * method decides whether or not a checkersPiece is allowed to move to a specific location.
	 * @param startRow the row of the piece to be moved
	 * @param startCol the column of the piece to be moved
	 * @param endRow the row of the new piece location
	 * @param endCol the column of the new piece location
	 * @return an array whose first element is true if the piece isn't jumping, but is a valid move, otherwise false.
	 *  The second element is true if the player is jumping and it is a valid move, otherwise false.
	 */
	public boolean[] canMove(int startRow, int startCol, int endRow, int endCol) {
		boolean[] type = { false, false };
		this.piece = this.board[startRow][startCol];
		if (this.board[endRow][endCol] != null) {
			return type;
		}
		if (this.piece.getPlayer() == Player.Red) {
			if (this.piece.isKing()) {
				return type;
			} else {
				if (startRow + 1 == endRow && (startCol + 1 == endCol || startCol - 1 == endCol)) {
					type[0] = true;
					return type;
				} else if (startRow + 2 == endRow && ((startCol + 2 == endCol
						&& this.board[startRow + 1][startCol + 1].getPlayer() == Player.Black)
						|| (startCol - 2 == endCol
								&& this.board[startRow + 1][startCol - 1].getPlayer() == Player.Black))) {
					type[1] = true;
					return type;
				}
			}
		} else {
			if (this.piece.isKing()) {
				type[0] = true;
				return type;
			} else {
				if (startRow - 1 == endRow && (startCol + 1 == endCol || startCol - 1 == endCol)) {
					type[1] = true;
					return type;
				} else if (startRow - 2 == endRow
						&& ((startCol - 2 == endCol && this.board[startRow - 1][startCol - 1].getPlayer() == Player.Red)
								|| (startCol + 2 == endCol
										&& this.board[startRow - 1][startCol + 1].getPlayer() == Player.Red))) {
					type[1] = true;
					return type;
				}
			}
		}
		return type;
	}

	/**
	 * Method decides the location of the player that has been jumped
	 * @param startRow the row of the current checkersPiece
	 * @param startCol the column of the current checkersPiece
	 * @param endRow the row of the new checkersPiece location
	 * @param endCol the column of the new checkersPiece location
	 * @return an array whose two elements are the row and column of the player who has been jumped respectively.
	 */
	public int[] jumped(int startRow, int startCol, int endRow, int endCol) {
		int[] location = new int[2];
		if (startRow < endRow) {
			location[0] = startRow + 1;
			if (startCol < endCol) {
				location[1] = startCol + 1;
			} else {
				location[1] = endCol + 1;
			}
		} else {
			location[0] = endRow + 1;
			if (startCol < endCol) {
				location[1] = startCol + 1;
			} else {
				location[1] = endCol + 1;
			}
		}
		this.board[location[0]][location[1]] = null;
		return location;
	}

	/**
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return this.player;
	}

	/**
	 * Switches the turn of the current player to the next player
	 */
	public void setCurrentPlayer() {
		this.player = this.player.nextTurn();
	}

	/**
	 * @param row the row of the checkersPiece
	 * @param col the column of the checkersPiece
	 * @return the checkersPiece that is at the given row and column.
	 */
	public CheckersPiece getPiece(int row, int col) {
		return this.board[row][col];
	}

}
