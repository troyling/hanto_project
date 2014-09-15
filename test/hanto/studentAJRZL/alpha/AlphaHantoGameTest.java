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
import hanto.studentAJRZL.common.HantoPieceCoordinate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for the Alpha version of Hanto.
 * 
 * @author anthonyjruffa
 * 
 */
public class AlphaHantoGameTest {

	/**
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */

	class TestHantoCoordinate implements HantoCoordinate {
		private final int x, y;

		/**
		 * Constructor for the test class.
		 * 
		 * @param x the x coordinate
		 * @param y the y coordinate
		 */
		TestHantoCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/*
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

	private HantoGame game;

	private ExpectedException exception = ExpectedException.none();

	private HantoGame newAlphaHantoGame = HantoGameFactory.getInstance().makeHantoGame(
			HantoGameID.ALPHA_HANTO);
	private HantoCoordinate origin = new HantoPieceCoordinate(0, 0);

	// Adjacent coordinates to origin.
	private HantoCoordinate top = new HantoPieceCoordinate(0, 1);
	private HantoCoordinate bottom = new HantoPieceCoordinate(0, -1);
	private HantoCoordinate topLeft = new HantoPieceCoordinate(-1, 1);
	private HantoCoordinate topRight = new HantoPieceCoordinate(1, 0);
	private HantoCoordinate bottomLeft = new HantoPieceCoordinate(-1, 0);
	private HantoCoordinate bottomRight = new HantoPieceCoordinate(1, -1);

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
		game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the bottom space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtBottomIsCorrect() throws HantoException {
		MoveResult firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);
		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);
		MoveResult nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, bottom);
		assertEquals(newAlphaHantoGame.getPieceAt(bottom).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(bottom).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the top space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtTopIsCorrect() throws HantoException {
		MoveResult firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);
		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);
		MoveResult nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, top);
		assertEquals(newAlphaHantoGame.getPieceAt(top).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(top).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the bottom right space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtBottomRightIsCorrect() throws HantoException {
		MoveResult firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);
		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);
		MoveResult nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null,
				bottomRight);
		assertEquals(newAlphaHantoGame.getPieceAt(bottomRight).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(bottomRight).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the top right space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtTopRightIsCorrect() throws HantoException {
		MoveResult firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);
		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);
		MoveResult nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, topRight);
		assertEquals(newAlphaHantoGame.getPieceAt(topRight).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(topRight).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the top left space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtTopLeftIsCorrect() throws HantoException {
		MoveResult firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);
		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);
		MoveResult nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, topLeft);
		assertEquals(newAlphaHantoGame.getPieceAt(topLeft).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(topLeft).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the bottom left space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtBottomLeftIsCorrect() throws HantoException {
		MoveResult firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		MoveResult nextMove = newAlphaHantoGame
				.makeMove(HantoPieceType.BUTTERFLY, null, bottomLeft);
		assertEquals(newAlphaHantoGame.getPieceAt(bottomLeft).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(bottomLeft).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that an illegal red move throws an exception.
	 */
	@Test(expected = HantoException.class)
	public void testThatIllegalRedMoveThrowsException() throws HantoException {
		MoveResult firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);
		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);
		newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, new HantoPieceCoordinate(1, 1));
	}

	/**
	 * Test that an illegal red move throws an exception.
	 */
	@Test(expected = HantoException.class)
	public void testThatIllegalBlueMoveThrowsException() throws HantoException {
		newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, bottom);
	}

	/**
	 * Test that using an illegal piece on the first move throws an exception.
	 */
	@Test
	public void testThatFirstIllegalPieceThrowsException() {
		try {
			newAlphaHantoGame.makeMove(HantoPieceType.SPARROW, null, origin);
		} catch (HantoException e) {
			exception.expectMessage("Invalid piece.");
		}
	}

	/**
	 * Test that using an illegal piece on the second move throws an exception.
	 */
	@Test(expected = HantoException.class)
	public void testThatIllegalPieceThrowsException() throws HantoException {
		MoveResult firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);
		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);
		newAlphaHantoGame.makeMove(HantoPieceType.SPARROW, null, top);
	}

	/**
	 * Sanity check on the alpha hanto game initialization.
	 */
	@Test
	public void getAnAlphaHantoGameFromTheFactory() {
		assertTrue(game instanceof AlphaHantoGame);
	}

	/**
	 * Check that blue makes a valid first move.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void blueMakesValidFirstMove() throws HantoException {
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null,
				new TestHantoCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
	}

	/**
	 * Check that the blue butterfly is at the origin after it moves.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void afterFirstMoveBlueButterflyIsAt0_0() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
	}

	/**
	 * Check that blue can only place a butterfly.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void bluePlacesNonButterfly() throws HantoException {
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
	}

	/**
	 * Check that red places its butterfly adjacent to the blue one.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void redPlacesButterflyNextToBlueButterfly() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 1));
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.RED, p.getColor());
	}

	/**
	 * Make sure blue can only place the butterfly at the origin.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void blueAttemptsToPlaceButterflyAtWrongLocation() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
	}

	/**
	 * Check to make sure red makes a valid second move and the game is a draw.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidSecondMoveAndGameIsDrawn() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null,
				new TestHantoCoordinate(-1, 1));
		assertEquals(MoveResult.DRAW, mr);
	}

	/**
	 * Check to make sure red can only place the buttefly adjacent to the blue one.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 2));
	}

	/**
	 * Check to make sure you cannot move, but only place.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void attemptToMoveRatherThanPlace() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1),
				new TestHantoCoordinate(0, 0));
	}
}