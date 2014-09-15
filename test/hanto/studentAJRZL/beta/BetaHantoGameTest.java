/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was taken at
 * Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.beta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import hanto.HantoGameFactory;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test cases for beta hanto game
 * 
 * @author troyling
 * 
 */
public class BetaHantoGameTest {
	/**
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */

	class TestHantoCoordinate implements HantoCoordinate {
		private final int x, y;

		/**
		 * Constructor for test coordinate
		 * 
		 * @param x
		 * @param y
		 */
		TestHantoCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/*
		 * 
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX() {
			return x;
		}

		/*
		 * 
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY() {
			return y;
		}
	}

	private static HantoGameFactory factory = null;
	private HantoGame betaGame;

	// coordinates for placing pieces on board
	private HantoCoordinate origin = new TestHantoCoordinate(0, 0);
	private HantoCoordinate top = new TestHantoCoordinate(0, 1);
	private HantoCoordinate bottom = new TestHantoCoordinate(0, -1);
	private HantoCoordinate topLeft = new TestHantoCoordinate(-1, 1);
	private HantoCoordinate topRight = new TestHantoCoordinate(1, 0);
	private HantoCoordinate bottomLeft = new TestHantoCoordinate(-1, 0);
	private HantoCoordinate bottomRight = new TestHantoCoordinate(1, -1);

	private HantoCoordinate coord1 = new TestHantoCoordinate(1, 1);
	private HantoCoordinate coord2 = new TestHantoCoordinate(2, 0);
	private HantoCoordinate coord3 = new TestHantoCoordinate(2, -1);
	private HantoCoordinate coord4 = new TestHantoCoordinate(2, -2);
	private HantoCoordinate coord5 = new TestHantoCoordinate(1, -2);

	/**
	 * Initialize the factory instance.
	 */
	@BeforeClass
	public static void initializeClass() {
		factory = HantoGameFactory.getInstance();
	}

	/**
	 * Set up the Hanto game.
	 */
	@Before
	public void setup() {
		betaGame = factory.makeHantoGame(HantoGameID.BETA_HANTO);
	}

	/**
	 * Check to make sure an unfilled printable board is an empty string.
	 */
	@Test
	public void testThatUnfilledBoardIsEmpty() {
		String printedBoard = betaGame.getPrintableBoard();
		assertEquals("", printedBoard);
	}

	/**
	 * Test ensures that the piece is placed at the origin.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testGetPieceAtFunction() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));

		HantoPiece p = betaGame.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
	}

	/**
	 * Attempt to place the first piece at place other than the origin. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testPlaceNotAtOrigin() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1, 1));
	}

	/**
	 * Attempt to move a piece rather than to place. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void attemptToMoveRatherThanPlace() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1),
				new TestHantoCoordinate(0, 0));
	}

	/**
	 * Attempt to place a piece at the nonadjacent tile. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 2));
	}

	/**
	 * Test ensures that red wins by surrounding the blue butterfly.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testRedWinBySurroundingBlueButterfly() throws HantoException {
		MoveResult result = null;

		// place pieces on board
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		betaGame.makeMove(HantoPieceType.SPARROW, null, top);
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottom);
		betaGame.makeMove(HantoPieceType.SPARROW, null, topLeft);
		betaGame.makeMove(HantoPieceType.SPARROW, null, topRight);
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottomLeft);
		result = betaGame.makeMove(HantoPieceType.SPARROW, null, bottomRight);

		assertEquals(MoveResult.RED_WINS, result);
	}

	/**
	 * Blue butterfly is not placed by the end of 4th turn. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testNoBlueButterflyByEndOfFourthTurn() throws HantoException {
		// place pieces on board
		betaGame.makeMove(HantoPieceType.SPARROW, null, origin);
		betaGame.makeMove(HantoPieceType.SPARROW, null, top);
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottom);
		betaGame.makeMove(HantoPieceType.SPARROW, null, topLeft);
		betaGame.makeMove(HantoPieceType.SPARROW, null, topRight);
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottomLeft);
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottomRight);
	}

	/**
	 * Red butterfly is not placed by the end of 4th turn. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testNoRedButterflyByEndOfFourthTurn() throws HantoException {
		betaGame.makeMove(HantoPieceType.SPARROW, null, origin);
		betaGame.makeMove(HantoPieceType.SPARROW, null, top);
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottom);
		betaGame.makeMove(HantoPieceType.SPARROW, null, topLeft);
		betaGame.makeMove(HantoPieceType.SPARROW, null, topRight);
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottomLeft);
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, bottomRight);
		betaGame.makeMove(HantoPieceType.SPARROW, null, coord1);
	}

	/**
	 * Attempt to place the piece at the same coordinate. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlacePieceAtSameCoordinate() throws HantoException {
		betaGame.makeMove(HantoPieceType.SPARROW, null, origin);
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
	}

	/**
	 * Test ensures that game ends in draw since neither of the butterfly is surronded.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testGameEndInDraw() throws HantoException {
		MoveResult result = null;

		// place pieces on board
		// 1st turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, origin);
		betaGame.makeMove(HantoPieceType.SPARROW, null, top);
		// 2nd turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottom);
		betaGame.makeMove(HantoPieceType.SPARROW, null, topLeft);
		// 3rd turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, topRight);
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottomRight);
		// 4th turn
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		// 5th turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, bottomLeft);
		betaGame.makeMove(HantoPieceType.SPARROW, null, coord3);
		// 6th turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, coord4);
		result = betaGame.makeMove(HantoPieceType.SPARROW, null, coord5);

		assertEquals(MoveResult.DRAW, result);
	}

	/**
	 * Test ensures that blue wins by surrounding the red butterfly.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testBlueWinBySurroundingRedButterfly() throws HantoException {
		MoveResult result = null;

		// place pieces on board
		// 1st turn
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
		// 2nd turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -1));
		// 3rd turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, -1));
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -2));
		// 4th turn
		result = betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));

		assertEquals(MoveResult.BLUE_WINS, result);

		// Check to make sure the printable board is working.
		assertNotNull(betaGame.getPrintableBoard());
	}

	/**
	 * Test that ensures the factory creates the game with red moving first
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testRedPlayerMoveFirst() throws HantoException {
		final HantoGame newGame = HantoGameFactory.getInstance().makeHantoGame(
				HantoGameID.BETA_HANTO, HantoPlayerColor.RED);

		newGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		newGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1, 0));

		HantoPiece redButterfly = newGame.getPieceAt(new TestHantoCoordinate(0, 0));
		HantoPiece blueButterfly = newGame.getPieceAt(new TestHantoCoordinate(1, 0));

		assertEquals(HantoPlayerColor.RED, redButterfly.getColor());
		assertEquals(HantoPlayerColor.BLUE, blueButterfly.getColor());
	}

	/**
	 * Attempt to place more than one butterfly for a player
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlaceTwoButterflies() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
	}

	/**
	 * Attempt to place piece after the game ends in draw
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlacePieceAfterGameEnds() throws HantoException {
		final HantoGame newGame = HantoGameFactory.getInstance().makeHantoGame(
				HantoGameID.BETA_HANTO, HantoPlayerColor.RED);

		newGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		newGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -1));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2, 0));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2, -1));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3, 0));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3, -1));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(4, 0));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(4, -1));

		// attempt to place extra piece after game ends
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(5, -1));

	}

	/**
	 * Test ensures that blue wins by surrounding the red butterfly.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlaceExtraPieceAfterBlueWins() throws HantoException {
		// place pieces on board
		// 1st turn
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
		// 2nd turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -1));
		// 3rd turn
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, -1));
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -2));
		// 4th turn - blue wins
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));

		// attempt to place extra piece after game ends
		betaGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -3));
	}

	/**
	 * Ensures that the player colors are all correct
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testPlayerColorsAreAllCorrect() throws HantoException {
		final HantoGame newGame = HantoGameFactory.getInstance().makeHantoGame(
				HantoGameID.BETA_HANTO, HantoPlayerColor.RED);

		MoveResult result = null;

		newGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		newGame.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));

		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));

		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -1));

		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2, 0));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2, -1));

		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3, 0));
		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3, -1));

		newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(4, 0));
		result = newGame.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(4, -1));

		HantoPiece red = newGame.getPieceAt(new TestHantoCoordinate(-1, 1));
		HantoPiece red2 = newGame.getPieceAt(new TestHantoCoordinate(3, 0));

		HantoPiece blue = newGame.getPieceAt(new TestHantoCoordinate(-1, 0));
		HantoPiece blue2 = newGame.getPieceAt(new TestHantoCoordinate(4, -1));

		// game status
		assertEquals(MoveResult.DRAW, result);

		// piece colors
		assertEquals(HantoPlayerColor.RED, red.getColor());
		assertEquals(HantoPlayerColor.RED, red2.getColor());

		assertEquals(HantoPlayerColor.BLUE, blue.getColor());
		assertEquals(HantoPlayerColor.BLUE, blue2.getColor());

	}
	
	/**
	 * Test ensures that blue wins by surrounding the red butterfly.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlaceOtherPices() throws HantoException {
		betaGame.makeMove(HantoPieceType.DOVE, null, origin);
	}
	
	/**
	 * Test ensures that blue wins by surrounding the red butterfly.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptTodoNothing() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, null);
	}

}
