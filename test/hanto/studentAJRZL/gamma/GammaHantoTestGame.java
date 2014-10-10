/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.MoveResult;
import hanto.studentAJRZL.HantoGameFactory;

/**
 * Class for the gamma hanto test game.
 * 
 * @author anthonyjruffa
 * 
 */
public class GammaHantoTestGame implements HantoTestGame {

	private final GammaHantoGame testGame;

	/**
	 * Constructor of the GammaHantoTestGame
	 */
	public GammaHantoTestGame(HantoPlayerColor movesFirst) {
		testGame = (GammaHantoGame) HantoGameFactory.getInstance().makeHantoGame(
				HantoGameID.GAMMA_HANTO, movesFirst);
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		return testGame.makeMove(pieceType, from, to);
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return testGame.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		return testGame.getPrintableBoard();
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		for (PieceLocationPair p : initialPieces) {
			testGame.placeHantoPieceOnBoard(p.pieceType, p.player, p.location);
			//makeMove(p.pieceType, null, p.location);
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
		testGame.setTurnNumber(turnNumber);
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		testGame.setCurrentPlayerColor(player);
	}
}
