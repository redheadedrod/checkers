package termProject;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class CheckersGUI extends JPanel implements ActionListener {

	private JButton[][] board;
	private CheckersPiece piece;
	private CheckersDesign design;
	private Icon redIcon;
	private Icon blackIcon;
	private Icon blank;
	private boolean firstClick;
	private int[] moves;
	private JLabel currentPlayer;

	public CheckersGUI() {
		this.board = new JButton[8][8];
		this.piece = null;
		this.design = new CheckersDesign();
		this.redIcon = new ImageIcon("Red.png");
		this.blackIcon = new ImageIcon("Black.png");
		this.blank = new ImageIcon("Blank.png");
		this.firstClick = true;
		this.moves = new int[4];

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
		JPanel currentPlayer = new JPanel();
		this.currentPlayer = new JLabel(this.currentPlayer());
		this.displayBoard();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(currentPlayer);
		add(boardPanel);
	}

	private void displayBoard() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				this.piece = this.design.getPiece(row, col);
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
	
	public String currentPlayer(){
		if(this.design.getCurrentPlayer()==Player.Red){
			return "It is Red's turn.";
		}else{
			return "It is Black's turn.";
		}
	}

	public void removeJumpedPiece(int row, int col){
		this.board[row][col].setIcon(this.blank);
	}
	
	private void movePiece(int startRow, int startCol, int endRow, int endCol) {
		this.piece = this.design.getPiece(endRow, endCol);
		this.board[startRow][startCol].setIcon(this.blank);
		if (this.piece.getPlayer() == Player.Red) {
			this.board[endRow][endCol].setIcon(this.redIcon);
		} else {
			this.board[endRow][endCol].setIcon(this.blackIcon);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (firstClick) {
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (e.getSource() == this.board[row][col]) {
						this.moves[0] = row;
						this.moves[1] = col;
						this.piece = this.design.getPiece(row, col);
						if(this.piece==null){
							return;
						}
						if(this.piece.getPlayer()==this.design.getCurrentPlayer()){
							this.firstClick = false;
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
						if (this.design.canMove(moves[0], moves[1], moves[2], moves[3])[0]) {
							this.design.movePiece(moves[0], moves[1], moves[2], moves[3]);
							this.movePiece(moves[0], moves[1], moves[2], moves[3]);
							this.firstClick = true;
							this.design.setCurrentPlayer();
						}else if(this.design.canMove(moves[0], moves[1], moves[2], moves[3])[1]){
							this.design.movePiece(moves[0], moves[1], moves[2], moves[3]);
							this.movePiece(moves[0], moves[1], moves[2], moves[3]);
							int[] location = this.design.jumped(moves[0], moves[1], moves[2], moves[3]);
							this.removeJumpedPiece(location[0],  location[1]);
							this.firstClick = true;
							this.design.setCurrentPlayer();
						}
					}
				}
			}
		}
	}

}
