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

	// Helper methods
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
