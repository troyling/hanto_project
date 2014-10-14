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

	/**
	 * Constructor for beta hanto game
	 * 
	 * @param movesFirst
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		MAX_TURN = 6;
		maxPiecesAllowed.put(HantoPieceType.BUTTERFLY, 1);
		maxPiecesAllowed.put(HantoPieceType.SPARROW, 5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preMakeMoveCheck(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException {
		validateMove(from);
		validateBufferflyPresence(pieceType);
		super.preMakeMoveCheck(pieceType, from, to);
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
			if ((currentPlayerColor == HantoPlayerColor.BLUE && blueButterflyCoordinate == null)
					|| (currentPlayerColor == HantoPlayerColor.RED && redButterflyCoordinate == null)) {
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
