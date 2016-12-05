package edu.gvsu.scis.cis350.termProject;

import java.util.ArrayList;

/**
 * Class creates a virtual layout for the board and provides the game logic.
 * 
 * @author Andrew Olesak
 */
public class CheckersModel {

  /**
   * a 2-D array of type CheckersPiece to record the state of the board.
   */
  private CheckersPiece[][] board;
  /**
   * a checkersPiece object which is a single game piece.
   */
  private CheckersPiece piece;
  /**
   * a Player type object(enum) which dictates which player's turn it is.
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
   *          the row of the piece to be moved
   * @param startCol
   *          the column of the piece to be moved
   * @param endRow
   *          the row of the location to which the piece will be moved
   * @param endCol
   *          the column of the location to which the piece will be moved
   */
  public final void movePiece(final int startRow, final int startCol, 
      final int endRow, final int endCol) {
    this.piece = this.board[startRow][startCol];
    this.board[startRow][startCol] = null;
    this.board[endRow][endCol] = piece;
  }

  /**
   * method decides whether or not a checkersPiece is allowed to move 
   * to a specific location.
   * 
   * @param startRow
   *          the row of the piece to be moved
   * @param startCol
   *          the column of the piece to be moved
   * @param endRow
   *          the row of the new piece location
   * @param endCol
   *          the column of the new piece location
   * @return an array whose first element is true if the piece isn't 
   *         jumping, but is a valid move otherwise false. The second
   *         element is true if the player is jumping and it is a valid
   *         move, otherwise false.
   */
  public final boolean[] canMove(final int startRow, final int startCol, 
      final int endRow, final int endCol) {
    boolean[] type = { false, false };
    if (endRow < 0 || endRow > 7 || endCol < 0 || endCol > 7) {
      return type;
    }
    this.piece = this.board[startRow][startCol];
    if (this.piece == null) {
      return type;
    }
    if (this.board[endRow][endCol] != null) {
      return type;
    }
    if (this.piece.getPlayer() == Player.Red
        || (this.piece.getPlayer() == Player.Black && this.piece.isKing())) {
      if (startRow + 1 == endRow && (startCol + 1 == endCol 
          || startCol - 1 == endCol)) {
        type[0] = true;
        return type;
      } else if (startRow + 2 == endRow) {
        if (startCol + 2 == endCol) {
          if (this.board[startRow + 1][startCol + 1] == null) {
            return type;
          } else if ((this.board[startRow + 1][startCol + 1].getPlayer() 
              == Player.Black
              && this.piece.getPlayer() == Player.Red)
              || (this.board[startRow + 1][startCol + 1].getPlayer() 
                  == Player.Red
                  && this.piece.getPlayer() == Player.Black)) {
            type[1] = true;
            return type;
          }
        } else if (startCol - 2 == endCol) {
          if (this.board[startRow + 1][startCol - 1] == null) {
            return type;
          } else if ((this.board[startRow + 1][startCol - 1].getPlayer() 
              == Player.Black
              && this.piece.getPlayer() == Player.Red)
              || (this.board[startRow + 1][startCol - 1].getPlayer()
                  == Player.Red
                  && this.piece.getPlayer() == Player.Black)) {
            type[1] = true;
            return type;
          }
        }
      }
    }
    if (this.piece.getPlayer() == Player.Black
        || (this.piece.getPlayer() == Player.Red && this.piece.isKing())) {
      if (startRow - 1 == endRow && (startCol + 1 == endCol 
          || startCol - 1 == endCol)) {
        type[0] = true;
        return type;
      } else if (startRow - 2 == endRow) {
        if (startCol - 2 == endCol) {
          if (this.board[startRow - 1][startCol - 1] == null) {
            return type;
          } else if ((this.board[startRow - 1][startCol - 1].getPlayer() 
              == Player.Red
              && this.piece.getPlayer() == Player.Black)
              || (this.board[startRow - 1][startCol - 1].getPlayer() 
                  == Player.Black
                  && this.piece.getPlayer() == Player.Red)) {
            type[1] = true;
            return type;
          }
        } else if (startCol + 2 == endCol) {
          if (this.board[startRow - 1][startCol + 1] == null) {
            return type;
          } else if ((this.board[startRow - 1][startCol + 1].getPlayer() 
              == Player.Red
              && this.piece.getPlayer() == Player.Black)
              || (this.board[startRow - 1][startCol + 1].getPlayer() 
                  == Player.Black
                  && this.piece.getPlayer() == Player.Red)) {
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
   *          the row of the current checkersPiece
   * @param startCol
   *          the column of the current checkersPiece
   * @param endRow
   *          the row of the new checkersPiece location
   * @param endCol
   *          the column of the new checkersPiece location
   * @return an array whose two elements are the row and column 
   * of the player who has been jumped
   *         respectively.
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
   * calculates all jumps that take the most jumps and puts them 
   * in an array list.
   * 
   * @param p
   *          the current player
   * @return an arraylist of all possible jumps of which one has to be performed
   */
  public final ArrayList<ArrayList<Integer>> canJump(final Player p) {
    int jumps = 0;
    int counter = -1;
    int listCounter = 0;
    int tempRow = 0;
    int tempCol = 0;
    ArrayList<Integer> jumpDir = new ArrayList<Integer>();
    ArrayList<Integer> currentJumps = new ArrayList<Integer>();
    ArrayList<ArrayList<Integer>> jumpList = 
        new ArrayList<ArrayList<Integer>>();
    for (int row = 0; row < BOARD_DIM; row++) {
      for (int col = 0; col < BOARD_DIM; col++) {
        this.piece = this.board[row][col];
        if (this.piece == null) {
          continue;
        }
        if (this.piece.getPlayer() == p) {
          int[] loc = { row, col };
          jumps = 0;
          int pRow = row;
          int pCol = col;
          tempRow = row;
          tempCol = col;
          currentJumps.add(row);
          currentJumps.add(col);
          for (int dir = 0; dir < 4; dir++) {
            loc = this.checkForMoreJumps(p, this.piece.isKing(), 
                dir, loc[0], loc[1], pRow, pCol);
            while (loc[0] != -1) {
              currentJumps.add(loc[0]);
              currentJumps.add(loc[1]);
              pRow = tempRow;
              pCol = tempCol;
              tempRow = loc[0];
              tempCol = loc[1];
              jumpDir.add(jumps, dir);
              jumps++;
              dir = 0;
              loc = this.checkForMoreJumps(p, this.piece.isKing(), 
                  dir, loc[0], loc[1], pRow, pCol);
            }
            if (loc[0] == -1) {
              if (jumps == 0) {
                if (jumpDir.size() == 0) {
                  jumpDir.add(0, 1);
                } else {
                  jumpDir.set(0, jumpDir.get(0) + 1);
                }
              }
              loc[0] = tempRow;
              loc[1] = tempCol;
              if (dir == 3) {
                if (currentJumps.size() > 3) {
                  jumpList.add(currentJumps);
                  currentJumps = new ArrayList<Integer>();
                  counter++;
                }
                currentJumps = new ArrayList<Integer>();
                if (4 == jumpDir.get(0)) {
                  dir = 4;
                  currentJumps = new ArrayList<Integer>();
                  jumpDir = new ArrayList<Integer>();
                  continue;
                }
                if (jumps > 0) {
                  jumps--;
                }
                if (jumpList.size() > 0) {
                  for (int i = 0; i < jumps * 2 + 1; i = i + 2) {
                    currentJumps.add(jumpList.get(counter).get(i));
                    currentJumps.add(jumpList.get(counter).get(i + 1));
                  }
                  dir = jumpDir.get(jumps);
                  jumpDir.set(jumps, jumpDir.get(jumps) + 1);
                  if (jumps == 0) {
                    if (jumpDir.get(0) == 4) {
                      currentJumps = new ArrayList<Integer>();
                      jumpDir = new ArrayList<Integer>();
                      continue;
                    }
                    pRow = row;
                    pCol = col;
                    loc[0] = row;
                    loc[1] = col;
                    tempRow = row;
                    tempCol = col;
                  } else if (jumpList.get(listCounter).size() > 4) {
                    pRow = jumpList.get(listCounter).
                        get(jumpList.get(listCounter).size() - 6);
                    pCol = jumpList.get(listCounter).
                        get(jumpList.get(listCounter).size() - 5);
                    loc[0] = jumpList.get(listCounter).
                        get(jumpList.get(listCounter).size() - 4);
                    loc[1] = jumpList.get(listCounter).
                        get(jumpList.get(listCounter).size() - 3);
                    tempRow = loc[0];
                    tempCol = loc[1];
                  }
                  listCounter++;
                }
              }
            }
          }
        }
      }
    }
    return this.findLongestJumps(jumpList);

  }

  /**
   * Helper method that checks to see if a piece 
   * can continue to jump an multiple times.
   * 
   * @param play
   *          the current player
   * @param king
   *          a boolean of whether the piece is a king
   * @param startRow
   *          the row in which the jump will be continued
   * @param startCol
   *          the column in which the jump will be continued
   * @param prevRow
   *          the row that the player can't end up in
   * @param prevCol
   *          the column that the player can't end up in
   * @param direction
   *          direction in which the next piece is
   * @return an array of locations that are made up of multiple jumps
   */
  public final int[] checkForMoreJumps(final Player play, final boolean king, 
      final int direction, final int startRow, final int startCol, 
      final int prevRow, final int prevCol) {
    int[] moves = { -1, 0 };
    if (!king && (direction == 2 || direction == 3)) {
      return moves;
    }
    if (play == Player.Black) {
      switch (direction) {
      case 0:
        if (startRow - 2 > -1 && startCol - 2 > -1) {
          if (startRow - 2 != prevRow || startCol - 2 != prevCol) {
            if (this.getPiece(startRow - 1, startCol - 1) != null
                && this.getPiece(startRow - 1, startCol - 1).getPlayer() 
                == Player.Red) {
              if (this.getPiece(startRow - 2, startCol - 2) == null) {
                moves[0] = startRow - 2;
                moves[1] = startCol - 2;
              }
            }
          }
        }
        break;

      case 1:
        if (startRow - 2 > -1 && startCol + 2 < BOARD_DIM) {
          if (startRow - 2 != prevRow || startCol + 2 != prevCol) {
            if (this.getPiece(startRow - 1, startCol + 1) != null
                && this.getPiece(startRow - 1, startCol + 1).getPlayer() 
                == Player.Red) {
              if (this.getPiece(startRow - 2, startCol + 2) == null) {
                moves[0] = startRow - 2;
                moves[1] = startCol + 2;
              }
            }
          }
        }
        break;

      case 2:
        if (startRow + 2 < BOARD_DIM && startCol + 2 < BOARD_DIM) {
          if (startRow + 2 != prevRow || startCol + 2 != prevCol) {
            if (this.getPiece(startRow + 1, startCol + 1) != null
                && this.getPiece(startRow + 1, startCol + 1).getPlayer() 
                == Player.Red) {
              if (this.getPiece(startRow + 2, startCol + 2) == null) {
                moves[0] = startRow + 2;
                moves[1] = startCol + 2;
              }
            }
          }
        }
        break;

      case 3:
        if (startRow + 2 < BOARD_DIM && startCol - 2 > -1) {
          if (startRow + 2 != prevRow || startCol - 2 != prevCol) {
            if (this.getPiece(startRow + 1, startCol - 1) != null
                && this.getPiece(startRow + 1, startCol - 1).getPlayer() 
                == Player.Red) {
              if (this.getPiece(startRow + 2, startCol - 2) == null) {
                moves[0] = startRow + 2;
                moves[1] = startCol - 2;
              }
            }
          }
        }
        break;
      
      default:
        return moves;

      }
    } else {
      switch (direction) {
      case 0:
        if (startRow + 2 < BOARD_DIM && startCol + 2 < BOARD_DIM) {
          if (startRow + 2 != prevRow || startCol + 2 != prevCol) {
            if (this.getPiece(startRow + 1, startCol + 1) != null
                && this.getPiece(startRow + 1, startCol + 1).getPlayer() 
                == Player.Black) {
              if (this.getPiece(startRow + 2, startCol + 2) == null) {
                moves[0] = startRow + 2;
                moves[1] = startCol + 2;
              }
            }
          }
        }
        break;

      case 1:
        if (startRow + 2 < BOARD_DIM && startCol - 2 > -1) {
          if (startRow + 2 != prevRow || startCol - 2 != prevCol) {
            if (this.getPiece(startRow + 1, startCol - 1) != null
                && this.getPiece(startRow + 1, startCol - 1).getPlayer() 
                == Player.Black) {
              if (this.getPiece(startRow + 2, startCol - 2) == null) {
                moves[0] = startRow + 2;
                moves[1] = startCol - 2;
              }
            }
          }
        }
        break;

      case 2:
        if (startRow - 2 > -1 && startCol - 2 > -1) {
          if (startRow - 2 != prevRow || startCol - 2 != prevCol) {
            if (this.getPiece(startRow - 1, startCol - 1) != null
                && this.getPiece(startRow - 1, startCol - 1).getPlayer() 
                == Player.Black) {
              if (this.getPiece(startRow - 2, startCol - 2) == null) {
                moves[0] = startRow - 2;
                moves[1] = startCol - 2;
              }
            }
          }
        }
        break;

      case 3:
        if (startRow - 2 > -1 && startCol + 2 < BOARD_DIM) {
          if (startRow - 2 != prevRow || startCol + 2 != prevCol) {
            if (this.getPiece(startRow - 1, startCol + 1) != null
                && this.getPiece(startRow - 1, startCol + 1).getPlayer() 
                == Player.Black) {
              if (this.getPiece(startRow - 2, startCol + 2) == null) {
                moves[0] = startRow - 2;
                moves[1] = startCol + 2;
              }
            }
          }
        }
        break;
      default:
        break;
      }
    }
    return moves;

  }

  /**
   * Method checks to see if there are any remaining pieces of a 
   * certain color to determine whether
   * there is a winner or not.
   * 
   * @param p
   *          the player for which we are checking to see if they lose
   * @return true if there are no remaining pieces of a certain color, 
   * otherwise false
   */
  public final boolean isWinner(final Player p) {
    for (int row = 0; row < BOARD_DIM; row++) {
      for (int col = 0; col < BOARD_DIM; col++) {
        this.piece = this.board[row][col];
        if (this.piece == null) {
          continue;
        } else if (this.piece.getPlayer() == p) {
          if (this.canMoveAnywhere(row, col)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Method calculates whether or not a given piece can make 
   * any allowed move in the game.
   * 
   * @param pRow
   *          the row of the piece
   * @param pCol
   *          the column of the piece
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
   * helper method that finds the longest jump or jumps 
   * if there is a tie and puts them in an
   * ArrayList.
   * 
   * @param jumpList
   *          the ArrayList containing the jumps.
   * @return the ArrayList that has the longest jump or jumps.
   */
  public final ArrayList<ArrayList<Integer>> findLongestJumps(
      final ArrayList<ArrayList<Integer>> jumpList) {
    ArrayList<ArrayList<Integer>> newList = new ArrayList<ArrayList<Integer>>();
    int longest = 0;
    for (ArrayList<Integer> jumps : jumpList) {
      if (jumps.size() > longest) {
        longest = jumps.size();
        newList.clear();
        newList.add(jumps);
      } else if (jumps.size() == longest) {
        newList.add(jumps);
      }

    }
    return newList;
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
   *          the row of the checkersPiece
   * @param col
   *          the column of the checkersPiece
   * @return the checkersPiece that is at the given row and column.
   */
  public final CheckersPiece getPiece(final int row, final int col) {
    return this.board[row][col];
  }

  /**
   * Removes a piece from a specific location.
   * 
   * @param row
   *          the row of the piece
   * @param col
   *          the col of the piece
   */
  public final void removePiece(final int row, final int col) {
    this.board[row][col] = null;
  }

  /**
   * Sets a certain piece at a certain location.
   * 
   * @param p
   *          the checkers piece
   * @param row
   *          the row on the board
   * @param col
   *          the column on the board
   */
  public final void setPiece(final CheckersPiece p, 
      final int row, final int col) {
    this.board[row][col] = p;
  }

  /**
   * Method sets the current player.
   * 
   * @param p
   *          the new current player
   */
  public final void setPlayer(final Player p) {
    this.player = p;
  }
}