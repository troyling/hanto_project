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

import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for the Alpha version of Hanto.
 * 
 * @author anthonyjruffa
 * 
 */
public class AlphaHantoGameTest {

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
	 * Test that the Alpha Hanto results are correct for the bottom space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtBottomIsCorrect() {
		MoveResult firstMove = null;
		try {
			firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		MoveResult nextMove = null;
		try {
			nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, bottom);
		} catch (HantoException e) {
			e.printStackTrace();
		}

		assertEquals(newAlphaHantoGame.getPieceAt(bottom).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(bottom).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the top space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtTopIsCorrect() {
		MoveResult firstMove = null;
		try {
			firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		MoveResult nextMove = null;
		try {
			nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, top);
		} catch (HantoException e) {
			e.printStackTrace();
		}

		assertEquals(newAlphaHantoGame.getPieceAt(top).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(top).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the bottom right space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtBottomRightIsCorrect() {
		MoveResult firstMove = null;
		try {
			firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		MoveResult nextMove = null;
		try {
			nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, bottomRight);
		} catch (HantoException e) {
			e.printStackTrace();
		}

		assertEquals(newAlphaHantoGame.getPieceAt(bottomRight).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(bottomRight).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the top right space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtTopRightIsCorrect() {
		MoveResult firstMove = null;
		try {
			firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		MoveResult nextMove = null;
		try {
			nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, topRight);
		} catch (HantoException e) {
			e.printStackTrace();
		}

		assertEquals(newAlphaHantoGame.getPieceAt(topRight).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(topRight).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the top left space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtTopLeftIsCorrect() {
		MoveResult firstMove = null;
		try {
			firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		MoveResult nextMove = null;
		try {
			nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, topLeft);
		} catch (HantoException e) {
			e.printStackTrace();
		}

		assertEquals(newAlphaHantoGame.getPieceAt(topLeft).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(topLeft).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that the Alpha Hanto results are correct for the bottom left space from the origin.
	 */
	@Test
	public void testThatSecondMoveAtBottomLeftIsCorrect() {
		MoveResult firstMove = null;
		try {
			firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		MoveResult nextMove = null;
		try {
			nextMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, bottomLeft);
		} catch (HantoException e) {
			e.printStackTrace();
		}

		assertEquals(newAlphaHantoGame.getPieceAt(bottomLeft).getColor(), HantoPlayerColor.RED);
		assertEquals(newAlphaHantoGame.getPieceAt(bottomLeft).getType(), HantoPieceType.BUTTERFLY);
		assertEquals(nextMove, MoveResult.DRAW);
	}

	/**
	 * Test that an illegal move throws an exception.
	 */
	@Test
	public void testThatIllegalMoveThrowsException() {
		MoveResult firstMove = null;
		try {
			firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		try {
			newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, new HantoPieceCoordinate(1,
					1));
		} catch (HantoException e) {
			exception.expectMessage("Invalid move.");
		}
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
	@Test
	public void testThatIllegalPieceThrowsException() {
		MoveResult firstMove = null;
		try {
			firstMove = newAlphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);
		assertEquals(firstMove, MoveResult.OK);

		try {
			newAlphaHantoGame.makeMove(HantoPieceType.SPARROW, null, top);
		} catch (HantoException e) {
			exception.expectMessage("Invalid piece.");
		}
	}
}