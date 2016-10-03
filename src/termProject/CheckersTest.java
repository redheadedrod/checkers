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
	private CheckersDesign design;
	
	/**
	 * Constructor sets values to the instance variables
	 */
	public CheckersTest(){
		this.piece = null;
		this.design = new CheckersDesign();
	}
	
	@Test
	public void testCanMoveMethod1() {
		assertTrue(this.design.canMove(startRow, startCol, endRow, endCol))
	}

}
