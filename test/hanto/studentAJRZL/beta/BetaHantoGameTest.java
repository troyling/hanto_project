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
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.studentAJRZL.common.HantoPieceCoordinate;

import org.junit.Test;

public class BetaHantoGameTest {
	
	private HantoGame betaGame = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.BETA_HANTO);
	
	// coordinates for placing pieces on board
	private HantoCoordinate origin = new HantoPieceCoordinate(0, 0);
	private HantoCoordinate top = new HantoPieceCoordinate(0, 1);
	private HantoCoordinate bottom = new HantoPieceCoordinate(0, -1);
	private HantoCoordinate topLeft = new HantoPieceCoordinate(-1, 1);
	private HantoCoordinate topRight = new HantoPieceCoordinate(1, 0);
	private HantoCoordinate bottomLeft = new HantoPieceCoordinate(-1, 0);
	private HantoCoordinate bottomRight = new HantoPieceCoordinate(1, -1);
	
	private HantoCoordinate coord1 = new HantoPieceCoordinate(1, 1);
	private HantoCoordinate coord2 = new HantoPieceCoordinate(2, 0);
	private HantoCoordinate coord3 = new HantoPieceCoordinate(2, -1);
	private HantoCoordinate coord4 = new HantoPieceCoordinate(2, -2);
	private HantoCoordinate coord5 = new HantoPieceCoordinate(1, -2);
	
	@Test
	public void testRedWinBySurroundingBlueButterfly() {
		MoveResult result = null;
		
		try {
			// place pieces on board
			betaGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
			betaGame.makeMove(HantoPieceType.SPARROW, null, top);
			betaGame.makeMove(HantoPieceType.SPARROW, null, bottom);
			betaGame.makeMove(HantoPieceType.SPARROW, null, topLeft);
			betaGame.makeMove(HantoPieceType.SPARROW, null, topRight);
			betaGame.makeMove(HantoPieceType.SPARROW, null, bottomLeft);
			result = betaGame.makeMove(HantoPieceType.SPARROW, null, bottomRight);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		
		assertEquals(result, MoveResult.RED_WINS);
	}
	
	@Test
	public void testGameEndInDraw() {
		MoveResult result = null;
		
		try {
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
			
		} catch (HantoException e) {
			e.printStackTrace();
		}
		
		assertEquals(result, MoveResult.DRAW);
	}
	
	
}
