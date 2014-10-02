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
import hanto.studentAJRZL.common.HantoPieceCoordinate;

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
		NUM_BUTTERFLY_ALLOWED = 1;
		NUM_SPARROW_ALLOWED = 4;
		NUM_CRAB_ALLOWED = 4;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validateAllowedPieceType(HantoPieceType pieceType)
			throws HantoException {
		if (pieceType != HantoPieceType.BUTTERFLY
				&& pieceType != HantoPieceType.SPARROW
				&& pieceType != HantoPieceType.CRAB) {
			throw new HantoException(
					"The piece you are trying to place is not allowed.");
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getAllowedWalkingDistance() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
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
	protected void preMakeMoveCheck(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException {
		super.preMakeMoveCheck(pieceType, from, to);
		validateCanFly(pieceType, from, to);
	}

	/**
	 * Validate if the flying is valid
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	private void validateCanFly(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		if (from != null && to != null) {
			HantoPieceCoordinate fromCoord = new HantoPieceCoordinate(from);

			// check for flying
			if (fromCoord.getDistanceTo(to) > getAllowedWalkingDistance()
					&& pieceType != HantoPieceType.SPARROW) {
				throw new HantoException(
						"Only sparrow can fly in delta hato game.");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isPieceAllowedToFly(HantoPieceType pieceType) {
		return pieceType == HantoPieceType.SPARROW;
	}

	/**
	 * Check if the current player intends to resign
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @return true if so; false otherwise
	 */
	private boolean isPlayerResigning(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) {
		return pieceType == null && from == null && to == null;
	}

}
