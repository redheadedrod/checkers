package edu.gvsu.scis.cis350.termProject;

/**
 * Class creates a virtual layout for the board and provides the game logic.
 * 
 * @author Andrew Olesak
 */
public class CheckersModel {

	/**
	 * a 2-D array of type CheckersPiece
	 *  to record the state of the board.
	 */
    private CheckersPiece[][] board;
    /**
     * a checkersPiece object which is a single game piece.
     */
    private CheckersPiece piece;
    /**
     * a Player type object(enum) which dictates 
     * which player's turn it is.
     */
    private Player player;
    /**
     * an int denoting board dimensions.
     */
    private static final int BOARD_DIM = 8;

    /**
     * Constructor creates the virtual board and places the pieces.
     */
    public CheckersModel() {
        this.player = Player.Black;
        this.piece = null;
        this.board = new CheckersPiece[BOARD_DIM][BOARD_DIM];

        for (int row = 0; row < BOARD_DIM; row++) {
            for (int col = 0; col < BOARD_DIM; col++) {
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
     * Moves a piece from one spot on the board to another.
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
    public final void movePiece(final int startRow, final int startCol,
    		final int endRow, final int endCol) {
        this.piece = this.board[startRow][startCol];
        this.board[startRow][startCol] = null;
        this.board[endRow][endCol] = piece;
    }

    /**
     * method decides whether or not a checkersPiece is allowed to move to a
     * specific location.
     * 
     * @param startRow
     *            the row of the piece to be moved
     * @param startCol
     *            the column of the piece to be moved
     * @param endRow
     *            the row of the new piece location
     * @param endCol
     *            the column of the new piece location
     * @return an array whose first element is true if the piece isn't jumping,
     *         but is a valid move, otherwise false. The second element is true
     *         if the player is jumping and it is a valid move, otherwise false.
     */
    public final boolean[] canMove(final int startRow, final int startCol,
    		final int endRow, final int endCol) {
        boolean[] type = { false, false };
        this.piece = this.board[startRow][startCol];
        if (this.piece == null) {
            return type;
        }
        if (this.board[endRow][endCol] != null) {
            return type;
        }
        if (this.piece.getPlayer() == Player.Red) {
            if (startRow + 1 == endRow && (startCol + 1 == endCol
            		|| startCol - 1 == endCol)) {
                type[0] = true;
                return type;
            } else if (startRow + 2 == endRow) {
                if (startCol + 2 == endCol) {
                    if (this.board[startRow + 1][startCol + 1] == null) {
                        return type;
                    } else if (this.board[startRow + 1]
                    		[startCol + 1].getPlayer() == Player.Black) {
                        type[1] = true;
                        return type;
                    }
                } else if (startCol - 2 == endCol) {
                    if (this.board[startRow + 1][startCol - 1] == null) {
                        return type;
                    } else if (this.board[startRow + 1]
                    		[startCol - 1].getPlayer() == Player.Black) {
                        type[1] = true;
                        return type;
                    }
                }
            }
        } else {
            if (startRow - 1 == endRow && (startCol + 1 == endCol
            		|| startCol - 1 == endCol)) {
                type[0] = true;
                return type;
            } else if (startRow - 2 == endRow) {
                if (startCol - 2 == endCol) {
                    if (this.board[startRow - 1][startCol - 1] == null) {
                        return type;
                    } else if (this.board[startRow - 1]
                    		[startCol - 1].getPlayer() == Player.Red) {
                        type[1] = true;
                        return type;
                    }
                } else if (startCol + 2 == endCol) {
                    if (this.board[startRow - 1][startCol + 1] == null) {
                        return type;
                    } else if (this.board[startRow - 1]
                    		[startCol + 1].getPlayer() == Player.Red) {
                        type[1] = true;
                        return type;
                    }
                }
            }
        }
        return type;

    }

    /**
     * Method decides the location of the player that has been jumped.
     * 
     * @param startRow
     *            the row of the current checkersPiece
     * @param startCol
     *            the column of the current checkersPiece
     * @param endRow
     *            the row of the new checkersPiece location
     * @param endCol
     *            the column of the new checkersPiece location
     * @return an array whose two elements are the row and column of the player
     *         who has been jumped respectively.
     */
    public final int[] jumped(final int startRow, final int startCol,
    		final int endRow, final int endCol) {
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
     * Method checks to see if there are any remaining pieces of a certain color
     * to determine whether there is a winner or not.
     * 
     * numOfPieces 
     * 			  keeps track of how many remaining pieces are still on the
     *            board from the opposing team
     * pRow
     *            the row of the remaining piece
     * pCol
     *            the column of the remaining piece
     * @return true if there are no remaining pieces of a certain color,
     *         otherwise false
     */
    public final boolean isWinner() {
        int numOfPieces = 0;
        int pRow = 0;
        int pCol = 0;
        if (this.player == Player.Black) {
            for (int row = 0; row < BOARD_DIM; row++) {
                for (int col = 0; col < BOARD_DIM; col++) {
                    if (this.board[row][col] == null) {
                        continue;
                    } else if (this.board[row][col].getPlayer() == Player.Red) {
                        numOfPieces++;
                        if (numOfPieces > 1) {
                            return false;
                        } else {
                            pRow = row;
                            pCol = col;
                        }
                    }
                }
            }
        } else {
            for (int row = 0; row < BOARD_DIM; row++) {
                for (int col = 0; col < BOARD_DIM; col++) {
                    if (this.board[row][col] == null) {
                        continue;
                    } else if (this.board[row][col].getPlayer()
                    		== Player.Black) {
                        numOfPieces++;
                        if (numOfPieces > 1) {
                            return false;
                        } else {
                            pRow = row;
                            pCol = col;
                        }
                    }
                }
            }
        }
        if (numOfPieces == 1 && canMoveAnywhere(pRow, pCol)) {
            return false;
        }
        return true;
    }

    /**
     * Method calculates whether or not a given piece can make any allowed move
     * in the game.
     * 
     * @param pRow
     *            the row of the piece
     * @param pCol
     *            the column of the piece
     * @return true if the piece has at least one possible move, otherwise false
     */
    public final boolean canMoveAnywhere(final int pRow, final int pCol) {
        for (int row = 0; row < BOARD_DIM; row++) {
            for (int col = 0; col < BOARD_DIM; col++) {
                if (this.canMove(pRow, pCol, row, col)[0]
                		|| this.canMove(pRow, pCol, row, col)[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the current player
     */
    public final Player getCurrentPlayer() {
        return this.player;
    }

    /**
     * Switches the turn of the current player to the next player. 
     */
	public final void setNextPlayer() {
		this.player = this.player.nextTurn();
	}

    /**
     * @param row
     *            the row of the checkersPiece
     * @param col
     *            the column of the checkersPiece
     * @return the checkersPiece that is at the given row and column.
     */
    public final CheckersPiece getPiece(final int row, final int col) {
        return this.board[row][col];
    }

    /**
     * Removes a piece from a specific location.
     * 
     * @param row
     *            the row of the piece
     * @param col
     *            the col of the piece
     */
    public final void removePiece(final int row, final int col) {
        this.board[row][col] = null;
    }

}
