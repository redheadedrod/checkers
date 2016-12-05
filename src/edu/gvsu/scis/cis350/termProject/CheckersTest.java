package edu.gvsu.scis.cis350.termProject;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

/**
 * Class tests the various methods associated with the checkers project
 * 
 * @author Andrew Olesak
 * @version September 24, 2016
 */
public class CheckersTest {

  private CheckersPiece piece;
  private CheckersModel model;

  /**
   * Constructor sets values to the instance variables
   */
  public CheckersTest() {
    this.piece = null;
    this.model = new CheckersModel();
  }

  @Test
  public void testCheckersPiece() {
    CheckersPiece p = new CheckersPiece(Player.Black, true);
    assertEquals(p.getPlayer(), Player.Black);
    assertEquals(p.isKing(), true);
    p.setKing(false);
    assertEquals(p.isKing(), false);
  }

  @Test
  public final void testCanMoveMethod1() {
    assertTrue(this.model.canMove(2, 3, 3, 2)[0]);
    assertTrue(this.model.canMove(2, 3, 3, 4)[0]);
    assertFalse(this.model.canMove(2, 3, 3, 3)[0]);
  }

  @Test
  public void testCanMoveMethod2() {
    assertFalse(this.model.canMove(5, 2, 3, 0)[0]);
    assertTrue(this.model.canMove(5, 2, 4, 3)[0]);
    assertFalse(this.model.canMove(5, 2, 4, 2)[0]);
  }

  @Test
  public void testCanMoveMethodNull() {
    assertFalse(this.model.canMove(1, 5, 2, 4)[0]);
    assertFalse(this.model.canMove(1, 5, 2, 4)[1]);
    assertFalse(this.model.canMove(4, 3, 3, 2)[0]);
    assertFalse(this.model.canMove(4, 3, 3, 2)[1]);
  }

  @Test
  public void testCanMoveMethod3() {
    this.model.movePiece(5, 4, 3, 4);
    assertFalse(this.model.canMove(2, 3, 3, 4)[0]);
    assertFalse(this.model.canMove(3, 4, 2, 3)[0]);
    assertTrue(this.model.canMove(2, 3, 4, 5)[1]);
  }

  @Test
  public void testCanMoveMethod4() {
    this.model.movePiece(1, 0, 4, 5);
    assertTrue(this.model.canMove(5, 6, 3, 4)[1]);
    assertTrue(this.model.canMove(5, 4, 3, 6)[1]);
    assertFalse(this.model.canMove(5, 4, 3, 2)[1]);
  }

  @Test
  public void testCanMoveMethod5() {
    assertFalse(this.model.canMove(2, 3, 4, 5)[1]);
    assertFalse(this.model.canMove(2, 3, 4, 5)[0]);
    this.model.movePiece(7, 0, 3, 2);
    assertTrue(this.model.canMove(2, 1, 4, 3)[1]);
    assertFalse(this.model.canMove(2, 1, 4, 3)[0]);
  }

  @Test
  public void testCanMoveMethod6() {
    assertFalse(this.model.canMove(2, 7, 4, 5)[1]);
    assertFalse(this.model.canMove(2, 7, 4, 5)[0]);
    this.model.movePiece(7, 4, 3, 4);
    assertTrue(this.model.canMove(2, 5, 4, 3)[1]);
    assertFalse(this.model.canMove(2, 5, 4, 3)[0]);
  }

  @Test
  public void testCanMoveMethod7() {
    this.model.movePiece(0, 7, 3, 6);
    assertFalse(this.model.canMove(2, 5, 4, 7)[1]);
    assertFalse(this.model.canMove(2, 5, 4, 7)[0]);
  }

  @Test
  public void testCanMoveMethod8() {
    this.model.movePiece(7, 0, 4, 3);
    assertFalse(this.model.canMove(5, 2, 3, 4)[1]);
    assertFalse(this.model.canMove(5, 2, 3, 4)[0]);
    assertFalse(this.model.canMove(5, 4, 3, 2)[1]);
    assertFalse(this.model.canMove(5, 4, 3, 2)[0]);
  }

  @Test
  public void testCanMoveKing1() {
    this.model.movePiece(5, 6, 3, 2);
    this.piece = this.model.getPiece(3, 2);
    this.piece.setKing(true);
    assertTrue(this.model.canMove(3, 2, 4, 1)[0]);
    assertTrue(this.model.canMove(3, 2, 4, 3)[0]);
    assertFalse(this.model.canMove(3, 2, 4, 3)[1]);
    assertFalse(this.model.canMove(3, 2, 4, 2)[0]);
  }

  @Test
  public void testCanMoveKing2() {
    this.model.setNextPlayer();
    this.model.movePiece(1, 6, 4, 1);
    this.piece = this.model.getPiece(4, 1);
    this.piece.setKing(true);
    assertTrue(this.model.canMove(4, 1, 3, 0)[0]);
    assertTrue(this.model.canMove(4, 1, 3, 2)[0]);
    assertFalse(this.model.canMove(4, 1, 3, 1)[0]);
    assertFalse(this.model.canMove(4, 1, 3, 2)[1]);
  }

  @Test
  public void testCanMoveKing3() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 8; col++) {
        if (row == 2 && (col == 5 || col == 3)) {
          continue;
        } else {
          this.model.removePiece(row, col);
        }
      }
    }
    this.model.movePiece(7, 2, 1, 4);
    this.piece = this.model.getPiece(1, 4);
    this.piece.setKing(true);
    assertTrue(this.model.canMove(1, 4, 3, 2)[1]);
    assertTrue(this.model.canMove(1, 4, 3, 6)[1]);
    assertFalse(this.model.canMove(1, 4, 3, 1)[1]);
    assertFalse(this.model.canMove(1, 4, 3, 6)[0]);
    assertFalse(this.model.canMove(1, 4, 3, 2)[0]);
  }

  @Test
  public void testCanMoveKing4() {

  }

  @Test
  public void testCanMoveInvalidNumbers() {
    assertFalse(this.model.canMove(2, 4, -1, 5)[0]);
    assertFalse(this.model.canMove(2, 4, 8, 5)[1]);
    assertFalse(this.model.canMove(5, 5, 9, 5)[0]);
    assertFalse(this.model.canMove(5, 5, -3, 5)[1]);
    assertFalse(this.model.canMove(5, 6, 6, 13)[0]);
    assertFalse(this.model.canMove(5, 6, 7, 18)[1]);
    assertFalse(this.model.canMove(5, 6, 2, -3)[0]);
    assertFalse(this.model.canMove(5, 6, 2, -1)[1]);
  }

  @Test
  public void testIsWinner1() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 8; col++) {
        this.model.removePiece(row, col);
      }
    }
    assertTrue(this.model.isWinner(Player.Red));
  }

  @Test
  public void testIsWinner2() {
    this.model.setNextPlayer();
    for (int row = 7; row > 4; row--) {
      for (int col = 0; col < 8; col++) {
        this.model.removePiece(row, col);
      }
    }
    assertTrue(this.model.isWinner(Player.Black));
  }

  @Test
  public void testJumped1() {
    assertEquals(4, this.model.jumped(5, 4, 3, 2)[0]);
    assertEquals(3, this.model.jumped(5, 4, 3, 2)[1]);
  }

  @Test
  public void testJumped2() {
    assertEquals(2, this.model.jumped(3, 2, 1, 4)[0]);
    assertEquals(3, this.model.jumped(3, 2, 1, 4)[1]);
  }

  @Test
  public void testJumped3() {
    assertEquals(2, this.model.jumped(1, 3, 3, 5)[0]);
    assertEquals(4, this.model.jumped(1, 3, 3, 5)[1]);
  }

  @Test
  public void testJumped4() {
    assertEquals(3, this.model.jumped(2, 5, 4, 3)[0]);
    assertEquals(4, this.model.jumped(2, 5, 4, 3)[1]);
  }

  @Test
  public void testCanMoveAnywhere() {
    assertFalse(this.model.canMoveAnywhere(1, 6));
    assertTrue(this.model.canMoveAnywhere(5, 2));
  }

  @Test
  public void testCheckersPieceClass() {
    this.piece = this.model.getPiece(0, 5);
    assertEquals(Player.Red, this.piece.getPlayer());
    this.piece = this.model.getPiece(6, 5);
    assertEquals(Player.Black, this.piece.getPlayer());
  }

  @Test
  public void testSetAndGetPlayer() {
    assertEquals(Player.Black, this.model.getCurrentPlayer());
    this.model.setNextPlayer();
    assertEquals(Player.Red, this.model.getCurrentPlayer());
  }

  @Test
  public void testIsWinner3() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 8; col++) {
        if (row == 2 && col == 7) {
          continue;
        } else {
          this.model.removePiece(row, col);
        }
      }
    }
    this.model.movePiece(2, 7, 0, 7);
    this.model.movePiece(7, 0, 1, 6);
    this.model.movePiece(7, 2, 2, 5);
    assertTrue(this.model.isWinner(Player.Red));
    this.model.movePiece(0, 7, 0, 5);
    this.model.movePiece(2, 5, 1, 4);
    this.model.movePiece(5, 6, 2, 3);
    assertFalse(this.model.isWinner(Player.Red));
  }

  @Test
  public void testIsWinner4() {
    this.model.setNextPlayer();
    for (int row = 7; row > 4; row--) {
      for (int col = 0; col < 8; col++) {
        if (row == 5 && col == 6) {
          continue;
        } else {
          this.model.removePiece(row, col);
        }
      }
    }
    this.model.movePiece(5, 6, 7, 2);
    this.model.movePiece(0, 1, 6, 1);
    this.model.movePiece(0, 3, 5, 0);
    this.model.movePiece(0, 5, 6, 3);
    this.model.movePiece(0, 7, 5, 4);
    assertTrue(this.model.isWinner(Player.Black));
    this.model.movePiece(6, 3, 3, 0);
    assertFalse(this.model.isWinner(Player.Black));
    this.model.movePiece(3, 0, 6, 3);
    this.model.movePiece(5, 0, 3, 0);
    assertFalse(this.model.isWinner(Player.Black));
  }

  @Test
  public void testIsWinner5() {
    assertFalse(this.model.isWinner(Player.Black));
    assertFalse(this.model.isWinner(Player.Red));
  }

  @Test
  public void testIsWinner6() {
    this.model.setNextPlayer();
    assertFalse(this.model.isWinner(Player.Black));
  }

  @Test
  public void testIsWinner7() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 8; col++) {
        if ((row == 2 && col == 7) || (row == 2 && col == 5)) {
          continue;
        } else {
          this.model.removePiece(row, col);
        }
      }
    }
    assertFalse(this.model.isWinner(Player.Red));
  }

  @Test
  public void testIsWinner8() {
    this.model.setNextPlayer();
    for (int row = 7; row > 4; row--) {
      for (int col = 0; col < 8; col++) {
        if ((row == 5 && col == 6) || (row == 5 && col == 4)) {
          continue;
        } else {
          this.model.removePiece(row, col);
        }
      }
    }
    assertFalse(this.model.isWinner(Player.Black));
  }

  @Test
  public void testCanJump1() {
    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < 8; col++) {
        this.model.removePiece(row, col);
      }
    }
    for (int row = 5; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        if (row != 7 || col != 6) {
          this.model.removePiece(row, col);
        }
      }
    }
    this.model.movePiece(7, 6, 6, 5);
    this.model.movePiece(2, 1, 5, 4);
    this.model.movePiece(2, 3, 3, 2);
    this.model.movePiece(2, 5, 3, 4);
    this.model.movePiece(2, 7, 3, 6);
    this.model.getPiece(6, 5).setKing(true);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Black);
    assertEquals(3, (int) loc.get(0).get(3));
    assertEquals(2, (int) loc.get(0).get(4));
    assertEquals(5, (int) loc.get(0).get(5));
    assertEquals(4, (int) loc.get(0).get(6));
    assertEquals(1, loc.size());
  }

  @Test
  public void testCanJump2() {
    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < 8; col++) {
        this.model.removePiece(row, col);
      }
    }
    for (int row = 5; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        if (row != 7 || col != 6) {
          this.model.removePiece(row, col);
        }
      }
    }
    this.model.movePiece(7, 6, 4, 3);
    this.model.movePiece(2, 1, 3, 2);
    this.model.movePiece(2, 3, 3, 4);
    this.model.movePiece(2, 5, 5, 4);
    this.model.movePiece(2, 7, 5, 2);
    this.model.getPiece(4, 3).setKing(true);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Black);
    assertEquals(2, (int) loc.get(0).get(2));
    assertEquals(2, (int) loc.get(1).get(2));
    assertEquals(5, (int) loc.get(2).get(3));
    assertEquals(3, (int) loc.get(3).get(1));
    assertEquals(4, loc.size());
  }

  @Test
  public void testCanJump3() {
    this.model.movePiece(1, 4, 4, 3);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Black);
    assertEquals(3, (int) loc.get(0).get(2));
    assertEquals(4, (int) loc.get(0).get(5));
    assertEquals(2, (int) loc.get(0).get(3));
    assertEquals(1, loc.size());
  }

  @Test
  public void testCanJump4() {
    this.model.movePiece(6, 1, 3, 2);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Red);
    assertEquals(2, (int) loc.get(0).get(0));
    assertEquals(1, (int) loc.get(0).get(1));
    assertEquals(3, (int) loc.get(0).get(3));
    assertEquals(6, (int) loc.get(0).get(4));
    assertEquals(1, (int) loc.get(0).get(5));
    assertEquals(1, loc.size());
  }

  @Test
  public void testCanJump5() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 8; col++) {
        if (row != 0 || col != 1) {
          this.model.removePiece(row, col);
        }
      }
    }
    for (int row = 5; row < 7; row++) {
      for (int col = 0; col < 8; col++) {
        this.model.removePiece(row, col);
      }
    }
    this.model.movePiece(0, 1, 3, 2);
    this.model.movePiece(7, 0, 2, 3);
    this.model.movePiece(7, 2, 4, 1);
    this.model.movePiece(7, 4, 2, 5);
    this.model.movePiece(7, 6, 4, 5);
    this.model.getPiece(3, 2).setKing(true);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Red);
    assertEquals(3, (int) loc.get(0).get(0));
    assertEquals(2, (int) loc.get(0).get(1));
    assertEquals(4, (int) loc.get(0).get(3));
    assertEquals(6, (int) loc.get(0).get(5));
    assertEquals(5, (int) loc.get(0).get(6));
    assertEquals(4, (int) loc.get(0).get(7));
    assertEquals(1, loc.size());
  }

  @Test
  public void canJump6() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 8; col++) {
        if ((row == 2 && col == 5) || (row == 2 && col == 7)) {
          continue;
        } else {
          this.model.removePiece(row, col);
        }
      }
    }
    for (int row = 5; row < 7; row++) {
      for (int col = 0; col < 8; col++) {
        if (row != 6 || col != 7) {
          this.model.removePiece(row, col);
        }
      }
    }
    this.model.movePiece(2, 5, 1, 4);
    this.model.movePiece(2, 7, 5, 6);
    this.model.movePiece(6, 7, 2, 1);
    this.model.movePiece(7, 0, 2, 3);
    this.model.movePiece(7, 2, 2, 5);
    this.model.movePiece(7, 4, 4, 5);
    this.model.movePiece(7, 6, 6, 3);
    this.model.getPiece(5, 6).setKing(true);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Red);
    assertEquals(2, loc.size());
    assertEquals(8, loc.get(0).size());
    assertEquals(8, loc.get(1).size());
    assertEquals(4, (int) loc.get(0).get(1));
    assertEquals(3, (int) loc.get(0).get(2));
    assertEquals(5, (int) loc.get(0).get(4));
    assertEquals(2, (int) loc.get(0).get(7));
    assertEquals(5, (int) loc.get(1).get(0));
    assertEquals(4, (int) loc.get(1).get(3));
    assertEquals(2, (int) loc.get(1).get(5));
    assertEquals(0, (int) loc.get(1).get(7));
  }

  @Test
  public void testCanJump7() {
    this.model.movePiece(5, 4, 4, 3);
    this.model.movePiece(2, 1, 3, 2);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Black);
    assertEquals(1, loc.size());
  }

  @Test
  public void testCanJump8() {
    this.model.removePiece(2, 1);
    this.model.removePiece(6, 3);
    this.model.movePiece(5, 2, 4, 1);
    this.model.movePiece(5, 4, 4, 3);
    this.model.movePiece(2, 3, 3, 2);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Black);
    assertEquals(2, loc.size());
    assertEquals(2, (int) loc.get(0).get(2));
    assertEquals(3, (int) loc.get(0).get(3));
    assertEquals(2, (int) loc.get(1).get(2));
    assertEquals(1, (int) loc.get(1).get(3));
  }

  @Test
  public void testSetPiece() {
    this.model.setPiece(null, 3, 2);
    assertEquals(null, this.model.getPiece(3, 2));
  }

  @Test
  public void testSetPlayer() {
    this.model.setPlayer(Player.Red);
    assertEquals(Player.Red, this.model.getCurrentPlayer());
  }

  @Test
  public void testCanJump9() {
    for (int col = 0; col < 8; col++) {
      this.model.removePiece(5, col);
    }
    this.model.movePiece(6, 3, 3, 2);
    this.model.movePiece(6, 5, 4, 3);
    this.model.getPiece(3, 2).setKing(true);
    for (int col = 0; col < 8; col++) {
      this.model.removePiece(2, col);
    }
    this.model.movePiece(1, 6, 2, 7);
    this.model.movePiece(0, 5, 4, 1);
    this.model.removePiece(1, 0);
    this.model.removePiece(1, 2);
    this.model.removePiece(0, 1);
    ArrayList<ArrayList<Integer>> loc = this.model.canJump(Player.Black);
    assertEquals(1, loc.size());
  }

  @Test
  public void testCheckForMoreJumps1() {
    this.model.movePiece(2, 3, 4, 2);
    this.model.movePiece(5, 6, 3, 3);
    this.model.getPiece(4, 2).setKing(true);
    int[] loc = this.model.checkForMoreJumps(Player.Red, true, 3, 4, 2, 4, 2);
    assertEquals(2, loc[0]);
    assertEquals(4, loc[1]);
  }

  @Test
  public void testCheckForMoreJumps2() {
    this.model.movePiece(2, 3, 4, 3);
    this.model.movePiece(5, 4, 3, 4);
    int[] loc = this.model.checkForMoreJumps(Player.Red, true, 3, 4, 3, 4, 3);
    assertEquals(-1, loc[0]);
    assertEquals(0, loc[1]);
  }
}