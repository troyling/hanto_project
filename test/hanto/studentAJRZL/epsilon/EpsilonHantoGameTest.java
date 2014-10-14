/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.epsilon;

import static org.junit.Assert.assertEquals;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.HantoTestGame;
import hanto.common.HantoTestGameFactory;
import hanto.common.MoveResult;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the epsilon hanto game.
 * 
 * @author anthonyjruffa
 * 
 */
public class EpsilonHantoGameTest {

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
	 * Initialize the game instance before each test.
	 */
	@Before
	public void setup() {
		game = factory.makeHantoTestGame(HantoGameID.EPSILON_HANTO);
	}

	/**
	 * Test for a valid horse jump
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testValidHorseJumpAttempts() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, -1),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, 0, 2),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, -2)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.RED);

		assertEquals(MoveResult.OK,
				game.makeMove(HantoPieceType.HORSE, makeCoordinate(0, 2), makeCoordinate(0, -3)));
	}
	
	/**
	 * Test for a valid horse jump
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testValidHorseVertialJumpAttempts() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 1, 0),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 2, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, -1, 0),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, -2, 0)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK,
				game.makeMove(HantoPieceType.HORSE, makeCoordinate(-2, 0), makeCoordinate(3, 0)));
	}

	/**
	 * Test for an invalid horse jump
	 * 
	 * @throws HantoException
	 * 
	 */
	@Test(expected = HantoException.class)
	public void testInvalidHorseJumpAttempts() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, -1),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, 0, 2),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, -2)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.RED);

		game.makeMove(HantoPieceType.HORSE, makeCoordinate(0, 2), makeCoordinate(1, -2));
	}

	/**
	 * Test that piece movements walk within the limit
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testThatPieceMovementIsWithinLimit() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, 0, -1),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 1, 0),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, -1, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 1, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, -2),
				plPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, -1, 1)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(8);
		game.setPlayerMoving(HantoPlayerColor.RED);

		assertEquals(MoveResult.OK,
				game.makeMove(HantoPieceType.CRAB, makeCoordinate(1, 1), makeCoordinate(2, 0))); // crab
																								 // walking
		assertEquals(MoveResult.OK,
				game.makeMove(HantoPieceType.SPARROW, makeCoordinate(-1, 0), makeCoordinate(2, -1))); // sparrow
																									  // flying
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(-1, 1),
				makeCoordinate(-1, 2))); // butterfly
										 // walking
		assertEquals(MoveResult.OK,
				game.makeMove(HantoPieceType.HORSE, makeCoordinate(0, -2), makeCoordinate(0, 2))); // horse
																								   // jumping
	}

	/**
	 * Test that walking butterfly with a distance large than allowed exception
	 */
	@Test(expected = HantoException.class)
	public void testThatButterflyExceedingWalkingLimitIsNotAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, 0, 2)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 1));
	}

	/**
	 * Test that walking crab with a distance large than allowed exception
	 */
	@Test(expected = HantoException.class)
	public void testThatCrabExceedingWalkingLimitIsNotAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, 0, 2)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.CRAB, makeCoordinate(0, 0), makeCoordinate(1, 1));
	}

	/**
	 * Test that flying pieces that do not walk within the limit throw an exception
	 */
	@Test(expected = HantoException.class)
	public void testThatExceedingFlyingLimitIsNotAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 2),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, -1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, -2),
				plPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, 1, -3)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.RED);

		game.makeMove(HantoPieceType.SPARROW, makeCoordinate(1, -3),
				makeCoordinate(0, 3));
	}

	/**
	 * Test that jumping pieces that do not walk within the limit throw an exception
	 */
	@Test(expected = HantoException.class)
	public void testThatExceedingJumpingLimitIsNotAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 2),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, -1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, -2),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, 0, -3)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.HORSE, makeCoordinate(0, -3), makeCoordinate(0, 3));
	}

	/**
	 * Test that an invalid resignation attempt throws the proper exception
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoPrematureResignationException.class)
	public void testInvalidResignationAttempt() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 2),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, -1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, -2),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, 0, -3)
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(null, null, null);
	}
	
	/**
	 * Test that an invalid resignation attempt throws the proper exception
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoPrematureResignationException.class)
	public void testPrematureResignationAttemptWithNoPieceToPlace() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, -1, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 2),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 3),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 4),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 5),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 6),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, 0, 7),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, 0, 8),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, 9),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, 10),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, 11),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, 12),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 1, 0),
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(25);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		game.makeMove(null, null, null);

	}

	/**
	 * Test that a valid resignation attempt will work properly
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testValidResignationAttempt() throws HantoException {
		// Each allowed up to 6 crabs, 4 horses, 2 sparrows, and 1 butterfly
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, 0, 0),
				plPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, 0, -1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 2),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 3),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 4),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 5),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 6),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, 0, 7),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, 0, 8),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, 9),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, 10),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, 11),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.HORSE, 0, 12),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 13),
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(25);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		assertEquals(MoveResult.RED_WINS, game.makeMove(null, null, null));
	}

	/**
	 * Test that a valid resignation attempt will work properly for red player resigning
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testRedPlayerValidResignationAttempt() throws HantoException {
		// Each allowed up to 6 crabs, 4 horses, 2 sparrows, and 1 butterfly
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[] {
				plPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, 0, 0),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, 0, -1),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 1),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 2),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 3),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 4),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 5),
				plPair(HantoPlayerColor.RED, HantoPieceType.CRAB, 0, 6),
				plPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, 0, 7),
				plPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, 0, 8),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, 0, 9),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, 0, 10),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, 0, 11),
				plPair(HantoPlayerColor.RED, HantoPieceType.HORSE, 0, 12),
				plPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, 0, 13),
		};
		game.initializeBoard(initialPieces);
		game.setTurnNumber(25);
		game.setPlayerMoving(HantoPlayerColor.RED);
		assertEquals(MoveResult.BLUE_WINS, game.makeMove(null, null, null));
	}

	// Helper methods
	/**
	 * Make a test coordinate for testing.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the new test coordinate
	 */
	private HantoCoordinate makeCoordinate(int x, int y) {
		return new TestHantoCoordinate(x, y);
	}

	/**
	 * Factory method to create a piece location pair.
	 * 
	 * @param player the player color
	 * @param pieceType the piece type
	 * @param x starting location
	 * @param y end location
	 * @return
	 */
	private HantoTestGame.PieceLocationPair plPair(HantoPlayerColor player,
			HantoPieceType pieceType, int x, int y) {
		return new HantoTestGame.PieceLocationPair(player, pieceType, new TestHantoCoordinate(x, y));
	}
}
