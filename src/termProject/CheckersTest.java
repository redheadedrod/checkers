package termProject;

import static org.junit.Assert.*;

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
	public void testCanMoveMethod1() {
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
	public void testCanMoveMethod7(){
		this.model.movePiece(0, 7, 3, 6);
		assertFalse(this.model.canMove(2, 5, 4, 7)[1]);
		assertFalse(this.model.canMove(2, 5, 4, 7)[0]);
	}
	
	@Test
	public void testCanMoveMethod8(){
		this.model.movePiece(7, 0, 4, 3);
		assertFalse(this.model.canMove(5, 2, 3, 4)[1]);
		assertFalse(this.model.canMove(5, 2, 3, 4)[0]);
		assertFalse(this.model.canMove(5, 4, 3, 2)[1]);
		assertFalse(this.model.canMove(5, 4, 3, 2)[0]);
	}

	@Test
	public void testIsWinner1() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 8; col++) {
				this.model.removePiece(row, col);
			}
		}
		assertTrue(this.model.isWinner());
	}

	@Test
	public void testIsWinner2() {
		this.model.setCurrentPlayer();
		for (int row = 7; row > 4; row--) {
			for (int col = 0; col < 8; col++) {
				this.model.removePiece(row, col);
			}
		}
		assertTrue(this.model.isWinner());
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
		this.model.setCurrentPlayer();
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
		assertTrue(this.model.isWinner());
		this.model.movePiece(0, 7, 0, 5);
		this.model.movePiece(2, 5, 1, 4);
		this.model.movePiece(5, 6, 2, 3);
		assertFalse(this.model.isWinner());
	}

	@Test
	public void testIsWinner4() {
		this.model.setCurrentPlayer();
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
		assertTrue(this.model.isWinner());
		this.model.movePiece(6, 3, 3, 0);
		assertFalse(this.model.isWinner());
		this.model.movePiece(3, 0, 6, 3);
		this.model.movePiece(5, 0, 3, 0);
		assertFalse(this.model.isWinner());
	}

	@Test
	public void testIsWinner5() {
		assertFalse(this.model.isWinner());
	}

	@Test
	public void testIsWinner6() {
		this.model.setCurrentPlayer();
		assertFalse(this.model.isWinner());
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
		assertFalse(this.model.isWinner());
	}
	
	@Test
	public void testIsWinner8(){
		this.model.setCurrentPlayer();
		for (int row = 7; row > 4; row--) {
			for (int col = 0; col < 8; col++) {
				if ((row == 5 && col == 6) || (row==5 && col==4)) {
					continue;
				} else {
					this.model.removePiece(row, col);
				}
			}
		}
		assertFalse(this.model.isWinner());
	}
}
