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

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentAJRZL.common.BaseHantoGame;

/**
 * Class for the delta hanto game.
 * 
 * @author anthonyjruffa
 * 
 */
public class DeltaHantoGame extends BaseHantoGame {
	private final int MAX_BOARD_SIZE = 18;

	public DeltaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
	}

	@Override
	protected int getMaxNumPiecesOnBoard() {
		return MAX_BOARD_SIZE;
	}

	@Override
	protected void validateAllowedPieceType(HantoPieceType pieceType) throws HantoException {
		if (pieceType != HantoPieceType.BUTTERFLY && pieceType != HantoPieceType.SPARROW
				&& pieceType != HantoPieceType.CRAB) {
			throw new HantoException("The piece you are trying to place is not allowed.");
		}

	}

	@Override
	protected int getAllowedWalkingDistance() {
		return 1;
	}
}
