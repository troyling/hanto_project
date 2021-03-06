/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was taken at
 * Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.gamma;

import static org.junit.Assert.assertEquals;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.HantoTestGameFactory;
import hanto.common.MoveResult;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test cases for gamma hanto game
 * 
 * @author troyling
 * 
 */
public class GammaHantoGameTest {
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

	private static HantoTestGameFactory factory;
	private HantoTestGame game;

	@BeforeClass
	public static void init() {
		factory = HantoTestGameFactory.getInstance();
	}

	/**
	 * Initialize fields needed for each test.
	 */
	@Before
	public void setup() {
		game = factory.makeHantoTestGame(HantoGameID.GAMMA_HANTO);
	}

	/**
	 * Check to make sure an unfilled printable board is an empty string.
	 */
	@Test
	public void testThatUnfilledBoardIsEmpty() {
		String printedBoard = game.getPrintableBoard();
		assertEquals("", printedBoard);
	}

	/**
	 * Test if the piece is moved correctly
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testValidMovePiece() throws HantoException {
		// initial test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// both butterflies' move should be valid
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0), new TestHantoCoordinate(1, 0)));
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1), new TestHantoCoordinate(1, 1)));
	}

	/**
	 * Test if the piece is moved incorrectly, causing disconnection
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testInvalidMovePiece() throws HantoException {
		// initial test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[4];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(1, 0));
	}

	/**
	 * Test for red winning the game
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testRedWinsBySurroundingBlueButterfly() throws HantoException {
		// initial test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[9];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 2));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, 1));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, 0));
		initialPieces[8] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, -2));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.RED);

		// both butterflies' move should be valid
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				2, 0), new TestHantoCoordinate(1, 0)));
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				2, -2), new TestHantoCoordinate(1, -2)));
		assertEquals(MoveResult.RED_WINS, game.makeMove(HantoPieceType.SPARROW,
				new TestHantoCoordinate(-1, 2), new TestHantoCoordinate(-1, 1)));
	}

	/**
	 * Test for moving the blue butterfly which would result into disconnection between other pieces
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testBlueButterflyCannotMove() throws HantoException {
		// initial test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[4];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 2));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(0, -1));
	}

	/**
	 * Attempts to move a piece to a null destination. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testMovePieceToNullDestionCoordinate() throws HantoException {
		// init test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0), null);
	}

	/**
	 * Attempts to move a piece to an occupied destination. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testMovePieceToOccupiedDestionCoordinate() throws HantoException {
		// init test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(0, 1));
	}

	/**
	 * Test for two neighbors are being occupied
	 * 
	 * @throws HantoException
	 */

	@Test(expected = HantoException.class)
	public void testAttemptToMoveWhereNeighborsAreOccupied() throws HantoException {
		// init test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[6];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, 1));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, 0));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0), new TestHantoCoordinate(1, 0)));
	}

	/**
	 * Attempts to move a sparrow which in fact is a butterfly
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testMoveWrongPiece() throws HantoException {
		// init test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(1, 0));
	}

	/**
	 * Attempts to move a piece for 2 steps, which is not allowed in gamma.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToMoveTwoSteps() throws HantoException {
		// init test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(1, 1));
	}

	/**
	 * Attempt to move other player's piece
	 * 
	 * @throws HantoException
	 */
	public void testBluePlayerAttemptToMoveRedButterfly() throws HantoException {
		// initial test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1),
				new TestHantoCoordinate(1, 0));
	}

	/**
	 * Call makeMove function without providing a hanto piece type
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToMoveWithoutPieceType() throws HantoException {
		// initial test
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(null, new TestHantoCoordinate(0, 1), new TestHantoCoordinate(1, 0));
	}

//	/**
//	 * Tests for valid attempts to move a piece.
//	 * 
//	 * @throws HantoException
//	 */
//	@Test
//	public void testValidAttemptsForPieceToWalk() throws HantoException {
//		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[8];
//
//		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
//				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
//		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
//				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 1));
//		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
//				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
//		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
//				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 2));
//		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
//				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, -1));
//		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
//				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
//		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
//				HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));
//		initialPieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
//				HantoPieceType.SPARROW, new TestHantoCoordinate(1, 1));
//
//		game.initializeBoard(initialPieces);
//		game.setTurnNumber(5);
//		game.setPlayerMoving(HantoPlayerColor.BLUE);
//
//		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY,
//				new TestHantoCoordinate(0, 0), new TestHantoCoordinate(1, 0)));
//	}

	/**
	 * Tests for invalid attempts to move a piece.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testInvalidAttemptsForPieceToWalk() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[4];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 2));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(1, 0));
	}

	/**
	 * Attempt to move a piece which is not on board
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToMovePieceNotOnBoard() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 1),
				new TestHantoCoordinate(1, 0));
	}

	/**
	 * Test that ensures the board allows making move after both player finish placing pieces on
	 * board
	 * 
	 * @throws HantoException
	 * 
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlaceExtraPiece() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[12];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -2));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 3));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -3));
		initialPieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 4));
		initialPieces[8] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -4));
		initialPieces[9] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 5));
		initialPieces[10] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -5));
		initialPieces[11] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 6));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(7);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -6));
	}

	/**
	 * Attempt to place a crab on board
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlaceCrab() throws HantoException {
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0, 0));
	}

	/**
	 * Check that pieces can only be placed next to their own pieces and not the opponent's
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testPiecePlacementAdjacentToItsOwnPieces() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, -1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
	}

	/**
	 * Check that pieces can only be placed next to their own pieces and not the opponent's
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testPiecePlacementAdjacentToItsOwnPiecesCorrectly() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
	}
}
