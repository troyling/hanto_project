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
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentAJRZL.common.BaseHantoGame;

/**
 * Class for the delta hanto game.
 * 
 * @author anthonyjruffa
 * 
 */
public class DeltaHantoGame extends BaseHantoGame {

	/**
	 * Constructor for delta hanto game
	 * 
	 * @param movesFirst
	 */
	public DeltaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		maxPiecesAllowed.put(HantoPieceType.BUTTERFLY, 1);
		maxPiecesAllowed.put(HantoPieceType.SPARROW, 4);
		maxPiecesAllowed.put(HantoPieceType.CRAB, 4);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		MoveResult result = MoveResult.OK;
		if (isPlayerResigning(pieceType, from, to)) {
			if (currentPlayerColor == HantoPlayerColor.BLUE) {
				result = MoveResult.RED_WINS;
			} else {
				result = MoveResult.BLUE_WINS;
			}
		} else {
			result = super.makeMove(pieceType, from, to);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preMakeMoveCheck(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		super.preMakeMoveCheck(pieceType, from, to);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isPieceAllowedToFly(HantoPieceType pieceType) {
		return pieceType == HantoPieceType.SPARROW;
	}
}
