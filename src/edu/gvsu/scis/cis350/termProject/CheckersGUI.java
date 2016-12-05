package edu.gvsu.scis.cis350.termProject;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * CheckersGUI creates a board or the game to be played on by way of extending
 * JPanel and implementing ActionListnener.
 * 
 * @author Andrew Olesak, Joel Vander Klipp, Rodney Fulk
 *
 */
public class CheckersGUI extends JFrame implements ActionListener {

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
   * Icon for red piece king.
   */
  private Icon redKing;
  /**
   * Icon for a black piece king.
   */
  private Icon blackKing;
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
   * An ArrayList of integers that holds all of the possible jumps that a 
   * player must execute.
   */
  private ArrayList<ArrayList<Integer>> jumpMoves;
  /**
   * Integer that keeps track of the current row of the must jump list.
   */
  private int jumpRow;
  /**
   * Integer that keeps track of the current column of the must jump list.
   */
  private int jumpCol;
  /**
   * An ArrayList of integers that keeps track of the jump that the player 
   * decides to use.
   */
  private ArrayList<ArrayList<Integer>> currentJumps;

  // create menu items
  /**
   * JMenuBar for the menu.
   */
  private JMenuBar menus;
  /**
   * Actual JMenu item for the menu.
   */
  private JMenu fileMenu;
  /**
   * new game menu item.
   */
  private JMenuItem newGameItem;
  /**
   * save game menu item.
   */
  private JMenuItem saveGameItem;
  /**
   * a menu item to resume a saved game.
   */
  private JMenuItem openGameItem;
  /**
   * boolean to determine if a player can jump or not.
   */
  private boolean canJump;
  /**
   * an int denoting dimensions of the board.
   */
  private static final int BOARD_DIM = 8;

  /**
   * Constructor creates the user board and sets values 
   * to the instance variables.
   */
  public CheckersGUI() {
    this.board = new JButton[BOARD_DIM][BOARD_DIM];
    this.piece = null;
    this.model = null;
    this.redIcon = new ImageIcon("Red.png");
    this.blackIcon = new ImageIcon("Black.png");
    this.blank = new ImageIcon("Blank.png");
    this.blackKing = new ImageIcon("BlackKing.png");
    this.redKing = new ImageIcon("RedKing.png");
    this.firstClick = true;
    this.moves = new int[4];
    this.currentPlayer = "Welcome to Checkers";
    this.canJump = false;
    this.jumpMoves = new ArrayList<ArrayList<Integer>>();
    this.jumpRow = 2;
    this.jumpCol = 3;
    this.currentJumps = new ArrayList<ArrayList<Integer>>();

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
        this.board[row][col].setBorder(BorderFactory.createEmptyBorder());
        boardPanel.add(this.board[row][col], place);
      }
    }

    // create the file menu
    this.fileMenu = new JMenu("File");
    this.newGameItem = new JMenuItem("new game");
    this.saveGameItem = new JMenuItem("Save");
    this.openGameItem = new JMenuItem("Open");
    fileMenu.add(this.newGameItem);
    fileMenu.add(this.saveGameItem);
    fileMenu.add(this.openGameItem);
    this.menus = new JMenuBar();
    setJMenuBar(menus);
    menus.add(fileMenu);
    this.newGameItem.addActionListener(this);
    this.saveGameItem.addActionListener(this);
    this.openGameItem.addActionListener(this);

    // create a panel to show who's turn it is
    JPanel currentPlayerLabel = new JPanel();
    this.displayCurrentPlayer = new JLabel(this.currentPlayer);
    currentPlayerLabel.add(this.displayCurrentPlayer);
    this.disableButtons();
    this.displayBlankBoard();
    JPanel big = new JPanel();
    big.setLayout(new BoxLayout(big, BoxLayout.Y_AXIS));
    menus.add(fileMenu);
    big.add(currentPlayerLabel);
    big.add(boardPanel);
    add(big);
  }

  /**
   * main method to drive the game.
   * 
   * @param args
   *          parameter for program arguments. this game has none.
   */
  public static void main(final String[] args) {
    CheckersGUI g = new CheckersGUI();
    g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    g.setTitle("Checkers");
    g.pack();
    g.setVisible(true);
  }

  /**
   * Displays a new board.
   */
  public final void displayBoard() {
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
   * Displays a blank board.
   */
  public final void displayBlankBoard() {
    for (int row = 0; row < BOARD_DIM; row++) {
      for (int col = 0; col < BOARD_DIM; col++) {
        this.board[row][col].setIcon(this.blank);
      }
    }
  }

  /**
   * Disables the buttons.
   */
  public final void disableButtons() {
    for (int row = 0; row < BOARD_DIM; row++) {
      for (int col = 0; col < BOARD_DIM; col++) {
        this.board[row][col].setEnabled(false);
      }
    }
  }

  /**
   * enables the buttons.
   */
  public final void enableButtons() {
    for (int row = 0; row < BOARD_DIM; row++) {
      for (int col = 0; col < BOARD_DIM; col++) {
        this.board[row][col].setEnabled(true);
      }
    }
  }

  /**
   * a string that lets the users know who's turn it currently is.
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
   *          the row of the piece to be removed
   * @param col
   *          the column of the piece to be removed
   */
  public final void removeJumpedPiece(final int row, final int col) {
    this.board[row][col].setIcon(this.blank);
  }

  /**
   * Moves the icons to represent a piece that has moved in the game model.
   * 
   * @param startRow
   *          the row of the piece that is now empy
   * @param startCol
   *          the column of the piece that is now empty
   * @param endRow
   *          the row of the location that now contains the moved piece
   * @param endCol
   *          the column of the location that now contains the moved piece
   */
  private void movePiece(final int startRow, final int startCol, 
      final int endRow, final int endCol) {
    this.piece = this.model.getPiece(endRow, endCol);
    this.board[startRow][startCol].setIcon(this.blank);
    if (this.piece == null) {
      this.board[startRow][startCol].setIcon(this.blank);
    } else if (this.piece.getPlayer() == Player.Red) {
      if (this.piece.isKing()) {
        this.board[endRow][endCol].setIcon(this.redKing);
      } else {
        this.board[endRow][endCol].setIcon(this.redIcon);
      }
    } else {
      if (this.piece.isKing()) {
        this.board[endRow][endCol].setIcon(this.blackKing);
      } else {
        this.board[endRow][endCol].setIcon(this.blackIcon);
      }
    }
  }

  /**
   * Turns all buttons on the board that are jump locations yellow.
   */
  private void lightUpJumps() {
    for (ArrayList<Integer> jumps : this.jumpMoves) {
      int row = 0;
      int col = 1;
      for (int i = 0; i < jumps.size(); i = i + 2) {
        this.board[jumps.get(row)][jumps.get(col)].setBackground(Color.YELLOW);
        row = row + 2;
        col = col + 2;
      }
    }
  }

  /**
   * Resets the board back to its original red and black colors.
   */
  public final void resetColors() {
    for (int row = 0; row < BOARD_DIM; row++) {
      for (int col = 0; col < BOARD_DIM; col++) {
        if ((row + col) % 2 == 0) {
          this.board[row][col].setBackground(Color.BLACK);
        } else {
          this.board[row][col].setBackground(Color.RED);
        }
      }
    }
  }

  /**
   * Method reacts to the different buttons that are pressed and 
   * updates the game accordingly.
   * 
   * @param e
   *          is the ActionEvent which drives movement of pieces
   */
  public final void actionPerformed(final ActionEvent e) {
    if (e.getSource() == this.openGameItem) {
      this.openGame();
      return;
    }

    if (e.getSource() == this.saveGameItem) {
      this.saveGame();
      return;
    }

    if (e.getSource() == this.newGameItem) {
      this.newGame();
      return;
    }
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
            if (this.piece.getPlayer() == this.model.getCurrentPlayer()) {
              if (this.canJump) {
                for (ArrayList<Integer> jumps : this.jumpMoves) {
                  if (row == jumps.get(0) && col == jumps.get(1)) {
                    this.currentJumps.add(jumps);
                  }
                }
                if (this.currentJumps.size() > 0) {
                  this.firstClick = false;
                  return;
                } else if (this.model.getCurrentPlayer() == Player.Red) {
                  this.lightUpJumps();
                  JOptionPane.showMessageDialog(null, player2 + " must jump!");
                } else {
                  this.lightUpJumps();
                  JOptionPane.showMessageDialog(null, player1 + " must jump!");
                }
              } else {
                this.firstClick = false;
                return;
              }
            } else {
              JOptionPane.showMessageDialog(null, this.currentPlayer);
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
            this.piece = this.model.getPiece(row, col);
            if (this.canJump) {
              if (this.currentJumps.size() == 1) {
                if (row == this.currentJumps.get(0).get(jumpRow)
                    && col == this.currentJumps.get(0).get(jumpCol)) {
                  this.model.movePiece(moves[0], moves[1], moves[2], moves[3]);
                  this.movePiece(moves[0], moves[1], moves[2], moves[3]);
                  int[] loc = this.model.jumped(moves[0], moves[1], 
                      moves[2], moves[3]);
                  this.removeJumpedPiece(loc[0], loc[1]);
                  if (this.currentJumps.get(0).size() - 1 == this.jumpCol) {
                    this.currentJumps.clear();
                    this.canJump = false;
                    if (!this.model.getPiece(moves[2], moves[3]).isKing()
                        && this.model.getCurrentPlayer() == Player.Black 
                        && moves[2] == 0) {
                      this.piece.setKing(true);
                      this.board[row][col].setIcon(this.blackKing);
                    } else if (!this.model.getPiece(moves[2], moves[3]).isKing()
                        && this.model.getCurrentPlayer() == Player.Red 
                        && moves[2] == 7) {
                      this.piece.setKing(true);
                      this.board[row][col].setIcon(this.redKing);
                    }
                    this.model.setNextPlayer();
                    this.jumpMoves = this.model.canJump(this.model.
                        getCurrentPlayer());
                    if (this.jumpMoves.size() > 0) {
                      this.canJump = true;
                    }
                    this.jumpRow = 2;
                    this.jumpCol = 3;
                    this.resetColors();
                    break;
                  }
                  this.moves[0] = this.moves[2];
                  this.moves[1] = this.moves[3];
                  this.jumpRow = this.jumpRow + 2;
                  this.jumpCol = this.jumpCol + 2;
                  return;

                } else {
                  if (this.model.getCurrentPlayer() == Player.Red) {
                    if (this.moves[0] == this.currentJumps.get(0).get(0)
                        && this.moves[1] == this.currentJumps.get(0).get(1)) {
                      this.firstClick = true;
                      this.currentJumps.clear();
                    }
                    this.lightUpJumps();
                    JOptionPane.showMessageDialog(null, player2 
                        + " must jump!");
                  } else {
                    if (this.moves[0] == this.currentJumps.get(0).get(0)
                        && this.moves[1] == this.currentJumps.get(0).get(1)) {
                      this.firstClick = true;
                      this.currentJumps.clear();
                    }
                    this.lightUpJumps();
                    JOptionPane.showMessageDialog(null, player1 
                        +  " must jump!");
                  }
                  return;
                }
              } else {
                for (ArrayList<Integer> jump : this.currentJumps) {
                  if (row == jump.get(2) && col == jump.get(3)) {
                    this.currentJumps.clear();
                    this.currentJumps.add(jump);
                  }
                }
                if (this.currentJumps.size() == 1) {
                  this.model.movePiece(moves[0], moves[1], moves[2], moves[3]);
                  this.movePiece(moves[0], moves[1], moves[2], moves[3]);
                  int[] loc = this.model.jumped(moves[0], moves[1], 
                      moves[2], moves[3]);
                  if (this.currentJumps.get(0).size() == 4) {
                    this.currentJumps.clear();
                    this.canJump = false;
                    if (!this.model.getPiece(moves[2], moves[3]).isKing()
                        && this.model.getCurrentPlayer() == Player.Black 
                        && moves[2] == 0) {
                      this.piece.setKing(true);
                      this.board[row][col].setIcon(this.blackKing);
                    } else if (!this.model.getPiece(moves[2], moves[3]).isKing()
                        && this.model.getCurrentPlayer() == Player.Red 
                        && moves[2] == 7) {
                      this.piece.setKing(true);
                      this.board[row][col].setIcon(this.redKing);
                    }
                    this.model.setNextPlayer();
                    this.jumpMoves = this.model.canJump(this.model.
                        getCurrentPlayer());
                    if (this.jumpMoves.size() > 0) {
                      this.canJump = true;
                    }
                    this.jumpRow = 2;
                    this.jumpCol = 3;
                    this.resetColors();
                    break;
                  }
                  this.removeJumpedPiece(loc[0], loc[1]);
                  this.moves[0] = this.moves[2];
                  this.moves[1] = this.moves[3];
                  this.jumpRow = this.jumpRow + 2;
                  this.jumpCol = this.jumpCol + 2;
                  return;
                } else {
                  if (this.model.getCurrentPlayer() == Player.Red) {
                    this.lightUpJumps();
                    JOptionPane.showMessageDialog(null, player2 
                        + " must jump!");
                  } else {
                    this.lightUpJumps();
                    JOptionPane.showMessageDialog(null, player1 
                        + " must jump!");
                  }
                  return;
                }
              }
            } else if (this.model.canMove(moves[0], moves[1], 
                moves[2], moves[3])[0]) {
              this.model.movePiece(moves[0], moves[1], moves[2], moves[3]);
              this.movePiece(moves[0], moves[1], moves[2], moves[3]);
              if (!this.model.getPiece(moves[2], moves[3]).isKing()
                  && this.model.getCurrentPlayer() == Player.Black 
                  && moves[2] == 0) {
                this.piece.setKing(true);
                this.board[row][col].setIcon(this.blackKing);
              } else if (!this.model.getPiece(moves[2], moves[3]).isKing()
                  && this.model.getCurrentPlayer() == Player.Red 
                  && moves[2] == 7) {
                this.piece.setKing(true);
                this.board[row][col].setIcon(this.redKing);
              }
              this.model.setNextPlayer();
              this.jumpMoves = this.model.canJump(this.model.
                  getCurrentPlayer());
              if (this.jumpMoves.size() > 0) {
                this.canJump = true;
              }
              this.firstClick = true;
            }
          }

        }
      }
    }
    if (this.model.isWinner(this.model.getCurrentPlayer())) {
      if (this.model.getCurrentPlayer() == Player.Red) {
        JOptionPane.showMessageDialog(null, "Black has Won!");
      } else {
        JOptionPane.showMessageDialog(null, "Red has Won!");
      }
      int selection = JOptionPane.YES_NO_OPTION;
      selection = JOptionPane.showConfirmDialog(null, 
          "Would You Like to start a new game?",
          "Warning", selection);
      if (selection == JOptionPane.YES_OPTION) {
        this.newGame();
      } else {
        this.displayBlankBoard();
        this.disableButtons();
      }
      return;
    }
    this.currentPlayer();
    this.displayCurrentPlayer.setText(this.currentPlayer);
    this.firstClick = true;
  }

  /**
   * Creates a new game object so that a new game can be played.
   */
  public final void newGame() {
    this.model = new CheckersModel();
    this.player1 = JOptionPane.showInputDialog(null, 
        "Enter the name of the first player.");
    this.player2 = JOptionPane.showInputDialog(null, 
        "Enter the name of the second player.");
    JOptionPane.showMessageDialog(null, player1 + " is Black and " 
        + player2 + " is Red.");
    this.currentPlayer = "It is " + player1 + "'s turn.";
    this.enableButtons();
    this.displayBoard();
    this.currentPlayer();
    this.displayCurrentPlayer.setText(this.currentPlayer);
    this.firstClick = true;
    this.canJump = false;
  }

  /**
   * Allows players to save the state of a game to a text file.
   */
  public final void saveGame() {
    if (this.model == null) {
      JOptionPane.showMessageDialog(null, 
          "You must start a new game in order to save it!");
      return;
    }
    // create File Chooser so that it starts at the current directory
    String userDir = System.getProperty("user.dir");
    JFileChooser fc = new JFileChooser(userDir);

    // show File Chooser and wait for user selection
    int returnVal = fc.showSaveDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      String filename = fc.getSelectedFile().getName();
      ArrayList<String> textCommands = new ArrayList<String>();
      for (int row = 0; row < BOARD_DIM; row++) {
        for (int col = 0; col < BOARD_DIM; col++) {
          this.piece = this.model.getPiece(row, col);
          if (this.piece == null) {
            textCommands.add(row + "+" + col + "+" + "n");
          } else if (this.piece.getPlayer() == Player.Black) {
            if (this.piece.isKing()) {
              textCommands.add(row + "+" + col + "+" + "b+k");
            } else {
              textCommands.add(row + "+" + col + "+" + "b+f");
            }
          } else if (this.piece.getPlayer() == Player.Red) {
            if (this.piece.isKing()) {
              textCommands.add(row + "+" + col + "+" + "r+k");
            } else {
              textCommands.add(row + "+" + col + "+" + "r+f");
            }
          }
        }
      }
      if (this.model.getCurrentPlayer() == Player.Black) {
        textCommands.add("p+b");
      } else {
        textCommands.add("p+r");
      }
      textCommands.add("c+" + this.player1);
      textCommands.add("c+" + this.player2);

      try {
        File file = new File(filename);
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        // BufferedWriter writer = new BufferedWriter(new
        // FileWriter(file));
        for (String s : textCommands) {
          writer.write(s + "/");
        }

        writer.close();
      } catch (FileNotFoundException error1) {
        System.out.println("Failed to read the data file: " + filename);

        // error while reading the file
      } catch (IOException error2) {
        System.out.println("Oops! Error related to: " + filename);
      }
    }
  }

  /**
   * Allows the players to load a previously played and
   * saved game from a text file.
   */
  public final void openGame() {
    String playerList = "";

    // create File Chooser so that it starts at the current directory
    String userDir = System.getProperty("user.dir");
    JFileChooser fc = new JFileChooser(userDir);

    // show File Chooser and wait for user selection
    int returnVal = fc.showOpenDialog(this);

    // did the user select a file?
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      String filename = fc.getSelectedFile().getName();
      Scanner inFS = null;
      FileInputStream fileByteStream = null;
      int playerNum = 0;
      try {
        // open the File and set delimiters
        fileByteStream = new FileInputStream(filename);
        inFS = new Scanner(fileByteStream);
        inFS.useDelimiter("/");

        // continue while there is more data to read
        while (inFS.hasNext()) {
          String command = inFS.next();
          String[] cmd = command.split("\\+");
          if (!cmd[0].equals("p") && !cmd[0].equals("c")) {
            if (cmd[2].equals("n")) {
              this.piece = null;
            }
            if (cmd[2].equals("b")) {
              if (cmd[3].equals("k")) {
                this.piece = new CheckersPiece(Player.Black, true);
              } else {
                this.piece = new CheckersPiece(Player.Black);
              }
            }
            if (cmd[2].equals("r")) {
              if (cmd[3].equals("k")) {
                this.piece = new CheckersPiece(Player.Red, true);
              } else {
                this.piece = new CheckersPiece(Player.Red);
              }
            }
            int row = Integer.parseInt(cmd[0]);
            int col = Integer.parseInt(cmd[1]);
            this.model.setPiece(this.piece, row, col);
          }
          if (cmd[0].equals("p")) {
            if (cmd[1].equals("b")) {
              this.model.setPlayer(Player.Black);
            } else {
              this.model.setPlayer(Player.Red);
            }
          }
          if (cmd[0].equals("c")) {
            if (playerNum == 0) {
              playerList = cmd[1] + "+";
              playerNum++;
            } else {
              playerList += cmd[1];
            }
          }
        }

      } catch (FileNotFoundException error1) {
        System.out.println("Failed to read the data file: " + filename);

        // error while reading the file
      } catch (IOException error2) {
        System.out.println("Oops! Error related to: " + filename);
      }
    }
    String[] list = playerList.split("\\+");
    this.player1 = list[0];
    this.player2 = list[1];
    this.displayCurrentPlayer.setText(this.currentPlayer);
    this.displayBoard();
  }
}
