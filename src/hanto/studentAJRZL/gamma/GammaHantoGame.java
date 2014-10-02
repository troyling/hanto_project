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
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentAJRZL.common.BaseHantoGame;
import hanto.studentAJRZL.common.HantoPieceCoordinate;

/**
 * Gamma Hanto Game class.
 * 
 * @author anthonyjruffa
 * 
 */
public class GammaHantoGame extends BaseHantoGame {

	public GammaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		MAX_TURN = 20;
		NUM_BUTTERFLY_ALLOWED = 1;
		NUM_SPARROW_ALLOWED = 5;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preMakeMoveCheck(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		if (numTurns > 1 && from == null) {
			validatePiecePlacedNextToOwnColor(to);
		}
		if (from != null && to != null) {
			validateWalk(from, to);
		}
		super.preMakeMoveCheck(pieceType, from, to);
	}

	/**
	 * Check if the player is placing the piece only next to its own
	 * 
	 * @param to
	 * @throws HantoException
	 */
	private void validatePiecePlacedNextToOwnColor(HantoCoordinate to) throws HantoException {
		HantoPieceCoordinate toCoord = new HantoPieceCoordinate(to);
		for (HantoCoordinate c : toCoord.getAdjacentCoordinates()) {
			if (board.get(c) != null && board.get(c).getColor() != currentPlayerColor) {
				throw new HantoException("You must not place a piece adjacent to other player's.");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validateAllowedPieceType(HantoPieceType pieceType) throws HantoException {
		if (pieceType != HantoPieceType.BUTTERFLY && pieceType != HantoPieceType.SPARROW) {
			throw new HantoException("The piece you are trying to place is not allowed.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getAllowedWalkingDistance() {
		return 1;
	}
}
