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

/**
 * Tests for the Alpha version of Hanto.
 * 
 * @author anthonyjruffa
 * 
 */
public class AlphaHantoGameTest {

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
	 * Test that the Alpha Hanto results are correct.
	 */
	@Test
	public void testThatResultIsCorrect() {

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

		assertEquals(nextMove, MoveResult.DRAW);
	}
}