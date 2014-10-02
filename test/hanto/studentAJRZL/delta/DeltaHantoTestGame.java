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

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.MoveResult;
import hanto.studentAJRZL.HantoGameFactory;

/**
 * Delta hanto test game class
 * 
 * @author anthonyjruffa
 * 
 */
public class DeltaHantoTestGame implements HantoTestGame {

	private final HantoGame testGame;

	/**
	 * Constructor of the DeltaHantoTestGame
	 */
	public DeltaHantoTestGame(HantoPlayerColor movesFirst) {
		testGame = HantoGameFactory.getInstance()
				.makeHantoGame(HantoGameID.DELTA_HANTO, movesFirst);
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
			try {
				makeMove(p.pieceType, null, p.location);
			} catch (HantoException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
	}
}
