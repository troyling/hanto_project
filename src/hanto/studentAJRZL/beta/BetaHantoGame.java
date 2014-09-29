/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.beta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentAJRZL.common.BaseHantoGame;

/**
 * Class for Beta hanto game
 * 
 * @author troyling
 * 
 */
public class BetaHantoGame extends BaseHantoGame {
	private final int MAX_BOARD_SIZE = 12;

	/**
	 * Constructor for beta hanto game
	 * 
	 * @param movesFirst
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getMaxBoardSize() {
		return MAX_BOARD_SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preMakeMoveCheck(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException {
		// ensure player is not placing other piece type onto the board
		validatePieceType(pieceType);

		// piece can only be placed, not moved
		validateMove(from);

		// check if butterfly is placed
		validateBufferflyPresence(pieceType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getAllowedWalkingDistance() {
		return 0;
	}

	/**
	 * Throws exception if the player attempts to place a piece other than
	 * butterfly or sparrow
	 * 
	 * @throws HantoException
	 */
	private void validatePieceType(HantoPieceType pieceType)
			throws HantoException {
		if (pieceType != HantoPieceType.BUTTERFLY
				&& pieceType != HantoPieceType.SPARROW) {
			throw new HantoException(
					"Can't place piece other than butterfly or sparrow.");
		}
	}

	/**
	 * Throw exception if butterfly is not placed by the end of 4th turn.
	 * 
	 * @param pieceType
	 * @throws HantoException
	 */
	private void validateBufferflyPresence(HantoPieceType pieceType)
			throws HantoException {
		if ((board.size() == 6 || board.size() == 7)
				&& pieceType != HantoPieceType.BUTTERFLY) {
			if ((currentPlayColor == HantoPlayerColor.BLUE && blueButterflyCoordiate == null)
					|| (currentPlayColor == HantoPlayerColor.RED && redButterflyCoordiate == null)) {
				throw new HantoException(
						"Butterfly must be placed by 4th turn.");
			}
		}
	}

	/**
	 * Throws exception if the move intends to move rather than to place a piece
	 * 
	 * @param from
	 * @throws HantoException
	 */
	private void validateMove(HantoCoordinate from) throws HantoException {
		if (from != null) {
			throw new HantoException("Can't move a piece in Beta hanto.");
		}
	}
}
