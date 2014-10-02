/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.delta;

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
 * Tests for delta hanto
 * 
 * @author anthonyjruffa
 * 
 */

public class DeltaHantoGameTest {
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
	 * Initialize fields for each test.
	 */
	@Before
	public void setup() {
		game = factory.makeHantoTestGame(HantoGameID.DELTA_HANTO);
	}


	/**
	 * Test that attempts to make crab fly
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToFlyCrab() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.CRAB,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.CRAB,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// making moves
		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(0, 2));
	}

	/**
	 * Test that validly flies a sparrow
	 */
	@Test
	public void testValidlyFlySparrow() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// making moves
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 0), new TestHantoCoordinate(0, 2)));

	}

	/**
	 * Test for blue player resigning
	 */
	@Test
	public void testBluePlayerResign() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// making moves
		assertEquals(MoveResult.RED_WINS, game.makeMove(null, null, null));
	}

	/**
	 * Test for red player resigning
	 */
	@Test
	public void testRedPlayerResign() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.CRAB,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.CRAB,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
	}

}
