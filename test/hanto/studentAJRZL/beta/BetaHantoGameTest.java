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
import hanto.HantoGameFactory;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import org.junit.Test;

public class BetaHantoGameTest {
	/**
	 * 
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */

	class TestHantoCoordinate implements HantoCoordinate

	{

		private final int x, y;

		public TestHantoCoordinate(int x, int y) {
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

	private HantoGame betaGame = HantoGameFactory.getInstance().makeHantoGame(
			HantoGameID.BETA_HANTO);

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

	@Test
	public void testGetPieceAtFunction() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null,
				new TestHantoCoordinate(0, 0));

		HantoPiece p = betaGame.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
	}

	@Test(expected = HantoException.class)
	public void testPlaceNotAtOrigin() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null,
				new TestHantoCoordinate(1, 1));
	}

	@Test(expected = HantoException.class)
	public void attemptToMoveRatherThanPlace() throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0,
				1), new TestHantoCoordinate(0, 0));
	}

	@Test(expected = HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly()
			throws HantoException {
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null,
				new TestHantoCoordinate(0, 0));
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null,
				new TestHantoCoordinate(0, 2));
	}

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

	@Test(expected = HantoException.class)
	public void testAttemptToPlacePieceAtSameCoordinate() throws HantoException {
		betaGame.makeMove(HantoPieceType.SPARROW, null, origin);
		betaGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
	}

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
	}

}
