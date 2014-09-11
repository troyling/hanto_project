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
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentAJRZL.common.HantoGameCoordinate;

import org.junit.Test;

/**
 * Tests for the Alpha version of Hanto.
 * 
 * @author anthonyjruffa
 * 
 */
public class AlphaHantoGameTest {

	HantoGame newAlphaHantoGame = HantoGameFactory.getInstance().makeHantoGame(
			HantoGameID.ALPHA_HANTO);
	HantoCoordinate origin = new HantoGameCoordinate(0, 0);
	HantoPiece pieceAtOrigin = newAlphaHantoGame.getPieceAt(origin);

	@Test
	public void testThatResultIsCorrect() {
		assertEquals(pieceAtOrigin.getColor(), HantoPlayerColor.BLUE);
		assertEquals(pieceAtOrigin.getType(), HantoPieceType.BUTTERFLY);

	}
}