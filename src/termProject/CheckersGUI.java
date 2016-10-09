package termProject;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class CheckersGUI extends JPanel implements ActionListener {

	private JButton[][] board;
	private CheckersPiece piece;
	private CheckersModel model;
	private Icon redIcon;
	private Icon blackIcon;
	private Icon blank;
	private boolean firstClick;
	private int[] moves;
	private JLabel displayCurrentPlayer;
	private String currentPlayer;
	private String player1;
	private String player2;

	/**
	 * Constructor creates the user board and sets values to the instance
	 * variables
	 */
	public CheckersGUI() {
		this.board = new JButton[8][8];
		this.piece = null;
		this.model = new CheckersModel();
		this.redIcon = new ImageIcon("Red.png");
		this.blackIcon = new ImageIcon("Black.png");
		this.blank = new ImageIcon("Blank.png");
		this.firstClick = true;
		this.moves = new int[4];
		this.player1 = JOptionPane.showInputDialog(null, "Enter the name of the first player.");
		this.player2 = JOptionPane.showInputDialog(null, "Enter the name of the second player.");
		JOptionPane.showMessageDialog(null, player1 + " is Black and " + player2 + " is Red.");
		this.currentPlayer = "It is " + player1 + "'s turn.";

		// creates a layout for the board
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridBagLayout());
		GridBagConstraints place = new GridBagConstraints();
		place.gridheight = 8;
		place.gridwidth = 8;
		place = new GridBagConstraints();

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
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
				this.board[row][col].setBorder(BorderFactory.createEmptyBorder());
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
	 * Helper method for the constructor that sets the icons for the board
	 */
	private void displayBoard() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
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
	 * @return a string that lets the users know who's turn it currently is.
	 */
	public void currentPlayer() {
		if (this.model.getCurrentPlayer() == Player.Red) {
			this.currentPlayer = "It is " + this.player2 + "'s turn.";
		} else {
			this.currentPlayer = "It is " + this.player1 + "'s turn.";
		}
	}

	/**
	 * Removes the piece that has been jumped
	 * 
	 * @param row
	 *            the row of the piece to be removed
	 * @param col
	 *            the column of the piece to be removed
	 */
	public void removeJumpedPiece(int row, int col) {
		this.board[row][col].setIcon(this.blank);
	}

	/**
	 * Moves the icons to represent a piece that has moved in the game model
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
	private void movePiece(int startRow, int startCol, int endRow, int endCol) {
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
	 * game accordinly
	 */
	public void actionPerformed(ActionEvent e) {
		if (firstClick) {
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (e.getSource() == this.board[row][col]) {
						this.moves[0] = row;
						this.moves[1] = col;
						this.piece = this.model.getPiece(row, col);
						if (this.piece == null) {
							return;
						}
						if (this.piece.getPlayer() == this.model.getCurrentPlayer()) {
							this.firstClick = false;
						} else {
							JOptionPane.showMessageDialog(null, this.currentPlayer);
						}
					}
				}
			}
		} else {
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (e.getSource() == this.board[row][col]) {
						this.moves[2] = row;
						this.moves[3] = col;
						if (this.model.canMove(moves[0], moves[1], moves[2], moves[3])[0]) {
							this.model.movePiece(moves[0], moves[1], moves[2], moves[3]);
							this.movePiece(moves[0], moves[1], moves[2], moves[3]);
							this.model.setCurrentPlayer();
						} else if (this.model.canMove(moves[0], moves[1], moves[2], moves[3])[1]) {
							this.model.movePiece(moves[0], moves[1], moves[2], moves[3]);
							this.movePiece(moves[0], moves[1], moves[2], moves[3]);
							int[] location = this.model.jumped(moves[0], moves[1], moves[2], moves[3]);
							this.removeJumpedPiece(location[0], location[1]);
							if (this.model.isWinner()) {
								if (this.model.getCurrentPlayer() == Player.Black) {
									JOptionPane.showMessageDialog(null, "Black has Won!");
								} else {
									JOptionPane.showMessageDialog(null, "Red has Won!");
								}
								this.model = new CheckersModel();
								this.displayBoard();
							}
							this.model.setCurrentPlayer();
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