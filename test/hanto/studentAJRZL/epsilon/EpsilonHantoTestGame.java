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
 * Class for the epsilon hanto test game
 * 
 * @author anthonyjruffa
 * 
 */
public class EpsilonHantoTestGame implements HantoTestGame {

	private final EpsilonHantoGame testGame;

	/**
	 * Constructor for the test game
	 * 
	 * @param movesFirst the color to go first
	 */
	public EpsilonHantoTestGame(HantoPlayerColor movesFirst) {
		testGame = (EpsilonHantoGame) HantoGameFactory.getInstance().makeHantoGame(
				HantoGameID.EPSILON_HANTO, movesFirst);
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
