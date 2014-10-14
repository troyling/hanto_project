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
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentAJRZL.common.BaseHantoGame;

/**
 * Class for the epsilon hanto game.
 * 
 * @author anthonyjruffa
 * 
 */
public class EpsilonHantoGame extends BaseHantoGame {

	/**
	 * Constructor for delta hanto game
	 * 
	 * @param movesFirst
	 */
	public EpsilonHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		maxPiecesAllowed.put(HantoPieceType.BUTTERFLY, 1);
		maxPiecesAllowed.put(HantoPieceType.SPARROW, 2);
		maxPiecesAllowed.put(HantoPieceType.CRAB, 6);
		maxPiecesAllowed.put(HantoPieceType.HORSE, 4);
		MAX_FLYING_DISTANCE = 4;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isPieceAllowedToFly(HantoPieceType pieceType) {
		return pieceType == HantoPieceType.SPARROW;
	}
	
	/**
	 * {@inheritDoc}
	 */
	protected boolean isPieceAllowedToJump(HantoPieceType pieceType) {
		return pieceType == HantoPieceType.HORSE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result = MoveResult.OK;
		if (isPlayerResigning(pieceType, from, to)) {
			// check for premature resignation
			validateResignation(pieceType, from, to);
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
	 * Check if the player is resigning at the right time. According to the
	 * rule, a player can resign only when 1) the player can't place any
	 * additional piece onto the board 2) moving any of the player's pieces will
	 * result into disconnection
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoPrematureResignationException
	 */
	private void validateResignation(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to)
			throws HantoPrematureResignationException {
		if (isCurrentPlayerAllowedToPlacePiece()
				|| isCurrentPlayerAllowedToMoveAnyPiece()) {
			throw new HantoPrematureResignationException();
		}
	}

}
