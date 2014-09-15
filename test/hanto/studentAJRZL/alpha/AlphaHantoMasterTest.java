/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was taken at
 * Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.alpha;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import org.junit.Test;

/**
 * Test for alpha hanto class imported from Blackboard posted by Professor
 * Pollice.
 * 
 * @author troyling
 * 
 */
public class AlphaHantoMasterTest

{

	/**
	 * 
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */

	class TestHantoCoordinate implements HantoCoordinate

	{

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

	private static final HantoPieceType BUTTERFLY = HantoPieceType.BUTTERFLY;
	private static final HantoPieceType SPARROW = HantoPieceType.SPARROW;
	private static final Object OK = MoveResult.OK;
	private static HantoGameFactory factory = HantoGameFactory.getInstance();
	private HantoGame game;

	/**
	 * Create alpha hanto game
	 */
	@Before
	public void setup() {
		game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
	}

	/**
	 * Test if the game is an instance of the alpha hanto game
	 */
	@Test
	public void getAnAlphaHantoGameFromTheFactory() {
		assertTrue(game instanceof AlphaHantoGame);
	}

	/**
	 * Test if player in blue makes the first move
	 * 
	 * @throws HantoException
	 */
	@Test
	public void blueMakesValidFirstMove() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null,
				new TestHantoCoordinate(0, 0));
		assertEquals(OK, mr);
	}

	/**
	 * Test if the butterfly is at the origin after first move
	 * 
	 * @throws HantoException
	 */
	@Test
	public void afterFirstMoveBlueButterflyIsAt0_0() throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
	}

	/**
	 * Attempt to place sparrow instead of the butterfly at the origin.
	 * Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void bluePlacesNonButterfly() throws HantoException {
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 0));
	}

	/**
	 * Test to ensure that the red butterfly is placed
	 * 
	 * @throws HantoException
	 */
	@Test
	public void redPlacesButterflyNextToBlueButterfly() throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 1));
		assertEquals(BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.RED, p.getColor());
	}

	/**
	 * Player in blue attempts to place butterfly at place other than origin.
	 * Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void blueAttemptsToPlaceButterflyAtWrongLocation()
			throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
	}

	/**
	 * Ensures that the game ends in draw after the second move
	 * 
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidSecondMoveAndGameIsDrawn() throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(BUTTERFLY, null,
				new TestHantoCoordinate(-1, 1));
		assertEquals(MoveResult.DRAW, mr);
	}

	/**
	 * Attempt to place the second piece at the nonadjacent tile. Exception is
	 * expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly()
			throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 2));
	}

	/**
	 * Attempt to move rather than to place a piece. Exception is expected.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void attemptToMoveRatherThanPlace() throws HantoException {
		game.makeMove(BUTTERFLY, new TestHantoCoordinate(0, 1),
				new TestHantoCoordinate(0, 0));
	}

}