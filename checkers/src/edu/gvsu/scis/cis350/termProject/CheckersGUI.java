package edu.gvsu.scis.cis350.termProject;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.*;

/**
 * CheckersGUI creates a board or the game to be played on by way of extending
 * JPanel and implementing ActionListnener.
 * 
 * @author Andrew Olesak, Joel Vander Klipp, Rodney Fulk
 *
 */
public class CheckersGUI extends JPanel implements ActionListener {

	/**
	 * a JButton representing the board.
	 */
	private JButton[][] board;
	/**
	 * a CheckersPiece object to implement pieces in the game.
	 */
	private CheckersPiece piece;
	/**
	 * a CheckersModel object which is the driver for game logic.
	 */
	private CheckersModel model;
	/**
	 * Icon representing red pieces.
	 */
	private Icon redIcon;
	/**
	 * Icon representing black pieces.
	 */
	private Icon blackIcon;
	/**
	 * Icon for an empty space.
	 */
	private Icon blank;
	/**
	 * boolean for deciding action in a game.
	 */
	private boolean firstClick;
	/**
	 * integer array for determining moves.
	 */
	private int[] moves;
	/**
	 * JLabel which displays the player whose turn it is.
	 */
	private JLabel displayCurrentPlayer;
	/**
	 * String which holds the current player.
	 */
	private String currentPlayer;
	/**
	 * string holding name of player1.
	 */
	private String player1;
	/**
	 * string holding the name of player2.
	 */
	private String player2;
	/**
	 * an int denoting dimensions of the board.
	 */
	private static final int BOARD_DIM = 8;

	/**
	 * Constructor creates the user board and sets values to the instance.
	 * variables.
	 */
	public CheckersGUI() {
		this.board = new JButton[BOARD_DIM][BOARD_DIM];
		this.piece = null;
		this.model = new CheckersModel();
		this.redIcon = new ImageIcon("Red.png");
		this.blackIcon = new ImageIcon("Black.png");
		this.blank = new ImageIcon("Blank.png");
		this.firstClick = true;
		this.moves = new int[4];
		this.player1 = JOptionPane.showInputDialog(null, 
				"Enter the name of the first player.");
		this.player2 = JOptionPane.showInputDialog(null, 
				"Enter the name of the second player.");
		JOptionPane.showMessageDialog(null, player1 
				+ " is Black and " + player2 + " is Red.");
		this.currentPlayer = "It is " + player1 + "'s turn.";

		// creates a layout for the board
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridBagLayout());
		GridBagConstraints place = new GridBagConstraints();
		place.gridheight = BOARD_DIM;
		place.gridwidth = BOARD_DIM;
		place = new GridBagConstraints();

		for (int row = 0; row < BOARD_DIM; row++) {
			for (int col = 0; col < BOARD_DIM; col++) {
				if ((row + col) % 2 == 0) {
					this.board[row][col] = new JButton();
					this.board[row][col].setBackground(Color.BLACK);
				} else {
					this.board[row][col] = new JButton();
					this.board[row][col].setBackground(Color.RED);
				}
				place.gridy = row;
				place.gridx = col;
				this.board[row][col].addActionListener(this);
				this.board[row][col]
						.setBorder(BorderFactory.createEmptyBorder());
				boardPanel.add(this.board[row][col], place);
			}
		}

		// create a panel to show who's turn it is
		JPanel currentPlayerLabel = new JPanel();
		this.displayCurrentPlayer = new JLabel(this.currentPlayer);
		currentPlayerLabel.add(this.displayCurrentPlayer);
		this.displayBoard();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(currentPlayerLabel);
		add(boardPanel);
	}

	/**
	 * Helper method for the constructor that sets the icons for the board.
	 */
	private void displayBoard() {
		for (int row = 0; row < BOARD_DIM; row++) {
			for (int col = 0; col < BOARD_DIM; col++) {
				this.piece = this.model.getPiece(row, col);
				if (this.piece == null) {
					this.board[row][col].setIcon(this.blank);
				} else if (this.piece.getPlayer() == Player.Red) {
					this.board[row][col].setIcon(this.redIcon);
				} else {
					this.board[row][col].setIcon(this.blackIcon);
				}
			}
		}
	}

	/**
	 * 		a string that lets the users know who's turn it currently is.
	 */
	public final void currentPlayer() {
		if (this.model.getCurrentPlayer() == Player.Red) {
			this.currentPlayer = "It is " + this.player2 + "'s turn.";
		} else {
			this.currentPlayer = "It is " + this.player1 + "'s turn.";
		}
	}

	/**
     * Removes the piece that has been jumped.
     * 
     * @param row
     *            the row of the piece to be removed
     * @param col
     *            the column of the piece to be removed
     */
    public final void removeJumpedPiece(final int row, final int col) {
        this.board[row][col].setIcon(this.blank);
    }

	/**
	 * Moves the icons to represent a piece that has moved in the game model.
	 * 
	 * @param startRow
	 *            the row of the piece that is now empy
	 * @param startCol
	 *            the column of the piece that is now empty
	 * @param endRow
	 *            the row of the location that now contains the moved piece
	 * @param endCol
	 *            the column of the location that now contains the moved piece
	 */
	private void movePiece(final int startRow, final int startCol,
			final int endRow, final int endCol) {
		this.piece = this.model.getPiece(endRow, endCol);
		this.board[startRow][startCol].setIcon(this.blank);
		if (this.piece.getPlayer() == Player.Red) {
			this.board[endRow][endCol].setIcon(this.redIcon);
		} else {
			this.board[endRow][endCol].setIcon(this.blackIcon);
		}
	}

	/**
	 * Method reacts to the different buttons that are pressed and updates the
	 * game accordinly.
	 * 
	 * @param e
	 *            is the ActionEvent which drives movement of pieces
	 */
	public final void actionPerformed(final ActionEvent e) {
		if (firstClick) {
			for (int row = 0; row < BOARD_DIM; row++) {
				for (int col = 0; col < BOARD_DIM; col++) {
					if (e.getSource() == this.board[row][col]) {
						this.moves[0] = row;
						this.moves[1] = col;
						this.piece = this.model.getPiece(row, col);
						if (this.piece == null) {
							return;
							}
						if (this.piece.getPlayer()
								== this.model.getCurrentPlayer()) {
							this.firstClick = false;
						} else {
							JOptionPane.showMessageDialog(null,
									this.currentPlayer);
						}
					}
				}
			}
		} else {
			for (int row = 0; row < BOARD_DIM; row++) {
				for (int col = 0; col < BOARD_DIM; col++) {
					if (e.getSource() == this.board[row][col]) {
						this.moves[2] = row;
						this.moves[3] = col;
						if (this.model.canMove(moves[0], moves[1],
								moves[2], moves[3])[0]) {
							this.model.movePiece(moves[0], moves[1],
									moves[2], moves[3]);
							this.movePiece(moves[0], moves[1],
									moves[2], moves[3]);
							this.model.setCurrentPlayer();
						} else if (this.model.canMove(moves[0], moves[1],
								moves[2], moves[3])[1]) {
							this.model.movePiece(moves[0], moves[1],
									moves[2], moves[3]);
							this.movePiece(moves[0], moves[1],
									moves[2], moves[3]);
							int[] location = this.model.jumped(moves[0],
									moves[1], moves[2], moves[3]);
							this.removeJumpedPiece(location[0], location[1]);
							if (this.model.isWinner()) {
								if (this.model.getCurrentPlayer() 
										== Player.Black) {
									JOptionPane.showMessageDialog(null,
											"Black has Won!");
								} else {
									JOptionPane.showMessageDialog(null,
											"Red has Won!");
								}
								this.model = new CheckersModel();
								this.displayBoard();
								this.currentPlayer();
								this.displayCurrentPlayer.setText(this.currentPlayer);
								this.firstClick = true;
								return;
							}
							this.model.setNextPlayer();
						}
					}
				}
			}
			this.currentPlayer();
			this.displayCurrentPlayer.setText(this.currentPlayer);

			this.firstClick = true;
		}
	}

}
